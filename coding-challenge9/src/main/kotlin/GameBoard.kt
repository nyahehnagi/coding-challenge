//TODO ensure unique names
//TODO ensure that only the max number of instances are created
//TODO try and do this through the use of a factory pattern
package codingchallenge9

import java.io.File
import java.lang.IllegalArgumentException

class GameBoard(){

    private val gameBoardLocations : MutableList<ILocation> = mutableListOf()

    init{
        InitialiseBoard()
    }

    private fun InitialiseBoard(){

        val boardConfigFile  = File("src/resources/gameboard.txt")

        boardConfigFile.readLines().map{it.split(",")}.map {
            when (it[0]){
                "GO" -> gameBoardLocations.add (Go())
                "RETAIL SITE" -> gameBoardLocations.add(
                    RetailSite(
                        name = it[1],
                        purchasePrice = GBP(it[2].toInt()),
                        costOfBuildingStores = StoreBuildingCosts(GBP(it[3].toInt()),GBP(it[4].toInt()),GBP(it[5].toInt())),
                        locationRentalValues = LocationRentalValues(GBP(it[6].toInt()),GBP(it[7].toInt()),GBP(it[8].toInt()),GBP(it[9].toInt())),
                        groupID = it[10].toInt()))
                "INDUSTRY" -> gameBoardLocations.add( Industry(name = it[1]))
                else -> throw IllegalArgumentException ("crappy file")
            }
        }
    }
}

