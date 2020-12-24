package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Tanyao implements Yaku {

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
        int[] tileCounts = hand.getTileCountsClone();

        for (int i = 0; i < tileCounts.length; i++) {
            if (i == 0 || i == 8 || i == 9 || i == 17 || i == 18 || i >= 26) {
                if (tileCounts[i] > 0)
                    return false;
            }
        }

        return true;
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
        return "Tanyao";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return new ArrayList<Class<? extends Yaku>>(7) {{
            add(Ittsuu.class);
            add(Yakuhai.class);
            add(Chantaiyao.class);
            add(JunchanTaiyao.class);
            add(Shousangen.class);
            add(Honroutou.class);
            add(Honitsu.class);
        }};
    }
}
