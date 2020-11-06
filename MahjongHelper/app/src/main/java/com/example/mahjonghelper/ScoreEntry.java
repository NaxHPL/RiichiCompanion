package com.example.mahjonghelper;

import android.util.Pair;

public class ScoreEntry {

    private final int dealerRonPoints;
    private final int dealerTsumoPoints;
    private final int nonDealerRonPoints;
    private final Pair<Integer, Integer> nonDealerTsumoPoints;

    public ScoreEntry(int dealerRonPoints, int dealerTsumoPoints, int nonDealerRonPoints, Pair<Integer, Integer> nonDealerTsumoPoints) {
        this.dealerRonPoints = dealerRonPoints;
        this.dealerTsumoPoints = dealerTsumoPoints;
        this.nonDealerRonPoints = nonDealerRonPoints;
        this.nonDealerTsumoPoints = nonDealerTsumoPoints;
    }

    public int getDealerRonPoints() {
        return dealerRonPoints;
    }

    public int getDealerTsumoPoints() {
        return dealerTsumoPoints;
    }

    public int getNonDealerRonPoints() {
        return nonDealerRonPoints;
    }

    public Pair<Integer, Integer> getNonDealerTsumoPoints() {
        return nonDealerTsumoPoints;
    }
}
