package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.GroupType;
import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.TileGroup;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Iipeikou implements Yaku {

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
        if (hand.isOpen() || arrangement.getChiiCount() < 2)
            return false;

        ArrayList<String> initialChiiTiles = new ArrayList<>();

        for (TileGroup group : arrangement.getGroups()) {
            if (group.getGroupType() != GroupType.Chii)
                continue;

            String firstTileStrRep = group.getTiles().get(0).getStringRep();

            if (initialChiiTiles.contains(firstTileStrRep))
                return true;

            initialChiiTiles.add(firstTileStrRep);
        }

        return false;
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
        return "Iipeikou";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return new ArrayList<Class<? extends Yaku>>(7) {{
            add(SanshokuDoukou.class);
            add(Toitoi.class);
            add(Sanankou.class);
            add(Sankantsu.class);
            add(Ryanpeikou.class);
            add(Honroutou.class);
            add(Chiitoitsu.class);
        }};
    }
}
