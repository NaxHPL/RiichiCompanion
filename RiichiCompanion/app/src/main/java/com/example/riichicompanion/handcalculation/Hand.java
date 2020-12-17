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

    public int[] getTileCountsClone() {
        return tileCounts.clone();
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
        for (Meld meld : melds) {
            if (meld.getMeldType() != MeldType.ClosedKan)
                return true;
        }

        return false;
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

    public boolean isComplete() {
        if (getTotalTileCount() != 14)
            return false;

        return hasValidSetsAndPair() || hasSevenPairs() || hasThirteenOrphans();
    }

    private boolean hasValidSetsAndPair() {
        int[] countsWithoutExtraKanTile = tileCounts.clone();

        for (Meld meld : melds) {
            MeldType type = meld.getMeldType();
            if (type == MeldType.Kan || type == MeldType.ClosedKan)
                countsWithoutExtraKanTile[Tile.tileIndices.get(meld.getTiles()[0].getStringRep())]--;
        }

        ArrayList<Integer> possiblePairIndices = new ArrayList<>();

        for (int i = 0; i < countsWithoutExtraKanTile.length; i++) {
            if (countsWithoutExtraKanTile[i] >= 2)
                possiblePairIndices.add(i);
        }

        for (Integer index : possiblePairIndices) {
            int[] counts = countsWithoutExtraKanTile.clone();
            int setsFound = 0;

            counts[index] -= 2;

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
            }

            if (setsFound == 4)
                return true;
        }

        return false;
    }

    private boolean hasSevenPairs() {
        if (isOpen())
            return false;

        for (int tileCount : tileCounts) {
            if (tileCount > 0 && tileCount != 2)
                return false;
        }

        return true;
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
