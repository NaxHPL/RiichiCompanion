package com.example.riichicompanion.handcalculation;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hand {

    private final int[] tileCounts;
    private final ArrayList<Tile> concealedTiles;
    private final ArrayList<Meld> melds;
    private Tile winTile;

    private HandArrangement workingArrangement;

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
        Integer idx = Tile.tileIndices.get(stringRep);
        return idx == null ? 0 : tileCounts[idx];
    }

    public int getTotalTileCount() {
        return concealedTiles.size() + (melds.size() * 3) + (winTile == null ? 0 : 1);
    }

    private void incrementTileCount(String key) {
        Integer idx = Tile.tileIndices.get(key);
        if (idx != null)
            tileCounts[idx]++;
    }

    private void decrementTileCount(String key) {
        Integer idx = Tile.tileIndices.get(key);
        if (idx != null)
            tileCounts[idx]--;
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
            if (type == MeldType.Kan || type == MeldType.ClosedKan) {
                Integer idx = Tile.tileIndices.get(meld.getTiles()[0].getStringRep());
                if (idx != null)
                    countsWithoutExtraKanTile[idx]--;
            }
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

                if (i <= 6 || i >= 9 && i <= 15 || i >= 18 && i <= 24) {
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

    public ArrayList<HandArrangement> getAllArrangements() {
        ArrayList<HandArrangement> arrangements = new ArrayList<>();

        Tile[] tiles = new Tile[concealedTiles.size() + 1];
        concealedTiles.toArray(tiles);
        tiles[tiles.length - 1] = winTile;
        Tile.sortTiles(tiles);

        if (hasSevenPairs())
            arrangements.add(getSevenPairsArrangement(tiles));

        // Find possible pairs.
        // When a pair is found: find arrangements of tiles without the pair, then
        // add the pair set to each arrangement found.
        for (int i = 0; i < tiles.length; i++) {
            if (i + 1 >= tiles.length)
                break;

            Tile curTile = tiles[i];
            Tile nextTile = tiles[i + 1];

            if (!curTile.isSameAs(nextTile))
                continue;

            ArrayList<Tile> pair = new ArrayList<>(2);
            pair.add(curTile);
            pair.add(nextTile);

            ArrayList<Tile> tilesWithoutPair = new ArrayList<>(Arrays.asList(tiles));
            tilesWithoutPair.remove(curTile);
            tilesWithoutPair.remove(nextTile);

            ArrayList<HandArrangement> arrangementsWithoutPair = getArrangements(tilesWithoutPair);

            for (HandArrangement arrangement : arrangementsWithoutPair)
                arrangement.addGroup(new TileGroup(pair, GroupType.Pair, false));

            arrangements.addAll(arrangementsWithoutPair);
            i++;
        }

        // Add fixed sets (melds) to arrangements
        for (HandArrangement arrangement : arrangements) {
            for (Meld meld : melds) {
                Tile[] meldTiles = meld.getTiles();
                GroupType groupType = meld.getMeldType() == MeldType.Chii ? GroupType.Chii : GroupType.Pon;

                arrangement.addGroup(new TileGroup(
                    new ArrayList<Tile>(4) {{
                        add(meldTiles[0]);
                        add(meldTiles[1]);
                        add(meldTiles[2]);
                        if (meld.getMeldType() == MeldType.Kan || meld.getMeldType() == MeldType.ClosedKan)
                            add(meldTiles[3]);
                    }},
                    groupType,
                    meld.getMeldType() != MeldType.ClosedKan
                ));
            }
        }

        return arrangements;
    }

    private HandArrangement getSevenPairsArrangement(Tile[] tiles) {
        HandArrangement arrangement = new HandArrangement();

        for (int i = 0; i < tiles.length; i += 2) {
            Tile t1 = tiles[i];
            Tile t2 = tiles[i + 1];
            arrangement.addGroup(new TileGroup(
                new ArrayList<Tile>(2) {{ add(t1); add(t2); }},
                GroupType.Pair,
                false
            ));
        }

        return arrangement;
    }

    private ArrayList<HandArrangement> getArrangements(ArrayList<Tile> remaining) {
        workingArrangement = new HandArrangement();
        return getArrangements(new ArrayList<>(), remaining);
    }

    private ArrayList<HandArrangement> getArrangements(ArrayList<HandArrangement> acc, ArrayList<Tile> remaining) {
        if (remaining.isEmpty()) {
            acc.add(workingArrangement);
            workingArrangement = new HandArrangement();
            return acc;
        }

        ArrayList<ArrayList<Tile>> initialSets = getInitialSets(remaining);

        if (initialSets.isEmpty()) {
            // There are tiles remaining, but can't make a set. The working arrangement is not
            // added to the accumulator because it caused an incomplete hand.
            return acc;
        }

        for (ArrayList<Tile> set : initialSets) {
            workingArrangement.addGroup(new TileGroup(set, Tile.getGroupType(set), false));

            ArrayList<Tile> rest = new ArrayList<>(remaining);
            for (Tile t : set)
                rest.remove(t);

            getArrangements(acc, rest);
        }

        return acc;
    }

    private ArrayList<ArrayList<Tile>> getInitialSets(List<Tile> tiles) {
        ArrayList<ArrayList<Tile>> sets = new ArrayList<>();
        ArrayList<Tile> initChii = getInitialChii(tiles);
        ArrayList<Tile> initPon = getInitialPon(tiles);

        if (initChii != null)
            sets.add(initChii);
        if (initPon != null)
            sets.add(initPon);

        return sets;
    }

    private ArrayList<Tile> getInitialChii(List<Tile> tiles) {
        if (tiles.size() < 3)
            return null;

        ArrayList<Tile> sequence = new ArrayList<Tile>(3) {{ add(tiles.get(0)); }};
        int curIdx = 0;
        int nextIdx = 1;

        while (nextIdx < tiles.size()) {
            // Advance past duplicates
            while (tiles.get(curIdx).isSameAs(tiles.get(nextIdx))) {
                curIdx++;
                nextIdx++;
                if (nextIdx >= tiles.size())
                    return null;
            }

            if (tiles.get(nextIdx).isSameAs(tiles.get(curIdx).nextInSuit())) {
                sequence.add(tiles.get(nextIdx));

                if (sequence.size() == 3)
                    return sequence;

                curIdx = nextIdx;
                nextIdx++;
            }
            else
                return null;
        }

        return null;
    }

    private ArrayList<Tile> getInitialPon(List<Tile> tiles) {
        if (tiles.size() < 3)
            return null;

        Tile t1 = tiles.get(0);
        Tile t2 = tiles.get(1);
        Tile t3 = tiles.get(2);

        if (t1.isSameAs(t2) && t2.isSameAs(t3))
            return new ArrayList<Tile>(3) {{ add(t1); add(t2); add(t3); }};

        return null;
    }
}
