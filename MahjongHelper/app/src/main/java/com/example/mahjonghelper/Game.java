package com.example.mahjonghelper;

import android.os.Parcel;
import android.os.Parcelable;

public class Game implements Parcelable {

    private Player eastPlayer;
    private Player southPlayer;
    private Player westPlayer;
    private Player northPlayer;
    private int minPointsToWin;

    public Game(Player eastPlayer, Player southPlayer, Player westPlayer, Player northPlayer, int minPointsToWin) {
        this.eastPlayer = eastPlayer;
        this.southPlayer = southPlayer;
        this.westPlayer = westPlayer;
        this.northPlayer = northPlayer;
        this.minPointsToWin = minPointsToWin;
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

    //region Parcelable Implementation

    protected Game(Parcel in) {
        eastPlayer = in.readParcelable(Player.class.getClassLoader());
        southPlayer = in.readParcelable(Player.class.getClassLoader());
        westPlayer = in.readParcelable(Player.class.getClassLoader());
        northPlayer = in.readParcelable(Player.class.getClassLoader());
        minPointsToWin = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(eastPlayer, flags);
        dest.writeParcelable(southPlayer, flags);
        dest.writeParcelable(westPlayer, flags);
        dest.writeParcelable(northPlayer, flags);
        dest.writeInt(minPointsToWin);
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
