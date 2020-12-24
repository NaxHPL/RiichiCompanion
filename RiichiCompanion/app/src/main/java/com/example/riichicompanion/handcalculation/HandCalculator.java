package com.example.riichicompanion.handcalculation;

import com.example.riichicompanion.HandScore;
import com.example.riichicompanion.ScoringTable;
import com.example.riichicompanion.handcalculation.yaku.Chankan;
import com.example.riichicompanion.handcalculation.yaku.Chantaiyao;
import com.example.riichicompanion.handcalculation.yaku.Chiitoitsu;
import com.example.riichicompanion.handcalculation.yaku.Chinitsu;
import com.example.riichicompanion.handcalculation.yaku.Chinroutou;
import com.example.riichicompanion.handcalculation.yaku.ChuurenPoutou;
import com.example.riichicompanion.handcalculation.yaku.Daisangen;
import com.example.riichicompanion.handcalculation.yaku.Daisuushii;
import com.example.riichicompanion.handcalculation.yaku.DoubleRiichi;
import com.example.riichicompanion.handcalculation.yaku.Haitei;
import com.example.riichicompanion.handcalculation.yaku.Honitsu;
import com.example.riichicompanion.handcalculation.yaku.Honroutou;
import com.example.riichicompanion.handcalculation.yaku.Houtei;
import com.example.riichicompanion.handcalculation.yaku.Iipeikou;
import com.example.riichicompanion.handcalculation.yaku.Ippatsu;
import com.example.riichicompanion.handcalculation.yaku.Ittsuu;
import com.example.riichicompanion.handcalculation.yaku.JunchanTaiyao;
import com.example.riichicompanion.handcalculation.yaku.KokushiMusou;
import com.example.riichicompanion.handcalculation.yaku.MenzenTsumo;
import com.example.riichicompanion.handcalculation.yaku.Pinfu;
import com.example.riichicompanion.handcalculation.yaku.Riichi;
import com.example.riichicompanion.handcalculation.yaku.Rinshan;
import com.example.riichicompanion.handcalculation.yaku.Ryanpeikou;
import com.example.riichicompanion.handcalculation.yaku.Ryuuiisou;
import com.example.riichicompanion.handcalculation.yaku.Sanankou;
import com.example.riichicompanion.handcalculation.yaku.Sankantsu;
import com.example.riichicompanion.handcalculation.yaku.SanshokuDoujun;
import com.example.riichicompanion.handcalculation.yaku.SanshokuDoukou;
import com.example.riichicompanion.handcalculation.yaku.Shousangen;
import com.example.riichicompanion.handcalculation.yaku.Shousuushii;
import com.example.riichicompanion.handcalculation.yaku.Suuankou;
import com.example.riichicompanion.handcalculation.yaku.Suukantsu;
import com.example.riichicompanion.handcalculation.yaku.Tanyao;
import com.example.riichicompanion.handcalculation.yaku.Toitoi;
import com.example.riichicompanion.handcalculation.yaku.Tsuuiisou;
import com.example.riichicompanion.handcalculation.yaku.Yaku;
import com.example.riichicompanion.handcalculation.yaku.Yakuhai;

import java.util.ArrayList;

public class HandCalculator {

    public static HandResponse calculateHand(Hand hand, WinConditions conditions) {
        ArrayList<Yaku> yakumanFound = new ArrayList<>();
        int yakumanCount = checkForYakuman(hand, conditions, yakumanFound);

        if (yakumanCount > 0) {
            Yaku[] yakuArray = new Yaku[yakumanFound.size()];
            return new HandResponse(
                new HandScore(yakumanCount),
                yakumanFound.toArray(yakuArray),
                new FuItem[0]
            );
        }

        ArrayList<HandResponse> handResponses = new ArrayList<>();

        for (HandArrangement arrangement : hand.getAllArrangements()) {
            ArrayList<Yaku> yakuFound = new ArrayList<>();
            int han = checkForYaku(hand, arrangement, conditions, yakuFound) + conditions.getDora();
            int fu = 0;

            if (han < 5) {
                // TODO: Calculate fu
            }

            Yaku[] yakuArray = new Yaku[yakuFound.size()];
            handResponses.add(new HandResponse(
                new HandScore(han, fu),
                yakuFound.toArray(yakuArray),
                new FuItem[0]
            ));
        }

        HandResponse bestResponse = handResponses.get(0);
        int bestScore = ScoringTable.getScoreEntry(bestResponse.getHandScore()).getNonDealerRon();

        for (int i = 1; i < handResponses.size(); i++) {
            HandResponse response = handResponses.get(i);
            int score = ScoringTable.getScoreEntry(response.getHandScore()).getNonDealerRon();

            if (score > bestScore) {
                bestResponse = response;
                bestScore = score;
            }
        }

        return bestResponse;
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

            if (yaku.isConditionMet(hand, null, conditions)) {
                yakumanCount += yaku.getYakumans();
                invalidYaku.addAll(yaku.getInvalidYaku());
                yakuFound.add(yaku);
            }
        }

        return yakumanCount;
    }

    private static int checkForYaku(Hand hand, HandArrangement arrangement, WinConditions conditions, ArrayList<Yaku> yakuFound) {
        Yaku[] yakus = {
            new Chinitsu(),
            new Ryanpeikou(),
            new JunchanTaiyao(),
            new Honitsu(),
            new Chiitoitsu(),
            new Honroutou(),
            new Shousangen(),
            new SanshokuDoukou(),
            new Toitoi(),
            new Sankantsu(),
            new Sanankou(),
            new DoubleRiichi(),
            new Ittsuu(),
            new SanshokuDoujun(),
            new Chantaiyao(),
            new Pinfu(),
            new Chankan(),
            new Tanyao(),
            new Rinshan(),
            new Iipeikou(),
            new Yakuhai(),
            new Houtei(),
            new Haitei(),
            new MenzenTsumo(),
            new Riichi(),
            new Ippatsu()
        };
        ArrayList<Class<? extends Yaku>> invalidYaku = new ArrayList<>();
        int hanCount = 0;

        for (Yaku yaku : yakus) {
            if (invalidYaku.contains(yaku.getClass()))
                continue;

            if (yaku.isConditionMet(hand, arrangement, conditions)) {
                hanCount += yaku.getHan();
                invalidYaku.addAll(yaku.getInvalidYaku());
                yakuFound.add(yaku);
            }
        }

        return hanCount;
    }
}
