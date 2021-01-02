package com.example.riichicompanion;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class HandCalculatorAdapter extends FragmentStateAdapter {

    private final WinType winType;
    private final Wind prevalentWind;
    private final Wind seatWind;
    private WinConditionsFragment winConditionsFragment;

    public HandCalculatorAdapter(@NonNull FragmentActivity fragmentActivity, WinType winType,
                                 Wind prevalentWind, Wind seatWind) {
        super(fragmentActivity);

        this.winType = winType;
        this.prevalentWind = prevalentWind;
        this.seatWind = seatWind;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0)
            return HandInputFragment.newInstance();
        else {
            winConditionsFragment = WinConditionsFragment.newInstance(winType, prevalentWind, seatWind);
            return winConditionsFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public WinConditionsFragment getWinConditionsFragment() {
        return winConditionsFragment;
    }
}
