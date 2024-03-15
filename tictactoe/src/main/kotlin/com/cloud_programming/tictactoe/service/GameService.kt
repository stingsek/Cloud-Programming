package com.cloud_programming.tictactoe.service

import com.cloud_programming.tictactoe.model.*
import com.cloud_programming.tictactoe.storage.GameStorage
import lombok.AllArgsConstructor
import org.springframework.stereotype.Service
import java.security.InvalidParameterException
import java.util.*

@Service
@AllArgsConstructor
class GameService {

    fun createGame(player: Player): Game =
        Game(
            gameID = UUID.randomUUID().toString(),
            player1 = player,
            player2 = null,
            winner = null
        ).also { GameStorage.addGame(it.gameID, it) }


    fun connectToGame(player: Player, gameID: String): Game =
        GameStorage.getGame(gameID)?.takeIf { it.player2 == null }
            ?.apply {
                player2 = player
                gameStatus = GameStatus.AFOOT
            } ?: throw InvalidParameterException("Game with ID $gameID doesn't exist or Player2 is not null.")


    fun connectToRandomGame(player: Player): Game? =
        GameStorage.getGames().values.firstOrNull { it.gameStatus == GameStatus.INITIALIZED }
            ?.apply {
                player2 = player
                gameStatus = GameStatus.AFOOT
            } ?: throw GameNotFoundException("an initialized game wasn't found")


    fun gameFlow(gameFlow: GameFlow): Game =
        GameStorage.getGame(gameFlow.gameID)?.takeIf { it.gameStatus != GameStatus.ENDED }
            ?.apply {
                board[gameFlow.coordinateX.toInt()][gameFlow.coordinateY.toInt()] = gameFlow.symbol.value

                winner = emergeWinner(board)

                if(winner != null)
                {
                    gameStatus = GameStatus.ENDED
                }

            } ?: throw GameNotFoundException("Game not found or already ended.")


    private fun emergeWinner(gameBoard: Array<Array<Byte>>): TicToe? {
        val flattenedBoard: List<Byte> = gameBoard.flatten()

        val rows: List<List<Byte>> =
            listOf(
                flattenedBoard.slice(0..2),
                flattenedBoard.slice(3..5),
                flattenedBoard.slice(6..8)
            )

        val columns: List<List<Byte>> =
            listOf(
                flattenedBoard.slice(listOf(0, 3, 6)),
                flattenedBoard.slice(listOf(1, 4, 7)),
                flattenedBoard.slice(listOf(2, 5, 8))
            )

        val diagonals: List<List<Byte>> =
            listOf(
                flattenedBoard.slice(listOf(0, 4, 8)),
                flattenedBoard.slice(listOf(2, 4, 6))
            )

        var winner: TicToe? = null

        for (three in rows + columns + diagonals) {
            winner = when {
                three.all { it == 1.toByte() } -> TicToe.X
                three.all { it == 2.toByte() } -> TicToe.O
                else -> null
            }


        }
        return winner
    }

}

