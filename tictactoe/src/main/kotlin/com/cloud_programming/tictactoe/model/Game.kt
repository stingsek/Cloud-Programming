package com.cloud_programming.tictactoe.model

data class Game(val gameID: String,
                val player1: Player?,
                var player2: Player?,
                var gameStatus: GameStatus =  GameStatus.INITIALIZED,
                var board: Array<Array<Byte>> = Array(3) { Array(3) { 0 } },
                var winner : TicToe?) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Game

        if (gameID != other.gameID) return false
        if (player1 != other.player1) return false
        if (player2 != other.player2) return false
        if (gameStatus != other.gameStatus) return false
        if (!board.contentDeepEquals(other.board)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = gameID.hashCode() ?: 0
        result = 31 * result + (player1?.hashCode() ?: 0)
        result = 31 * result + (player2?.hashCode() ?: 0)
        result = 31 * result + (gameStatus.hashCode() ?: 0)
        result = 31 * result + board.contentDeepHashCode()
        return result
    }

}