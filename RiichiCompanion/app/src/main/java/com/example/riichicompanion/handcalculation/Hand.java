package com.example.riichicompanion.handcalculation;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

public class Hand {

    private final HashMap<String, Integer> mapTileCounts;
    private final ArrayList<Tile> concealedTiles;
    private final ArrayList<Meld> melds;
    private Tile winTile;

    public Hand() {
        this.mapTileCounts = new HashMap<>();
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

    public int getTileCount(String tileStr) {
        Integer count =  mapTileCounts.get(tileStr);
        return count == null ? 0 : count;
    }

    public int getTotalTileCount() {
        return concealedTiles.size() + (melds.size() * 3) + (winTile == null ? 0 : 1);
    }

    public boolean isValid() {
        if (getTotalTileCount() != 14)
            return false;

        // TODO: Check more things...

        return true;
    }

    private void incrementTileCount(String key) {
        if (!mapTileCounts.containsKey(key))
            mapTileCounts.put(key, 0);

        mapTileCounts.put(key, mapTileCounts.get(key) + 1);
    }

    private void decrementTileCount(String key) {
        if (!mapTileCounts.containsKey(key))
            return;

        mapTileCounts.put(key, Math.max(mapTileCounts.get(key) - 1, 0));
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
}
