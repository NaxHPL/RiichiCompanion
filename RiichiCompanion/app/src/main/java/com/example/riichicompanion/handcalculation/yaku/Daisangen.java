package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Daisangen implements Yaku {

    @Override
    public boolean isConditionMet(Hand hand, WinConditions conditions) {
        int[] tileCounts = hand.getTileCountsClone();
        return tileCounts[31] >= 3 && tileCounts[32] >= 3 && tileCounts[33] >= 3;
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
        return "Daisangen";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return null;
    }
}
