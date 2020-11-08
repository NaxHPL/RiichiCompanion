package com.example.mahjonghelper;

public class HandScore {

    private final int han;
    private final int fu;
    private final int yakumans;

    public HandScore(int han, int fu, int yakumans) {
        this.han = han;
        this.fu = fu;
        this.yakumans = yakumans;
    }

    public int getHan() {
        return han;
    }

    public int getFu() {
        return fu;
    }

    public int getYakumans() {
        return yakumans;
    }
}
