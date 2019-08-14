import codingchallenge9.*
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

class GameboardTest {
    @Test
    fun `Should test that the gameboard file has been loaded and a Gameboard has been created`() {
        val configData: MutableList<String> = mutableListOf()
        configData.add("GO,,,,,,,,,,")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,White City,130,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Peter Jones,150,10,20,20,40,60,70,80,1")
        configData.add("INDUSTRY,Magna Park,,,,,,,,,")
        configData.add("FREE PARKING,,,,,,,,,,")

        val gameBoard = GameBoard(configData)

        assertThat(gameBoard.gameBoardLocations.count(), equalTo(6))
        assertThat(gameBoard.gameBoardLocations[0].name, equalTo("Go"))
        assertThat(gameBoard.gameBoardLocations[2].name, equalTo("White City"))
        assertThat(gameBoard.gameBoardLocations[5].name, equalTo("Free Parking"))
        assertThat((gameBoard.gameBoardLocations[2] as RetailSite).purchasePrice.value, equalTo(130))
    }

    @Test
    fun `Should test that the gameboard file is invalid because of 2 Free parking locations in config file`() {
        val configData: MutableList<String> = mutableListOf()
        configData.add("FREE PARKING,,,,,,,,,,")
        configData.add("FREE PARKING,,,,,,,,,,")

        val exception = assertThrows<IllegalArgumentException> { GameBoard(configData) }
        assertThat(exception.message, equalTo("Too many Free Parking - Max is 1"))
    }

    @Test
    fun `Should test that the gameboard file is invalid because of 2 Go  locations in config file`() {
        val configData: MutableList<String> = mutableListOf()
        configData.add("GO,,,,,,,,,,")
        configData.add("GO,,,,,,,,,,")

        val exception = assertThrows<IllegalArgumentException> { GameBoard(configData) }
        assertThat(exception.message, equalTo("Too many Go - Max is 1"))
    }

    @Test
    fun `Should test that first location is always GO`() {
        val configData: MutableList<String> = mutableListOf()
        configData.add("FREE PARKING,,,,,,,,,,")

        val exception = assertThrows<IllegalArgumentException> { GameBoard(configData) }
        assertThat(exception.message, equalTo("First location is not GO"))
    }


    @Test
    fun `Should test that the gameboard file is invalid because of too few retail locations of the same type`() {
        val configData: MutableList<String> = mutableListOf()
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")

        val exception = assertThrows<IllegalArgumentException> { GameBoard(configData) }
        assertThat(exception.message, equalTo("Invalid number of locations per group. min is 2, max is 3"))
    }

    @Test
    fun `Should test that the gameboard file is invalid because of too many industry locations `() {
        val configData: MutableList<String> = mutableListOf()
        configData.add("INDUSTRY,Magna Park,,,,,,,,,")
        configData.add("INDUSTRY,Magna Park,,,,,,,,,")
        configData.add("INDUSTRY,Magna Park,,,,,,,,,")
        configData.add("INDUSTRY,Magna Park,,,,,,,,,")
        configData.add("INDUSTRY,Magna Park,,,,,,,,,")

        val exception = assertThrows<IllegalArgumentException> { GameBoard(configData) }
        assertThat(exception.message, equalTo("Too many industry locations - Max is 4"))
    }

    @Test
    fun `Should test that the gameboard file is invalid because of too many retail locations of the same type`() {
        val configData: MutableList<String> = mutableListOf()
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")

        val exception = assertThrows<IllegalArgumentException> { GameBoard(configData) }
        assertThat(exception.message, equalTo("Invalid number of locations per group. min is 2, max is 3"))
    }

    @Test
    fun `Should test that the gameboard file is invalid because of too many retail locations`() {
        val configData: MutableList<String> = mutableListOf()
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")

        val exception = assertThrows<IllegalArgumentException> { GameBoard(configData) }
        assertThat(exception.message, equalTo("Too many retail locations - Max is 20"))
    }

    @Test
    fun `A Should test that the gameboard file is invalid because a location type is not valid`() {
        val configData: MutableList<String> = mutableListOf()
        configData.add("RUBBISH,Oxford Street,100,10,20,20,40,50,60,70,1")

        val exception = assertThrows<IllegalArgumentException> { GameBoard(configData) }
        assertThat(exception.message, equalTo("Invalid input data"))
    }

    @Test
    fun `Should test out of bounds exception`() {
        val configData: MutableList<String> = mutableListOf()
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70") // missing last param
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")

        val exception = assertThrows<IndexOutOfBoundsException> { GameBoard(configData) }
        assertThat(exception.message, equalTo("Index 10 out of bounds for length 10"))
    }

    @Test
    fun `Should test an initial move of 2 spaces on the gameboard`() {
        val configData: MutableList<String> = mutableListOf()
        configData.add("GO,,,,,,,,,,")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,White City,130,10,20,20,40,50,60,70,1")

        val gameBoard = GameBoard(configData)
        //roll a 2
        var dice = Dice(6)
        while (dice.totalDiceRoll() != 2){
            dice = Dice(6)
        }
        val nextLocation :ILocation = gameBoard.getNextLocation(dice)

        assertThat(nextLocation.name, equalTo("Oxford Street"))
    }

    @Test
    fun `Should test a move of 5 spaces from position 2 to position 7 `() {
        val configData: MutableList<String> = mutableListOf()
        configData.add("GO,,,,,,,,,,")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,White City,130,10,20,20,40,50,60,70,1")
        configData.add("INDUSTRY,Magna Park,,,,,,,,,")
        configData.add("RETAIL SITE,Peter Jones,150,10,20,20,40,60,70,80,2")
        configData.add("RETAIL SITE,High Wycombe,150,10,20,20,40,60,70,80,2")
        configData.add("FREE PARKING,,,,,,,,,,")

        val gameBoard = GameBoard(configData)
        val currentLocation :ILocation = gameBoard.gameBoardLocations[1]
        var dice = Dice(6)
        while (dice.totalDiceRoll() != 5){
            dice = Dice(6)
        }
        val nextLocation :ILocation = gameBoard.getNextLocation(dice,currentLocation)

        assertThat(nextLocation.name, equalTo("Free Parking"))
    }

    @Test
    fun `Should test a move of 5 spaces from position 4 to position 2 `() {
        val configData: MutableList<String> = mutableListOf()
        configData.add("GO,,,,,,,,,,")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,White City,130,10,20,20,40,50,60,70,1")
        configData.add("INDUSTRY,Magna Park,,,,,,,,,")
        configData.add("RETAIL SITE,Peter Jones,150,10,20,20,40,60,70,80,2")
        configData.add("RETAIL SITE,High Wycombe,150,10,20,20,40,60,70,80,2")
        configData.add("FREE PARKING,,,,,,,,,,")

        val gameBoard = GameBoard(configData)
        val currentLocation :ILocation = gameBoard.gameBoardLocations[3]
        var dice = Dice(6)
        while (dice.totalDiceRoll() != 5){
            dice = Dice(6)
        }
        val nextLocation :ILocation = gameBoard.getNextLocation(dice,currentLocation)

        assertThat(nextLocation.name, equalTo("Oxford Street"))
    }

    // Not an ideal test this. Need to look into the concept of Property Based Testing
    @Test
    fun `Should test that a dice roll is not less than 2 or greater than 12 over 1000 rolls `() {

        for (i in 1..1000) {
            val dice  = Dice (6)
            assertThat (dice.totalDiceRoll(), greaterThan(1) )
            assertThat (dice.totalDiceRoll(), lessThan(13))
        }
    }

    @Test
    fun `Should test that Go has not been passed `() {
        val configData: MutableList<String> = mutableListOf()
        configData.add("GO,,,,,,,,,,")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,White City,130,10,20,20,40,50,60,70,1")
        configData.add("INDUSTRY,Magna Park,,,,,,,,,")
        configData.add("RETAIL SITE,Peter Jones,150,10,20,20,40,60,70,80,2")
        configData.add("RETAIL SITE,High Wycombe,150,10,20,20,40,60,70,80,2")
        configData.add("FREE PARKING,,,,,,,,,,")

        val gameBoard = GameBoard(configData)
        val currentLocation = gameBoard.gameBoardLocations[2]
        val nextLocation = gameBoard.gameBoardLocations[5]

        val hasPassedGo  = gameBoard.hasPassedGo(currentLocation, nextLocation)
        assertThat(hasPassedGo, equalTo(false))
    }

    @Test
    fun `Should test that Go has  been passed `() {
        val configData: MutableList<String> = mutableListOf()
        configData.add("GO,,,,,,,,,,")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,White City,130,10,20,20,40,50,60,70,1")
        configData.add("INDUSTRY,Magna Park,,,,,,,,,")
        configData.add("RETAIL SITE,Peter Jones,150,10,20,20,40,60,70,80,2")
        configData.add("RETAIL SITE,High Wycombe,150,10,20,20,40,60,70,80,2")
        configData.add("FREE PARKING,,,,,,,,,,")

        val gameBoard = GameBoard(configData)
        val currentLocation = gameBoard.gameBoardLocations[2]
        val nextLocation = gameBoard.gameBoardLocations[1]

        val hasPassedGo  = gameBoard.hasPassedGo(currentLocation, nextLocation)
        assertThat(hasPassedGo, equalTo(true))
    }
}


