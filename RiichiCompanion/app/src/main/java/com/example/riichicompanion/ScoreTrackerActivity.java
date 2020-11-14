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

    private TextView tvRiichiCount;
    private TextView tvHonbaCount;
    private TextView tvRoundCount;
    private TextView tvBottomName;
    private TextView tvRightName;
    private TextView tvTopName;
    private TextView tvLeftName;
    private TextView tvBottomScore;
    private TextView tvRightScore;
    private TextView tvTopScore;
    private TextView tvLeftScore;
    private ImageView ivRoundWind;
    private ImageView ivBottomWind;
    private ImageView ivRightWind;
    private ImageView ivTopWind;
    private ImageView ivLeftWind;
    private Button btnRon;
    private Button btnTsumo;
    private Button btnRyuukyoku;
    private Button btnChombo;

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_tracker);
        setSupportActionBar(findViewById(R.id.toolbarScoreTrackerActivity));

        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);

        tvRiichiCount = findViewById(R.id.tvRiichiCount);
        tvHonbaCount = findViewById(R.id.tvHonbaCount);
        tvRoundCount = findViewById(R.id.tvRoundCount);
        tvBottomName = findViewById(R.id.tvBottomName);
        tvRightName = findViewById(R.id.tvRightName);
        tvTopName = findViewById(R.id.tvTopName);
        tvLeftName = findViewById(R.id.tvLeftName);
        tvBottomScore = findViewById(R.id.tvBottomScore);
        tvRightScore = findViewById(R.id.tvRightScore);
        tvTopScore = findViewById(R.id.tvTopScore);
        tvLeftScore = findViewById(R.id.tvLeftScore);
        tvLeftScore = findViewById(R.id.tvLeftScore);
        ivRoundWind = findViewById(R.id.ivRoundWind);
        ivBottomWind = findViewById(R.id.ivBottomWind);
        ivRightWind = findViewById(R.id.ivRightWind);
        ivTopWind = findViewById(R.id.ivTopWind);
        ivLeftWind = findViewById(R.id.ivLeftWind);
        btnRon = findViewById(R.id.btnRon);
        btnTsumo = findViewById(R.id.btnTsumo);
        btnRyuukyoku = findViewById(R.id.btnRyuukyoku);
        btnChombo = findViewById(R.id.btnChombo);
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
        tvRiichiCount.setText(String.format(Locale.getDefault(), "%d", game.getRiichiCount()));
        tvHonbaCount.setText(String.format(Locale.getDefault(), "%d", game.getHonbaCount()));
    }

    private void updateRoundInformation() {
        tvRoundCount.setText(String.format(Locale.getDefault(), "%d", game.getRoundNumberForDisplay()));
        ivRoundWind.setImageDrawable(game.getPrevalentWind().getImage(this));
    }

    private void updatePlayerInformation() {
        tvBottomName.setText(String.format(Locale.getDefault(), "%s", game.getBottomPlayer().getName()));
        tvRightName.setText(String.format(Locale.getDefault(), "%s", game.getRightPlayer().getName()));
        tvTopName.setText(String.format(Locale.getDefault(), "%s", game.getTopPlayer().getName()));
        tvLeftName.setText(String.format(Locale.getDefault(), "%s", game.getLeftPlayer().getName()));
        tvBottomScore.setText(String.format(Locale.getDefault(), "%d", game.getBottomPlayer().getScore()));
        tvRightScore.setText(String.format(Locale.getDefault(), "%d", game.getRightPlayer().getScore()));
        tvTopScore.setText(String.format(Locale.getDefault(), "%d", game.getTopPlayer().getScore()));
        tvLeftScore.setText(String.format(Locale.getDefault(), "%d", game.getLeftPlayer().getScore()));
        ivBottomWind.setImageDrawable(game.getBottomPlayer().getSeatWind().getImage(this));
        ivRightWind.setImageDrawable(game.getRightPlayer().getSeatWind().getImage(this));
        ivTopWind.setImageDrawable(game.getTopPlayer().getSeatWind().getImage(this));
        ivLeftWind.setImageDrawable(game.getLeftPlayer().getSeatWind().getImage(this));
    }

    public void endRound(View view) {
        showRoundInfo(false);
        showEndRoundOptions(true);
    }

    private void showRoundInfo(boolean show) {
        int visibility = show ? View.VISIBLE : View.INVISIBLE;

        ivRoundWind.setVisibility(visibility);
        tvRoundCount.setVisibility(visibility);
    }

    private void showEndRoundOptions(boolean show) {
        int visibility = show ? View.VISIBLE : View.INVISIBLE;

        btnRon.setVisibility(visibility);
        btnTsumo.setVisibility(visibility);
        btnRyuukyoku.setVisibility(visibility);
        btnChombo.setVisibility(visibility);
    }
}
