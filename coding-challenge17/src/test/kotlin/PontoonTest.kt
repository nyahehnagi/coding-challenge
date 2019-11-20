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

    @Test
    fun `Should test that a hand has been created`(){
        val cardString1 = "AH"
        val cardString2 = "8C"
        val card1 = Card(cardString1)
        val card2 = Card(cardString2)
        val hand = Hand(listOf(card1, card2))
        assertThat(hand.currentHand[0].rank.rankName, equalTo("Ace"))
        assertThat(hand.currentHand[0].suit.suitName, equalTo("Hearts"))
        assertThat(hand.currentHand[1].rank.rankName, equalTo("Eight"))
        assertThat(hand.currentHand[1].suit.suitName, equalTo("Clubs"))
    }

    @Test
    fun `Should test that a card has been added to a hand`(){
        val cardString1 = "AH"
        val cardString2 = "8C"
        val card1 = Card(cardString1)
        val hand = Hand(listOf(card1))
        val card2 = Card(cardString2)
        val newHand = hand.addCard(card2)

        assertThat(newHand.currentHand[0].rank.rankName, equalTo("Ace"))
        assertThat(newHand.currentHand[0].suit.suitName, equalTo("Hearts"))
        assertThat(newHand.currentHand[1].rank.rankName, equalTo("Eight"))
        assertThat(newHand.currentHand[1].suit.suitName, equalTo("Clubs"))
    }
}