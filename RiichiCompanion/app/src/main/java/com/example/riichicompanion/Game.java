package com.example.riichicompanion;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.util.ArrayDeque;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Game implements Parcelable {

    private final Player bottomPlayer;
    private final Player rightPlayer;
    private final Player topPlayer;
    private final Player leftPlayer;
    private final int initialPoints;
    private final int minPointsToWin;
    private int riichiCount;
    private int honbaCount;
    private int roundNumber;
    private final int numberOfPlayers;
    private final String startDateTime;
    private final GameLength gameLength;
    private final boolean useTsumoLoss;
    private boolean isFinished;
    private boolean dealerRecentlyWonInAllLast;
    private final ArrayDeque<GameState> gameStateStack;

    public Game(Player bottomPlayer, Player rightPlayer, Player topPlayer, Player leftPlayer, int initialPoints,
                int minPointsToWin, int numberOfPlayers, GameLength gameLength, boolean useTsumoLoss) {
        this.bottomPlayer = bottomPlayer;
        this.rightPlayer = rightPlayer;
        this.topPlayer = topPlayer;
        this.leftPlayer = leftPlayer;
        this.initialPoints = initialPoints;
        this.minPointsToWin = minPointsToWin;
        this.riichiCount = 0;
        this.honbaCount = 0;
        this.roundNumber = 1;
        this.numberOfPlayers = numberOfPlayers;
        this.gameLength = gameLength;
        this.useTsumoLoss = useTsumoLoss;
        this.isFinished = false;
        this.dealerRecentlyWonInAllLast = false;
        this.gameStateStack = new ArrayDeque<>();

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
        initialPoints = in.readInt();
        minPointsToWin = in.readInt();
        riichiCount = in.readInt();
        honbaCount = in.readInt();
        roundNumber = in.readInt();
        numberOfPlayers = in.readInt();
        startDateTime = in.readString();
        gameLength = GameLength.valueOf(in.readString());
        useTsumoLoss = in.readInt() == 1;
        isFinished = in.readInt() == 1;
        dealerRecentlyWonInAllLast = in.readInt() == 1;

        ArrayDeque<GameState> deque = (ArrayDeque<GameState>) in.readSerializable();
        if (deque != null)
            gameStateStack = deque;
        else
            gameStateStack = new ArrayDeque<>();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(bottomPlayer, flags);
        dest.writeParcelable(rightPlayer, flags);
        dest.writeParcelable(topPlayer, flags);
        dest.writeParcelable(leftPlayer, flags);
        dest.writeInt(initialPoints);
        dest.writeInt(minPointsToWin);
        dest.writeInt(riichiCount);
        dest.writeInt(honbaCount);
        dest.writeInt(roundNumber);
        dest.writeInt(numberOfPlayers);
        dest.writeString(startDateTime);
        dest.writeString(gameLength.name());
        dest.writeInt(useTsumoLoss ? 1 : 0);
        dest.writeInt(isFinished ? 1 : 0);
        dest.writeInt(dealerRecentlyWonInAllLast ? 1 : 0);
        dest.writeSerializable(gameStateStack);
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

    public int getInitialPoints() {
        return initialPoints;
    }

    public int getMinPointsToWin() {
        return minPointsToWin;
    }

    public int getRiichiCount() {
        return riichiCount;
    }

    public int getHonbaCount() {
        return honbaCount;
    }

    public void setHonbaCount(int honbaCount) {
        this.honbaCount = honbaCount;
    }

    public void setRiichiCount(int riichiCount) {
        this.riichiCount = riichiCount;
    }

    public void incrementRiichiStickCount() {
        riichiCount++;
    }

    public void incrementHonbaCount() {
        honbaCount++;
    }

    public int getRoundNumberForDisplay() {
        int numForDisplay = roundNumber % 4;
        return numForDisplay == 0 ? 4 : numForDisplay;
    }

    public Wind getPrevalentWind() {
        int d = roundNumber <= 1 ? 0 : (roundNumber - 1) / 4;

        switch (d % 4) {
            case 0:
                return Wind.East;
            case 1:
                return Wind.South;
            case 2:
                return Wind.West;
            default:
                return Wind.North;
        }
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

    public boolean usesTsumoLoss() {
        return useTsumoLoss;
    }

    public int getHonbaValue() {
        return getNumberOfPlayers() == 3 ? 200 : 300;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public void setDealerRecentlyWonInAllLast(boolean dealerWon) {
        this.dealerRecentlyWonInAllLast = dealerWon;
    }

    public Player[] getPlayers() {
        if (numberOfPlayers == 3)
            return new Player[] {bottomPlayer, rightPlayer, topPlayer};
        else
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

        if (numberOfPlayers == 3 && roundNumber % 4 == 0)
            roundNumber++;
    }

    private void rotatePlayerSeats() {
        if (numberOfPlayers == 3) {
            for (Player player : getPlayers()) {
                switch (player.getSeatWind()) {
                    case East:
                        player.setSeatWind(Wind.West);
                        break;
                    case South:
                        player.setSeatWind(Wind.East);
                        break;
                    case West:
                        player.setSeatWind(Wind.South);
                }
            }

            return;
        }

        for (Player player : getPlayers()) {
            switch (player.getSeatWind()) {
                case East:
                    player.setSeatWind(Wind.North);
                    break;
                case South:
                    player.setSeatWind(Wind.East);
                    break;
                case West:
                    player.setSeatWind(Wind.South);
                    break;
                case North:
                    player.setSeatWind(Wind.West);
            }
        }
    }

    public boolean satisfiesFinishConditions() {
        if (isInExtraRound() && aPlayerHasEnoughPointsToWin())
            return true;
        if (aPlayerHasNegativeScore())
            return true;
        if (dealerRecentlyWonInAllLast && getDealer() == getPlayerInFirst())
            return true;

        return false;
    }

    private boolean aPlayerHasNegativeScore() {
        for (Player player : getPlayers()) {
            if (player.getScore() < 0)
                return true;
        }

        return false;
    }

    private boolean aPlayerHasEnoughPointsToWin() {
        for (Player player : getPlayers()) {
            if (player.getScore() >= minPointsToWin)
                return true;
        }

        return false;
    }

    private boolean isInExtraRound() {
        if (gameLength == GameLength.Tonpuusen)
            return roundNumber > 4;
        else
            return roundNumber > 8;
    }

    public boolean isInAllLast() {
        if (gameLength == GameLength.Tonpuusen) {
            return numberOfPlayers == 3 ?
                roundNumber == 3 :
                roundNumber == 4;
        }
        else {
            return numberOfPlayers == 3 ?
                roundNumber == 7 :
                roundNumber == 8;
        }
    }

    private Player getPlayerInFirst() {
        Player[] players = getPlayers();
        Player playerInFirst = players[0];

        for (int i = 1; i < players.length; i++) {
            if (players[i].getScore() > playerInFirst.getScore())
                playerInFirst = players[i];
        }

        return playerInFirst;
    }

    public void saveGameState() {
        gameStateStack.push(new GameState(
            bottomPlayer.getScore(), bottomPlayer.getSeatWind(),
            rightPlayer.getScore(), rightPlayer.getSeatWind(),
            topPlayer.getScore(), topPlayer.getSeatWind(),
            leftPlayer.getScore(), leftPlayer.getSeatWind(),
            riichiCount,
            honbaCount,
            roundNumber,
            isFinished,
            dealerRecentlyWonInAllLast
        ));
    }

    public void restoreLastGameState() {
        if (!hasPreviousRoundState())
            return;

        GameState stateToRestore = gameStateStack.pop();

        bottomPlayer.setScore(stateToRestore.getBottomPlayerScore());
        bottomPlayer.setSeatWind(stateToRestore.getBottomPlayerSeatWind());
        rightPlayer.setScore(stateToRestore.getRightPlayerScore());
        rightPlayer.setSeatWind(stateToRestore.getRightPlayerSeatWind());
        topPlayer.setScore(stateToRestore.getTopPlayerScore());
        topPlayer.setSeatWind(stateToRestore.getTopPlayerSeatWind());
        leftPlayer.setScore(stateToRestore.getLeftPlayerScore());
        leftPlayer.setSeatWind(stateToRestore.getLeftPlayerSeatWind());

        riichiCount = stateToRestore.getRiichiCount();
        honbaCount = stateToRestore.getHonbaCount();
        roundNumber = stateToRestore.getRoundNumber();
        isFinished = stateToRestore.getIsFinished();
        dealerRecentlyWonInAllLast = stateToRestore.getDealerRecentlyWonInAllLast();
    }

    public boolean hasPreviousRoundState() {
        return !gameStateStack.isEmpty();
    }
}
