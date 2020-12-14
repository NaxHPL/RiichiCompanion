package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.Tile;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class KokushiMusou implements Yaku {

    private boolean junsei = false;

    @Override
    public boolean isConditionMet(Hand hand, WinConditions conditions) {
        if (hand.isOpen())
            return false;

        int[] tileCounts = hand.getTileCounts();
        boolean hasThirteenOrphans =
            tileCounts[0] > 0 &&
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

        if (!hasThirteenOrphans)
            return false;

        int winTileCount = tileCounts[Tile.tileIndices.get(hand.getWinTile().getStringRep())];
        junsei = winTileCount == 2;

        return true;
    }

    @Override
    public int getYakumans() {
        return junsei ? 2 : 1;
    }

    @Override
    public int getClosedHan() {
        return 0;
    }

    @Override
    public int getOpenHan() {
        return 0;
    }

    @Override
    public String getDisplayName() {
        return junsei ? "Junsei kokushi musou" : "kokushi musou";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return null;
    }
}
