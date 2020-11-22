package com.example.riichicompanion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class HandCalculatorActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 12;
    public static final int RESULT_CODE_CONFIRMED = 24;
    public static final int RESULT_CODE_CANCELLED = 36;
    public static final String WIN_TYPE_EXTRA = "com.example.riichicompanion.WIN_TYPE_EXTRA";

    private WinType winType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_calculator);
        setSupportActionBar(findViewById(R.id.toolbarHandCalc));

        winType = WinType.valueOf(getIntent().getStringExtra(WIN_TYPE_EXTRA));
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

    public void confirmHandScore(View view) {
        Intent data = new Intent();
        data.putExtra(ScoreTrackerActivity.CALCULATED_HAND_EXTRA, new HandScore(2, 60));
        setResult(RESULT_CODE_CONFIRMED, data);
        finish();
    }
}