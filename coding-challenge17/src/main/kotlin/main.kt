package codingchallenge17

import java.lang.IllegalArgumentException

fun main() {

}

enum class Suit(val suitName: String, val suitShortName: Char, val suitValue: Int) {
    CLUBS("Clubs", 'C', 1),
    DIAMONDS("Diamonds", 'D', 2),
    HEARTS("Hearts", 'H', 3),
    SPADES("Spades", 'S', 4)

}

enum class Rank(val rankValue: Int, val rankShortName: Char, val rankName: String) {
    ONE(1, '1', "One"),
    TWO(2, '2', "Two"),
    THREE(3, '3', "Three"),
    FOUR(4, '4', "Four"),
    FIVE(5, '5', "Five"),
    SIX(6, '6', "Six"),
    SEVEN(7, '7', "Seven"),
    EIGHT(8, '8', "Eight"),
    NINE(9, '9', "Nine"),
    TEN(10, 'T', "Ten"),
    JACK(11, 'J', "Jack"),
    QUEEN(12, 'Q', "Queen"),
    KING(13, 'K', "King"),
    ACE(14, 'A', "Ace")
}

class Card(rankAndSuit: String) {

    val rank: Rank
    val suit: Suit

    init {
        rank = setRank(rankAndSuit[0])
        suit = setSuit(rankAndSuit[1])
    }

    private fun setRank(shortName: Char): Rank {

        return when (shortName) {
            Rank.ONE.rankShortName -> Rank.ONE
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
    val currentHand: List<Card> = hand

    fun addCard(card: Card): Hand {
        return Hand(currentHand + listOf(card))
    }
}