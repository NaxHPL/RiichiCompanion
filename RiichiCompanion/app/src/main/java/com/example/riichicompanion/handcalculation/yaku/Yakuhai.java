package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;
import java.util.Locale;

public class Yakuhai implements Yaku {

    private int yakuhaiCount = 0;

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
        int[] tileCounts = hand.getTileCountsClone();

        int prevWindIdx;
        switch (conditions.getPrevWind()) {
            case East: prevWindIdx = 27; break;
            case South: prevWindIdx = 28; break;
            case West: prevWindIdx = 29; break;
            default: prevWindIdx = 30; break;
        }

        int seatWindIdx;
        switch (conditions.getSeatWind()) {
            case East: seatWindIdx = 27; break;
            case South: seatWindIdx = 28; break;
            case West: seatWindIdx = 29; break;
            default: seatWindIdx = 30; break;
        }

        yakuhaiCount = 0;

        for (int i = 0; i < tileCounts.length; i++) {
            if (tileCounts[i] < 3)
                continue;

            if (i == prevWindIdx || i == seatWindIdx)
                yakuhaiCount += prevWindIdx == seatWindIdx ? 2 : 1;
            else if (i >= 31)
                yakuhaiCount++;
        }

        return yakuhaiCount > 0;
    }

    @Override
    public int getYakumans() {
        return 0;
    }

    @Override
    public int getHan() {
        return yakuhaiCount;
    }

    @Override
    public String getDisplayName() {
        return yakuhaiCount > 1 ?
            String.format(Locale.getDefault(), "Yakuhai x%d", yakuhaiCount) :
            "Yakuhai";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return new ArrayList<Class<? extends Yaku>>(6) {{
            add(Tanyao.class);
            add(Pinfu.class);
            add(JunchanTaiyao.class);
            add(Ryanpeikou.class);
            add(Chinitsu.class);
            add(Chiitoitsu.class);
        }};
    }
}
