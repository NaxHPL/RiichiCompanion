package com.example.riichicompanion.handcalculation;

import androidx.core.util.Pair;

import com.example.riichicompanion.Fu;
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
            ArrayList<FuItem> fuItems = new ArrayList<>();

            int han = checkForYaku(hand, arrangement, conditions, yakuFound) + conditions.getDora();
            int fu = 20;

            if (han < 5)
                fu = calculateFu(
                    hand,
                    arrangement,
                    conditions,
                    fuItems,
                    yakuWasFound(Chiitoitsu.class, yakuFound),
                    yakuWasFound(Pinfu.class, yakuFound));

            Yaku[] yakuArray = new Yaku[yakuFound.size()];
            FuItem[] fuArray = new FuItem[fuItems.size()];

            handResponses.add(new HandResponse(
                new HandScore(han, fu),
                yakuFound.toArray(yakuArray),
                fuItems.toArray(fuArray)
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

    private static int calculateFu(Hand hand, HandArrangement arrangement, WinConditions conditions,
                                   ArrayList<FuItem> fuItems, boolean chiitoitsuFound, boolean pinfuFound) {
        if (chiitoitsuFound) {
            fuItems.add(new FuItem("Base points", 25));
            return 25;
        }

        if (pinfuFound) {
            fuItems.add(new FuItem("Base points", 20));
            if (!conditions.isTsumo()) {
                fuItems.add(new FuItem("Closed ron", 10));
                return 30;
            }

            return 20;
        }

        ArrayList<TileGroup> concealedGroupsWithWinTile = new ArrayList<>();
        for (TileGroup group : arrangement.getGroups()) {
            if (!group.isOpenMeld() && group.containsTile(hand.getWinTile()))
                concealedGroupsWithWinTile.add(group);
        }

        // The win tile might complete multiple groups. The win group that maximizes fu must be used.
        ArrayList<Pair<Integer, ArrayList<FuItem>>> fuAndFuItems = new ArrayList<>();

        for (TileGroup groupWithWinTile : concealedGroupsWithWinTile) {
            ArrayList<FuItem> fuItemsForThisWinGroup = new ArrayList<>();
            fuItemsForThisWinGroup.add(new FuItem("Base points", 20));
            int fuForThisWinGroup = 20;

            for (TileGroup group : arrangement.getGroups()) {
                if (group.getGroupType() == GroupType.Pon) {
                    boolean groupIsOpen = group.isOpenMeld() || (group == groupWithWinTile && !conditions.isTsumo());
                    FuItem groupFuItem = group.getFuItem(conditions.getPrevWind(), conditions.getSeatWind(), groupIsOpen);

                    if (groupFuItem != null) {
                        fuItemsForThisWinGroup.add(groupFuItem);
                        fuForThisWinGroup += groupFuItem.getValue();
                    }
                }
                else if (group.getGroupType() == GroupType.Chii) {
                    if (group == groupWithWinTile) {
                        if (isClosedWait(group, hand.getWinTile())) {
                            fuItemsForThisWinGroup.add(new FuItem("Closed wait", 2));
                            fuForThisWinGroup += 2;
                        }
                        else if (isEdgeWait(group, hand.getWinTile())) {
                            fuItemsForThisWinGroup.add(new FuItem("Edge wait", 2));
                            fuForThisWinGroup += 2;
                        }
                    }
                }
                else { // Group is a pair
                    if (group == groupWithWinTile) {
                        fuItemsForThisWinGroup.add(new FuItem("Pair wait", 2));
                        fuForThisWinGroup += 2;
                    }

                    FuItem groupFuItem = group.getFuItem(conditions.getPrevWind(), conditions.getSeatWind(), false);
                    if (groupFuItem != null) {
                        fuItemsForThisWinGroup.add(groupFuItem);
                        fuForThisWinGroup += groupFuItem.getValue();
                    }
                }
            }

            if (!hand.isOpen() && !conditions.isTsumo()) {
                fuItemsForThisWinGroup.add(new FuItem("Closed ron", 10));
                fuForThisWinGroup += 10;
            }

            if (conditions.isTsumo()) {
                fuItemsForThisWinGroup.add(new FuItem("Tsumo", 2));
                fuForThisWinGroup += 2;
            }

            if (fuForThisWinGroup == 20) {
                fuItemsForThisWinGroup.add(new FuItem("Open pinfu", 2));
                fuForThisWinGroup += 2;
            }

            FuItem roundUpFuItem = getRoundUpFuItem(fuForThisWinGroup);
            fuItemsForThisWinGroup.add(roundUpFuItem);
            fuForThisWinGroup += roundUpFuItem.getValue();

            fuAndFuItems.add(Pair.create(fuForThisWinGroup, fuItemsForThisWinGroup));
        }

        Pair<Integer, ArrayList<FuItem>> maxFuAndItems = fuAndFuItems.get(0);
        for (Pair<Integer, ArrayList<FuItem>> fuAndItems : fuAndFuItems) {
            if (fuAndItems.first > maxFuAndItems.first)
                maxFuAndItems = fuAndItems;
        }

        fuItems.addAll(maxFuAndItems.second);
        return maxFuAndItems.first;
    }

    private static boolean yakuWasFound(Class<? extends Yaku> toFind, ArrayList<Yaku> yakuList) {
        for (Yaku yaku : yakuList) {
            if (yaku.getClass() == toFind)
                return true;
        }

        return false;
    }

    private static boolean isClosedWait(TileGroup group, Tile winTile) {
        if (group.getGroupType() != GroupType.Chii)
            return false;

        return group.getTiles().get(1).isSameAs(winTile);
    }

    private static boolean isEdgeWait(TileGroup group, Tile winTile) {
        if (group.getGroupType() != GroupType.Chii)
            return false;

        return (group.getTiles().get(0).getRank() == 1 && winTile.getRank() == 3)
            || (group.getTiles().get(2).getRank() == 9 && winTile.getRank() == 7);
    }

    private static FuItem getRoundUpFuItem(int fu) {
        int roundedUpFu = ((fu + 9) / 10 ) * 10;
        return new FuItem("Round up", roundedUpFu - fu);
    }
}
