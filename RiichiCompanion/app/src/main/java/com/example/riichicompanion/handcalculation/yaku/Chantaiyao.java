package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.GroupType;
import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.Tile;
import com.example.riichicompanion.handcalculation.TileGroup;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class Chantaiyao implements Yaku {

    private boolean open = true;

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
        for (TileGroup group : arrangement.getGroups()) {
            Tile firstTile = group.getTiles().get(0);

            if (group.getGroupType() == GroupType.Chii && (firstTile.getRank() != 1 && firstTile.getRank() != 7))
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
        return new ArrayList<Class<? extends Yaku>>(7) {{
            add(Tanyao.class);
            add(Ittsuu.class);
            add(Toitoi.class);
            add(JunchanTaiyao.class);
            add(Honroutou.class);
            add(Chinitsu.class);
            add(Chiitoitsu.class);
        }};
    }
}
