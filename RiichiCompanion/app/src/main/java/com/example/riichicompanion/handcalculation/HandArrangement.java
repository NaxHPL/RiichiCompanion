package com.example.riichicompanion.handcalculation;

import java.util.ArrayList;

public class HandArrangement {

    private final ArrayList<TileGroup> groups;
    private int ponCount;
    private int chiiCount;
    private int pairCount;

    public HandArrangement() {
        this.groups = new ArrayList<>();
        this.ponCount = 0;
        this.chiiCount = 0;
        this.pairCount = 0;
    }

    public void addGroup(TileGroup group) {
        groups.add(group);

        if (group.getGroupType() == GroupType.Pon)
            ponCount++;
        else if (group.getGroupType() == GroupType.Chii)
            chiiCount++;
        else
            pairCount++;
    }

    public ArrayList<TileGroup> getGroups() {
        return groups;
    }

    public int getPonCount() {
        return ponCount;
    }

    public int getChiiCount() {
        return chiiCount;
    }

    public int getPairCount() {
        return pairCount;
    }
}
