package com.example.riichicompanion;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

public class HandScore implements Parcelable {

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

    //region Parcelable Implementation

    protected HandScore(Parcel in) {
        han = in.readInt();
        fu = in.readInt();
        yakumans = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(han);
        dest.writeInt(fu);
        dest.writeInt(yakumans);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HandScore> CREATOR = new Creator<HandScore>() {
        @Override
        public HandScore createFromParcel(Parcel in) {
            return new HandScore(in);
        }

        @Override
        public HandScore[] newArray(int size) {
            return new HandScore[size];
        }
    };

    //endregion

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
