package com.example.riichicompanion.handcalculation;

import com.example.riichicompanion.Wind;

public class WinConditions {

    private final Wind seatWind;
    private final Wind prevWind;
    private final int dora;
    private final boolean isTsumo;
    private final boolean isRiichi;
    private final boolean isDoubleRiichi;
    private final boolean isIppatsu;
    private final boolean isRinshan;
    private final boolean isChankan;
    private final boolean isHaiteiOrHoutei;

    public WinConditions(Wind seatWind, Wind prevWind, int dora, boolean isTsumo, boolean isRiichi, boolean isDoubleRiichi,
                         boolean isIppatsu, boolean isRinshan, boolean isChankan, boolean isHaiteiOrHoutei) {
        this.seatWind = seatWind;
        this.prevWind = prevWind;
        this.dora = dora;
        this.isTsumo = isTsumo;
        this.isRiichi = isRiichi;
        this.isDoubleRiichi = isDoubleRiichi;
        this.isIppatsu = isIppatsu;
        this.isRinshan = isRinshan;
        this.isChankan = isChankan;
        this.isHaiteiOrHoutei = isHaiteiOrHoutei;
    }

    public Wind getSeatWind() {
        return seatWind;
    }

    public Wind getPrevWind() {
        return prevWind;
    }

    public int getDora() {
        return dora;
    }

    public boolean isTsumo() {
        return isTsumo;
    }

    public boolean isRiichi() {
        return isRiichi;
    }

    public boolean isDoubleRiichi() {
        return isDoubleRiichi;
    }

    public boolean isIppatsu() {
        return isIppatsu;
    }

    public boolean isRinshan() {
        return isRinshan;
    }

    public boolean isChankan() {
        return isChankan;
    }

    public boolean isHaiteiOrHoutei() {
        return isHaiteiOrHoutei;
    }
}
