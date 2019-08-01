import codingchallenge9.*
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class GameboardTest {
//TODO I need to work how I test without actually opening up the game board file, as I need to be able to test creating to many locations for example
    @Test
    fun `Should test that the gameboard file has been loaded and a Gameboard has been created`() {
        val gameBoard = GameBoard()
        assertThat(gameBoard.gameBoardLocations.count(), equalTo(6))
        assertThat(gameBoard.gameBoardLocations[0].name, equalTo("Go"))
        assertThat(gameBoard.gameBoardLocations[2].name, equalTo("White City"))
        assertThat(gameBoard.gameBoardLocations[5].name, equalTo("Free Parking"))
        assertThat((gameBoard.gameBoardLocations[2] as RetailSite).purchasePrice.value, equalTo(130))
    }
}


