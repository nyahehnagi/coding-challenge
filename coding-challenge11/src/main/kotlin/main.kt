package cc11

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.*
import java.awt.List
import java.io.File

fun main(args: Array<String>){

    val jsonFile  = File( "src/main/resources/pubjson.json")
    convert( jsonFile )


}

private fun convert( jsonFile: File ) {
    val pubList :MutableList<Pub> = mutableListOf()

    val mapper = jacksonObjectMapper()

    val jsonNode : JsonNode = mapper.readTree(jsonFile)

    val pubsNode : JsonNode = jsonNode.path("Pubs")
    pubsNode.forEach(){
        val pub  = Pub(
            Name = it.get("Name").textValue(),
            PostCode = it.get("PostCode").textValue(),
            RegularBeers = getRegularBeersFromJSON(pubsNode),
            GuestBeers = mutableListOf("beertest2"),//jsonNode.get("GuestBeers").textValue(),
            PubService = it.get("PubService").textValue(),
            Id = it.get("Id").textValue(),
            Branch = it.get("Branch").textValue(),
            CreateTS = it.get("CreateTS").textValue()
        )
        pubList.add(pub)

    }
    println("Conversion finished !")
}

fun getRegularBeersFromJSON( pubsNode : JsonNode): MutableList<String> {
    val regularBeersNode : JsonNode = pubsNode.path("RegularBeers")
    val regularBeersList : MutableList<String> = mutableListOf()

    if (regularBeersNode.isArray){
        regularBeersNode.forEach(){
            regularBeersList.add(it.textValue())
        }
    }

    return regularBeersList
}
