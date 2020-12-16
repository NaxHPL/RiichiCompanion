package com.example.riichicompanion.handcalculation;

import com.example.riichicompanion.HandScore;
import com.example.riichicompanion.handcalculation.yaku.Chinroutou;
import com.example.riichicompanion.handcalculation.yaku.ChuurenPoutou;
import com.example.riichicompanion.handcalculation.yaku.Daisangen;
import com.example.riichicompanion.handcalculation.yaku.Daisuushii;
import com.example.riichicompanion.handcalculation.yaku.KokushiMusou;
import com.example.riichicompanion.handcalculation.yaku.Ryuuiisou;
import com.example.riichicompanion.handcalculation.yaku.Shousuushii;
import com.example.riichicompanion.handcalculation.yaku.Suuankou;
import com.example.riichicompanion.handcalculation.yaku.Suukantsu;
import com.example.riichicompanion.handcalculation.yaku.Tsuuiisou;
import com.example.riichicompanion.handcalculation.yaku.Yaku;

import java.util.ArrayList;

public class HandCalculator {

    public static HandResponse calculateHand(Hand hand, WinConditions conditions) {
        ArrayList<Yaku> yakuFound = new ArrayList<>();
        int yakumanCount = checkForYakuman(hand, conditions, yakuFound);

        if (yakumanCount > 0) {
            Yaku[] yakuArray = new Yaku[yakuFound.size()];

            return new HandResponse(
                new HandScore(yakumanCount),
                yakuFound.toArray(yakuArray),
                new FuItem[0]
            );
        }

        

        return new HandResponse(null, null, null);
    }

    private static int checkForYakuman(Hand hand, WinConditions conditions, ArrayList<Yaku> yakuFound) {
        Yaku[] yakuman = {
            new KokushiMusou(),
            new ChuurenPoutou(),
            new Ryuuiisou(),
            new Chinroutou(),
            new Daisangen(),
            new Shousuushii(),
            new Daisuushii(),
            new Tsuuiisou(),
            new Suuankou(),
            new Suukantsu()
        };
        ArrayList<Class<? extends Yaku>> invalidYaku = new ArrayList<>();
        int yakumanCount = 0;

        for (Yaku yaku : yakuman) {
            if (invalidYaku.contains(yaku.getClass()))
                continue;

            if (yaku.isConditionMet(hand, conditions)) {
                yakumanCount += yaku.getYakumans();
                invalidYaku.addAll(yaku.getInvalidYaku());
                yakuFound.add(yaku);
            }
        }

        return yakumanCount;
    }
}
