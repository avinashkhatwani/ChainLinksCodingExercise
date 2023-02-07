package com.schonfeld.model;

import java.util.*;

public class Game {

    private final int gameId;
    private Date createdDate;
    private final Player playerOne;
    private final Player playerTwo;
    private Player currentPlayer;
    private Deque<Integer> chain;
    private final Bot bot;

    public Game(int gameId, Player playerOne, Player playerTwo) {
        this.gameId = gameId;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        currentPlayer = playerOne;
        this.chain = createChain();
        bot = new Bot(chain);

    }

    private Deque<Integer> createChain() {

        chain = new LinkedList<>();
        Random random = new Random();
        int chainLength = random.nextInt(96) + 5;
//        int chainLength = 5;
        for (int i = 0; i < chainLength; i++) {
            chain.add(random.nextInt(201) - 100);
        }
        return chain;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public Deque<Integer> getChain() {
        return chain;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setCreated(Date date) {
        this.createdDate = date;
    }

    public Bot getBot() {
        return bot;
    }

}