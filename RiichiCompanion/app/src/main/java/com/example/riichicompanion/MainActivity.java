package com.example.riichicompanion;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity
    extends AppCompatActivity
    implements AppSettingsDialog.AppSettingsDialogListener, NavigationView.OnNavigationItemSelectedListener {

    private ConstraintLayout clMainRoot;
    private DrawerLayout dlMain;
    private ActionBarDrawerToggle drawerToggle;
    private int idToConstrainNextGameTo;
    private ArrayList<ConstraintLayout> savedGameCLs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int themeId = PersistentStorage.getThemeOption(this).getThemeId();
        setTheme(themeId);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clMainRoot = findViewById(R.id.clMainRoot);
        dlMain = findViewById(R.id.dlMain);

        Toolbar toolbarMainActivity = findViewById(R.id.toolbarMainActivity);
        toolbarMainActivity.setPopupTheme(themeId);
        setSupportActionBar(toolbarMainActivity);

        drawerToggle = new ActionBarDrawerToggle(
            this,
            dlMain,
            toolbarMainActivity,
            R.string.nav_drawer_open,
            R.string.nav_drawer_close
        );
        dlMain.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        NavigationView nvMain = findViewById(R.id.nvMain);
        nvMain.setNavigationItemSelectedListener(this);
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
        if (drawerToggle.onOptionsItemSelected(item))
            return true;
        if (item.getItemId() == R.id.miNewGame) {
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
            new Handler().postDelayed(this::recreate, 100);
    }

    private void updateSavedGames() {
        if (savedGameCLs == null)
            savedGameCLs = new ArrayList<>();
        else {
            for (ConstraintLayout cl : savedGameCLs)
                clMainRoot.removeView(cl);
            savedGameCLs.clear();
        }

        idToConstrainNextGameTo = -1;
        ArrayList<Game> savedGames = PersistentStorage.retrieveSavedGames(this);

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
        constraintSet.connect(clSavedGame.getId(), ConstraintSet.TOP, idToConstrainNextGameTo, ConstraintSet.BOTTOM);

        constraintSet.applyTo(clMainRoot);

        clSavedGame.setOnClickListener(v -> openGame(game));
        clSavedGame.setOnLongClickListener(v -> {
            showDeleteGameDialog(game);
            return true;
        });

        savedGameCLs.add(clSavedGame);
        idToConstrainNextGameTo = clSavedGame.getId();
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

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (dlMain.isOpen())
            dlMain.close();
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.miHandCalculator) {
            openHandCalculator();
            dlMain.close();
            return true;
        }
        if (item.getItemId() == R.id.miSettings) {
            openSettings();
            dlMain.close();
            return true;
        }
        if (item.getItemId() == R.id.miAbout) {
            openAboutDialog();
            dlMain.close();
            return true;
        }

        return false;
    }

    private void openHandCalculator() {

    }

    private void openAboutDialog() {

    }
}
