package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.Tile;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Chantaiyao implements Yaku {

    private boolean open = true;

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
        for (ArrayList<Tile> set : arrangement.getSets()) {
            Tile firstTile = set.get(0);

            if (Tile.isChii(set) && (firstTile.getRank() != 1 && firstTile.getRank() != 7))
                return false;
            else if (firstTile.isSimple())
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
        return open ? 1 : 2;
    }

    @Override
    public String getDisplayName() {
        return "Chanta";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return null;
    }
}
