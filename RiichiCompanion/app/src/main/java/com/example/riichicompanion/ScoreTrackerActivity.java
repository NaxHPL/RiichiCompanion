package com.example.riichicompanion;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Locale;

public class ScoreTrackerActivity extends AppCompatActivity {

    public static final String GAME_TO_SHOW = "com.example.mahjonghelper.GAME_TO_SHOW";

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_tracker);
        setSupportActionBar(findViewById(R.id.toolbarScoreTrackerActivity));

        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_score_tracker, menu);
        return true;
    }

    private void updateInterface() {
        updateRoundInformation();
        updateStickCounts();
        updatePlayerInformation();
    }

    private void updateStickCounts() {
        TextView tvRiichiCount = findViewById(R.id.tvRiichiCount);
        tvRiichiCount.setText(String.format(Locale.getDefault(), "%d", game.getRiichiCount()));

        TextView tvHonbaCount = findViewById(R.id.tvHonbaCount);
        tvHonbaCount.setText(String.format(Locale.getDefault(), "%d", game.getHonbaCount()));
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
        showRoundInfo(false);
        showEndRoundOptions(true);
    }

    private void showRoundInfo(boolean show) {
        int visibility = show ? View.VISIBLE : View.INVISIBLE;

        ImageView iv = findViewById(R.id.ivRoundWind);
        iv.setVisibility(visibility);

        TextView tv = findViewById(R.id.tvRoundCount);
        tv.setVisibility(visibility);
    }

    private void showEndRoundOptions(boolean show) {
        int visibility = show ? View.VISIBLE : View.INVISIBLE;

        Button btn = findViewById(R.id.btnRon);
        btn.setVisibility(visibility);

        btn = findViewById(R.id.btnTsumo);
        btn.setVisibility(visibility);

        btn = findViewById(R.id.btnRyuukyoku);
        btn.setVisibility(visibility);

        btn = findViewById(R.id.btnChombo);
        btn.setVisibility(visibility);
    }
}
