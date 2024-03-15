package com.cloud_programming.tictactoe.controller.dto

import com.cloud_programming.tictactoe.model.Player

data class ConnectRequest(val player: Player, val gameID: String) {
}