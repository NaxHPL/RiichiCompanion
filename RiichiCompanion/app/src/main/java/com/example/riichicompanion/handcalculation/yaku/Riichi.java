package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Riichi implements Yaku {

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
        return conditions.isRiichi();
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
        return "Riichi";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return new ArrayList<Class<? extends Yaku>>(1) {{
            add(DoubleRiichi.class);
        }};
    }
}
