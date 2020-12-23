package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.Wind;
import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.Tile;
import com.example.riichicompanion.handcalculation.TileGroup;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Pinfu implements Yaku {

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
        if (hand.isOpen() || arrangement.getPonCount() > 0 || arrangement.getPairCount() != 1)
            return false;

        Wind prevWind = conditions.getPrevWind();
        Wind seatWind = conditions.getSeatWind();
        int[] tileCounts = hand.getTileCountsClone();

        // Check if the hand contains any yakuhai tiles
        if ((prevWind == Wind.East || seatWind == Wind.East) && tileCounts[27] > 0)
            return false;
        if ((prevWind == Wind.South || seatWind == Wind.South) && tileCounts[28] > 0)
            return false;
        if ((prevWind == Wind.West || seatWind == Wind.West) && tileCounts[29] > 0)
            return false;
        if ((prevWind == Wind.North || seatWind == Wind.North) && tileCounts[30] > 0)
            return false;
        if (tileCounts[31] > 0 || tileCounts[32] > 0 || tileCounts[33] > 0)
            return false;

        Tile winTile = hand.getWinTile();
        for (TileGroup group : arrangement.getGroups()) {
            ArrayList<Tile> tiles = group.getTiles();

            if (tiles.size() != 3)
                continue;

            // Check for edge wait
            if (tiles.get(0).getRank() == 1 && tiles.get(2).isSameAs(winTile))
                continue;
            if (tiles.get(2).getRank() == 9 && tiles.get(0).isSameAs(winTile))
                continue;

            // Check for middle wait
            if (tiles.get(1).isSameAs(winTile))
                continue;

            return true;
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
        return "Pinfu";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return null;
    }
}
