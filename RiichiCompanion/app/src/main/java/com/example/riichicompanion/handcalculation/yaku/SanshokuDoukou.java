package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.GroupType;
import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.Suit;
import com.example.riichicompanion.handcalculation.Tile;
import com.example.riichicompanion.handcalculation.TileGroup;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class SanshokuDoukou implements Yaku {

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
        if (arrangement.getPonCount() < 3)
            return false;

        ArrayList<Integer> manPon = new ArrayList<>();
        ArrayList<Integer> pinPon = new ArrayList<>();
        ArrayList<Integer> souPon = new ArrayList<>();

        for (TileGroup group : arrangement.getGroups()) {
            if (group.getGroupType() != GroupType.Pon)
                continue;

            Tile firstTile = group.getTiles().get(0);

            if (firstTile.getSuit() == Suit.Man)
                manPon.add(firstTile.getRank());
            else if (firstTile.getSuit() == Suit.Pin)
                pinPon.add(firstTile.getRank());
            else
                souPon.add(firstTile.getRank());
        }

        for (Integer i : manPon) {
            if (pinPon.contains(i) && souPon.contains(i))
                return true;
        }

        return false;
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
        return "Sanshoku doukou";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return new ArrayList<Class<? extends Yaku>>(9) {{
            add(Pinfu.class);
            add(Iipeikou.class);
            add(Ittsuu.class);
            add(SanshokuDoujun.class);
            add(Ryanpeikou.class);
            add(Shousangen.class);
            add(Honitsu.class);
            add(Chinitsu.class);
            add(Chiitoitsu.class);
        }};
    }
}
