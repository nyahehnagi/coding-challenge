import codingchallenge9.*
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*


class BoardLocationTest {
    @Test
    fun `Should test an initial move of 2 spaces on the gameboard`() {
        val configData: MutableList<String> = mutableListOf()
        configData.add("GO,,,,,,,,,,")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,White City,130,10,20,20,40,50,60,70,1")

        val gameBoard = GameBoard(configData)
        //roll a 2
        var dice = Dice(6)
        while (dice.totalDiceRoll() != 2) {
            dice = Dice(6)
        }

        val boardLocation = Boardlocation()

        boardLocation.updateLocation(gameBoard, dice)

        assertThat(gameBoard.gameBoardLocations[boardLocation.getCurrentLocationIndex()].name, equalTo("Oxford Street"))
    }

    @Test
    fun `Should test a move of 5 spaces from position 2 to position 7 and go has not been passed `() {
        val configData: MutableList<String> = mutableListOf()
        configData.add("GO,,,,,,,,,,")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,White City,130,10,20,20,40,50,60,70,1")
        configData.add("INDUSTRY,Magna Park,,,,,,,,,")
        configData.add("RETAIL SITE,Peter Jones,150,10,20,20,40,60,70,80,2")
        configData.add("RETAIL SITE,High Wycombe,150,10,20,20,40,60,70,80,2")
        configData.add("FREE PARKING,,,,,,,,,,")

        val gameBoard = GameBoard(configData)
        var dice = Dice(6)
        while (dice.totalDiceRoll() != 5) {
            dice = Dice(6)
        }

        val boardLocation = Boardlocation(1)
        boardLocation.updateLocation(gameBoard, dice)

        assertThat(gameBoard.gameBoardLocations[boardLocation.getCurrentLocationIndex()].name, equalTo("Free Parking"))
        assertThat(boardLocation.hasPassedGo(), equalTo(false))
    }

    @Test
    fun `Should test a move of 5 spaces from position 4 to position 2 and go has been passed `() {
        val configData: MutableList<String> = mutableListOf()
        configData.add("GO,,,,,,,,,,")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,White City,130,10,20,20,40,50,60,70,1")
        configData.add("INDUSTRY,Magna Park,,,,,,,,,")
        configData.add("RETAIL SITE,Peter Jones,150,10,20,20,40,60,70,80,2")
        configData.add("RETAIL SITE,High Wycombe,150,10,20,20,40,60,70,80,2")
        configData.add("FREE PARKING,,,,,,,,,,")

        val gameBoard = GameBoard(configData)
        var dice = Dice(6)
        while (dice.totalDiceRoll() != 5) {
            dice = Dice(6)
        }

        val boardLocation = Boardlocation(3)
        boardLocation.updateLocation(gameBoard, dice)

        assertThat(gameBoard.gameBoardLocations[boardLocation.getCurrentLocationIndex()].name, equalTo("Oxford Street"))
        assertThat(boardLocation.hasPassedGo(), equalTo(true))
    }

    // Not an ideal test this. Need to look into the concept of Property Based Testing
    @Test
    fun `Should test that a dice roll is not less than 2 or greater than 12 over 1000 rolls `() {

        for (i in 1..1000) {
            val dice = Dice(6)
            assertThat(dice.totalDiceRoll(), greaterThan(1))
            assertThat(dice.totalDiceRoll(), lessThan(13))
        }
    }

    @Test
    fun `Should test that only the first 30 characters of a name are taken when creating a player`(){
        val newPlayer = Player("ABCDEFGHIJKLMNOPQRSTUVWXYZ12345")
        assertThat(newPlayer.name, equalTo("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234"))
    }


}