package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.Suit;
import com.example.riichicompanion.handcalculation.Tile;
import com.example.riichicompanion.handcalculation.TileGroup;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Chinitsu implements Yaku {

    private boolean open = true;

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
        Suit requiredSuit = arrangement.getGroups().get(0).getTiles().get(0).getSuit();
        if (requiredSuit == null)
            return false;

        for (TileGroup group : arrangement.getGroups()) {
            Tile firstTile = group.getTiles().get(0);
            if (firstTile.isHonour() || firstTile.getSuit() != requiredSuit)
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
        return open ? 5 : 6;
    }

    @Override
    public String getDisplayName() {
        return "Chinitsu";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return null;
    }
}
