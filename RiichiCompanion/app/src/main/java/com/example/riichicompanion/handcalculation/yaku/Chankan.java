package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Chankan implements Yaku {

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
        return conditions.isChankan();
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
        return "Chankan";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return null;
    }
}
