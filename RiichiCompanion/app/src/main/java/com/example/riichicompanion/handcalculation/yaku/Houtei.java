package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Houtei implements Yaku {

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
        return conditions.isHaiteiOrHoutei() && !conditions.isTsumo();
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
        return "Houtei raoyui";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return new ArrayList<Class<? extends Yaku>>(5) {{
            add(Ippatsu.class);
            add(MenzenTsumo.class);
            add(Rinshan.class);
            add(Haitei.class);
            add(Chankan.class);
        }};
    }
}
