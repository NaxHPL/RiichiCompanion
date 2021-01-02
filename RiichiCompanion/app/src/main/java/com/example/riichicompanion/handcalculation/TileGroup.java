package com.example.riichicompanion.handcalculation;

import com.example.riichicompanion.Wind;

import java.util.ArrayList;

public class TileGroup {

    private final ArrayList<Tile> tiles;
    private final GroupType groupType;
    private boolean isOpenMeld;

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

    public void setOpenMeld(boolean openMeld) {
        isOpenMeld = openMeld;
    }

    public FuItem getFuItem(Wind prevWind, Wind seatWind, boolean open) {
        if (groupType == GroupType.Chii)
            return null;
        else if (groupType == GroupType.Pair) {
            Tile firstTile = tiles.get(0);

            if (firstTile.isSuited())
                return null;
            if (firstTile.isDragon())
                return new FuItem("Dragon pair", 2);
            if (firstTile.getWind() == prevWind && firstTile.getWind() == seatWind)
                return new FuItem("Double wind pair", 4);
            if (firstTile.getWind() == prevWind)
                return new FuItem("Prevalent wind pair", 2);
            if (firstTile.getWind() == seatWind)
                return new FuItem("Seat wind pair", 2);

            return null;
        }
        else { // Group is 3 or 4 of a kind
            boolean isKan = tiles.size() == 4;
            boolean isSimple = tiles.get(0).isSimple();

            if (open && !isKan) {
                return isSimple ?
                    new FuItem("Open simples triplet", 2) :
                    new FuItem("Open non-simples triplet", 4);
            }
            if (open && isKan) {
                return isSimple ?
                    new FuItem("Open simples kan", 8) :
                    new FuItem("Open non-simples kan", 16);
            }
            if (!open && !isKan) {
                return isSimple ?
                    new FuItem("Closed simples triplet", 4) :
                    new FuItem("Closed non-simples triplet", 8);
            }
            if (!open && isKan) {
                return isSimple ?
                    new FuItem("Closed simples kan", 16) :
                    new FuItem("Closed non-simples kan", 32);
            }

            return null;
        }
    }

    public boolean containsTile(Tile tile) {
        for (Tile t : tiles) {
            if (t.isSameAs(tile))
                return true;
        }

        return false;
    }
}
