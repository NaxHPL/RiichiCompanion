package com.example.riichicompanion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WinConditionsFragment extends Fragment {

    private static final String ARG_WIN_TYPE = "ARG_WIN_TYPE";
    private static final String ARG_PREVALENT_WIND = "ARG_PREVALENT_WIND";
    private static final String ARG_SEAT_WIND = "ARG_SEAT_WIND";
    private static final String ARG_HONBA_COUNT = "ARG_HONBA_COUNT";

    private WinType winType;
    private Wind prevalentWind;
    private Wind seatWind;
    private int honbaCount;

    public WinConditionsFragment() {
        // Required empty public constructor
    }

    public static WinConditionsFragment newInstance(WinType winType, Wind prevalentWind, Wind seatWind, int honbaCount) {
        WinConditionsFragment fragment = new WinConditionsFragment();

        Bundle args = new Bundle();
        args.putString(ARG_WIN_TYPE, winType.name());
        args.putString(ARG_PREVALENT_WIND, prevalentWind.name());
        args.putString(ARG_SEAT_WIND, seatWind.name());
        args.putInt(ARG_HONBA_COUNT, honbaCount);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            try {
                winType = WinType.valueOf(args.getString(ARG_WIN_TYPE));
            }
            catch (Exception e) {
                winType = null;
            }

            try {
                prevalentWind = Wind.valueOf(args.getString(ARG_PREVALENT_WIND));
            }
            catch (Exception e) {
                prevalentWind = null;
            }

            try {
                seatWind = Wind.valueOf(args.getString(ARG_SEAT_WIND));
            }
            catch (Exception e) {
                seatWind = null;
            }

            honbaCount = args.getInt(ARG_HONBA_COUNT, -1);
        }

        // Set views if winType != null

        // Set views if prevalentWind != null

        // Set views if seatWind != null

        // Set views if honbaCount > -1
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_win_conditions, container, false);
    }
}