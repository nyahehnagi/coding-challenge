package codingchallenge17

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat

class PontoonTest {

    @Test
    fun `Should test that a card has been instantiated correctly`(){
        val cardString = "AH"
        val card = Card(cardString)
        assertThat(card.rank.rankName, equalTo("Ace"))
        assertThat(card.suit.suitName, equalTo("Hearts"))
    }

}