import codingchallenge9.*
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat
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

        val exception = assertThrows<IllegalArgumentException> {GameBoard(configData) }
        assertThat(exception.message, equalTo("Too many Free Parking - Max is 1"))
    }

    @Test
    fun `Should test that the gameboard file is invalid because of 2 Go  locations in config file`() {
        val configData: MutableList<String> = mutableListOf()
        configData.add("GO,,,,,,,,,,")
        configData.add("GO,,,,,,,,,,")

        val exception = assertThrows<IllegalArgumentException> {GameBoard(configData) }
        assertThat(exception.message, equalTo("Too many Go - Max is 1"))
    }

    @Test
    fun `Should test that the gameboard file is invalid because of too few retail locations of the same type`() {
        val configData: MutableList<String> = mutableListOf()
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")

        val exception = assertThrows<IllegalArgumentException> {GameBoard(configData) }
        assertThat(exception.message, equalTo("Invalid number of locations per group. min is 2, max is 3"))
    }

    @Test
    fun `Should test that the gameboard file is invalid because of too many retail locations of the same type`() {
        val configData: MutableList<String> = mutableListOf()
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")

        val exception = assertThrows<IllegalArgumentException> {GameBoard(configData) }
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

        val exception = assertThrows<IllegalArgumentException> {GameBoard(configData) }
        assertThat(exception.message, equalTo("Too many retail locations - Max is 20"))
    }

    @Test
    fun `A Should test that the gameboard file is invalid because a location type is not valid`() {
        val configData: MutableList<String> = mutableListOf()
        configData.add("RUBBISH,Oxford Street,100,10,20,20,40,50,60,70,1")

        val exception = assertThrows<IllegalArgumentException> {GameBoard(configData) }
        assertThat(exception.message, equalTo("Invalid input data"))
    }

    @Test
    fun `Should test out of bounds exception`() {
        val configData: MutableList<String> = mutableListOf()
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70") // missing last param
        configData.add("RETAIL SITE,Oxford Street,100,10,20,20,40,50,60,70,1")

        val exception = assertThrows<IndexOutOfBoundsException> {GameBoard(configData) }
        assertThat(exception.message, equalTo("Index 10 out of bounds for length 10"))
    }
}


