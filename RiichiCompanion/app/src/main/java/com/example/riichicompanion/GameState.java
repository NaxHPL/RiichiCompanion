package com.example.riichicompanion;

import java.io.Serializable;

public class GameState implements Serializable {

    private final int bottomPlayerScore;
    private final Wind bottomPlayerSeatWind;
    private final int rightPlayerScore;
    private final Wind rightPlayerSeatWind;
    private final int topPlayerScore;
    private final Wind topPlayerSeatWind;
    private final int leftPlayerScore;
    private final Wind leftPlayerSeatWind;
    private final int riichiCount;
    private final int honbaCount;
    private final int roundNumber;
    private final boolean isFinished;
    private final boolean dealerRecentlyWonInAllLast;

    public GameState(int bottomPlayerScore, Wind bottomPlayerSeatWind, int rightPlayerScore, Wind rightPlayerSeatWind,
                     int topPlayerScore, Wind topPlayerSeatWind, int leftPlayerScore, Wind leftPlayerSeatWind,
                     int riichiCount, int honbaCount, int roundNumber, boolean isFinished, boolean dealerRecentlyWonInAllLast) {
        this.bottomPlayerScore = bottomPlayerScore;
        this.bottomPlayerSeatWind = bottomPlayerSeatWind;
        this.rightPlayerScore = rightPlayerScore;
        this.rightPlayerSeatWind = rightPlayerSeatWind;
        this.topPlayerScore = topPlayerScore;
        this.topPlayerSeatWind = topPlayerSeatWind;
        this.leftPlayerScore = leftPlayerScore;
        this.leftPlayerSeatWind = leftPlayerSeatWind;
        this.riichiCount = riichiCount;
        this.honbaCount = honbaCount;
        this.roundNumber = roundNumber;
        this.isFinished = isFinished;
        this.dealerRecentlyWonInAllLast = dealerRecentlyWonInAllLast;
    }

    public int getBottomPlayerScore() {
        return bottomPlayerScore;
    }

    public Wind getBottomPlayerSeatWind() {
        return bottomPlayerSeatWind;
    }

    public int getRightPlayerScore() {
        return rightPlayerScore;
    }

    public Wind getRightPlayerSeatWind() {
        return rightPlayerSeatWind;
    }

    public int getTopPlayerScore() {
        return topPlayerScore;
    }

    public Wind getTopPlayerSeatWind() {
        return topPlayerSeatWind;
    }

    public int getLeftPlayerScore() {
        return leftPlayerScore;
    }

    public Wind getLeftPlayerSeatWind() {
        return leftPlayerSeatWind;
    }

    public int getRiichiCount() {
        return riichiCount;
    }

    public int getHonbaCount() {
        return honbaCount;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public boolean getIsFinished() {
        return isFinished;
    }

    public boolean getDealerRecentlyWonInAllLast() {
        return dealerRecentlyWonInAllLast;
    }
}
