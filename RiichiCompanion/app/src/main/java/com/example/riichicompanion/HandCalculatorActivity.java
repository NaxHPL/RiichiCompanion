package com.example.riichicompanion;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.WinConditions;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Locale;

public class HandCalculatorActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 12;
    public static final int RESULT_CODE_CONFIRMED = 24;
    public static final int RESULT_CODE_CANCELLED = 36;

    public static final String WINNER_NAME_EXTRA = "com.example.riichicompanion.WINNER_NAME_EXTRA";
    public static final String WIN_TYPE_EXTRA = "com.example.riichicompanion.WIN_TYPE_EXTRA";
    public static final String PREVALENT_WIND_EXTRA = "com.example.riichicompanion.PREVALENT_WIND_EXTRA";
    public static final String SEAT_WIND_EXTRA = "com.example.riichicompanion.SEAT_WIND_EXTRA";

    private HandInputViewModel viewModel;
    private HandCalculatorAdapter adapter;
    private String winnerName;
    private WinType winType;
    private Wind prevalentWind;
    private Wind seatWind;
    private Button btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int themeId = PersistentStorage.getThemeOption(this).getThemeId();
        setTheme(themeId);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_calculator);

        btnCalculate = findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(v -> calculateHand());

        viewModel = new ViewModelProvider(this).get(HandInputViewModel.class);
        viewModel.getHand().observe(this, hand -> {
            if (hand.isValid()) {
                btnCalculate.setText(String.format(Locale.getDefault(), "%s", "Calculate"));
                btnCalculate.setEnabled(true);
            }
            else {
                btnCalculate.setText(String.format(Locale.getDefault(), "%s", "Invalid Hand"));
                btnCalculate.setEnabled(false);
            }
        });

        setExtras(getIntent());
        setupToolbar(themeId);
        setupTabs();

        if (winnerName != null)
            setTitle(String.format(Locale.getDefault(), "Calculate hand for %s", winnerName));
    }

    private void setExtras(Intent intent) {
        winnerName = intent.getStringExtra(WINNER_NAME_EXTRA);
        winType = WinType.valueOf(intent.getStringExtra(WIN_TYPE_EXTRA));
        prevalentWind = Wind.valueOf(intent.getStringExtra(PREVALENT_WIND_EXTRA));
        seatWind = Wind.valueOf(intent.getStringExtra(SEAT_WIND_EXTRA));
    }

    private void setupToolbar(int themeId) {
        Toolbar toolbarHandCalc = findViewById(R.id.toolbarHandCalc);
        toolbarHandCalc.setPopupTheme(themeId);
        setSupportActionBar(toolbarHandCalc);
    }

    private void setupTabs() {
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(1);
        adapter = new HandCalculatorAdapter(this, winType, prevalentWind, seatWind);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0)
                tab.setText("Tiles");
            else
                tab.setText("Conditions");
        }).attach();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CODE_CANCELLED);
        finish();

        super.onBackPressed();
    }

    private void calculateHand() {
        WinConditions winConditions = adapter.getWinConditionsFragment().getWinConditions();
        Hand hand = viewModel.getHand().getValue();

        
    }
}