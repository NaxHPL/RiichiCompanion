package com.example.mahjonghelper;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

public class Game implements Parcelable {

    private Player eastPlayer;
    private Player southPlayer;
    private Player westPlayer;
    private Player northPlayer;
    private int minPointsToWin;
    private int riichiStickCount;
    private int honbaStickCount;
    private int roundNumber;

    public Game(Player eastPlayer, Player southPlayer, Player westPlayer, Player northPlayer,
                int minPointsToWin, int riichiStickCount, int honbaStickCount, int roundNumber) {
        this.eastPlayer = eastPlayer;
        this.southPlayer = southPlayer;
        this.westPlayer = westPlayer;
        this.northPlayer = northPlayer;
        this.minPointsToWin = minPointsToWin;
        this.riichiStickCount = riichiStickCount;
        this.honbaStickCount = honbaStickCount;
        this.roundNumber = roundNumber;
    }

    public Player getEastPlayer() {
        return eastPlayer;
    }

    public void setEastPlayer(Player eastPlayer) {
        this.eastPlayer = eastPlayer;
    }

    public Player getSouthPlayer() {
        return southPlayer;
    }

    public void setSouthPlayer(Player southPlayer) {
        this.southPlayer = southPlayer;
    }

    public Player getWestPlayer() {
        return westPlayer;
    }

    public void setWestPlayer(Player westPlayer) {
        this.westPlayer = westPlayer;
    }

    public Player getNorthPlayer() {
        return northPlayer;
    }

    public void setNorthPlayer(Player northPlayer) {
        this.northPlayer = northPlayer;
    }

    public int getMinPointsToWin() {
        return minPointsToWin;
    }

    public void setMinPointsToWin(int minPointsToWin) {
        this.minPointsToWin = minPointsToWin;
    }

    public int getRiichiStickCount() {
        return riichiStickCount;
    }

    public void setRiichiStickCount(int riichiStickCount) {
        this.riichiStickCount = riichiStickCount;
    }

    public int getHonbaStickCount() {
        return honbaStickCount;
    }

    public void setHonbaStickCount(int honbaStickCount) {
        this.honbaStickCount = honbaStickCount;
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

    //region Parcelable Implementation

    protected Game(Parcel in) {
        eastPlayer = in.readParcelable(Player.class.getClassLoader());
        southPlayer = in.readParcelable(Player.class.getClassLoader());
        westPlayer = in.readParcelable(Player.class.getClassLoader());
        northPlayer = in.readParcelable(Player.class.getClassLoader());
        minPointsToWin = in.readInt();
        riichiStickCount = in.readInt();
        honbaStickCount = in.readInt();
        roundNumber = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(eastPlayer, flags);
        dest.writeParcelable(southPlayer, flags);
        dest.writeParcelable(westPlayer, flags);
        dest.writeParcelable(northPlayer, flags);
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
}
