package com.example.riichicompanion;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout constraintLayoutOngoingGame;
    private TextView tvOngoingGamePlayers;
    private TextView tvOngoingGameDateTime;
    private TextView tvOngoingGameRoundNumber;
    private ImageView ivOngoingGameRoundWind;
    private View divider1;

    private Game ongoingGame;
    private AlertDialog.Builder newGameDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbarMainActivity));

        constraintLayoutOngoingGame = findViewById(R.id.ConstraintLayout_ongoing_game);
        tvOngoingGamePlayers = findViewById(R.id.tvOngoingGamePlayers);
        tvOngoingGameDateTime = findViewById(R.id.tvOngoingGameDateTime);
        tvOngoingGameRoundNumber = findViewById(R.id.tvOngoingGameRoundNumber);
        ivOngoingGameRoundWind = findViewById(R.id.ivOngoingGameRoundWind);
        divider1 = findViewById(R.id.divider1);

        newGameDialogBuilder = new AlertDialog.Builder(this) {{
            setTitle(R.string.new_game_dialog_title);
            setMessage(R.string.new_game_dialog_message);
            setPositiveButton(R.string.new_game, (dialog, which) -> startCreateGameActivity());
            setNegativeButton(R.string.cancel, (dialog, which) -> {});
        }};
    }

    @Override
    protected void onStart() {
        super.onStart();

        ongoingGame = PersistentStorage.retrieveOngoingGame(this);

        if (ongoingGame != null) {
            updateOngoingGameInfo();
            constraintLayoutOngoingGame.setVisibility(View.VISIBLE);
            divider1.setVisibility(View.VISIBLE);
        }
        else {
            constraintLayoutOngoingGame.setVisibility(View.GONE);
            divider1.setVisibility(View.GONE);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.miAppSettings) {
            openSettings();
            return true;
        }
        else if (item.getItemId() == R.id.miNewGame) {
            createNewGame();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
//        super.onApplyThemeResource(theme, resid, first);
//
//        if (ivOngoingGameRoundWind != null)
//            ivOngoingGameRoundWind.setImageDrawable(ongoingGame.getPrevalentWind().getImage(this));
//    }

    private void openSettings() {
        AppSettingsDialog dialog = new AppSettingsDialog();
        dialog.show(getSupportFragmentManager(), "app_settings_dialog");
    }

    public void createNewGame() {
        if (ongoingGame == null) {
            startCreateGameActivity();
            return;
        }

        newGameDialogBuilder.create().show();
    }

    private void startCreateGameActivity() {
        Intent intent = new Intent(this, CreateGameActivity.class);
        startActivity(intent);
    }

    private void updateOngoingGameInfo() {
        String players = String.format(
            "%s, %s, %s",
            ongoingGame.getBottomPlayer().getName(),
            ongoingGame.getRightPlayer().getName(),
            ongoingGame.getTopPlayer().getName()
        );

        if (ongoingGame.getNumberOfPlayers() == 4)
            players = players.concat(String.format(", %s", ongoingGame.getLeftPlayer().getName()));

        tvOngoingGamePlayers.setText(String.format(Locale.getDefault(), "%s", players));
        tvOngoingGameDateTime.setText(String.format(Locale.getDefault(), "%s", ongoingGame.getStartDateTime()));

        if (ongoingGame.isFinished()) {
            ivOngoingGameRoundWind.setVisibility(View.INVISIBLE);
            tvOngoingGameRoundNumber.setText(String.format(Locale.getDefault(), "%s", "Finished"));
        }
        else {
            tvOngoingGameRoundNumber.setText(String.format(Locale.getDefault(), "%d", ongoingGame.getRoundNumberForDisplay()));
            ivOngoingGameRoundWind.setVisibility(View.VISIBLE);
            ivOngoingGameRoundWind.setImageDrawable(ongoingGame.getPrevalentWind().getImage(this));
        }
    }

    public void openOngoingGame(View view) {
        Intent intent = new Intent(this, ScoreTrackerActivity.class);
        intent.putExtra(ScoreTrackerActivity.GAME_TO_SHOW, ongoingGame);
        startActivity(intent);
    }
}
