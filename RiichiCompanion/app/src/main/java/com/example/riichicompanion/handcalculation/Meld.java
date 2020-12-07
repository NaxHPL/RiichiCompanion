package com.example.riichicompanion.handcalculation;

import androidx.annotation.NonNull;

public class Meld {

    private final MeldType meldType;
    private final Tile[] tiles;

    public Meld(MeldType meldType, Tile[] tiles) {
        this.meldType = meldType;
        this.tiles = tiles;
    }

    public MeldType getMeldType() {
        return meldType;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    @NonNull
    @Override
    public String toString() {
        String str = meldType.toString() + ":";

        for (Tile t : tiles)
            str = str.concat(" " + t.getStringRep());

        return str;
    }
}
