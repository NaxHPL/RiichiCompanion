package com.example.mahjonghelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ScoreTrackerActivity extends AppCompatActivity {

    public static final String KEY_EAST_PLAYER = "com.example.mahjonghelper.EAST_PLAYER";
    public static final String KEY_SOUTH_PLAYER = "com.example.mahjonghelper.SOUTH_PLAYER";
    public static final String KEY_WEST_PLAYER = "com.example.mahjonghelper.WEST_PLAYER";
    public static final String KEY_NORTH_PLAYER = "com.example.mahjonghelper.NORTH_PLAYER";
    public static final String KEY_INITIAL_POINTS = "com.example.mahjonghelper.INITIAL_POINTS";
    public static final String KEY_MIN_POINTS_TO_WIN = "com.example.mahjonghelper.MIN_POINTS_TO_WIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_tracker);

        Intent intent = getIntent();

        String eastPlayer = intent.getStringExtra(KEY_EAST_PLAYER);
        String southPlayer = intent.getStringExtra(KEY_SOUTH_PLAYER);
        String westPlayer = intent.getStringExtra(KEY_WEST_PLAYER);
        String northPlayer = intent.getStringExtra(KEY_NORTH_PLAYER);
        String initialPoints = intent.getStringExtra(KEY_INITIAL_POINTS);
        String minPointsToWin = intent.getStringExtra(KEY_MIN_POINTS_TO_WIN);
    }
}