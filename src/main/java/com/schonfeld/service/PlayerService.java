package com.schonfeld.service;

import com.schonfeld.model.Player;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PlayerService {
    private final AtomicInteger playerIdCounter = new AtomicInteger(0);
//    private final Map<Integer, Player> players = new ConcurrentHashMap<>();
    private Player playerOne;
    private Player playerTwo;

    public Player createBotPlayer() {
        int playerId = playerIdCounter.incrementAndGet();
        return new Player(playerId, "Bot", true);
    }

    public Player createPlayer(String name) {
        int playerId = playerIdCounter.incrementAndGet();
        return new Player(playerId, name, false);
    }

    public void createPlayers(String player1Name) {
        playerOne = createPlayer(player1Name);
        playerTwo = createBotPlayer();
    }

    public void createPlayers(String player1Name, String player2Name) {
        playerOne = createPlayer(player1Name);
        playerTwo = createPlayer(player2Name);
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    public String getScore(Player playerOne) {
        return null;
    }

    public String getWinner() {
        return null;
    }

    //    public int addToScore(int playerId, int score) {
//        Player player = players.get(playerId);
//        player.addToScore(score);
//        return player.getScore();
//    }

//    public int getScore(Player player){
//        return player.getScore();
//    }

//    public String getWinnerPlayerName() {
//
//    }
}
