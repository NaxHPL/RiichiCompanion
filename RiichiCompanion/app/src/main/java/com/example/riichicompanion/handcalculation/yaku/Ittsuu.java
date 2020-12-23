package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.Suit;
import com.example.riichicompanion.handcalculation.Tile;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Ittsuu implements Yaku {

    private boolean open = true;

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
        if (arrangement.getChiiCount() < 3)
            return false;

        ArrayList<Integer> manChii = new ArrayList<>();
        ArrayList<Integer> pinChii = new ArrayList<>();
        ArrayList<Integer> souChii = new ArrayList<>();

        for (ArrayList<Tile> set : arrangement.getSets()) {
            if (!Tile.isChii(set))
                continue;

            Tile firstTile = set.get(0);

            if (firstTile.getSuit() == Suit.Man)
                manChii.add(firstTile.getRank());
            else if (firstTile.getSuit() == Suit.Pin)
                pinChii.add(firstTile.getRank());
            else
                souChii.add(firstTile.getRank());
        }

        ArrayList<Integer> requiredRanks = new ArrayList<Integer>(3) {{
            add(1);
            add(4);
            add(7);
        }};

        if (manChii.containsAll(requiredRanks) || pinChii.containsAll(requiredRanks) || souChii.containsAll(requiredRanks)) {
            open = hand.isOpen();
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
        return open ? 1 : 2;
    }

    @Override
    public String getDisplayName() {
        return "Ittsuu";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return null;
    }
}
