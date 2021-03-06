package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Shousuushii implements Yaku {

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
        int[] tileCounts = hand.getTileCountsClone();
        int windTripletsFound = 0;
        boolean windPairFound = false;

        for (int i = 27; i <= 30; i++) {
            if (tileCounts[i] >= 3)
                windTripletsFound++;
            else if (tileCounts[i] == 2)
                windPairFound = true;
        }

        return windPairFound && windTripletsFound == 3;
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
        return "Shousuushii";
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
