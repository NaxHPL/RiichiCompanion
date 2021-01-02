package com.example.riichicompanion.handcalculation;

import com.example.riichicompanion.HandScore;
import com.example.riichicompanion.handcalculation.yaku.Yaku;

public class HandResponse {

    private final HandScore handScore;
    private final Yaku[] yaku;
    private final FuItem[] fuItems;

    public HandResponse(HandScore handScore, Yaku[] yaku, FuItem[] fuItems) {
        this.handScore = handScore;
        this.yaku = yaku;
        this.fuItems = fuItems;
    }

    public HandScore getHandScore() {
        return handScore;
    }

    public Yaku[] getYaku() {
        return yaku;
    }

    public FuItem[] getFuItems() {
        return fuItems;
    }
}
