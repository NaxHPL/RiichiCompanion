package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Daisangen implements Yaku {

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
        int[] tileCounts = hand.getTileCountsClone();
        return tileCounts[31] >= 3 && tileCounts[32] >= 3 && tileCounts[33] >= 3;
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
        return "Daisangen";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return new ArrayList<Class<? extends Yaku>>(6) {{
            add(KokushiMusou.class);
            add(Shousuushii.class);
            add(Daisuushii.class);
            add(Ryuuiisou.class);
            add(Chinroutou.class);
            add(ChuurenPoutou.class);
        }};
    }
}
