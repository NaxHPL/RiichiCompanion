package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Toitoi implements Yaku {

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
        return arrangement.getPonCount() == 4
            && arrangement.getPairCount() == 1
            && arrangement.getChiiCount() == 0;
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
        return "Toitoi";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return new ArrayList<Class<? extends Yaku>>(7) {{
            add(MenzenTsumo.class);
            add(Pinfu.class);
            add(Iipeikou.class);
            add(Ittsuu.class);
            add(SanshokuDoujun.class);
            add(Ryanpeikou.class);
            add(Chiitoitsu.class);
        }};
    }
}
