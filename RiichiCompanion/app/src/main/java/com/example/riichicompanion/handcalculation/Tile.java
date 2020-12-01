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
}
