package codingchallenge9

class Boardlocation(currentLocation: Int = 0) {
    private var previousLocationIndex: Int = 0 //Default to Go
    private var currentLocationIndex: Int
    private var hasPassedGo: Boolean = false

    init {
        currentLocationIndex = currentLocation
    }

    fun updateLocation(gameBoard: GameBoard, dice: Dice) {

        val nextLocationIndex: Int = currentLocationIndex + dice.totalDiceRoll()
        previousLocationIndex = currentLocationIndex

        if (nextLocationIndex > gameBoard.gameBoardLocations.lastIndex) {
            currentLocationIndex = nextLocationIndex - gameBoard.gameBoardLocations.lastIndex - 1
            hasPassedGo = true
        } else {
            currentLocationIndex = nextLocationIndex
            hasPassedGo = false
        }

    }

    fun getCurrentLocationIndex(): Int = currentLocationIndex
    fun hasPassedGo(): Boolean = hasPassedGo

}