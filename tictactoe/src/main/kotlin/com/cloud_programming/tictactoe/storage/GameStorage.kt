package com.cloud_programming.tictactoe.storage

import com.cloud_programming.tictactoe.model.Game

object GameStorage {
    private var games: MutableMap<String, Game> = mutableMapOf()

    fun addGame(gameID: String, game: Game) {
        games[gameID] = game
    }

    fun getGame(gameID: String): Game? {
        return games[gameID]
    }

    fun getGames(): Map<String, Game> {
        return games.toMap()
    }


}