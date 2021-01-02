package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.Meld;
import com.example.riichicompanion.handcalculation.MeldType;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Suukantsu implements Yaku {

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
        int kanCount = 0;

        for (Meld meld : hand.getMelds()) {
            if (meld.getMeldType() != MeldType.Kan && meld.getMeldType() != MeldType.ClosedKan)
                return false;

            kanCount++;
        }

        return kanCount == 4;
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
        return "Suukantsu";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return new ArrayList<Class<? extends Yaku>>(2) {{
            add(KokushiMusou.class);
            add(ChuurenPoutou.class);
        }};
    }
}
