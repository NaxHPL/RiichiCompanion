package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Chinroutou implements Yaku {

    private static final ArrayList<Class<? extends Yaku>> invalidYaku = new ArrayList<Class<? extends Yaku>>() {{
        add(KokushiMusou.class);
        add(Daisangen.class);
        add(Shousuushii.class);
        add(Daisuushii.class);
        add(Tsuuiisou.class);
        add(Ryuuiisou.class);
        add(ChuurenPoutou.class);
    }};

    @Override
    public boolean isConditionMet(Hand hand, WinConditions conditions) {
        int[] tileCounts = hand.getTileCountsClone();

        for (int i = 0; i < tileCounts.length; i++) {
            if (i != 0 && i != 8 && i != 9 && i != 17 && i != 18 && i != 26) {
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
    public int getHan() {
        return 0;
    }

    @Override
    public String getDisplayName() {
        return "Chinroutou";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return invalidYaku;
    }
}
