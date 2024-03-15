package com.cloud_programming.tictactoe.service

class InvalidGameException(override val message: String?) : Exception(message) {
}