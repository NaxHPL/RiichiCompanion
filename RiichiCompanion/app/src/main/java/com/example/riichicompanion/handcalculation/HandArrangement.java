package com.example.riichicompanion.handcalculation;

import java.util.ArrayList;
import java.util.List;

public class HandArrangement {

    ArrayList<ArrayList<Tile>> sets;

    public HandArrangement() {
        this.sets = new ArrayList<>();
    }

    public void addSet(ArrayList<Tile> set) {
        sets.add(set);
    }

    public ArrayList<ArrayList<Tile>> getSets() {
        return sets;
    }
}
