//TODO ensure unique names
package codingchallenge9

import java.lang.IllegalArgumentException

const val MAX_NUMBER_OF_INDUSTRY = 4
const val MAX_NUMBER_OF_GO = 1
const val MAX_NUMBER_OF_RETAIL = 20
const val MAX_NUMBER_OF_FREEPARKING = 1
const val MIN_RETAIL_GROUP_SIZE = 2
const val MAX_RETAIL_GROUP_SIZE = 3

class GameBoard(boardData: List<String>) {

    //TODO make this private and add functions to support gameboard as we don't want anyone fiddling with this once initialised
    val gameBoardLocations: MutableList<ILocation> = mutableListOf()

    init {
        initialiseBoard(boardData)
    }

    private fun initialiseBoard(boardData: List<String>) {

        boardData.map { it.split(",") }.map {
            when (it[0]) {
                "GO" -> gameBoardLocations.add(Go())
                "RETAIL SITE" -> gameBoardLocations.add(createRetailSite(it))
                "INDUSTRY" -> gameBoardLocations.add(createIndustry(it))
                "FREE PARKING" -> gameBoardLocations.add(FreeParking())
                else -> throw IllegalArgumentException("Invalid input data") //Is there a better way rather than throwing an exception?
            }
        }

        //check what's been created conforms to board rules
        verifyBoard()
    }

    //Checks that the board conforms to the rules of this board
    //Pondering if I should build a list of everything that has gone wrong with the load file
    //TODO Think of a better way to do this. Perhaps verify file contents before building the board?
    private fun verifyBoard() {
        if (gameBoardLocations.filterIsInstance<RetailSite>().count() > MAX_NUMBER_OF_RETAIL) {
            throw IllegalArgumentException("Too many retail locations - Max is $MAX_NUMBER_OF_RETAIL")
        }


        val numberOfOccurrences =
            gameBoardLocations.filterIsInstance<RetailSite>().groupingBy { (it.retailGroup) }.eachCount()
        numberOfOccurrences.forEach {
            if (it.value > MAX_RETAIL_GROUP_SIZE || it.value < MIN_RETAIL_GROUP_SIZE) {
                throw IllegalArgumentException("Invalid number of locations per group. min is $MIN_RETAIL_GROUP_SIZE, max is $MAX_RETAIL_GROUP_SIZE")
            }
        }

        if (gameBoardLocations.filterIsInstance<FreeParking>().count() > MAX_NUMBER_OF_FREEPARKING) {
            throw IllegalArgumentException("Too many Free Parking - Max is $MAX_NUMBER_OF_FREEPARKING")
        }
        if (gameBoardLocations.filterIsInstance<Go>().count() > MAX_NUMBER_OF_GO) {
            throw IllegalArgumentException("Too many Go - Max is $MAX_NUMBER_OF_GO")
        }
        if (gameBoardLocations.filterIsInstance<Industry>().count() > MAX_NUMBER_OF_INDUSTRY) {
            throw IllegalArgumentException("Too many industry locations - Max is $MAX_NUMBER_OF_INDUSTRY")
        }

    }

    private fun createRetailSite(retailLocationData: List<String>): RetailSite {
        return RetailSite(
            name = retailLocationData[1],
            purchasePrice = GBP(retailLocationData[2].toInt()),
            costOfBuildingStores = StoreBuildingCosts(
                GBP(retailLocationData[3].toInt()),
                GBP(retailLocationData[4].toInt()),
                GBP(retailLocationData[5].toInt())
            ),
            locationRentalValues = LocationRentalValues(
                GBP(retailLocationData[6].toInt()),
                GBP(retailLocationData[7].toInt()),
                GBP(retailLocationData[8].toInt()),
                GBP(retailLocationData[9].toInt())
            ),
            groupID = retailLocationData[10].toInt()
        )
    }

    private fun createIndustry(industryLocationData: List<String>): Industry {
        return Industry(
            name = industryLocationData[1]
        )
    }
}

