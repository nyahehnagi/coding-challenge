package codingchallenge9

class Dice (numberOfSides :Int) {
    val dieOne : Int = (1..numberOfSides).shuffled().first()
    val dieTwo : Int = (1..numberOfSides).shuffled().first()

    fun totalDiceRoll() : Int = dieOne + dieTwo
}