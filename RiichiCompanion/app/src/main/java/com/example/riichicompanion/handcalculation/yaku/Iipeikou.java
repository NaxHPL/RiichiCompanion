package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.Tile;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Iipeikou implements Yaku {

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
        if (hand.isOpen() || arrangement.getChiiCount() == 0)
            return false;

        ArrayList<String> initialChiiTiles = new ArrayList<>();

        for (ArrayList<Tile> set : arrangement.getSets()) {
            if (!Tile.isChii(set))
                continue;

            if (initialChiiTiles.contains(set.get(0).getStringRep()))
                return true;

            initialChiiTiles.add(set.get(0).getStringRep());
        }

        return false;
    }

    @Override
    public int getYakumans() {
        return 0;
    }

    @Override
    public int getHan() {
        return 1;
    }

    @Override
    public String getDisplayName() {
        return "Iipeikou";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return null;
    }
}
