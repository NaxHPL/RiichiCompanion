package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.Meld;
import com.example.riichicompanion.handcalculation.MeldType;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Sankantsu implements Yaku {

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
        int kanCount = 0;

        for (Meld meld : hand.getMelds()) {
            if (meld.getMeldType() != MeldType.Kan && meld.getMeldType() != MeldType.ClosedKan)
                return false;

            kanCount++;
        }

        return kanCount == 3;
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
        return "Sankantsu";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return null;
    }
}
