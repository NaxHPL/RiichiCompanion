package com.example.riichicompanion;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Locale;

public class ScoreTrackerActivity extends AppCompatActivity {

    public static final String GAME_TO_SHOW_EXTRA = "com.example.riichicompanion.GAME_TO_SHOW_EXTRA";
    public static final String CALCULATED_HAND_EXTRA = "com.example.riichicompanion.CALCULATED_HAND_EXTRA";
    private static final int TIME_BEFORE_FADE_OUT = 8000;
    private static final int FADE_OUT_TIME = 1000;

    private Game game;
    private boolean inEndRoundProcess;
    private EndRoundStep currentEndRoundStep;
    private Handler beforeFadeOutHandler;
    private Handler afterFadeOutHandler;
    private Player playerToApplyHandScore;

    private ArrayList<Pair<Player, HandScore>> ronWinnersAndHandScores;
    private Pair<Player, HandScore> tsumoWinnerAndHandScore;
    private Player loser;
    private ArrayList<Player> playersInTenpai;
    private ArrayList<Player> playersDeclaredRiichi;

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
    private ImageView ivRiichiBottom;
    private ImageView ivRiichiRight;
    private ImageView ivRiichiTop;
    private ImageView ivRiichiLeft;
    private Button btnRon;
    private Button btnTsumo;
    private Button btnRyuukyoku;
    private Button btnChombo;
    private Button btnEndRound;
    private Button btnContinue;
    private Button btnConfirm;

    //endregion

    //region End round steps

    private final EndRoundStep ronStepConfirm = new EndRoundStep(
        "",
        () -> {
            tvMiddleText.setVisibility(View.INVISIBLE);
            btnConfirm.setText(String.format(Locale.getDefault(), "%s", "Confirm Ron"));
            btnConfirm.setVisibility(View.VISIBLE);
        },
        (player) -> {},
        () -> {
            game.saveGameState();

            int bottomPlayerScoreBefore = game.getBottomPlayer().getScore();
            int rightPlayerScoreBefore = game.getRightPlayer().getScore();
            int topPlayerScoreBefore = game.getTopPlayer().getScore();
            int leftPlayerScoreBefore = game.getLeftPlayer().getScore();

            RoundCalculator.updateGameStateFromRon(game, loser, ronWinnersAndHandScores, playersDeclaredRiichi);
            PersistentStorage.saveGame(this, game);

            if (game.satisfiesFinishConditions()) {
                updateInterface(false);
                finishGame();
                return;
            }

            updateInterface(true);

            hidePlayerRiichiSticks();
            btnConfirm.setVisibility(View.INVISIBLE);
            btnEndRound.setVisibility(View.VISIBLE);
            showRiichiAndHonba(true);
            showRoundInfo(true);

            showScoreChange(game.getBottomPlayer().getScore() - bottomPlayerScoreBefore, tvBottomPlayerAux);
            showScoreChange(game.getRightPlayer().getScore() - rightPlayerScoreBefore, tvRightPlayerAux);
            showScoreChange(game.getTopPlayer().getScore() - topPlayerScoreBefore, tvTopPlayerAux);
            showScoreChange(game.getLeftPlayer().getScore() - leftPlayerScoreBefore, tvLeftPlayerAux);
            beforeFadeOutHandler.postDelayed(this::fadeOutAuxViews, TIME_BEFORE_FADE_OUT);
        },
        null
    );
    private final EndRoundStep ronStepGetRiichi = new EndRoundStep(
        "Who declared riichi?",
        () -> {
            playersDeclaredRiichi.clear();
            tvMiddleText.setText(String.format(Locale.getDefault(), "%s", currentEndRoundStep.getPromptText()));
            btnContinue.setVisibility(View.VISIBLE);
            setAllPlayersClickable(true);
        },
        this::setPlayerAsDeclaredRiichi,
        () -> {
            btnContinue.setVisibility(View.INVISIBLE);
            setAllPlayersClickable(false);
        },
        ronStepConfirm
    );
    private final EndRoundStep ronStepGetWinners = new EndRoundStep(
        "Who won?",
        () -> {
            ronWinnersAndHandScores.clear();
            tvMiddleText.setText(String.format(Locale.getDefault(), "%s", currentEndRoundStep.getPromptText()));
            setAllPlayersClickable(true);
            getLayoutFromPlayer(loser).setClickable(false);
        },
        this::setPlayerAsRonWinner,
        () -> setAllPlayersClickable(false),
        ronStepGetRiichi
    );
    private final EndRoundStep ronStepGetLoser = new EndRoundStep(
        "Who lost?",
        () -> {
            loser = null;
            showEndRoundButtons(false);
            tvMiddleText.setText(String.format(Locale.getDefault(), "%s", currentEndRoundStep.getPromptText()));
            tvMiddleText.setVisibility(View.VISIBLE);
            setAllPlayersClickable(true);
        },
        (player) -> {
            setPlayerAsRonLoser(player);
            continueToNextStep();
        },
        () -> setAllPlayersClickable(false),
        ronStepGetWinners
    );

    private final EndRoundStep tsumoStepConfirm = new EndRoundStep(
        "",
        () -> {
            tvMiddleText.setVisibility(View.INVISIBLE);
            btnConfirm.setText(String.format(Locale.getDefault(), "%s", "Confirm Tsumo"));
            btnConfirm.setVisibility(View.VISIBLE);
        },
        (player) -> {},
        () -> {
            game.saveGameState();

            int bottomPlayerScoreBefore = game.getBottomPlayer().getScore();
            int rightPlayerScoreBefore = game.getRightPlayer().getScore();
            int topPlayerScoreBefore = game.getTopPlayer().getScore();
            int leftPlayerScoreBefore = game.getLeftPlayer().getScore();

            RoundCalculator.updateGameStateFromTsumo(game, tsumoWinnerAndHandScore.first, tsumoWinnerAndHandScore.second, playersDeclaredRiichi);
            PersistentStorage.saveGame(this, game);

            if (game.satisfiesFinishConditions()) {
                updateInterface(false);
                finishGame();
                return;
            }

            updateInterface(true);

            hidePlayerRiichiSticks();
            btnConfirm.setVisibility(View.INVISIBLE);
            btnEndRound.setVisibility(View.VISIBLE);
            showRiichiAndHonba(true);
            showRoundInfo(true);

            showScoreChange(game.getBottomPlayer().getScore() - bottomPlayerScoreBefore, tvBottomPlayerAux);
            showScoreChange(game.getRightPlayer().getScore() - rightPlayerScoreBefore, tvRightPlayerAux);
            showScoreChange(game.getTopPlayer().getScore() - topPlayerScoreBefore, tvTopPlayerAux);
            showScoreChange(game.getLeftPlayer().getScore() - leftPlayerScoreBefore, tvLeftPlayerAux);
            beforeFadeOutHandler.postDelayed(this::fadeOutAuxViews, TIME_BEFORE_FADE_OUT);
        },
        null
    );
    private final EndRoundStep tsumoStepGetRiichi = new EndRoundStep(
        "Who declared riichi?",
        () -> {
            playersDeclaredRiichi.clear();
            tvMiddleText.setText(String.format(Locale.getDefault(), "%s", currentEndRoundStep.getPromptText()));
            btnContinue.setVisibility(View.VISIBLE);
            setAllPlayersClickable(true);
        },
        this::setPlayerAsDeclaredRiichi,
        () -> {
            btnContinue.setVisibility(View.INVISIBLE);
            setAllPlayersClickable(false);
        },
        tsumoStepConfirm
    );
    private final EndRoundStep tsumoStepGetWinner = new EndRoundStep(
        "Who won?",
        () -> {
            tsumoWinnerAndHandScore = null;
            showEndRoundButtons(false);
            tvMiddleText.setText(String.format(Locale.getDefault(), "%s", currentEndRoundStep.getPromptText()));
            tvMiddleText.setVisibility(View.VISIBLE);
            setAllPlayersClickable(true);
        },
        this::setPlayerAsTsumoWinner,
        () -> setAllPlayersClickable(false),
        tsumoStepGetRiichi
    );

    private final EndRoundStep ryuukyokuStepConfirm = new EndRoundStep(
        "",
        () -> {
            tvMiddleText.setVisibility(View.INVISIBLE);
            btnConfirm.setText(String.format(Locale.getDefault(), "%s", "Confirm Ryuukyoku"));
            btnConfirm.setVisibility(View.VISIBLE);
        },
        (player) -> {},
        () -> {
            game.saveGameState();

            int bottomPlayerScoreBefore = game.getBottomPlayer().getScore();
            int rightPlayerScoreBefore = game.getRightPlayer().getScore();
            int topPlayerScoreBefore = game.getTopPlayer().getScore();
            int leftPlayerScoreBefore = game.getLeftPlayer().getScore();

            RoundCalculator.updateGameStateFromRyuukyoku(game, playersInTenpai, playersDeclaredRiichi);
            PersistentStorage.saveGame(this, game);

            if (game.satisfiesFinishConditions()) {
                updateInterface(false);
                finishGame();
                return;
            }

            updateInterface(true);

            hidePlayerRiichiSticks();
            btnConfirm.setVisibility(View.INVISIBLE);
            btnEndRound.setVisibility(View.VISIBLE);
            showRiichiAndHonba(true);
            showRoundInfo(true);

            showScoreChange(game.getBottomPlayer().getScore() - bottomPlayerScoreBefore, tvBottomPlayerAux);
            showScoreChange(game.getRightPlayer().getScore() - rightPlayerScoreBefore, tvRightPlayerAux);
            showScoreChange(game.getTopPlayer().getScore() - topPlayerScoreBefore, tvTopPlayerAux);
            showScoreChange(game.getLeftPlayer().getScore() - leftPlayerScoreBefore, tvLeftPlayerAux);
            beforeFadeOutHandler.postDelayed(this::fadeOutAuxViews, TIME_BEFORE_FADE_OUT);
        },
        null
    );
    private final EndRoundStep ryuukyokuStepGetRiichi = new EndRoundStep(
        "Who declared riichi?",
        () -> {
            playersDeclaredRiichi.clear();
            tvMiddleText.setText(String.format(Locale.getDefault(), "%s", currentEndRoundStep.getPromptText()));
            setAllPlayersClickable(true);
        },
        this::setPlayerAsDeclaredRiichi,
        () -> {
            btnContinue.setVisibility(View.INVISIBLE);
            setAllPlayersClickable(false);
        },
        ryuukyokuStepConfirm
    );
    private final EndRoundStep ryuukyokuStepGetTenpai = new EndRoundStep(
        "Who was in tenpai?",
        () -> {
            playersInTenpai.clear();
            showEndRoundButtons(false);
            tvMiddleText.setText(String.format(Locale.getDefault(), "%s", currentEndRoundStep.getPromptText()));
            tvMiddleText.setVisibility(View.VISIBLE);
            btnContinue.setVisibility(View.VISIBLE);
            setAllPlayersClickable(true);
        },
        this::setPlayerAsInTenpai,
        () -> setAllPlayersClickable(false),
        ryuukyokuStepGetRiichi
    );

    private final EndRoundStep chomboStepConfirm = new EndRoundStep(
        "",
        () -> {
            tvMiddleText.setVisibility(View.INVISIBLE);
            btnConfirm.setText(String.format(Locale.getDefault(), "%s", "Confirm Chombo"));
            btnConfirm.setVisibility(View.VISIBLE);
        },
        (player) -> {},
        () -> {
            game.saveGameState();

            int bottomPlayerScoreBefore = game.getBottomPlayer().getScore();
            int rightPlayerScoreBefore = game.getRightPlayer().getScore();
            int topPlayerScoreBefore = game.getTopPlayer().getScore();
            int leftPlayerScoreBefore = game.getLeftPlayer().getScore();

            RoundCalculator.updateGameStateFromChombo(game, loser);
            PersistentStorage.saveGame(this, game);

            if (game.satisfiesFinishConditions()) {
                updateInterface(false);
                finishGame();
                return;
            }

            updateInterface(true);

            btnConfirm.setVisibility(View.INVISIBLE);
            btnEndRound.setVisibility(View.VISIBLE);
            showRiichiAndHonba(true);
            showRoundInfo(true);

            showScoreChange(game.getBottomPlayer().getScore() - bottomPlayerScoreBefore, tvBottomPlayerAux);
            showScoreChange(game.getRightPlayer().getScore() - rightPlayerScoreBefore, tvRightPlayerAux);
            showScoreChange(game.getTopPlayer().getScore() - topPlayerScoreBefore, tvTopPlayerAux);
            showScoreChange(game.getLeftPlayer().getScore() - leftPlayerScoreBefore, tvLeftPlayerAux);
            beforeFadeOutHandler.postDelayed(this::fadeOutAuxViews, TIME_BEFORE_FADE_OUT);
        },
        null
    );
    private final EndRoundStep chomboStepGetFailed = new EndRoundStep(
        "Who failed?",
        () -> {
            loser = null;
            showEndRoundButtons(false);
            tvMiddleText.setText(String.format(Locale.getDefault(), "%s", currentEndRoundStep.getPromptText()));
            tvMiddleText.setVisibility(View.VISIBLE);
            setAllPlayersClickable(true);
        },
        (player) -> {
            setPlayerAsChomboLoser(player);
            continueToNextStep();
        },
        () -> setAllPlayersClickable(false),
        chomboStepConfirm
    );

    //endregion

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeOption theme = PersistentStorage.getThemeOption(this);
        setTheme(theme.getThemeId());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_tracker);
        setSupportActionBar(findViewById(R.id.toolbarScoreTrackerActivity));

        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);

        game = getIntent().getParcelableExtra(GAME_TO_SHOW_EXTRA);
        inEndRoundProcess = false;
        beforeFadeOutHandler = new Handler();
        afterFadeOutHandler = new Handler();
        ronWinnersAndHandScores = new ArrayList<>();
        playersInTenpai = new ArrayList<>();
        playersDeclaredRiichi = new ArrayList<>();

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
        ivRiichiBottom = findViewById(R.id.ivRiichiBottom);
        ivRiichiRight = findViewById(R.id.ivRiichiRight);
        ivRiichiTop = findViewById(R.id.ivRiichiTop);
        ivRiichiLeft = findViewById(R.id.ivRiichiLeft);
        btnRon = findViewById(R.id.btnRon);
        btnTsumo = findViewById(R.id.btnTsumo);
        btnRyuukyoku = findViewById(R.id.btnRyuukyoku);
        btnChombo = findViewById(R.id.btnChombo);
        btnEndRound = findViewById(R.id.btnEndRound);
        btnContinue = findViewById(R.id.btnContinue);
        btnConfirm = findViewById(R.id.btnConfirm);

        //endregion

        Toolbar toolbarScoreTrackerActivity = findViewById(R.id.toolbarScoreTrackerActivity);
        toolbarScoreTrackerActivity.setPopupTheme(theme.getThemeId());
        setSupportActionBar(toolbarScoreTrackerActivity);

        setScoreTextColours(theme);

        btnContinue.setOnClickListener((v) -> continueToNextStep());
        btnConfirm.setOnClickListener((v) -> continueToNextStep());

        clBottom.setOnTouchListener(this::onPlayerTouched);
        clRight.setOnTouchListener(this::onPlayerTouched);
        clTop.setOnTouchListener(this::onPlayerTouched);
        clLeft.setOnTouchListener(this::onPlayerTouched);

        ConstraintLayout clScoreTrackerMain = findViewById(R.id.clScoreTrackerMain);
        clScoreTrackerMain.setKeepScreenOn(PersistentStorage.getKeepScreenOn(this));

        if (game.getNumberOfPlayers() == 3) {
            clLeft.setVisibility(View.INVISIBLE);
            clLeft.setOnClickListener(null);
            clLeft.setOnTouchListener(null);
        }

        updateInterface(true);
        setAllPlayersClickable(true);

        if (game.isFinished()) {
            showRoundInfo(false);
            showRiichiAndHonba(false);
            btnEndRound.setVisibility(View.INVISIBLE);
            showPlayerAuxTextViews(false);

            tvMiddleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 34);
            tvMiddleText.setText(String.format(Locale.getDefault(), "%s", "Finish!"));
            tvMiddleText.setVisibility(View.VISIBLE);
        }
    }

    private void setScoreTextColours(ThemeOption theme) {
        int color;
        if (theme == ThemeOption.Dark)
            color = getResources().getColor(R.color.white, getTheme());
        else
            color = getResources().getColor(R.color.black, getTheme());

        tvBottomScore.setTextColor(color);
        tvRightScore.setTextColor(color);
        tvTopScore.setTextColor(color);
        tvLeftScore.setTextColor(color);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_score_tracker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        else if (item.getItemId() == R.id.miGameSettings) {
            openGameSettingsDialog();
            return true;
        }
        else if (item.getItemId() == R.id.miUndoRound) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Undo Last Round?")
                .setMessage("Are you sure you want to undo the last round?")
                .setPositiveButton("Undo Round", ((dialog, which) -> {
                    game.restoreLastGameState();
                    PersistentStorage.saveGame(this, game);
                    updateInterface(true);
                }))
                .setNegativeButton("Cancel", ((dialog, which) -> {}));

            builder.create().show();
            return true;
        }
        else if (item.getItemId() == R.id.miFinishGame) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Finish Game?")
                   .setMessage("Are you sure you want to end the game?")
                   .setPositiveButton("Finish Game", ((dialog, which) -> {
                       game.saveGameState();
                       finishGame();
                   }))
                   .setNegativeButton("Cancel", ((dialog, which) -> {}));

            builder.create().show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.getItem(1).setEnabled(game.hasPreviousRoundState());
        menu.getItem(2).setEnabled(!inEndRoundProcess && !game.isFinished());
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (inEndRoundProcess)
            cancelEndRoundProcess();
        else
            super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == HandCalculatorActivity.REQUEST_CODE) {
            if (resultCode == HandCalculatorActivity.RESULT_CODE_CONFIRMED) {
                HandScore hs = data.getParcelableExtra(CALCULATED_HAND_EXTRA);

                if (currentEndRoundStep == tsumoStepGetWinner) {
                    tsumoWinnerAndHandScore = Pair.create(playerToApplyHandScore, hs);
                    continueToNextStep();
                }
                else if (currentEndRoundStep == ronStepGetWinners) {
                    ronWinnersAndHandScores.add(Pair.create(playerToApplyHandScore, hs));
                    btnContinue.setVisibility(View.VISIBLE);
                    getLayoutFromPlayer(playerToApplyHandScore).setClickable(false);
                }

                TextView tvAux = getAuxTextView(playerToApplyHandScore);

                tvAux.setText(String.format(Locale.getDefault(), "%s", hs.toDisplayString()));
                tvAux.setTextColor(ResourcesCompat.getColor(getResources(), R.color.won_text, getTheme()));
                tvAux.setVisibility(View.VISIBLE);
            }
            else if (resultCode == HandCalculatorActivity.RESULT_CODE_CANCELLED) {
                if (currentEndRoundStep == tsumoStepGetWinner)
                    setAllPlayersClickable(true);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void openGameSettingsDialog() {
        GameSettingsDialog dialog = new GameSettingsDialog(game);
        dialog.show(getSupportFragmentManager(), "game_settings_dialog");
    }

    private void updateInterface(boolean updateSeatWinds) {
        showRoundInfo(true);
        showRiichiAndHonba(true);
        btnEndRound.setVisibility(View.VISIBLE);
        tvMiddleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        tvMiddleText.setVisibility(View.INVISIBLE);

        updateRoundInformation();
        updateStickCounts();
        updatePlayerInformation(updateSeatWinds);
    }

    private void updateStickCounts() {
        tvRiichiCount.setText(String.format(Locale.getDefault(), "%d", game.getRiichiCount()));
        tvHonbaCount.setText(String.format(Locale.getDefault(), "%d", game.getHonbaCount()));
    }

    private void updateRoundInformation() {
        tvRoundCount.setText(String.format(Locale.getDefault(), "%d", game.getRoundNumberForDisplay()));
        ivRoundWind.setImageDrawable(game.getPrevalentWind().getImage(this));
    }

    private void updatePlayerInformation(boolean updateSeatWinds) {
        tvBottomName.setText(String.format(Locale.getDefault(), "%s", game.getBottomPlayer().getName()));
        tvRightName.setText(String.format(Locale.getDefault(), "%s", game.getRightPlayer().getName()));
        tvTopName.setText(String.format(Locale.getDefault(), "%s", game.getTopPlayer().getName()));
        tvLeftName.setText(String.format(Locale.getDefault(), "%s", game.getLeftPlayer().getName()));

        tvBottomScore.setText(String.format(Locale.getDefault(), "%d", game.getBottomPlayer().getScore()));
        tvRightScore.setText(String.format(Locale.getDefault(), "%d", game.getRightPlayer().getScore()));
        tvTopScore.setText(String.format(Locale.getDefault(), "%d", game.getTopPlayer().getScore()));
        tvLeftScore.setText(String.format(Locale.getDefault(), "%d", game.getLeftPlayer().getScore()));

        if (updateSeatWinds) {
            ivBottomWind.setImageDrawable(game.getBottomPlayer().getSeatWind().getImage(this));
            ivRightWind.setImageDrawable(game.getRightPlayer().getSeatWind().getImage(this));
            ivTopWind.setImageDrawable(game.getTopPlayer().getSeatWind().getImage(this));
            ivLeftWind.setImageDrawable(game.getLeftPlayer().getSeatWind().getImage(this));
        }
    }

    public void endRound(View view) {
        inEndRoundProcess = true;

        beforeFadeOutHandler.removeCallbacksAndMessages(null);
        afterFadeOutHandler.removeCallbacksAndMessages(null);

        btnEndRound.setVisibility(View.INVISIBLE);
        showPlayerAuxTextViews(false);
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

    private void showPlayerAuxTextViews(boolean show) {
        int visibility = show ? View.VISIBLE : View.INVISIBLE;

        tvBottomPlayerAux.setVisibility(visibility);
        tvRightPlayerAux.setVisibility(visibility);
        tvTopPlayerAux.setVisibility(visibility);

        if (game.getNumberOfPlayers() == 4)
            tvLeftPlayerAux.setVisibility(visibility);
    }

    private void hidePlayerRiichiSticks() {
        ivRiichiBottom.setVisibility(View.INVISIBLE);
        ivRiichiRight.setVisibility(View.INVISIBLE);
        ivRiichiTop.setVisibility(View.INVISIBLE);
        ivRiichiLeft.setVisibility(View.INVISIBLE);
    }

    public void startRonProcess(View view) {
        currentEndRoundStep = ronStepGetLoser;
        currentEndRoundStep.doOnStepStart();
    }

    public void startTsumoProcess(View view) {
        currentEndRoundStep = tsumoStepGetWinner;
        currentEndRoundStep.doOnStepStart();
    }

    public void startRyuukyokuProcess(View view) {
        currentEndRoundStep = ryuukyokuStepGetTenpai;
        currentEndRoundStep.doOnStepStart();
    }

    public void startChomboProcess(View view) {
        currentEndRoundStep = chomboStepGetFailed;
        currentEndRoundStep.doOnStepStart();
    }

    private void setPlayerAsRonLoser(Player player) {
        loser = player;

        TextView tvAux = getAuxTextView(player);

        tvAux.setText(String.format(Locale.getDefault(),  "%s", "Loser"));
        tvAux.setTextColor(ResourcesCompat.getColor(getResources(), R.color.lost_text, getTheme()));
        tvAux.setVisibility(View.VISIBLE);
    }

    private void setPlayerAsRonWinner(Player player) {
        playerToApplyHandScore = player;
        openHandCalculator(WinType.Ron);
    }

    private void setPlayerAsTsumoWinner(Player player) {
        playerToApplyHandScore = player;
        openHandCalculator(WinType.Tsumo);
    }

    private void setPlayerAsInTenpai(Player player) {
        if (!playersInTenpai.contains(player))
            playersInTenpai.add(player);

        TextView tvAux = getAuxTextView(player);

        tvAux.setText(String.format(Locale.getDefault(),  "%s", "Tenpai"));
        tvAux.setTextColor(ResourcesCompat.getColor(getResources(), R.color.won_text, getTheme()));
        tvAux.setVisibility(View.VISIBLE);
    }

    private void setPlayerAsDeclaredRiichi(Player player) {
        if(!playersDeclaredRiichi.contains(player))
            playersDeclaredRiichi.add(player);

        if (player == game.getBottomPlayer())
            ivRiichiBottom.setVisibility(View.VISIBLE);
        else if (player == game.getRightPlayer())
            ivRiichiRight.setVisibility(View.VISIBLE);
        else if (player == game.getTopPlayer())
            ivRiichiTop.setVisibility(View.VISIBLE);
        else
            ivRiichiLeft.setVisibility(View.VISIBLE);
    }

    private void setPlayerAsChomboLoser(Player player) {
        loser = player;

        TextView tvAux = getAuxTextView(player);

        tvAux.setText(String.format(Locale.getDefault(),  "%s", "Failed"));
        tvAux.setTextColor(ResourcesCompat.getColor(getResources(), R.color.lost_text, getTheme()));
        tvAux.setVisibility(View.VISIBLE);

    }

    private TextView getAuxTextView(Player player) {
        if (player == game.getBottomPlayer())
            return tvBottomPlayerAux;
        else if (player == game.getRightPlayer())
            return tvRightPlayerAux;
        else if (player == game.getTopPlayer())
            return tvTopPlayerAux;

        return tvLeftPlayerAux;
    }

    private void openHandCalculator(WinType winType) {
        Intent intent = new Intent(this, HandCalculatorActivity.class);

        intent.putExtra(HandCalculatorActivity.WINNER_NAME_EXTRA, playerToApplyHandScore.getName());
        intent.putExtra(HandCalculatorActivity.WIN_TYPE_EXTRA, winType.name());
        intent.putExtra(HandCalculatorActivity.PREVALENT_WIND_EXTRA, game.getPrevalentWind().name());
        intent.putExtra(HandCalculatorActivity.SEAT_WIND_EXTRA, playerToApplyHandScore.getSeatWind().name());

        startActivityForResult(intent, HandCalculatorActivity.REQUEST_CODE);
    }

    public void selectPlayer(View view) {
        if (!inEndRoundProcess || currentEndRoundStep == null)
            return;

        if (currentEndRoundStep != ronStepGetWinners)
            view.setClickable(false);

        if (view == clBottom)
            currentEndRoundStep.doOnPlayerSelected(game.getBottomPlayer());
        else if (view == clRight)
            currentEndRoundStep.doOnPlayerSelected(game.getRightPlayer());
        else if (view == clTop)
            currentEndRoundStep.doOnPlayerSelected(game.getTopPlayer());
        else if (view == clLeft)
            currentEndRoundStep.doOnPlayerSelected(game.getLeftPlayer());
    }

    private void setAllPlayersClickable(boolean clickable) {
        clBottom.setClickable(clickable);
        clRight.setClickable(clickable);
        clTop.setClickable(clickable);
        clLeft.setClickable(clickable);
    }

    public void continueToNextStep() {
        currentEndRoundStep.doOnStepEnd();
        currentEndRoundStep = currentEndRoundStep.getNextStep();

        if (currentEndRoundStep != null)
            currentEndRoundStep.doOnStepStart();
        else {
            setAllPlayersClickable(true);
            inEndRoundProcess = false;
        }
    }

    private void cancelEndRoundProcess() {
        tvMiddleText.setVisibility(View.INVISIBLE);
        btnConfirm.setVisibility(View.INVISIBLE);
        btnContinue.setVisibility(View.INVISIBLE);
        showPlayerAuxTextViews(false);
        hidePlayerRiichiSticks();
        showEndRoundButtons(false);

        btnEndRound.setVisibility(View.VISIBLE);
        showRiichiAndHonba(true);
        showRoundInfo(true);

        currentEndRoundStep = null;
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

    private ConstraintLayout getLayoutFromPlayer(Player player) {
        if (player == game.getBottomPlayer())
            return clBottom;
        if (player == game.getRightPlayer())
            return clRight;
        if (player == game.getTopPlayer())
            return clTop;

        return clLeft;
    }

    private Player getPlayerFromLayout(ConstraintLayout cl) {
        if (cl == clBottom)
            return game.getBottomPlayer();
        if (cl == clRight)
            return game.getRightPlayer();
        if (cl == clTop)
            return game.getTopPlayer();

        return game.getLeftPlayer();
    }

    private void finishGame() {
        showRoundInfo(false);
        showRiichiAndHonba(false);
        btnEndRound.setVisibility(View.INVISIBLE);
        btnConfirm.setVisibility(View.INVISIBLE);
        showPlayerAuxTextViews(false);
        hidePlayerRiichiSticks();

        tvMiddleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 34);
        tvMiddleText.setText(String.format(Locale.getDefault(), "%s", "Finish!"));
        tvMiddleText.setVisibility(View.VISIBLE);

        game.setFinished(true);
        PersistentStorage.saveGame(this, game);
    }

    private boolean onPlayerTouched(View view, MotionEvent event) {
        if (inEndRoundProcess || currentEndRoundStep != null)
            return false;

        ConstraintLayout cl;

        try {
            cl = (ConstraintLayout) view;
        } catch (ClassCastException e) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                showScoreDifferences(getPlayerFromLayout(cl));
                break;
            case MotionEvent.ACTION_UP:
                showPlayerAuxTextViews(false);
        }

        return false;
    }

    private void showScoreDifferences(Player player) {
        beforeFadeOutHandler.removeCallbacksAndMessages(null);
        afterFadeOutHandler.removeCallbacksAndMessages(null);

        for (Player p : game.getPlayers()) {
            if (p == player)
                continue;

            int scoreDiff = p.getScore() - player.getScore();
            TextView tvAux = getAuxTextView(p);

            if (scoreDiff < 0) {
                tvAux.setText(String.format(Locale.getDefault(), "%d", scoreDiff));
                tvAux.setTextColor(ResourcesCompat.getColor(getResources(), R.color.lost_text, getTheme()));
            }
            else if (scoreDiff > 0) {
                tvAux.setText(String.format(Locale.getDefault(), "+%d", scoreDiff));
                tvAux.setTextColor(ResourcesCompat.getColor(getResources(), R.color.won_text, getTheme()));
            }
            else {
                tvAux.setText(String.format(Locale.getDefault(), "%d", scoreDiff));
                tvAux.setTextColor(ResourcesCompat.getColor(getResources(), R.color.neutral_text, getTheme()));
            }
        }

        showPlayerAuxTextViews(true);
        getAuxTextView(player).setVisibility(View.INVISIBLE);
    }
}
