package com.example.riichicompanion;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import java.util.Locale;

public class ScoreTrackerActivity extends AppCompatActivity {

    public static final String GAME_TO_SHOW = "com.example.riichicompanion.GAME_TO_SHOW";
    private static final int TIME_BEFORE_AUX_FADE_OUT = 8000;
    private static final int FADE_OUT_TIME = 1000;

    private Game game;
    private AlertDialog.Builder gameSettingsDialogBuilder;
    private boolean inEndRoundProcess;
    private EndRoundStep currentStep;
    private Player selectedLoser;
    private Handler beforeFadeOutHandler;
    private Handler afterFadeOutHandler;

    //region Views

    private ConstraintLayout clBottom;
    private ConstraintLayout clRight;
    private ConstraintLayout clTop;
    private ConstraintLayout clLeft;
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
    private TextView tvX;
    private TextView tvX2;
    private TextView tvBottomPlayerAux;
    private TextView tvRightPlayerAux;
    private TextView tvTopPlayerAux;
    private TextView tvLeftPlayerAux;
    private TextView tvMiddleText;
    private ImageView ivRoundWind;
    private ImageView ivBottomWind;
    private ImageView ivRightWind;
    private ImageView ivTopWind;
    private ImageView ivLeftWind;
    private ImageView ivRiichi;
    private ImageView ivHonba;
    private Button btnRon;
    private Button btnTsumo;
    private Button btnRyuukyoku;
    private Button btnChombo;
    private Button btnEndRound;
    private Button btnContinue;
    private Button btnConfirm;

    //endregion

    //region End round steps

    private final EndRoundStep chomboStep2 = new EndRoundStep(
        "",
        () -> {
            tvMiddleText.setVisibility(View.INVISIBLE);
            btnConfirm.setVisibility(View.VISIBLE);
        },
        (player) -> {},
        () -> {
            int bottomPlayerScoreBefore = game.getBottomPlayer().getScore();
            int rightPlayerScoreBefore = game.getRightPlayer().getScore();
            int topPlayerScoreBefore = game.getTopPlayer().getScore();
            int leftPlayerScoreBefore = game.getLeftPlayer().getScore();

            RoundCalculator.updateGameStateFromChombo(game, selectedLoser);
            PersistentStorage.saveOngoingGame(this, game);
            updateInterface();

            btnConfirm.setVisibility(View.INVISIBLE);
            btnEndRound.setVisibility(View.VISIBLE);
            showRiichiAndHonba(true);
            showRoundInfo(true);

            showScoreChange(game.getBottomPlayer().getScore() - bottomPlayerScoreBefore, tvBottomPlayerAux);
            showScoreChange(game.getRightPlayer().getScore() - rightPlayerScoreBefore, tvRightPlayerAux);
            showScoreChange(game.getTopPlayer().getScore() - topPlayerScoreBefore, tvTopPlayerAux);
            showScoreChange(game.getLeftPlayer().getScore() - leftPlayerScoreBefore, tvLeftPlayerAux);
            beforeFadeOutHandler.postDelayed(this::fadeOutAuxViews, TIME_BEFORE_AUX_FADE_OUT);

            inEndRoundProcess = false;
        },
        null
    );
    private final EndRoundStep chomboStep1 = new EndRoundStep(
        "Who failed?",
        () -> {
            selectedLoser = null;
            showEndRoundButtons(false);
            tvMiddleText.setText(String.format(Locale.getDefault(), "%s", currentStep.getPromptText()));
            tvMiddleText.setVisibility(View.VISIBLE);
            setAllPlayersClickable(true);
        },
        (player) -> {
            setPlayerAsChomboLoser(player);
            continueToNextStep();
        },
        () -> setAllPlayersClickable(false),
        chomboStep2
    );

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_tracker);
        setSupportActionBar(findViewById(R.id.toolbarScoreTrackerActivity));

        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);

        game = getIntent().getParcelableExtra(GAME_TO_SHOW);
        setupGameSettingsDialog();
        inEndRoundProcess = false;
        beforeFadeOutHandler = new Handler();
        afterFadeOutHandler = new Handler();

        //region Views

        clBottom = findViewById(R.id.clBottom);
        clRight = findViewById(R.id.clRight);
        clTop = findViewById(R.id.clTop);
        clLeft = findViewById(R.id.clLeft);
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
        tvX = findViewById(R.id.tvX);
        tvX2 = findViewById(R.id.tvX2);
        tvBottomPlayerAux = findViewById(R.id.tvBottomPlayerAux);
        tvRightPlayerAux = findViewById(R.id.tvRightPlayerAux);
        tvTopPlayerAux = findViewById(R.id.tvTopPlayerAux);
        tvLeftPlayerAux = findViewById(R.id.tvLeftPlayerAux);
        tvMiddleText = findViewById(R.id.tvMiddleText);
        ivRoundWind = findViewById(R.id.ivRoundWind);
        ivBottomWind = findViewById(R.id.ivBottomWind);
        ivRightWind = findViewById(R.id.ivRightWind);
        ivTopWind = findViewById(R.id.ivTopWind);
        ivLeftWind = findViewById(R.id.ivLeftWind);
        ivRiichi = findViewById(R.id.ivRiichi);
        ivHonba = findViewById(R.id.ivHonba);
        btnRon = findViewById(R.id.btnRon);
        btnTsumo = findViewById(R.id.btnTsumo);
        btnRyuukyoku = findViewById(R.id.btnRyuukyoku);
        btnChombo = findViewById(R.id.btnChombo);
        btnEndRound = findViewById(R.id.btnEndRound);
        btnContinue = findViewById(R.id.btnContinue);
        btnConfirm = findViewById(R.id.btnConfirm);

        //endregion
    }

    private void setupGameSettingsDialog() {
        String message = String.format(
            Locale.getDefault(),
            "Initial points: %d\nMin. points to win: %d\nGame length: %s",
            game.getInitialPoints(),
            game.getMinPointsToWin(),
            game.getGameLength().toString()
        );

        if (game.getNumberOfPlayers() == 3) {
            message = message.concat(String.format(
                Locale.getDefault(),
                "\nUse tsumo loss: %s",
                game.usesTsumoLoss() ? "Yes" : "No"
            ));
        }

        gameSettingsDialogBuilder = new AlertDialog.Builder(this);
        gameSettingsDialogBuilder.setTitle(R.string.game_settings_dialog_title)
                                 .setMessage(message)
                                 .setPositiveButton(R.string.close, (dialog, which) -> {});
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (game.getNumberOfPlayers() == 3) {
            clLeft.setVisibility(View.INVISIBLE);
            clLeft.setOnClickListener(null);
        }

        updateInterface();

        clBottom.setClickable(false);
        clRight.setClickable(false);
        clTop.setClickable(false);
        clLeft.setClickable(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_score_tracker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.miGameSettings) {
            gameSettingsDialogBuilder.create().show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (inEndRoundProcess)
            cancelEndRoundProcess();
        else
            super.onBackPressed();
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
        inEndRoundProcess = true;

        beforeFadeOutHandler.removeCallbacksAndMessages(null);
        afterFadeOutHandler.removeCallbacksAndMessages(null);

        btnEndRound.setVisibility(View.INVISIBLE);
        hidePlayerAuxTextViews();
        showRoundInfo(false);
        showRiichiAndHonba(false);
        showEndRoundButtons(true);
    }

    private void showRiichiAndHonba(boolean show) {
        int visibility = show ? View.VISIBLE : View.INVISIBLE;

        ivRiichi.setVisibility(visibility);
        tvX.setVisibility(visibility);
        tvRiichiCount.setVisibility(visibility);
        ivHonba.setVisibility(visibility);
        tvX2.setVisibility(visibility);
        tvHonbaCount.setVisibility(visibility);
    }

    private void showRoundInfo(boolean show) {
        int visibility = show ? View.VISIBLE : View.INVISIBLE;

        ivRoundWind.setVisibility(visibility);
        tvRoundCount.setVisibility(visibility);
    }

    private void showEndRoundButtons(boolean show) {
        int visibility = show ? View.VISIBLE : View.INVISIBLE;

        btnRon.setVisibility(visibility);
        btnTsumo.setVisibility(visibility);
        btnRyuukyoku.setVisibility(visibility);
        btnChombo.setVisibility(visibility);
    }

    private void hidePlayerAuxTextViews() {
        tvBottomPlayerAux.setVisibility(View.INVISIBLE);
        tvRightPlayerAux.setVisibility(View.INVISIBLE);
        tvTopPlayerAux.setVisibility(View.INVISIBLE);
        tvLeftPlayerAux.setVisibility(View.INVISIBLE);
    }

    public void startChomboProcess(View view) {
        currentStep = chomboStep1;
        chomboStep1.doOnStepStart();
    }

    private void setPlayerAsChomboLoser(Player player) {
        TextView tvPlayerState;

        if (player == game.getBottomPlayer())
            tvPlayerState = tvBottomPlayerAux;
        else if (player == game.getRightPlayer())
            tvPlayerState = tvRightPlayerAux;
        else if (player == game.getTopPlayer())
            tvPlayerState = tvTopPlayerAux;
        else
            tvPlayerState = tvLeftPlayerAux;

        tvPlayerState.setText(String.format(Locale.getDefault(),  "%s", "Failed"));
        tvPlayerState.setTextColor(ResourcesCompat.getColor(getResources(), R.color.lost_text, getTheme()));
        tvPlayerState.setVisibility(View.VISIBLE);

        selectedLoser = player;
    }

    public void selectBottomPlayer(View view) {
        clBottom.setClickable(false);
        currentStep.doOnPlayerSelected(game.getBottomPlayer());
    }

    public void selectRightPlayer(View view) {
        clRight.setClickable(false);
        currentStep.doOnPlayerSelected(game.getRightPlayer());
    }

    public void selectTopPlayer(View view) {
        clTop.setClickable(false);
        currentStep.doOnPlayerSelected(game.getTopPlayer());
    }

    public void selectLeftPlayer(View view) {
        clLeft.setClickable(false);
        currentStep.doOnPlayerSelected(game.getLeftPlayer());
    }

    private void setAllPlayersClickable(boolean clickable) {
        clBottom.setClickable(clickable);
        clRight.setClickable(clickable);
        clTop.setClickable(clickable);
        clLeft.setClickable(clickable);
    }

    public void continueToNextStep(View view) {
        continueToNextStep();
    }

    private void continueToNextStep() {
        currentStep.doOnStepEnd();
        currentStep = currentStep.getNextStep();

        if (currentStep != null)
            currentStep.doOnStepStart();
    }

    private void cancelEndRoundProcess() {
        tvMiddleText.setVisibility(View.INVISIBLE);
        btnConfirm.setVisibility(View.INVISIBLE);
        btnContinue.setVisibility(View.INVISIBLE);
        hidePlayerAuxTextViews();
        showEndRoundButtons(false);

        btnEndRound.setVisibility(View.VISIBLE);
        showRiichiAndHonba(true);
        showRoundInfo(true);

        inEndRoundProcess = false;
    }

    private void showScoreChange(int scoreDelta, TextView auxView) {
        if (scoreDelta == 0) {
            auxView.setVisibility(View.INVISIBLE);
            return;
        }

        if (scoreDelta > 0) {
            auxView.setTextColor(ResourcesCompat.getColor(getResources(), R.color.won_text, getTheme()));
            auxView.setText(String.format(Locale.getDefault(), "+%d", scoreDelta));
        }
        else {
            auxView.setTextColor(ResourcesCompat.getColor(getResources(), R.color.lost_text, getTheme()));
            auxView.setText(String.format(Locale.getDefault(), "%d", scoreDelta));
        }

        auxView.setVisibility(View.VISIBLE);
    }

    private void fadeOutAuxViews() {
        for (TextView tv : new TextView[] {tvBottomPlayerAux, tvRightPlayerAux, tvTopPlayerAux, tvLeftPlayerAux}) {
            if (tv.getVisibility() == View.VISIBLE) {
                tv.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
            }
        }

        afterFadeOutHandler.postDelayed(
            () -> {
                tvBottomPlayerAux.setVisibility(View.INVISIBLE);
                tvRightPlayerAux.setVisibility(View.INVISIBLE);
                tvTopPlayerAux.setVisibility(View.INVISIBLE);
                tvLeftPlayerAux.setVisibility(View.INVISIBLE);
            },
            FADE_OUT_TIME
        );
    }
}
