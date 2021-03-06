package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Daisuushii implements Yaku {

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
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
        return new ArrayList<Class<? extends Yaku>>(5) {{
            add(KokushiMusou.class);
            add(Daisangen.class);
            add(Ryuuiisou.class);
            add(Chinroutou.class);
            add(ChuurenPoutou.class);
        }};
    }
}
