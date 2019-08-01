//TODO ensure unique names
//TODO ensure that only the max number of instances are created (for all locations and retail groupings)
//TODO try and do this through the use of a factory pattern - maybe
package codingchallenge9

import java.io.File
import java.lang.IllegalArgumentException

const val MAX_NUMBER_OF_INDUSTRY = 4
const val MAX_NUMBER_OF_GO = 1
const val MAX_NUMBER_OF_RETAIL = 20
const val MAX_NUMBER_OF_FREEPARKING = 1
const val MIN_RETAIL_GROUP_SIZE = 2
const val MAX_RETAIL_GROUP_SIZE = 3

class GameBoard() {

    val gameBoardLocations: MutableList<ILocation> = mutableListOf()

    init {
        InitialiseBoard()
    }

    private fun InitialiseBoard() {
        //TODO Learn how to do this with JSON rather than CSV
        val boardConfigFile = File("src/resources/gameboard.txt")

        boardConfigFile.readLines().map { it.split(",") }.map {
            when (it[0]) {
                "GO" -> gameBoardLocations.add(Go())
                "RETAIL SITE" -> gameBoardLocations.add(createRetailSite(it))
                "INDUSTRY" -> gameBoardLocations.add(Industry(name = it[1]))
                "FREE PARKING" -> gameBoardLocations.add(FreeParking())
                else -> throw IllegalArgumentException("crappy file") //Is there a better way rather than throwing an exception?
            }
        }
    }

    private fun createRetailSite(retailStoreData: List<String>): RetailSite {
        // TODO this checks needs to go elsewhere... I am still working on it
        if (gameBoardLocations.filter { it is RetailSite }.count() == MAX_NUMBER_OF_RETAIL) {
            throw IllegalArgumentException("to many retail locations")
        }

        return RetailSite(
            name = retailStoreData[1],
            purchasePrice = GBP(retailStoreData[2].toInt()),
            costOfBuildingStores = StoreBuildingCosts(
                GBP(retailStoreData[3].toInt()),
                GBP(retailStoreData[4].toInt()),
                GBP(retailStoreData[5].toInt())
            ),
            locationRentalValues = LocationRentalValues(
                GBP(retailStoreData[6].toInt()),
                GBP(retailStoreData[7].toInt()),
                GBP(retailStoreData[8].toInt()),
                GBP(retailStoreData[9].toInt())
            ),
            groupID = retailStoreData[10].toInt()
        )
    }
}


//RetailSite(
//name = it[1],
//purchasePrice = GBP(it[2].toInt()),
//costOfBuildingStores = StoreBuildingCosts(
//GBP(it[3].toInt()),
//GBP(it[4].toInt()),
//GBP(it[5].toInt())
//),
//locationRentalValues = LocationRentalValues(
//GBP(it[6].toInt()),
//GBP(it[7].toInt()),
//GBP(it[8].toInt()),
//GBP(it[9].toInt())
//),
//groupID = it[10].toInt()
//)