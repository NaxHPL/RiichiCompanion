package com.example.riichicompanion.handcalculation;

import androidx.annotation.IntRange;

import com.example.riichicompanion.Wind;

import java.util.HashMap;
import java.util.Locale;

public class Tile {

    public final static HashMap<String, Integer> tileIndices = new HashMap<String, Integer>() {{
        put("1m", 0);
        put("2m", 1);
        put("3m", 2);
        put("4m", 3);
        put("5m", 4);
        put("6m", 5);
        put("7m", 6);
        put("8m", 7);
        put("9m", 8);
        put("1p", 9);
        put("2p", 10);
        put("3p", 11);
        put("4p", 12);
        put("5p", 13);
        put("6p", 14);
        put("7p", 15);
        put("8p", 16);
        put("9p", 17);
        put("1s", 18);
        put("2s", 19);
        put("3s", 20);
        put("4s", 21);
        put("5s", 22);
        put("6s", 23);
        put("7s", 24);
        put("8s", 25);
        put("9s", 26);
        put("E", 27);
        put("S", 28);
        put("W", 29);
        put("N", 30);
        put("g", 31);
        put("r", 32);
        put("w", 33);
    }};

    private final Suit suit;
    private final int rank;
    private final Wind wind;
    private final Dragon dragon;
    private final String stringRep;

    public Tile(Suit suit, @IntRange(from = 1, to = 9) int rank) {
        this.suit = suit;
        this.rank = rank;
        this.wind = null;
        this.dragon = null;

        if (suit == Suit.Man)
            stringRep = String.format(Locale.getDefault(), "%dm", rank);
        else if (suit == Suit.Pin)
            stringRep = String.format(Locale.getDefault(), "%dp", rank);
        else
            stringRep = String.format(Locale.getDefault(), "%ds", rank);
    }

    public Tile(Wind wind) {
        this.suit = null;
        this.rank = 0;
        this.wind = wind;
        this.dragon = null;

        if (wind == Wind.East)
            stringRep = "E";
        else if (wind == Wind.South)
            stringRep = "S";
        else if (wind == Wind.West)
            stringRep = "W";
        else
            stringRep = "N";
    }

    public Tile(Dragon dragon) {
        this.suit = null;
        this.rank = 0;
        this.wind = null;
        this.dragon = dragon;

        if (dragon == Dragon.Green)
            stringRep = "g";
        else if (dragon == Dragon.Red)
            stringRep = "r";
        else
            stringRep = "w";
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

    public String getStringRep() {
        return stringRep;
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
        Integer thisIdx = tileIndices.get(stringRep);
        Integer otherIdx = tileIndices.get(other.getStringRep());

        if (thisIdx == null || otherIdx == null)
            return 0;

        return thisIdx.compareTo(otherIdx);
    }

    public Meld getChii() {
        if (!isSuited() || rank == 8 || rank == 9)
            return null;

        Tile[] tiles = new Tile[] {
            this,
            new Tile(suit, rank + 1),
            new Tile(suit, rank + 2)
        };

        return new Meld(MeldType.Chii, tiles);
    }

    public Meld getPon() {
        Tile[] tiles = new Tile[] {this, this, this};
        return new Meld(MeldType.Pon, tiles);
    }

    public Meld getKan() {
        Tile[] tiles = new Tile[] {this, this, this, this};
        return new Meld(MeldType.Kan, tiles);
    }

    public Meld getClosedKan() {
        Tile[] tiles = new Tile[] {this, this, this, this};
        return new Meld(MeldType.ClosedKan, tiles);
    }

    public boolean isSameAs(Tile other) {
        return stringRep.equals(other.getStringRep());
    }

    public static void sortTiles(Tile[] tiles) {
        // Insertion sort
        for (int i = 1; i < tiles.length; i++) {
            Tile t = tiles[i];
            int j = i - 1;
            
            while (j >= 0 && tiles[j].compareTo(t) > 0) {
                tiles[j + 1] = tiles[j];
                j--;
            }

            tiles[j + 1] = t;
        }
    }
}
