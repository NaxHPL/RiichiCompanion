package com.example.riichicompanion.handcalculation;

import java.util.ArrayList;
import java.util.List;

public class HandArrangement {

    private ArrayList<ArrayList<Tile>> sets;
    private int ponCount;
    private int chiiCount;
    private int pairCount;

    public HandArrangement() {
        this.sets = new ArrayList<>();
        this.ponCount = 0;
        this.chiiCount = 0;
        this.pairCount = 0;
    }

    public void addSet(ArrayList<Tile> set, SetType setType) {
        sets.add(set);

        if (setType == SetType.Pon)
            ponCount++;
        else if (setType == SetType.Chii)
            chiiCount++;
        else
            pairCount++;
    }

    public ArrayList<ArrayList<Tile>> getSets() {
        return sets;
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
