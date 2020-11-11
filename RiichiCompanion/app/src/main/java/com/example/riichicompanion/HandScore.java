package com.example.riichicompanion;

public class HandScore {

    private final int han;
    private final int fu;
    private final int yakumans;

    public HandScore(int han, int fu) {
        this.han = han;
        this.fu = fu;
        this.yakumans = 0;
    }

    public HandScore(int yakumans) {
        this.han = 0;
        this.fu = 0;
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
