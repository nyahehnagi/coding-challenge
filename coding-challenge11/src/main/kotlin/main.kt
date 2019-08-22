package cc11

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.*
import java.io.File


fun main(args: Array<String>) {

    val jsonFile = File("src/main/resources/pubjson.json")
    val pubList: List<Pub> = deserialisePubsJSON(jsonFile)
    val beerList: List<Beer> = obtainListOfBeers(pubList)

}

private fun obtainListOfBeers(pubList: List<Pub>): List<Beer> {
    val beerList = mutableListOf<Beer>()

    pubList.forEach {
        val pubName = it.name
        val pubService = it.pubService

        if (!it.regularBeers.isNullOrEmpty()) {

            it.regularBeers.forEach {
                val newBeer = Beer(
                    beerName = it,
                    pubName = pubName,
                    pubService = pubService,
                    beerType = BeerType.REGULARBEER
                )
                beerList.add(newBeer)
            }
        }
        if (!it.guestBeers.isNullOrEmpty()) {
            it.guestBeers.forEach {
                val newBeer = Beer(
                    beerName = it,
                    pubName = pubName,
                    pubService = pubService,
                    beerType = BeerType.GUESTBEER
                )
                beerList.add(newBeer)
            }
        }
    }
    beerList.sortBy { it.beerName }

    return beerList
}


private fun deserialisePubsJSON(jsonFile: File): List<Pub> {

    val mapper = jacksonObjectMapper()

    val jsonNode: JsonNode = mapper.readTree(jsonFile)
    val pubsNode: JsonNode = jsonNode.path("Pubs")

    val pubList: MutableList<Pub> = mutableListOf()
    pubsNode.forEach() {
        val pub = Pub(
            name = it.get("Name").textValue(),
            postCode = it.get("PostCode").textValue(),
            regularBeers = getBeersFromJSON(it.path("RegularBeers")),
            guestBeers = getBeersFromJSON(it.path("GuestBeers")),
            pubService = it.get("PubService").textValue(),
            id = it.get("Id").textValue(),
            branch = it.get("Branch").textValue(),
            createTS = it.get("CreateTS").textValue()
        )
        pubList.add(pub)
    }

    // sort and removed duplicates
    return pubList.sortedWith(compareBy<Pub> { it.id }.thenBy { it.branch }.thenByDescending { it.createTS })
        .distinctBy { Pair(it.id, it.branch) }

}

fun getBeersFromJSON(node: JsonNode): List<String>? {

    val beerList: MutableList<String> = mutableListOf()
    node.forEach {
        beerList.add(it.textValue())
    }
    return beerList
}
