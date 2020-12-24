package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.TileGroup;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;
import java.util.HashMap;

public class Ryanpeikou implements Yaku {

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
        if (hand.isOpen() || arrangement.getChiiCount() < 4)
            return false;

        HashMap<String, Integer> chiiCounts = new HashMap<>();

        for (TileGroup group : arrangement.getGroups()) {
            String key = group.getTiles().get(0).getStringRep();
            if (chiiCounts.containsKey(key))
                chiiCounts.put(key, chiiCounts.get(key) + 1);
            else
                chiiCounts.put(key, 1);
        }

        boolean foundIipeikou = false;

        for (String key : chiiCounts.keySet()) {
            int count = chiiCounts.get(key);

            if (count == 4)
                return true;
            else if (count >= 2) {
                if (foundIipeikou)
                    return true;
                else
                    foundIipeikou = true;
            }
        }

        return false;
    }

    @Override
    public int getYakumans() {
        return 0;
    }

    @Override
    public int getHan() {
        return 3;
    }

    @Override
    public String getDisplayName() {
        return "Ryanpeikou";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return new ArrayList<Class<? extends Yaku>>(13) {{
            add(Iipeikou.class);
            add(Ittsuu.class);
            add(Yakuhai.class);
            add(SanshokuDoujun.class);
            add(SanshokuDoukou.class);
            add(Toitoi.class);
            add(Sanankou.class);
            add(Sankantsu.class);
            add(Shousangen.class);
            add(Honroutou.class);
            add(Chiitoitsu.class);
            add(Rinshan.class);
            add(Chankan.class);
        }};
    }
}
