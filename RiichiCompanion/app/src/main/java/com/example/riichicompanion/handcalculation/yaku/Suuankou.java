package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.Tile;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Suuankou implements Yaku {

    private boolean tanki = false;

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
        if (hand.isOpen())
            return false;

        int[] tileCounts = hand.getTileCountsClone();
        int tripletsFound = 0;

        for (int count : tileCounts) {
            if (count >= 3)
                tripletsFound++;
        }

        if (tripletsFound != 4)
            return false;

        int winTileCount = tileCounts[Tile.tileIndices.get(hand.getWinTile().getStringRep())];
        if (winTileCount == 2) {
            tanki = true;
            return true;
        }

        return conditions.isTsumo();
    }

    @Override
    public int getYakumans() {
        return tanki ? 2 : 1;
    }

    @Override
    public int getHan() {
        return 0;
    }

    @Override
    public String getDisplayName() {
        return tanki ? "Suuankou tanki" : "Suuankou";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return new ArrayList<Class<? extends Yaku>>(2) {{
            add(KokushiMusou.class);
            add(ChuurenPoutou.class);
        }};
    }
}
