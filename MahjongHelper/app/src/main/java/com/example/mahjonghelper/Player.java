package com.example.mahjonghelper;

import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable {

    private final String name;
    private int score;
    private Wind seatWind;

    public Player(String name, int score, Wind seatWind) {
        this.name = name;
        this.score = score;
        this.seatWind = seatWind;
    }

    //region Parcelable Implementation

    protected Player(Parcel in) {
        name = in.readString();
        score = in.readInt();
        seatWind = Wind.valueOf(in.readString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(score);
        dest.writeString(seatWind.name());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    //endregion

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void changeScoreBy(int amount) {
        this.score += amount;
    }

    public Wind getSeatWind() {
        return seatWind;
    }

    public void setSeatWind(Wind seatWind) {
        this.seatWind = seatWind;
    }
}
