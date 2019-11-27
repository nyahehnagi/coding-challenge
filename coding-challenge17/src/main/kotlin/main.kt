package codingchallenge17

import java.lang.IllegalArgumentException

fun main() {
    // TODO Add a game of pontoon :-)
}

enum class Suit(val suitName: String, val suitShortName: Char) {
    CLUBS("Clubs", 'C'),
    DIAMONDS("Diamonds", 'D'),
    HEARTS("Hearts", 'H'),
    SPADES("Spades", 'S')

}

enum class Rank(val rankShortName: Char, val rankName: String) {
    TWO('2', "Two"),
    THREE('3', "Three"),
    FOUR('4', "Four"),
    FIVE('5', "Five"),
    SIX('6', "Six"),
    SEVEN('7', "Seven"),
    EIGHT('8', "Eight"),
    NINE('9', "Nine"),
    TEN('T', "Ten"),
    JACK('J', "Jack"),
    QUEEN('Q', "Queen"),
    KING('K', "King"),
    ACE('A', "Ace")
}

class Card(rankAndSuit: String) {

    val rank: Rank
    val suit: Suit

    init {
        rank = setRank(rankAndSuit.first())
        suit = setSuit(rankAndSuit.last())
    }

    private fun setRank(shortName: Char): Rank {

        return when (shortName) {
            Rank.TWO.rankShortName -> Rank.TWO
            Rank.THREE.rankShortName -> Rank.THREE
            Rank.FOUR.rankShortName -> Rank.FOUR
            Rank.FIVE.rankShortName -> Rank.FIVE
            Rank.SIX.rankShortName -> Rank.SIX
            Rank.SEVEN.rankShortName -> Rank.SEVEN
            Rank.EIGHT.rankShortName -> Rank.EIGHT
            Rank.NINE.rankShortName -> Rank.NINE
            Rank.TEN.rankShortName -> Rank.TEN
            Rank.JACK.rankShortName -> Rank.JACK
            Rank.QUEEN.rankShortName -> Rank.QUEEN
            Rank.KING.rankShortName -> Rank.KING
            Rank.ACE.rankShortName -> Rank.ACE
            else -> throw IllegalArgumentException("Invalid Card Rank")
        }
    }

    private fun setSuit(shortName: Char): Suit {
        return when (shortName) {
            Suit.CLUBS.suitShortName -> Suit.CLUBS
            Suit.DIAMONDS.suitShortName -> Suit.DIAMONDS
            Suit.HEARTS.suitShortName -> Suit.HEARTS
            Suit.SPADES.suitShortName -> Suit.SPADES
            else -> throw IllegalArgumentException("Invalid Card Suit")
        }
    }
}

class Hand(hand: List<Card> = emptyList()) {
    val listOfCards: List<Card> = hand

    fun addCard(card: Card): Hand {
        return Hand(listOfCards + listOf(card))
    }
}

class Pontoon {

    // giving ACE a default value of 1.
    private val pontoonCardValues: Map<Rank, Int> = mapOf(
        Rank.TWO to 2,
        Rank.THREE to 3,
        Rank.FOUR to 4,
        Rank.FIVE to 5,
        Rank.SIX to 6,
        Rank.SEVEN to 7,
        Rank.EIGHT to 8,
        Rank.NINE to 9,
        Rank.TEN to 10,
        Rank.JACK to 10,
        Rank.QUEEN to 10,
        Rank.KING to 10,
        Rank.ACE to 1
    )

    private val aceHigh: Int = 11
    private val bust : Int = 22

    fun determineWinner(dealerHand: Hand, playerHand: Hand): String {
        return when{
            isPontoon(dealerHand) -> "Dealer wins with Pontoon"
            isPontoon(playerHand) -> "Player wins with Pontoon"
            isFiveCardTrick(dealerHand) -> "Dealer wins with 5 Card Trick"
            isFiveCardTrick(playerHand) -> "Player wins with 5 Card Trick"
            handValue(playerHand) == bust && handValue(dealerHand) < bust -> "Dealer wins with ${handValue(dealerHand)}, Player Bust"
            handValue(dealerHand) == bust && handValue(playerHand) < bust -> "Player wins with ${handValue(playerHand)}, Dealer Bust"
            handValue(playerHand) == bust && handValue(dealerHand) == bust -> "Player and Dealer Bust - Dealer wins"
            handValue(playerHand) > handValue(dealerHand) -> "Player wins with ${handValue(playerHand)}, Dealer has ${handValue(dealerHand)}"
            handValue(dealerHand) >= handValue(playerHand) -> "Dealer wins with ${handValue(dealerHand)}, Player has ${handValue(playerHand)}"
            else -> "How did we get here, logic is wrong"
        }
    }

    fun isPontoon(hand: Hand): Boolean {
        return hand.listOfCards.size == 2 &&
                (hand.listOfCards[0].rank == Rank.ACE && hand.listOfCards[1].rank in Rank.JACK..Rank.KING) ||
                (hand.listOfCards[0].rank in Rank.JACK..Rank.KING && hand.listOfCards[1].rank == Rank.ACE)

    }

    fun isFiveCardTrick(hand: Hand): Boolean {
        return hand.listOfCards.size == 5 &&
                hand.listOfCards.sumBy { pontoonCardValues.getValue(it.rank) } <= 21
    }

    fun handValue(hand: Hand): Int {

        var handValue = hand.listOfCards.filter { it.rank != Rank.ACE }
            .sumBy { pontoonCardValues.getValue(it.rank) }
        var countOfAces = hand.listOfCards.filter { it.rank == Rank.ACE }.count()

        while (handValue <= 21 && countOfAces > 0) {
            handValue += if (handValue + aceHigh > 21 || countOfAces > 1) pontoonCardValues.getValue(Rank.ACE) else aceHigh
            countOfAces -= 1
        }

        return if (handValue > 21) bust else handValue
    }
}