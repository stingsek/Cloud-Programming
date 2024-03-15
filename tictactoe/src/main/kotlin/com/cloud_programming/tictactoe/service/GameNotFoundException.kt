package com.cloud_programming.tictactoe.service

class GameNotFoundException(override val message: String?) : Exception(message) {

}