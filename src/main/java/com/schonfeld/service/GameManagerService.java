package com.schonfeld.service;

import com.schonfeld.model.Game;
import com.schonfeld.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GameManagerService {

    Map<Integer, Game> currentGames;
//    private Integer gameIdCounter = 0;
private AtomicInteger gameIdCounter = new AtomicInteger(0);

    @Autowired
    PlayerService playerService;

    public GameManagerService() {
        currentGames = new HashMap<>();
    }

    public Integer createNewGameWithBot(String playerOneName) {

        Player playerOne = playerService.createPlayer(playerOneName);
        Player playerTwo = playerService.createBotPlayer();
        return createNewGame(playerOne, playerTwo);
    }

    public Integer createNewGameWithTwoPlayers(String playerOneName, String playerTwoName) {
        Player playerOne = playerService.createPlayer(playerOneName);
        Player playerTwo = playerService.createPlayer(playerTwoName);
        return createNewGame(playerOne, playerTwo);
    }

    private Integer createNewGame(Player playerOne, Player playerTwo) {
        int gameId = gameIdCounter.getAndIncrement();

        Game game = new Game(gameId, playerOne, playerTwo);
        game.setCreated(new Date());
        currentGames.put(gameId, game);
        return gameId;
    }

    public Game getGameById(Integer gameId){
        return currentGames.get(gameId);
    }


}
