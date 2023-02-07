package com.schonfeld.model;

import java.util.concurrent.atomic.AtomicInteger;


public class Player {
    private static final AtomicInteger uniqueId = new AtomicInteger(0);
    private Integer id;
    private final String name;
    private int score;
    private boolean isBot;

    public Player(Integer id, String name, boolean isBot) {
        this.id = id;
        this.name = name;
        this.score = 0;
        this.isBot = isBot;
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }

    public Integer getId() {
        return id;
    }

    public boolean isBot() {
        return isBot;
    }

    public void addToScore(int score) {
        this.score += score;
    }
}