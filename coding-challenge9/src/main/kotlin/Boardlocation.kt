package codingchallenge9

class Boardlocation(currentLocation: Int = -1) {
    private var previousLocationIndex: Int = -1
    private var currentLocationIndex: Int
    private var hasPassedGo: Boolean = false

    init {
        currentLocationIndex = currentLocation
    }

    fun updateLocation(gameBoard: GameBoard, dice: Dice) {

        val nextLocationIndex: Int

        //Test for no location - starting move
        if (currentLocationIndex == -1) {
            currentLocationIndex = dice.totalDiceRoll() //We do not count first location as this is GO
        } else {
            nextLocationIndex = currentLocationIndex + dice.totalDiceRoll()
            previousLocationIndex = currentLocationIndex
            if (nextLocationIndex > gameBoard.gameBoardLocations.lastIndex) {
                currentLocationIndex = nextLocationIndex - gameBoard.gameBoardLocations.lastIndex - 1
                hasPassedGo = true
            } else
                currentLocationIndex = nextLocationIndex
        }

    }

    fun getCurrentLocationIndex(): Int = currentLocationIndex
    fun hasPassedGo(): Boolean = hasPassedGo

}