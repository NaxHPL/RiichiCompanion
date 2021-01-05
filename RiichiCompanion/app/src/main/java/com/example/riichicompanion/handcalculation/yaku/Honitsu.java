package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.Suit;
import com.example.riichicompanion.handcalculation.Tile;
import com.example.riichicompanion.handcalculation.TileGroup;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Honitsu implements Yaku {

    private boolean open = true;

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
        Suit requiredSuit = null;
        for (TileGroup group : arrangement.getGroups()) {
            Tile firstTile = group.getTiles().get(0);
            if (firstTile.isSuited()) {
                requiredSuit = firstTile.getSuit();
                break;
            }
        }

        if (requiredSuit == null)
            return false;

        for (TileGroup group : arrangement.getGroups()) {
            Tile firstTile = group.getTiles().get(0);
            if (firstTile.isSuited() && firstTile.getSuit() != requiredSuit)
                return false;
        }

        open = hand.isOpen();
        return true;
    }

    @Override
    public int getYakumans() {
        return 0;
    }

    @Override
    public int getHan() {
        return open ? 2 : 3;
    }

    @Override
    public String getDisplayName() {
        return "Honitsu";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return new ArrayList<Class<? extends Yaku>>(5) {{
            add(Tanyao.class);
            add(SanshokuDoujun.class);
            add(SanshokuDoukou.class);
            add(JunchanTaiyao.class);
            add(Chinitsu.class);
        }};
    }
}
