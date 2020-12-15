package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public interface Yaku {
    boolean isConditionMet(Hand hand, WinConditions conditions);
    int getYakumans();
    int getHan();
    String getDisplayName();
    ArrayList<Class<? extends Yaku>> getInvalidYaku();
}
