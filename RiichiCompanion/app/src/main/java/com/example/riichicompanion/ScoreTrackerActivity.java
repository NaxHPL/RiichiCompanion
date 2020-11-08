package com.example.riichicompanion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class ScoreTrackerActivity extends AppCompatActivity {

    public static final String GAME_TO_SHOW = "com.example.mahjonghelper.GAME_TO_SHOW";

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_tracker);
    }

    @Override
    protected void onStart() {
        super.onStart();
        game = getIntent().getParcelableExtra(GAME_TO_SHOW);

        if (game.getNumberOfPlayers() == 3) {
            ConstraintLayout layout = findViewById(R.id.ConstraintLayout_Left);
            layout.setVisibility(View.INVISIBLE);
        }

        updateInterface();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private void updateInterface() {
        updateRoundInformation();
        updateStickCounts();
        updatePlayerInformation();
    }

    private void updateStickCounts() {
        TextView tvRiichiCount = findViewById(R.id.tvRiichiCount);
        tvRiichiCount.setText(String.format(Locale.getDefault(), "%d", game.getRiichiStickCount()));

        TextView tvHonbaCount = findViewById(R.id.tvHonbaCount);
        tvHonbaCount.setText(String.format(Locale.getDefault(), "%d", game.getHonbaStickCount()));
    }

    private void updateRoundInformation() {
        TextView tvRoundCount = findViewById(R.id.tvRoundCount);
        tvRoundCount.setText(String.format(Locale.getDefault(), "%d", game.getRoundNumberForDisplay()));

        ImageView ivRoundWind = findViewById(R.id.ivRoundWind);
        ivRoundWind.setImageDrawable(game.getPrevalentWind().getImage(this));
    }

    private void updatePlayerInformation() {
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
        iv.setImageDrawable(game.getBottomPlayer().getSeatWind().getImage(this));

        iv = findViewById(R.id.ivRightWind);
        iv.setImageDrawable(game.getRightPlayer().getSeatWind().getImage(this));

        iv = findViewById(R.id.ivTopWind);
        iv.setImageDrawable(game.getTopPlayer().getSeatWind().getImage(this));

        iv = findViewById(R.id.ivLeftWind);
        iv.setImageDrawable(game.getLeftPlayer().getSeatWind().getImage(this));
    }

    public void endRound(View view) {
        ImageView iv = findViewById(R.id.ivRoundWind);
        iv.setVisibility(View.INVISIBLE);

        TextView tv = findViewById(R.id.tvRoundCount);
        tv.setVisibility(View.INVISIBLE);

        Button btn = findViewById(R.id.btnRon);
        btn.setVisibility(View.VISIBLE);

        btn = findViewById(R.id.btnTsumo);
        btn.setVisibility(View.VISIBLE);

        btn = findViewById(R.id.btnRyuukyoku);
        btn.setVisibility(View.VISIBLE);

        btn = findViewById(R.id.btnChombo);
        btn.setVisibility(View.VISIBLE);
    }
}