package com.example.riichicompanion.handcalculation;

import java.util.ArrayList;

public class TileGroup {

    private final ArrayList<Tile> tiles;
    private final GroupType groupType;
    private final boolean isOpenMeld;

    public TileGroup(ArrayList<Tile> tiles, GroupType groupType, boolean isOpenMeld) {
        this.tiles = tiles;
        this.groupType = groupType;
        this.isOpenMeld = isOpenMeld;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public boolean isOpenMeld() {
        return isOpenMeld;
    }
}
