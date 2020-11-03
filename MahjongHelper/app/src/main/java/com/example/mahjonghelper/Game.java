package com.example.mahjonghelper;

import android.os.Parcel;
import android.os.Parcelable;

public class Game implements Parcelable {

    private final Player bottomPlayer;
    private final Player rightPlayer;
    private final Player topPlayer;
    private final Player leftPlayer;
    private final int minPointsToWin;
    private int riichiStickCount;
    private int honbaStickCount;
    private int roundNumber;

    public Game(Player bottomPlayer, Player rightPlayer, Player topPlayer, Player leftPlayer,
                int minPointsToWin, int riichiStickCount, int honbaStickCount, int roundNumber) {
        this.bottomPlayer = bottomPlayer;
        this.rightPlayer = rightPlayer;
        this.topPlayer = topPlayer;
        this.leftPlayer = leftPlayer;
        this.minPointsToWin = minPointsToWin;
        this.riichiStickCount = riichiStickCount;
        this.honbaStickCount = honbaStickCount;
        this.roundNumber = roundNumber;
    }

    //region Parcelable Implementation

    protected Game(Parcel in) {
        bottomPlayer = in.readParcelable(Player.class.getClassLoader());
        rightPlayer = in.readParcelable(Player.class.getClassLoader());
        topPlayer = in.readParcelable(Player.class.getClassLoader());
        leftPlayer = in.readParcelable(Player.class.getClassLoader());
        minPointsToWin = in.readInt();
        riichiStickCount = in.readInt();
        honbaStickCount = in.readInt();
        roundNumber = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(bottomPlayer, flags);
        dest.writeParcelable(rightPlayer, flags);
        dest.writeParcelable(topPlayer, flags);
        dest.writeParcelable(leftPlayer, flags);
        dest.writeInt(minPointsToWin);
        dest.writeInt(riichiStickCount);
        dest.writeInt(honbaStickCount);
        dest.writeInt(roundNumber);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    //endregion

    public Player getBottomPlayer() {
        return bottomPlayer;
    }

    public Player getRightPlayer() {
        return rightPlayer;
    }

    public Player getTopPlayer() {
        return topPlayer;
    }

    public Player getLeftPlayer() {
        return leftPlayer;
    }

    public int getMinPointsToWin() {
        return minPointsToWin;
    }

    public int getRiichiStickCount() {
        return riichiStickCount;
    }

    public int getHonbaStickCount() {
        return honbaStickCount;
    }

    public void incrementRiichiStickCount() {
        riichiStickCount++;
    }

    public void incrementHonbaStickCounter() {
        honbaStickCount++;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public int getRoundNumberForDisplay() {
        return roundNumber <= 4 ? roundNumber : roundNumber - 4;
    }

    public void incrementRoundNumber() {
        roundNumber++;
    }

    public Wind getPrevalentWind() {
        return roundNumber <= 4 ? Wind.East : Wind.South;
    }

//    public boolean gameIsOver() {
//
//    }
}
