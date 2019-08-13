package codingchallenge9

const val MAX_NAME_LENGTH = 30

interface IAccountHolder {
    val name: String
}

class Bank : IAccountHolder {
    override val name = "bank"
}

class Player(_name: String) : IAccountHolder {
    override val name = _name.take(MAX_NAME_LENGTH) //maybe add some logic to not allow any reserved names e.g bank

    var boardLocation : ILocation? = null //null when not on the board


}


class Dice () {
    val dieRoll : Int

    init {
        rollDice()
    }
    fun rollDice() :Int {
        dieRoll = (1..6).shuffled().first()
    }
}