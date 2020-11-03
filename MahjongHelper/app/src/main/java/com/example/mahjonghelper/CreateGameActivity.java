package com.example.mahjonghelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class CreateGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
    }

    public void changeInitialPoints(int change) {
        TextView tvInitialPoints = findViewById(R.id.tvInitialPoints);
        int initialPoints = Integer.parseInt(tvInitialPoints.getText().toString());
        tvInitialPoints.setText(String.format(Locale.getDefault(), "%d", initialPoints + change));
    }

    public void changeMinPointsToWin(int change) {
        TextView tvMinPointsToWin = findViewById(R.id.tvMinPointsToWin);
        int minPointsToWin = Integer.parseInt(tvMinPointsToWin.getText().toString());
        tvMinPointsToWin.setText(String.format(Locale.getDefault(), "%d", minPointsToWin + change));
    }

    public void decreaseInitialPointsBy1000(View view) {
        changeInitialPoints(-1000);
    }

    public void increaseInitialPointsBy1000(View view) {
        changeInitialPoints(1000);
    }

    public void decreaseMinPointsToWinBy1000(View view) {
        changeMinPointsToWin(-1000);
    }

    public void increaseMinPointsToWinBy1000(View view) {
        changeMinPointsToWin(1000);
    }

    public void createGame(View view) {
        Game game = new Game(
            new Player(getEastPlayerName(), getInitialPoints(), Wind.East),
            new Player(getSouthPlayerName(), getInitialPoints(), Wind.South),
            new Player(getWestPlayerName(), getInitialPoints(), Wind.West),
            new Player(getNorthPlayerName(), getInitialPoints(), Wind.North),
            getMinPointsToWin(),
            0,
            0,
            1,
            getNorthPlayerName().isEmpty() ? 3 : 4
        );

        Intent intent = new Intent(this, ScoreTrackerActivity.class);
        intent.putExtra(ScoreTrackerActivity.GAME_TO_SHOW, game);
        startActivity(intent);
    }

    public String getEastPlayerName() {
        EditText etEastPlayer = findViewById(R.id.etEastPlayer);
        return etEastPlayer.getText().toString().trim();
    }

    public String getSouthPlayerName() {
        EditText etSouthPlayer = findViewById(R.id.etSouthPlayer);
        return etSouthPlayer.getText().toString().trim();
    }

    public String getWestPlayerName() {
        EditText etWestPlayer = findViewById(R.id.etWestPlayer);
        return etWestPlayer.getText().toString().trim();
    }

    public String getNorthPlayerName() {
        EditText etNorthPlayer = findViewById(R.id.etNorthPlayer);
        return etNorthPlayer.getText().toString().trim();
    }

    public int getInitialPoints() {
        TextView tvInitialPoints = findViewById(R.id.tvInitialPoints);
        return Integer.parseInt(tvInitialPoints.getText().toString());
    }

    public int getMinPointsToWin() {
        TextView tvMinPointsToWin = findViewById(R.id.tvMinPointsToWin);
        return Integer.parseInt(tvMinPointsToWin.getText().toString());
    }
}