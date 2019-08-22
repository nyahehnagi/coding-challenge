package cc11

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.*
import java.io.File


fun main() {

    val jsonFile = File("src/main/resources/pubjson.json")
    val pubList: List<Pub> = deserialisePubsJSON(jsonFile)
    val beerList: List<Beer> = obtainListOfBeers(pubList)

}

fun obtainListOfBeers(pubList: List<Pub>): List<Beer> {
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


fun deserialisePubsJSON(jsonFile: File): List<Pub> {

    val mapper = jacksonObjectMapper()

    val pubsNode: JsonNode = mapper.readTree(jsonFile).path("Pubs")
   // val pubsNode: JsonNode = jsonNode.path("Pubs")

    val pubList: MutableList<Pub> = mutableListOf()

    pubsNode.forEach {
        val pub = Pub(
            name = getStringFromJSON(it, "Name"),
            postCode = getStringFromJSON(it, "PostCode"),
            regularBeers = getListFromJSON(it, "RegularBeers"),
            guestBeers = getListFromJSON(it, "GuestBeers"),
            pubService = getStringFromJSON(it, "PubService"),
            id = getStringFromJSON(it, "Id"),
            branch = getStringFromJSON(it, "Branch"),
            createTS = getStringFromJSON(it, "CreateTS")
        )
        pubList.add(pub)
    }

    // sort and remove duplicates
    return pubList.sortedWith(compareBy<Pub> { it.id }.thenBy { it.branch }.thenByDescending { it.createTS })
        .distinctBy { Pair(it.id, it.branch) }

}

fun getStringFromJSON(node: JsonNode, propertyString: String): String {
    return if (node.get(propertyString) != null) {
        node.get(propertyString).textValue().toString()
    } else ""

}

fun getListFromJSON(node: JsonNode, propertyString: String): List<String> {

    val jsonList: MutableList<String> = mutableListOf()

    if (node.path(propertyString) == null) {
        return jsonList
    }

    node.path(propertyString).forEach {
        jsonList.add(it.textValue().toString())
    }
    return jsonList
}
