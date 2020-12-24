package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Chiitoitsu implements Yaku {

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
        return !hand.isOpen() && arrangement.getPairCount() == 7;
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
        return "Chiitoitsu";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return new ArrayList<Class<? extends Yaku>>(15) {{
            add(Pinfu.class);
            add(Iipeikou.class);
            add(Ittsuu.class);
            add(Yakuhai.class);
            add(SanshokuDoujun.class);
            add(SanshokuDoukou.class);
            add(Toitoi.class);
            add(Sanankou.class);
            add(Sankantsu.class);
            add(Chantaiyao.class);
            add(JunchanTaiyao.class);
            add(Ryanpeikou.class);
            add(Shousangen.class);
            add(Rinshan.class);
            add(Chankan.class);
        }};
    }
}
