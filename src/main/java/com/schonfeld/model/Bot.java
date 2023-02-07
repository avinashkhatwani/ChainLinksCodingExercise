package com.schonfeld.model;

import com.schonfeld.model.enums.Move;

import java.util.Deque;

public class Bot {
    private final int[][] dp;
    private final Integer[] chainArray;
    private int leftIndex;
    private int rightIndex;

    public Bot(Deque<Integer> chain) {
        dp = new int[chain.size()][chain.size()];
        leftIndex = 0;
        rightIndex = chain.size() - 1;
        chainArray = chain.toArray(new Integer[0]);
        populateDp();
    }

    private void populateDp() {
        for (int g = 0; g < chainArray.length; g++) {
            for (int i = 0, j = g; j < chainArray.length; i++, j++) {
                if (g == 0) {
                    dp[i][j] = chainArray[i];
                } else if (g == 1) {
                    dp[i][j] = Math.max(chainArray[i], chainArray[j]);
                } else {
                    int pickLeft = chainArray[i] + Math.min(dp[i + 2][j], dp[i + 1][j - 1]);
                    int pickRight = chainArray[j] + Math.min(dp[i + 1][j - 1], dp[i][j - 2]);
                    dp[i][j] = Math.max(pickLeft, pickRight);
                }
            }
        }

    }

    public Move makeBotMove() {
        int i = leftIndex;
        int j = rightIndex;
        if (i == j)
            return Move.PICK_LEFT;
        if (j - i == 1)
            return (chainArray[i] >= chainArray[j]) ? Move.PICK_LEFT : Move.PICK_RIGHT;

        int pickLeft = chainArray[i] + Math.min(dp[i + 2][j], dp[i + 1][j - 1]);
        int pickRight = chainArray[j] + Math.min(dp[i + 1][j - 1], dp[i][j - 2]);
        return (pickLeft >= pickRight) ? Move.PICK_LEFT : Move.PICK_RIGHT;
    }

    public void decrementRightIndex() {
        rightIndex -=1;
    }

    public void incrementRightIdx() {
        leftIndex+=1;
    }
}
