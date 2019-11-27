package codingchallenge17

import com.sun.org.apache.xpath.internal.operations.Bool
import java.lang.IllegalArgumentException

fun main() {

}

enum class Suit(val suitName: String, val suitShortName: Char) {
    CLUBS("Clubs", 'C'),
    DIAMONDS("Diamonds", 'D'),
    HEARTS("Hearts", 'H'),
    SPADES("Spades", 'S')

}

enum class Rank(val rankShortName: Char, val rankName: String) {
    ONE('1', "One"),
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
        validateCard(rankAndSuit)
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

    private fun validateCard(rankAndSuit : String){
        //TODO Add some code to check length of String
    }
}

class Hand(hand: List<Card> = emptyList()) {
    val currentHand: List<Card> = hand

    fun addCard(card: Card): Hand {
        return Hand(currentHand + listOf(card))
    }
}

class Pontoon(val dealerHand : Hand, val playerHand: Hand){

    fun determineWinner() : String{

    }

    fun isPontoon(hand: Hand) : Boolean{

    }

    fun isFiveCardTrick(hand: Hand) : Boolean{

    }

}