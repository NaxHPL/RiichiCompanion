package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Ryuuiisou implements Yaku {

    @Override
    public boolean isConditionMet(Hand hand, WinConditions conditions) {
        int[] tileCounts = hand.getTileCountsClone();

        for (int i = 0; i < tileCounts.length; i++) {
            if (i != 19 && i != 20 && i != 21 && i != 23 && i != 25 && i != 31) {
                if (tileCounts[i] > 0)
                    return false;
            }
        }

        return true;
    }

    @Override
    public int getYakumans() {
        return 1;
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
        return "Ryuuiisou";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return null;
    }
}
