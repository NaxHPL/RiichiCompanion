package com.example.riichicompanion;

import java.util.Locale;

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

    public String toDisplayString() {
        if (yakumans > 0)
            return String.format(Locale.getDefault(), "%d Yakuman", yakumans);

        int displayFu = fu;

        if (displayFu < 20)
            displayFu = 20;
        else if (displayFu != 25)
            displayFu = roundUpToNearest10(fu);

        return String.format(Locale.getDefault(), "%d Han, %d Fu", han, displayFu);
    }

    private int roundUpToNearest10(int x) {
        return ((x + 9) / 10 ) * 10;
    }
}
