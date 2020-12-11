package com.example.riichicompanion.handcalculation;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Hand {

    private final int[] tileCounts;
    private final ArrayList<Tile> concealedTiles;
    private final ArrayList<Meld> melds;
    private Tile winTile;

    public Hand() {
        this.tileCounts = new int[34];
        this.concealedTiles = new ArrayList<>();
        this.melds = new ArrayList<>();
        winTile = null;
    }

    public Tile[] getConcealedTiles() {
        Tile[] arr = new Tile[concealedTiles.size()];
        return concealedTiles.toArray(arr);
    }

    public void addConcealedTile(Tile tile) {
        concealedTiles.add(tile);
        incrementTileCount(tile.getStringRep());
    }

    public void removeConcealedTile(Tile tile) {
        concealedTiles.remove(tile);
        decrementTileCount(tile.getStringRep());
    }

    public Meld[] getMelds() {
        Meld[] arr = new Meld[melds.size()];
        return melds.toArray(arr);
    }

    public void addMeld(Meld meld) {
        melds.add(meld);

        for (Tile t : meld.getTiles())
            incrementTileCount(t.getStringRep());
    }

    public void removeMeld(Meld meld) {
        melds.remove(meld);

        for (Tile t : meld.getTiles())
            decrementTileCount(t.getStringRep());
    }

    public Tile getWinTile() {
        return winTile;
    }

    public void setWinTile(Tile tile) {
        if (winTile != null)
            decrementTileCount(winTile.getStringRep());

        winTile = tile;

        if (winTile != null)
            incrementTileCount(winTile.getStringRep());
    }

    public int getTileCount(String stringRep) {
        return tileCounts[Tile.tileIndices.get(stringRep)];
    }

    public int getTotalTileCount() {
        return concealedTiles.size() + (melds.size() * 3) + (winTile == null ? 0 : 1);
    }

    private void incrementTileCount(String key) {
        tileCounts[Tile.tileIndices.get(key)]++;
    }

    private void decrementTileCount(String key) {
        tileCounts[Tile.tileIndices.get(key)]--;
    }

    public boolean isOpen() {
        return melds.size() > 0;
    }

    @NonNull
    @Override
    public String toString() {
        String str = " \n[Hand (" + getTotalTileCount() + ")]\n";

        for (Meld m : melds)
            str = str.concat("[Meld] " + m.toString() + "\n");

        str = str.concat("[Concealed]");

        if (concealedTiles.size() > 0) {
            for (Tile t : concealedTiles)
                str = str.concat(" " + t.getStringRep());
        }

        if (winTile != null)
            str = str.concat("\n[Win Tile] " + winTile.getStringRep());

        return str;
    }

    public boolean isValid() {
        if (getTotalTileCount() != 14)
            return false;

        return hasValidSetsAndPair() || hasSevenPairs() || hasThirteenOrphans();
    }

    private boolean hasValidSetsAndPair() {
        int[] counts = tileCounts.clone();

        for (Meld meld : melds) {
            MeldType type = meld.getMeldType();
            if (type == MeldType.Kan || type == MeldType.ClosedKan)
                counts[Tile.tileIndices.get(meld.getTiles()[0].getStringRep())]--;
        }

        int setsFound = 0;
        boolean hasFoundPair = false;

        // TODO: Fix algorithm: it does not find a valid hand if 3 identical tiles
        //       are used for a pair and first tile of a chii.

        for (int i = 0; i < counts.length; i++) {
            if (counts[i] == 0)
                continue;

            if (counts[i] >= 3) {
                counts[i] -= 3;
                setsFound++;
            }

            if ((i >= 0 && i <= 6) || (i >= 9 && i <= 15) || (i >= 18 && i <= 24)) {
                int minOfChii = Math.min(counts[i], Math.min(counts[i+1], counts[i+2]));
                counts[i] -= minOfChii;
                counts[i+1] -= minOfChii;
                counts[i+2] -= minOfChii;
                setsFound += minOfChii;
            }

            if (counts[i] == 2)
                hasFoundPair = true;
        }

        return setsFound == 4 && hasFoundPair;
    }

    private boolean hasSevenPairs() {
        if (isOpen())
            return false;

        int[] counts = tileCounts.clone();
        int pairsFound = 0;

        for (int i = 0; i < counts.length; i++) {
            while (counts[i] >= 2) {
                counts[i] -= 2;
                pairsFound++;
            }
        }

        return pairsFound == 7;
    }

    private boolean hasThirteenOrphans() {
        if (isOpen())
            return false;

        return tileCounts[0] > 0 &&
            tileCounts[8] > 0 &&
            tileCounts[9] > 0 &&
            tileCounts[17] > 0 &&
            tileCounts[18] > 0 &&
            tileCounts[26] > 0 &&
            tileCounts[27] > 0 &&
            tileCounts[28] > 0 &&
            tileCounts[29] > 0 &&
            tileCounts[30] > 0 &&
            tileCounts[31] > 0 &&
            tileCounts[32] > 0 &&
            tileCounts[33] > 0;
    }
}
