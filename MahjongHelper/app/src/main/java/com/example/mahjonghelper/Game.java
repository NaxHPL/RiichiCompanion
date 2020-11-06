package com.example.mahjonghelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Game implements Parcelable {

    private final Player bottomPlayer;
    private final Player rightPlayer;
    private final Player topPlayer;
    private final Player leftPlayer;
    private final int minPointsToWin;
    private int riichiStickCount;
    private int honbaStickCount;
    private int roundNumber;
    private final int numberOfPlayers;
    private final String startDateTime;
    private final GameLength gameLength;

    public Game(Player bottomPlayer, Player rightPlayer, Player topPlayer, Player leftPlayer,
                int minPointsToWin, int riichiStickCount, int honbaStickCount, int roundNumber,
                int numberOfPlayers, GameLength gameLength) {
        this.bottomPlayer = bottomPlayer;
        this.rightPlayer = rightPlayer;
        this.topPlayer = topPlayer;
        this.leftPlayer = leftPlayer;
        this.minPointsToWin = minPointsToWin;
        this.riichiStickCount = riichiStickCount;
        this.honbaStickCount = honbaStickCount;
        this.roundNumber = roundNumber;
        this.numberOfPlayers = numberOfPlayers;
        this.gameLength = gameLength;

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, Locale.getDefault());
        startDateTime = dateFormat.format(date);
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
        numberOfPlayers = in.readInt();
        startDateTime = in.readString();
        gameLength = GameLength.valueOf(in.readString());
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
        dest.writeInt(numberOfPlayers);
        dest.writeString(startDateTime);
        dest.writeString(gameLength.name());
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

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public GameLength getGameLength() {
        return gameLength;
    }

    public void saveAsOngoingGame(Context context) {
        Gson gson = new Gson();
        String json = gson.toJson(this);

        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.ongoing_game_file_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(context.getString(R.string.ongoing_game_key), json);
        editor.apply();
    }

//    public boolean gameIsOver() {
//
//    }
}
