package com.example.riichicompanion;

import android.util.Pair;

public class ScoreEntry {

    private final int dealerRon;
    private final int dealerTsumo;
    private final int nonDealerRon;
    private final Pair<Integer, Integer> nonDealerTsumo;

    public ScoreEntry(int dealerRon, int dealerTsumo, int nonDealerRon, Pair<Integer, Integer> nonDealerTsumo) {
        this.dealerRon = dealerRon;
        this.dealerTsumo = dealerTsumo;
        this.nonDealerRon = nonDealerRon;
        this.nonDealerTsumo = nonDealerTsumo;
    }

    public int getDealerRon() {
        return dealerRon;
    }

    public int getDealerTsumo() {
        return dealerTsumo;
    }

    public int getNonDealerRon() {
        return nonDealerRon;
    }

    public Pair<Integer, Integer> getNonDealerTsumo() {
        return nonDealerTsumo;
    }
}
