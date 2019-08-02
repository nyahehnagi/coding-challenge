package codingchallenge9

import java.io.File

fun main(args: Array<String>){


    val gameBoard = GameBoard(getConfigData())

}

fun getConfigData () : List<String>{
    //TODO Learn how to do this with JSON rather than CSV
    val boardConfigFile = File("src/resources/gameboard.txt")
    return boardConfigFile.readLines()
}