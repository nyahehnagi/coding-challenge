package codingchallenge9

const val MAX_NAME_LENGTH = 30

interface IAccountHolder {
    val name: String
}

class Bank : IAccountHolder {
    override val name = "bank"
}

class Player(_name: String) : IAccountHolder {
    override val name = _name.take(MAX_NAME_LENGTH)

    val currentBoardLocation: Boardlocation = Boardlocation()

    fun move(gameBoard: GameBoard, dice: Dice) {
        currentBoardLocation.updateLocation(gameBoard, dice)
    }

}
