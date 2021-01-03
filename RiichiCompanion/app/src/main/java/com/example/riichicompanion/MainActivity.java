package com.example.riichicompanion;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AppSettingsDialog.AppSettingsDialogListener {

    private ConstraintLayout clMainRoot;
    private Toolbar toolbarMainActivity;
    private View constrainNextGameTo;
    private ArrayList<ConstraintLayout> savedGameCLs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int themeId = PersistentStorage.getThemeOption(this).getThemeId();
        setTheme(themeId);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clMainRoot = findViewById(R.id.clMainRoot);
        toolbarMainActivity = findViewById(R.id.toolbarMainActivity);

        toolbarMainActivity.setPopupTheme(themeId);
        setSupportActionBar(toolbarMainActivity);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateSavedGames();
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

    private void openSettings() {
        AppSettingsDialog dialog = new AppSettingsDialog();
        dialog.show(getSupportFragmentManager(), "app_settings_dialog");
    }

    public void createNewGame() {
        Intent intent = new Intent(this, CreateGameActivity.class);
        startActivity(intent);
    }

    public void openGame(Game game) {
        Intent intent = new Intent(this, ScoreTrackerActivity.class);
        intent.putExtra(ScoreTrackerActivity.GAME_TO_SHOW_EXTRA, game);
        startActivity(intent);
    }

    @Override
    public void onSettingsSaved(boolean themeChanged) {
        if (themeChanged)
            (new Handler()).postDelayed(this::recreate, 100);
    }

    private void updateSavedGames() {
        if (savedGameCLs == null)
            savedGameCLs = new ArrayList<>();
        for (ConstraintLayout cl : savedGameCLs)
            clMainRoot.removeView(cl);

        ArrayList<Game> savedGames = PersistentStorage.retrieveSavedGames(this);
        constrainNextGameTo = toolbarMainActivity;

        for (int i = savedGames.size() - 1; i >= 0; i--)
            showSavedGame(savedGames.get(i));
    }

    private void showSavedGame(Game game) {
        ConstraintLayout clSavedGame = (ConstraintLayout) getLayoutInflater().inflate(
            R.layout.saved_game,
            clMainRoot,
            false
        );
        clSavedGame.setId(View.generateViewId());

        TextView tvPlayerNames = (TextView) clSavedGame.getChildAt(0);
        TextView tvGameCreationTime = (TextView) clSavedGame.getChildAt(1);
        ImageView ivRoundWind = (ImageView) clSavedGame.getChildAt(2);
        TextView tvRoundNumber = (TextView) clSavedGame.getChildAt(3);

        String playerNames = String.format(
            Locale.getDefault(),
            "%s, %s, %s",
            game.getBottomPlayer().getName(),
            game.getRightPlayer().getName(),
            game.getTopPlayer().getName()
        );

        if (game.getNumberOfPlayers() == 4)
            playerNames = playerNames.concat(String.format(Locale.getDefault(), ", %s", game.getLeftPlayer().getName()));

        tvPlayerNames.setText(playerNames);
        tvGameCreationTime.setText(String.format(Locale.getDefault(), "%s", game.getStartDateTime()));

        if (game.isFinished()) {
            ivRoundWind.setVisibility(View.INVISIBLE);
            tvRoundNumber.setText(String.format(Locale.getDefault(), "%s", "Finished"));
        }
        else {
            tvRoundNumber.setText(String.format(Locale.getDefault(), "%d", game.getRoundNumberForDisplay()));
            ivRoundWind.setVisibility(View.VISIBLE);
            ivRoundWind.setImageDrawable(game.getPrevalentWind().getImage(this));
        }

        clMainRoot.addView(clSavedGame);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(clMainRoot);

        constraintSet.connect(clSavedGame.getId(), ConstraintSet.START, clMainRoot.getId(), ConstraintSet.START);
        constraintSet.connect(clSavedGame.getId(), ConstraintSet.END, clMainRoot.getId(), ConstraintSet.END);
        constraintSet.connect(clSavedGame.getId(), ConstraintSet.TOP, constrainNextGameTo.getId(), ConstraintSet.BOTTOM);

        constraintSet.applyTo(clMainRoot);

        clSavedGame.setTag(R.id.saved_game_object, game);
        clSavedGame.setOnClickListener(v -> openGame((Game)clSavedGame.getTag(R.id.saved_game_object)));
        clSavedGame.setOnLongClickListener(v -> {
            showDeleteGameDialog((Game)clSavedGame.getTag(R.id.saved_game_object));
            return true;
        });

        savedGameCLs.add(clSavedGame);
        constrainNextGameTo = clSavedGame;
    }

    private void showDeleteGameDialog(Game gameToDelete) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
            .setTitle(R.string.delete_game_dialog_title)
            .setMessage(R.string.delete_game_dialog_message)
            .setNegativeButton(R.string.cancel, (dialog, which) -> {})
            .setPositiveButton(R.string.delete_game, ((dialog, which) -> {
                PersistentStorage.deleteGame(this, gameToDelete);
                updateSavedGames();
            }));

        builder.create().show();
    }
}
