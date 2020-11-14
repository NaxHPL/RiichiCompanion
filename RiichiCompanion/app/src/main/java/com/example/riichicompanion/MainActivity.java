package com.example.riichicompanion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Game ongoingGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ongoingGame = PersistentStorage.retrieveOngoingGame(this);

        if (ongoingGame != null) {
            updateOngoingGameInfo();
            findViewById(R.id.ConstraintLayout_ongoing_game).setVisibility(View.VISIBLE);
        }
        else
            findViewById(R.id.ConstraintLayout_ongoing_game).setVisibility(View.GONE);
    }

    public void createNewGame(View view) {
        if (ongoingGame == null) {
            startCreateGameActivity();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.new_game_dialog_title);
        builder.setMessage(R.string.new_game_dialog_message);
        builder.setPositiveButton(R.string.yes, (dialog, which) -> startCreateGameActivity());
        builder.setNegativeButton(R.string.no, (dialog, which) -> {});

        AlertDialog dialog = builder.create();
        dialog.show();
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

        TextView tv = findViewById(R.id.tvOngoingGamePlayers);
        tv.setText(String.format(Locale.getDefault(), "%s", players));

        tv = findViewById(R.id.tvOngoingGameDateTime);
        tv.setText(String.format(Locale.getDefault(), "%s", ongoingGame.getStartDateTime()));

        tv = findViewById(R.id.tvOngoingGameRoundNumber);
        tv.setText(String.format(Locale.getDefault(), "%d", ongoingGame.getRoundNumberForDisplay()));

        ImageView iv = findViewById(R.id.ivOngoingGameRoundWind);
        iv.setImageDrawable(ongoingGame.getPrevalentWind().getImage(this));
        iv.setImageDrawable(ongoingGame.getPrevalentWind().getImage(this));
    }

    public void openOngoingGame(View view) {
        Intent intent = new Intent(this, ScoreTrackerActivity.class);
        intent.putExtra(ScoreTrackerActivity.GAME_TO_SHOW, ongoingGame);
        startActivity(intent);
    }
}
