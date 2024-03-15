package com.cloud_programming.tictactoe.controller

import com.cloud_programming.tictactoe.controller.dto.ConnectRequest
import com.cloud_programming.tictactoe.model.Game
import com.cloud_programming.tictactoe.model.GameFlow
import com.cloud_programming.tictactoe.model.Player
import com.cloud_programming.tictactoe.service.GameService
import lombok.AllArgsConstructor
import lombok.extern.java.Log
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/game")
class GameController(val gameService: GameService, val simpMessagingTemplate: SimpMessagingTemplate) {

    private val log = LoggerFactory.getLogger(GameController::class.java)

    @PostMapping("/start")
    fun start(@RequestBody player: Player): ResponseEntity<Game> {
        log.info("start game request player $player")

        val game = gameService.createGame(player)
        return ResponseEntity.ok(game)
    }

    @PostMapping("/connect")
    fun connectToGame(@RequestBody connectRequest: ConnectRequest): ResponseEntity<Game> {
        println("dupka")
        log.info("start connect request with player ${connectRequest.player} and game ${connectRequest.gameID}")

        val game = gameService.connectToGame(connectRequest.player,connectRequest.gameID)
        return ResponseEntity.ok(game)
    }


    @PostMapping("/connect/random")
    fun connectToRandomGame(@RequestBody player : Player): ResponseEntity<Game> {
        log.info("start connect request with player $player")

        val game = gameService.connectToRandomGame(player)
        return ResponseEntity.ok(game)
    }


    @PostMapping("/gameflow")
    fun gameFlow(@RequestBody gameFlowRequest: GameFlow): ResponseEntity<Game> {
        log.info("game flow request $gameFlowRequest")

        val game = gameService.gameFlow(gameFlowRequest)

        simpMessagingTemplate.convertAndSend("/topic/game-progress" + game.gameID, game)

        return ResponseEntity.ok(game)
    }




}