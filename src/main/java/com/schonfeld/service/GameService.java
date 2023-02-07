package com.schonfeld.service;

import com.schonfeld.model.Bot;
import com.schonfeld.model.Game;
import com.schonfeld.model.enums.Move;
import com.schonfeld.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

    private final GameManagerService gameManagerService;

    @Autowired
    public GameService(GameManagerService gameManagerService) {
        this.gameManagerService = gameManagerService;
    }

    public void makeBotMove(int gameId) {
        int points;
        Player player = getCurrentPlayer(gameId);
        Bot bot = gameManagerService.getGameById(gameId).getBot();
        Move move = bot.makeBotMove();
        if (move.equals(Move.PICK_LEFT))
            points = pickLeft(gameId);
        else
            points = pickRight(gameId);
        player.addToScore(points);
    }

    public void handlePlayerClick(Player player, Move curMove, int gameId) {
        int points;
        if (curMove.equals(Move.PICK_RIGHT))
            points = pickRight(gameId);
        else
            points = pickLeft(gameId);
        player.addToScore(points);
    }

    private int pickRight(int gameId) {
        Bot bot = gameManagerService.getGameById(gameId).getBot();
        bot.decrementRightIndex();
        Game game = gameManagerService.getGameById(gameId);
        return game.getChain().removeLast();
    }

    private int pickLeft(int gameId) {
        Bot bot = gameManagerService.getGameById(gameId).getBot();
        bot.incrementRightIdx();
        Game game = gameManagerService.getGameById(gameId);
        return game.getChain().removeFirst();
    }

    public void switchPlayer(int gameId) {
        Game game = gameManagerService.getGameById(gameId);
        Player playerOne = game.getPlayerOne();
        Player playerTwo = game.getPlayerTwo();
        Player currentPlayer = game.getCurrentPlayer();
        if (Objects.equals(currentPlayer.getId(), playerOne.getId()))
            game.setCurrentPlayer(playerTwo);
        else
            game.setCurrentPlayer(playerOne);
    }

    public Player getPlayerOne(int gameId) {
        Game game = gameManagerService.getGameById(gameId);
        return game.getPlayerOne();
    }

    public Player getPlayerTwo(int gameId) {
        Game game = gameManagerService.getGameById(gameId);
        return game.getPlayerTwo();
    }

    public Deque<Integer> getChain(Integer gameId) {
        Game game = gameManagerService.getGameById(gameId);
        return game.getChain();
    }

    public Player getCurrentPlayer(Integer gameId) {
        Game game = gameManagerService.getGameById(gameId);
        return game.getCurrentPlayer();
    }

    public String getWinner(Integer gameId) {
        Game game = gameManagerService.getGameById(gameId);
        Player player1 = game.getPlayerOne();
        Player player2 = game.getPlayerTwo();
        if (player1.getScore() == player2.getScore())
            return "Tie match";
        else
            return player1.getScore() > player2.getScore() ? player1.getName() : player2.getName();
    }
}
