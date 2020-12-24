package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.GroupType;
import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.Tile;
import com.example.riichicompanion.handcalculation.TileGroup;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Sanankou implements Yaku {

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
        if (arrangement.getPonCount() < 3)
            return false;

        int concealedPons = 0;
        for (TileGroup group : arrangement.getGroups()) {
            if (group.isOpenMeld() || group.getGroupType() != GroupType.Pon)
                continue;

            Tile firstTile = group.getTiles().get(0);
            if (firstTile.isSameAs(hand.getWinTile()) && !conditions.isTsumo())
                continue;

            concealedPons++;
        }

        return concealedPons == 3;
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
        return "Sanankou";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return new ArrayList<Class<? extends Yaku>>(6) {{
            add(Pinfu.class);
            add(Iipeikou.class);
            add(Ittsuu.class);
            add(SanshokuDoujun.class);
            add(Ryanpeikou.class);
            add(Chiitoitsu.class);
        }};
    }
}
