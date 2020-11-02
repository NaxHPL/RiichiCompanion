package com.example.mahjonghelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;

public class ScoreTrackerActivity extends AppCompatActivity {

    public static final String GAME = "com.example.mahjonghelper.GAME";

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

        game = getIntent().getParcelableExtra(GAME);
        UpdateInterface();
    }

    private void UpdateInterface() {
        TextView tvRoundCount = findViewById(R.id.tvRoundCount);
        tvRoundCount.setText(String.format(Locale.getDefault(), "%d", game.getRoundNumberForDisplay()));

        ImageView ivRoundWind = findViewById(R.id.ivRoundWind);
        ivRoundWind.setImageDrawable(game.getPrevalentWind() == Wind.East ? eastImage : southImage);

        TextView tvRiichiCount = findViewById(R.id.tvRiichiCount);
        tvRiichiCount.setText(String.format(Locale.getDefault(), "%d", game.getRiichiStickCount()));

        TextView tvHonbaCount = findViewById(R.id.tvHonbaCount);
        tvHonbaCount.setText(String.format(Locale.getDefault(), "%d", game.getHonbaStickCount()));
    }
}