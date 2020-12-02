package com.example.riichicompanion.handcalculation;

import androidx.annotation.IntRange;

import com.example.riichicompanion.Wind;

public class Tile {

    public enum Suit { Man, Pin, Sou }
    public enum Dragon { Green, Red, White }

    private final Suit suit;
    private final int rank;
    private final Wind wind;
    private final Dragon dragon;

    public Tile(Suit suit, @IntRange(from = 1, to = 9) int rank) {
        this.suit = suit;
        this.rank = rank;
        this.wind = null;
        this.dragon = null;
    }

    public Tile(Wind wind) {
        this.suit = null;
        this.rank = 0;
        this.wind = wind;
        this.dragon = null;
    }

    public Tile(Dragon dragon) {
        this.suit = null;
        this.rank = 0;
        this.wind = null;
        this.dragon = dragon;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }

    public Wind getWind() {
        return wind;
    }

    public Dragon getDragon() {
        return dragon;
    }

    public boolean isSuited() {
        return suit != null;
    }

    public boolean isWind() {
        return wind != null;
    }
    
    public boolean isDragon() {
        return dragon != null;
    }

    public boolean isHonour() {
        return wind != null || dragon != null;
    }

    public boolean isTerminal() {
        return rank == 1 || rank == 9;
    }

    public boolean isSimple() {
        return isSuited() && !isTerminal();
    }

    public int compareTo(Tile other) {
        if (other == null)
            return 1;

        if (isSuited()) {
            if (other.isSuited()) {
                int thisOrd = suit.ordinal();
                int otherOrd = other.getSuit().ordinal();

                if (thisOrd == otherOrd) {
                    if (rank == other.getRank())
                        return 0;

                    return rank < other.getRank() ? -1 : 1;
                }

                return thisOrd < otherOrd ? -1 : 1;
            }

            return -1;
        }
        else if (isWind()) {
            if (other.isWind()) {
                int thisOrd = wind.ordinal();
                int otherOrd = other.getWind().ordinal();

                if (thisOrd == otherOrd)
                    return 0;

                return thisOrd < otherOrd ? -1 : 1;
            }

            return other.isSuited() ? 1 : -1;
        }
        else {
            if (other.isDragon()) {
                int thisOrd = dragon.ordinal();
                int otherOrd = other.getDragon().ordinal();

                if (thisOrd == otherOrd)
                    return 0;

                return thisOrd < otherOrd ? -1 : 1;
            }

            return 1;
        }
    }
}
