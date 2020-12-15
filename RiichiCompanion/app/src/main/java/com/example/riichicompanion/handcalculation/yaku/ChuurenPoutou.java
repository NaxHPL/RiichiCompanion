package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.Suit;
import com.example.riichicompanion.handcalculation.Tile;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class ChuurenPoutou implements Yaku {

    private boolean junsei = false;

    @Override
    public boolean isConditionMet(Hand hand, WinConditions conditions) {
        if (hand.isOpen())
            return false;

        Suit firstTileSuit = hand.getConcealedTiles()[0].getSuit();

        if (firstTileSuit == null)
            return false;

        int offset = 0;

        if (firstTileSuit == Suit.Pin)
            offset = 9;
        else if (firstTileSuit == Suit.Sou)
            offset = 18;

        int[] tileCounts = hand.getTileCountsClone();

        boolean hasNineGates =
            tileCounts[offset] >= 3 &&
            tileCounts[1 + offset] >= 1 &&
            tileCounts[2 + offset] >= 1 &&
            tileCounts[3 + offset] >= 1 &&
            tileCounts[4 + offset] >= 1 &&
            tileCounts[5 + offset] >= 1 &&
            tileCounts[6 + offset] >= 1 &&
            tileCounts[7 + offset] >= 1 &&
            tileCounts[8 + offset] >= 3;

        if (!hasNineGates)
            return false;

        int winTileCount = tileCounts[Tile.tileIndices.get(hand.getWinTile().getStringRep())];
        junsei = winTileCount == 2 || winTileCount == 4;

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
        return junsei ? "Junsei chuuren poutou" : "Chuuren poutou";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return null;
    }
}
