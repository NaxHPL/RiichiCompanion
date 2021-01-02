package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Shousangen implements Yaku {

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
        int[] tileCounts = hand.getTileCountsClone();
        int dragonTripletsFound = 0;
        boolean dragonPairFound = false;

        for (int i = 31; i <= 33; i++) {
            if (tileCounts[i] >= 3)
                dragonTripletsFound++;
            else if (tileCounts[i] == 2)
                dragonPairFound = true;
        }

        return dragonPairFound && dragonTripletsFound == 2;
    }

    @Override
    public int getYakumans() {
        return 0;
    }

    @Override
    public int getHan() {
        return 2;
    }

    @Override
    public String getDisplayName() {
        return "Shousangen";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return new ArrayList<Class<? extends Yaku>>(9) {{
            add(Tanyao.class);
            add(Pinfu.class);
            add(Ittsuu.class);
            add(SanshokuDoujun.class);
            add(SanshokuDoukou.class);
            add(JunchanTaiyao.class);
            add(Ryanpeikou.class);
            add(Chinitsu.class);
            add(Chiitoitsu.class);
        }};
    }
}
