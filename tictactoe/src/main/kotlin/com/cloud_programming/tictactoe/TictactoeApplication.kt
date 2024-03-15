package com.cloud_programming.tictactoe

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TictactoeApplication

fun main(args: Array<String>) {
	runApplication<TictactoeApplication>(*args)
}
