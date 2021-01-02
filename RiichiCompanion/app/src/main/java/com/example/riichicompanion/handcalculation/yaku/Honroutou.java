package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Honroutou implements Yaku {

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
        int[] tileCounts = hand.getTileCountsClone();

        for (int i = 0; i < tileCounts.length; i++) {
            if ((i >= 1 && i <= 7) || (i >= 10 && i <= 16) || (i >= 19 && i <= 25)) {
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
        return 2;
    }

    @Override
    public String getDisplayName() {
        return "Honroutou";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return new ArrayList<Class<? extends Yaku>>(10) {{
            add(Tanyao.class);
            add(Pinfu.class);
            add(Iipeikou.class);
            add(Ittsuu.class);
            add(SanshokuDoujun.class);
            add(Chantaiyao.class);
            add(JunchanTaiyao.class);
            add(Ryanpeikou.class);
            add(Chinitsu.class);
            add(Chankan.class);
        }};
    }
}
