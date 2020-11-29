package com.example.riichicompanion;

import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Locale;

public class WinConditionsFragment extends Fragment {

    private static final String ARG_WIN_TYPE = "ARG_WIN_TYPE";
    private static final String ARG_PREVALENT_WIND = "ARG_PREVALENT_WIND";
    private static final String ARG_SEAT_WIND = "ARG_SEAT_WIND";
    private static final String ARG_HONBA_COUNT = "ARG_HONBA_COUNT";

    //region Views

    ToggleButton tbRon;
    ToggleButton tbTsumo;
    ToggleButton tbPrevWindEast;
    ToggleButton tbPrevWindSouth;
    ToggleButton tbPrevWindWest;
    ToggleButton tbPrevWindNorth;
    ToggleButton tbSeatWindEast;
    ToggleButton tbSeatWindSouth;
    ToggleButton tbSeatWindWest;
    ToggleButton tbSeatWindNorth;
    ToggleButton tbRiichi;
    ToggleButton tbDoubleRiichi;
    ToggleButton tbRinshan;
    ToggleButton tbChankan;
    ToggleButton tbIppatsu;
    ToggleButton tbHouteiHaitei;
    Button btnDecreaseDora;
    Button btnIncreaseDora;
    Button btnDecreaseHonba;
    Button btnIncreaseHonba;
    TextView tvDora;
    TextView tvHonba;

    //endregion

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
        setArguments();
    }

    private void setArguments() {
        Bundle args = getArguments();
        winType = WinType.valueOf(args.getString(ARG_WIN_TYPE));
        prevalentWind = Wind.valueOf(args.getString(ARG_PREVALENT_WIND));
        seatWind = Wind.valueOf(args.getString(ARG_SEAT_WIND));
        honbaCount = args.getInt(ARG_HONBA_COUNT, -1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_win_conditions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //region Views

        tbRon = view.findViewById(R.id.tbRon);
        tbTsumo = view.findViewById(R.id.tbTsumo);
        tbPrevWindEast = view.findViewById(R.id.tbPrevWindEast);
        tbPrevWindSouth = view.findViewById(R.id.tbPrevWindSouth);
        tbPrevWindWest = view.findViewById(R.id.tbPrevWindWest);
        tbPrevWindNorth = view.findViewById(R.id.tbPrevWindNorth);
        tbSeatWindEast = view.findViewById(R.id.tbSeatWindEast);
        tbSeatWindSouth = view.findViewById(R.id.tbSeatWindSouth);
        tbSeatWindWest = view.findViewById(R.id.tbSeatWindWest);
        tbSeatWindNorth = view.findViewById(R.id.tbSeatWindNorth);
        tbRiichi = view.findViewById(R.id.tbRiichi);
        tbDoubleRiichi = view.findViewById(R.id.tbDoubleRiichi);
        tbRinshan = view.findViewById(R.id.tbRinshan);
        tbChankan = view.findViewById(R.id.tbChankan);
        tbIppatsu = view.findViewById(R.id.tbIppatsu);
        tbHouteiHaitei = view.findViewById(R.id.tbHouteiHaitei);
        btnDecreaseDora = view.findViewById(R.id.btnDecreaseDora);
        btnIncreaseDora = view.findViewById(R.id.btnIncreaseDora);
        btnDecreaseHonba = view.findViewById(R.id.btnDecreaseHonba);
        btnIncreaseHonba = view.findViewById(R.id.btnIncreaseHonba);
        tvDora = view.findViewById(R.id.tvDora);
        tvHonba = view.findViewById(R.id.tvHonba);

        //endregion

        setWindColours(PersistentStorage.getThemeOption(getActivity()));
        setButtonListeners();

        if (winType != WinType.Unknown) {
            if (winType == WinType.Ron)
                tbRon.setChecked(true);
            else
                tbTsumo.setChecked(true);

            tbRon.setEnabled(false);
            tbTsumo.setEnabled(false);
        }

        if (prevalentWind != Wind.Unknown) {
            switch (prevalentWind) {
                case East: tbPrevWindEast.setChecked(true); break;
                case South: tbPrevWindSouth.setChecked(true); break;
                case West: tbPrevWindWest.setChecked(true); break;
                case North: tbPrevWindNorth.setChecked(true); break;
            }

            disableWindToggleButton(tbPrevWindEast);
            disableWindToggleButton(tbPrevWindSouth);
            disableWindToggleButton(tbPrevWindWest);
            disableWindToggleButton(tbPrevWindNorth);
        }

        if (seatWind != Wind.Unknown) {
            switch (seatWind) {
                case East: tbSeatWindEast.setChecked(true); break;
                case South: tbSeatWindSouth.setChecked(true); break;
                case West: tbSeatWindWest.setChecked(true); break;
                case North: tbSeatWindNorth.setChecked(true); break;
            }

            disableWindToggleButton(tbSeatWindEast);
            disableWindToggleButton(tbSeatWindSouth);
            disableWindToggleButton(tbSeatWindWest);
            disableWindToggleButton(tbSeatWindNorth);
        }

        if (honbaCount > -1) {
            tvHonba.setText(String.format(Locale.getDefault(), "%d", honbaCount));
            btnDecreaseHonba.setEnabled(false);
            btnIncreaseHonba.setEnabled(false);
        }
    }

    private void setWindColours(ThemeOption theme) {
        boolean nightModeOn = (getActivity().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
        ColorStateList color;

        if (theme == ThemeOption.Dark || (theme == ThemeOption.Auto && nightModeOn))
            color = ColorStateList.valueOf(Color.parseColor("#FFFFFF"));
        else
            color = ColorStateList.valueOf(Color.parseColor("#000000"));

        TextViewCompat.setCompoundDrawableTintList(tbPrevWindEast, color);
        TextViewCompat.setCompoundDrawableTintList(tbPrevWindSouth, color);
        TextViewCompat.setCompoundDrawableTintList(tbPrevWindWest, color);
        TextViewCompat.setCompoundDrawableTintList(tbPrevWindNorth, color);

        TextViewCompat.setCompoundDrawableTintList(tbSeatWindEast, color);
        TextViewCompat.setCompoundDrawableTintList(tbSeatWindSouth, color);
        TextViewCompat.setCompoundDrawableTintList(tbSeatWindWest, color);
        TextViewCompat.setCompoundDrawableTintList(tbSeatWindNorth, color);
    }

    private void disableWindToggleButton(ToggleButton tb) {
        ColorStateList color = TextViewCompat.getCompoundDrawableTintList(tb).withAlpha(127);
        TextViewCompat.setCompoundDrawableTintList(tb, color);
        tb.setEnabled(false);
    }

    private void setButtonListeners() {
        tbRon.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                tbTsumo.setChecked(false);
        });
        tbTsumo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                tbRon.setChecked(false);
        });

        ToggleButton[] prevWindButtons = new ToggleButton[] {tbPrevWindEast, tbPrevWindSouth, tbPrevWindWest, tbPrevWindNorth};
        ToggleButton[] seatWindButtons = new ToggleButton[] {tbSeatWindEast, tbSeatWindSouth, tbSeatWindWest, tbSeatWindNorth};

        CompoundButton.OnCheckedChangeListener occListenerPrevWind = (buttonView, isChecked) -> {
            for (ToggleButton tb : prevWindButtons ) {
                if (tb != buttonView && isChecked)
                    tb.setChecked(false);
                if (tb == buttonView)
                    tb.setClickable(!isChecked);
            }
        };
        CompoundButton.OnCheckedChangeListener occListenerSeatWind = (buttonView, isChecked) -> {
            for (ToggleButton tb : seatWindButtons ) {
                if (tb != buttonView && isChecked)
                    tb.setChecked(false);
                if (tb == buttonView)
                    tb.setClickable(!isChecked);
            }
        };

        for (ToggleButton tb : prevWindButtons)
            tb.setOnCheckedChangeListener(occListenerPrevWind);
        for (ToggleButton tb : seatWindButtons)
            tb.setOnCheckedChangeListener(occListenerSeatWind);

        btnDecreaseDora.setOnClickListener(v -> changeDoraBy(-1));
        btnIncreaseDora.setOnClickListener(v -> changeDoraBy(1));
        btnDecreaseHonba.setOnClickListener(v -> changeHonbaBy(-1));
        btnIncreaseHonba.setOnClickListener(v -> changeHonbaBy(1));
    }

    private void changeDoraBy(int amount) {
        int dora = Integer.parseInt(tvDora.getText().toString()) + amount;
        tvDora.setText(String.format(Locale.getDefault(), "%d", Math.max(0, dora)));
    }

    private void changeHonbaBy(int amount) {
        int honba = Integer.parseInt(tvHonba.getText().toString()) + amount;
        tvHonba.setText(String.format(Locale.getDefault(), "%d", Math.max(0, honba)));
    }
}
