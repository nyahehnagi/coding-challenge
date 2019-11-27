package codingchallenge17

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat

class PontoonTest {

    @Test
    fun `Should test that a card has been instantiated correctly`() {
        val cardString = "AH"
        val card = Card(cardString)
        assertThat(card.rank.rankName, equalTo("Ace"))
        assertThat(card.suit.suitName, equalTo("Hearts"))
    }

    @Test
    fun `Should test that a hand has been created`() {
        val cardString1 = "AH"
        val cardString2 = "8C"
        val card1 = Card(cardString1)
        val card2 = Card(cardString2)
        val hand = Hand(listOf(card1, card2))
        assertThat(hand.listOfCards[0].rank.rankName, equalTo("Ace"))
        assertThat(hand.listOfCards[0].suit.suitName, equalTo("Hearts"))
        assertThat(hand.listOfCards[1].rank.rankName, equalTo("Eight"))
        assertThat(hand.listOfCards[1].suit.suitName, equalTo("Clubs"))
    }

    @Test
    fun `Should test that a card has been added to a hand`() {
        val cardString1 = "AH"
        val cardString2 = "8C"
        val card1 = Card(cardString1)
        val hand = Hand(listOf(card1))
        val card2 = Card(cardString2)
        val newHand = hand.addCard(card2)

        assertThat(newHand.listOfCards[0].rank.rankName, equalTo("Ace"))
        assertThat(newHand.listOfCards[0].suit.suitName, equalTo("Hearts"))
        assertThat(newHand.listOfCards[1].rank.rankName, equalTo("Eight"))
        assertThat(newHand.listOfCards[1].suit.suitName, equalTo("Clubs"))
    }

    @Test
    fun `Should test that a hand is Pontoon with ace as first card in hand`() {
        val cardString1 = "AH"
        val cardString2 = "JH"
        val card1 = Card(cardString1)
        val hand = Hand(listOf(card1))
        val card2 = Card(cardString2)
        val newHand = hand.addCard(card2)

        val playPontoon = Pontoon()
        val isPontoon = playPontoon.isPontoon(newHand)
        assertThat(isPontoon, equalTo(true))

    }

    @Test
    fun `Should test that a hand is Pontoon with ace as second card in hand`() {
        val cardString1 = "JC"
        val cardString2 = "AS"
        val card1 = Card(cardString1)
        val hand = Hand(listOf(card1))
        val card2 = Card(cardString2)
        val newHand = hand.addCard(card2)

        val playPontoon = Pontoon()
        val isPontoon = playPontoon.isPontoon(newHand)
        assertThat(isPontoon, equalTo(true))
    }

    @Test
    fun `Should test that a hand is not Pontoon with an ace in the hand`() {
        val cardString1 = "AC"
        val cardString2 = "TS"
        val card1 = Card(cardString1)
        val hand = Hand(listOf(card1))
        val card2 = Card(cardString2)
        val newHand = hand.addCard(card2)

        val playPontoon = Pontoon()
        val isPontoon = playPontoon.isPontoon(newHand)
        assertThat(isPontoon, equalTo(false))
    }

    @Test
    fun `Should test that a hand is not Pontoon 2 non picture cards`() {
        val cardString1 = "2S"
        val cardString2 = "3S"
        val card1 = Card(cardString1)
        val hand = Hand(listOf(card1))
        val card2 = Card(cardString2)
        val newHand = hand.addCard(card2)

        val playPontoon = Pontoon()
        val isPontoon = playPontoon.isPontoon(newHand)
        assertThat(isPontoon, equalTo(false))
    }

    @Test
    fun `Should test that a hand is not Pontoon with 3 cards in a hand`() {
        val cardString1 = "AC"
        val cardString2 = "QH"
        val cardString3 = "AS"
        val card1 = Card(cardString1)
        val hand = Hand(listOf(card1))
        val card2 = Card(cardString2)
        val hand2 = hand.addCard(card2)
        val card3 = Card(cardString3)
        val testHand = hand2.addCard(card3)

        val playPontoon = Pontoon()
        val isPontoon = playPontoon.isPontoon(testHand)
        assertThat(isPontoon, equalTo(false))
    }

    @Test
    fun `Should test that a hand is a five card trick`() {
        val listOfCards = listOf(Card("3D"), Card("2C"), Card("4H"), Card("AD"), Card("5C"))
        val hand = Hand(listOfCards)
        val playPontoon = Pontoon()
        val isPontoon = playPontoon.isFiveCardTrick(hand)
        assertThat(isPontoon, equalTo(true))
    }

    @Test
    fun `Should test that a hand is not a five card trick due to having 4 cards`() {
        val listOfCards = listOf(Card("3D"), Card("2C"), Card("4H"), Card("AD"))
        val hand = Hand(listOfCards)
        val playPontoon = Pontoon()
        val isPontoon = playPontoon.isFiveCardTrick(hand)
        assertThat(isPontoon, equalTo(false))
    }

    @Test
    fun `Should test that a hand is not five card trick due to being greater than 21 in value`() {
        val listOfCards = listOf(Card("3D"), Card("4C"), Card("4H"), Card("AD"), Card("TH"))
        val hand = Hand(listOfCards)
        val playPontoon = Pontoon()
        val isPontoon = playPontoon.isFiveCardTrick(hand)
        assertThat(isPontoon, equalTo(false))
    }

    @Test
    fun `Should test that a hand's value is calculated correctly with 4 cards and no ace`() {
        val listOfCards = listOf(Card("TD"), Card("4C"), Card("4H"), Card("3H"))
        val hand = Hand(listOfCards)
        val playPontoon = Pontoon()
        val handValue = playPontoon.handValue(hand)
        assertThat(handValue, equalTo(21))
    }

    @Test
    fun `Should test that a hand's value is calculated correctly with 4 cards and 1 ace but  bust`() {
        val listOfCards = listOf(Card("TD"), Card("4C"), Card("4H"), Card("3H"), Card("AH"))
        val hand = Hand(listOfCards)
        val playPontoon = Pontoon()
        val handValue = playPontoon.handValue(hand)
        assertThat(handValue, equalTo(22))
    }

    @Test
    fun `Should test that a hand's value is calculated correctly with 4 cards and 1 ace but not bust`() {
        val listOfCards = listOf(Card("TD"), Card("4C"), Card("2H"), Card("3H"), Card("AH"))
        val hand = Hand(listOfCards)
        val playPontoon = Pontoon()
        val handValue = playPontoon.handValue(hand)
        assertThat(handValue, equalTo(20))
    }

    @Test
    fun `Should test that a hand's value is calculated correctly with 3 cards and 2 aces not bust, one ace having value of 11`() {
        val listOfCards = listOf(Card("2D"), Card("4C"), Card("2H"), Card("AD"), Card("AH"))
        val hand = Hand(listOfCards)
        val playPontoon = Pontoon()
        val handValue = playPontoon.handValue(hand)
        assertThat(handValue, equalTo(20))
    }

    @Test
    fun `Should test that a hand's value is calculated correctly with 3 cards and 3 aces not bust - all aces of value 1`() {
        val listOfCards = listOf(Card("TD"), Card("4C"), Card("2H"), Card("AD"), Card("AH"), Card("AC"))
        val hand = Hand(listOfCards)
        val playPontoon = Pontoon()
        val handValue = playPontoon.handValue(hand)
        assertThat(handValue, equalTo(19))
    }

    @Test
    fun `Should test the winner of a pontoon hand`() {
        val dealerPontoon = Hand(listOf(Card("AD"), Card("KC")))
        val playerPontoon = Hand(listOf(Card("AH"), Card("QC")))
        val dealerTwentyOne = Hand(listOf(Card("TS"), Card("AC")))
        val playerTwentyOne = Hand(listOf(Card("TH"), Card("AC")))
        val dealerFiveCardTrick = Hand(listOf(Card("TD"), Card("4C"), Card("2H"), Card("AD"), Card("AH")))
        val playerFiveCardTrick = Hand(listOf(Card("TS"), Card("4C"), Card("2H"), Card("AD"), Card("AH")))
        val dealerBust = Hand(listOf(Card("TD"), Card("TC"), Card("2H")))
        val playerBust = Hand(listOf(Card("TD"), Card("TC"), Card("2H")))
        val dealerEighteen = Hand(listOf(Card("TS"), Card("8C")))
        val playerEighteen = Hand(listOf(Card("KS"), Card("8C")))

        val playPontoon = Pontoon()

        assertThat(
            playPontoon.determineWinner(dealerPontoon, playerPontoon),
            equalTo("Dealer wins with Pontoon")
        )
        assertThat(
            playPontoon.determineWinner(dealerTwentyOne, playerPontoon),
            equalTo("Player wins with Pontoon")
        )
        assertThat(
            playPontoon.determineWinner(dealerFiveCardTrick, playerTwentyOne),
            equalTo("Dealer wins with 5 Card Trick")
        )
        assertThat(
            playPontoon.determineWinner(dealerTwentyOne, playerFiveCardTrick),
            equalTo("Player wins with 5 Card Trick")
        )
        assertThat(
            playPontoon.determineWinner(dealerBust, playerEighteen),
            equalTo("Player wins with 18, Dealer Bust")
        )
        assertThat(
            playPontoon.determineWinner(dealerTwentyOne, playerBust),
            equalTo("Dealer wins with 21, Player Bust")
        )
        assertThat(
            playPontoon.determineWinner(dealerBust, playerBust),
            equalTo("Player and Dealer Bust - Dealer wins")
        )
        assertThat(
            playPontoon.determineWinner(dealerEighteen, playerTwentyOne),
            equalTo("Player wins with 21, Dealer has 18")
        )
        assertThat(
            playPontoon.determineWinner(dealerTwentyOne, playerEighteen),
            equalTo("Dealer wins with 21, Player has 18")
        )
        assertThat(
            playPontoon.determineWinner(dealerEighteen, playerEighteen),
            equalTo("Dealer wins with 18, Player has 18")
        )
    }
}