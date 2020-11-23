package com.example.riichicompanion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.Locale;

public class HandCalculatorActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 12;
    public static final int RESULT_CODE_CONFIRMED = 24;
    public static final int RESULT_CODE_CANCELLED = 36;

    public static final String WINNER_NAME_EXTRA = "com.example.riichicompanion.WINNER_NAME_EXTRA";
    public static final String WIN_TYPE_EXTRA = "com.example.riichicompanion.WIN_TYPE_EXTRA";
    public static final String PREVALENT_WIND_EXTRA = "com.example.riichicompanion.PREVALENT_WIND_EXTRA";
    public static final String SEAT_WIND_EXTRA = "com.example.riichicompanion.SEAT_WIND_EXTRA";

    private String winnerName;
    private WinType winType;
    private Wind prevalentWind;
    private Wind seatWind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(PersistentStorage.getThemeOption(this).getThemeId());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_calculator);
        setSupportActionBar(findViewById(R.id.toolbarHandCalc));
        setExtras(getIntent());

        if (winnerName != null)
            setTitle(String.format(Locale.getDefault(), "Calculate hand for %s", winnerName));
    }

    private void setExtras(Intent intent) {
        winnerName = intent.getStringExtra(WINNER_NAME_EXTRA);

        try {
            winType = WinType.valueOf(intent.getStringExtra(WIN_TYPE_EXTRA));
        }
        catch (Exception e) {
            winType = WinType.Unknown;
        }

        try {
            prevalentWind = Wind.valueOf(intent.getStringExtra(PREVALENT_WIND_EXTRA));
        }
        catch (Exception e) {
            prevalentWind = Wind.East;
        }

        try {
            seatWind = Wind.valueOf(intent.getStringExtra(SEAT_WIND_EXTRA));
        }
        catch (Exception e) {
            seatWind = Wind.East;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(RESULT_CODE_CANCELLED);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}