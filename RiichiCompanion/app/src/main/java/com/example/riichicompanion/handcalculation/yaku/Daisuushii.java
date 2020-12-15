package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Daisuushii implements Yaku {

    private static final ArrayList<Class<? extends Yaku>> invalidYaku = new ArrayList<Class<? extends Yaku>>() {{
        add(KokushiMusou.class);
        add(Daisangen.class);
        add(Ryuuiisou.class);
        add(Chinroutou.class);
        add(ChuurenPoutou.class);
    }};

    @Override
    public boolean isConditionMet(Hand hand, WinConditions conditions) {
        int[] tileCounts = hand.getTileCountsClone();
        return tileCounts[27] >= 3 && tileCounts[28] >= 3 && tileCounts[29] >= 3 && tileCounts[30] >= 3;
    }

    @Override
    public int getYakumans() {
        return 2;
    }

    @Override
    public int getHan() {
        return 0;
    }

    @Override
    public String getDisplayName() {
        return "Daisuushii";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return invalidYaku;
    }
}
