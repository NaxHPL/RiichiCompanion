package com.example.riichicompanion;

import android.os.Parcel;
import android.os.Parcelable;

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
        int numForDisplay = roundNumber % 4;
        return numForDisplay == 0 ? 4 : numForDisplay;
    }

    public Wind getPrevalentWind() {
        if (roundNumber <= 4)
            return Wind.East;
        if (roundNumber <= 8)
            return Wind.South;

        return Wind.West;
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

    public Player[] getPlayers() {
        return new Player[] {bottomPlayer, rightPlayer, topPlayer, leftPlayer};
    }

    public Player getDealer() {
        for (Player player : getPlayers()) {
            if (player.isDealer())
                return player;
        }

        return null;
    }

    public void nextRound() {
        rotatePlayerSeats();
        roundNumber++;
    }

    private void rotatePlayerSeats() {
        for (Player player : getPlayers())
            player.incrementSeatWind();
    }
}
