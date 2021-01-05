package com.example.riichicompanion.handcalculation.yaku;

import com.example.riichicompanion.handcalculation.GroupType;
import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.HandArrangement;
import com.example.riichicompanion.handcalculation.Tile;
import com.example.riichicompanion.handcalculation.TileGroup;
import com.example.riichicompanion.handcalculation.WinConditions;

import java.util.ArrayList;

public class JunchanTaiyao implements Yaku {

    private boolean open = true;

    @Override
    public boolean isConditionMet(Hand hand, HandArrangement arrangement, WinConditions conditions) {
        for (TileGroup group : arrangement.getGroups()) {
            Tile firstTile = group.getTiles().get(0);

            if (firstTile.isHonour())
                return false;

            if (group.getGroupType() == GroupType.Chii) {
                if (firstTile.getRank() != 1 && firstTile.getRank() != 7)
                    return false;
            }
            else {
                if (firstTile.getRank() != 1 && firstTile.getRank() != 9)
                    return false;
            }
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
        return "Junchan taiyao";
    }

    @Override
    public ArrayList<Class<? extends Yaku>> getInvalidYaku() {
        return new ArrayList<Class<? extends Yaku>>(9) {{
            add(Tanyao.class);
            add(Ittsuu.class);
            add(Yakuhai.class);
            add(Toitoi.class);
            add(Chantaiyao.class);
            add(Shousangen.class);
            add(Honroutou.class);
            add(Honitsu.class);
            add(Chiitoitsu.class);
        }};
    }
}
