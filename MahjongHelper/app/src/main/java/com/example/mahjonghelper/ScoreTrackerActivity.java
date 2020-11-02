package com.example.mahjonghelper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ScoreTrackerActivity extends AppCompatActivity {

    public static final String GAME = "com.example.mahjonghelper.GAME";

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_tracker);

        game = getIntent().getParcelableExtra(GAME);
    }
}