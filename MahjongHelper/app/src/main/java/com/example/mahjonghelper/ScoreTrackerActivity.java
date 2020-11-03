package com.example.mahjonghelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class ScoreTrackerActivity extends AppCompatActivity {

    public static final String GAME_TO_SHOW = "com.example.mahjonghelper.GAME";

    private Game game;
    private Drawable eastImage;
    private Drawable southImage;
    private Drawable westImage;
    private Drawable northImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_tracker);

        eastImage = ResourcesCompat.getDrawable(getResources(), R.drawable.east, getTheme());
        southImage = ResourcesCompat.getDrawable(getResources(), R.drawable.south, getTheme());
        westImage = ResourcesCompat.getDrawable(getResources(), R.drawable.west, getTheme());
        northImage = ResourcesCompat.getDrawable(getResources(), R.drawable.north, getTheme());

        game = getIntent().getParcelableExtra(GAME_TO_SHOW);
        UpdateInterface();
    }

    private void UpdateInterface() {
        UpdateRoundInformation();
        UpdateStickCounts();
        UpdatePlayerInformation();
    }

    private void UpdateStickCounts() {
        TextView tvRiichiCount = findViewById(R.id.tvRiichiCount);
        tvRiichiCount.setText(String.format(Locale.getDefault(), "%d", game.getRiichiStickCount()));

        TextView tvHonbaCount = findViewById(R.id.tvHonbaCount);
        tvHonbaCount.setText(String.format(Locale.getDefault(), "%d", game.getHonbaStickCount()));
    }

    private void UpdateRoundInformation() {
        TextView tvRoundCount = findViewById(R.id.tvRoundCount);
        tvRoundCount.setText(String.format(Locale.getDefault(), "%d", game.getRoundNumberForDisplay()));

        ImageView ivRoundWind = findViewById(R.id.ivRoundWind);
        ivRoundWind.setImageDrawable(game.getPrevalentWind() == Wind.East ? eastImage : southImage);
    }

    private void UpdatePlayerInformation() {
        TextView tv = findViewById(R.id.tvBottomName);
        tv.setText(String.format(Locale.getDefault(), "%s", game.getBottomPlayer().getName()));

        tv = findViewById(R.id.tvRightName);
        tv.setText(String.format(Locale.getDefault(), "%s", game.getRightPlayer().getName()));

        tv = findViewById(R.id.tvTopName);
        tv.setText(String.format(Locale.getDefault(), "%s", game.getTopPlayer().getName()));

        tv = findViewById(R.id.tvLeftName);
        tv.setText(String.format(Locale.getDefault(), "%s", game.getLeftPlayer().getName()));

        tv = findViewById(R.id.tvBottomScore);
        tv.setText(String.format(Locale.getDefault(), "%d", game.getBottomPlayer().getScore()));

        tv = findViewById(R.id.tvRightScore);
        tv.setText(String.format(Locale.getDefault(), "%d", game.getRightPlayer().getScore()));

        tv = findViewById(R.id.tvTopScore);
        tv.setText(String.format(Locale.getDefault(), "%d", game.getTopPlayer().getScore()));

        tv = findViewById(R.id.tvLeftScore);
        tv.setText(String.format(Locale.getDefault(), "%d", game.getLeftPlayer().getScore()));

        ImageView iv = findViewById(R.id.ivBottomWind);
        iv.setImageDrawable(getWindImage(game.getBottomPlayer().getSeatWind()));

        iv = findViewById(R.id.ivRightWind);
        iv.setImageDrawable(getWindImage(game.getRightPlayer().getSeatWind()));

        iv = findViewById(R.id.ivTopWind);
        iv.setImageDrawable(getWindImage(game.getTopPlayer().getSeatWind()));

        iv = findViewById(R.id.ivLeftWind);
        iv.setImageDrawable(getWindImage(game.getLeftPlayer().getSeatWind()));
    }

    private Drawable getWindImage(Wind wind) {
        switch (wind) {
            case East:
                return eastImage;
            case South:
                return southImage;
            case West:
                return westImage;
            default:
                return northImage;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}