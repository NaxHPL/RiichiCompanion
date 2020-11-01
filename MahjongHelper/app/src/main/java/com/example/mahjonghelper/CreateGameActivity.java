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
        TextView tvInitialPoints = (TextView) findViewById(R.id.tvInitialPoints);
        int initialPoints = Integer.parseInt(tvInitialPoints.getText().toString());
        tvInitialPoints.setText(String.format(Locale.getDefault(), "%d", initialPoints + change));
    }

    public void changeMinPointsToWin(int change) {
        TextView tvMinPointsToWin = (TextView) findViewById(R.id.tvMinPointsToWin);
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
        Intent intent = new Intent(this, ScoreTrackerActivity.class);

        intent.putExtra(ScoreTrackerActivity.KEY_EAST_PLAYER, getEastPlayerName());
        intent.putExtra(ScoreTrackerActivity.KEY_SOUTH_PLAYER, getSouthPlayerName());
        intent.putExtra(ScoreTrackerActivity.KEY_WEST_PLAYER, getWestPlayerName());
        intent.putExtra(ScoreTrackerActivity.KEY_NORTH_PLAYER, getNorthPlayerName());
        intent.putExtra(ScoreTrackerActivity.KEY_INITIAL_POINTS, getInitialPoints());
        intent.putExtra(ScoreTrackerActivity.KEY_MIN_POINTS_TO_WIN, getMinPointsToWin());

        startActivity(intent);
    }

    public String getEastPlayerName() {
        EditText etEastPlayer = (EditText) findViewById(R.id.etEastPlayer);
        return etEastPlayer.getText().toString().trim();
    }

    public String getSouthPlayerName() {
        EditText etSouthPlayer = (EditText) findViewById(R.id.etSouthPlayer);
        return etSouthPlayer.getText().toString().trim();
    }

    public String getWestPlayerName() {
        EditText etWestPlayer = (EditText) findViewById(R.id.etWestPlayer);
        return etWestPlayer.getText().toString().trim();
    }

    public String getNorthPlayerName() {
        EditText etNorthPlayer = (EditText) findViewById(R.id.etNorthPlayer);
        return etNorthPlayer.getText().toString().trim();
    }

    public String getInitialPoints() {
        TextView tvInitialPoints = (TextView) findViewById(R.id.tvInitialPoints);
        return tvInitialPoints.getText().toString();
    }

    public String getMinPointsToWin() {
        TextView tvMinPointsToWin = (TextView) findViewById(R.id.tvMinPointsToWin);
        return tvMinPointsToWin.getText().toString();
    }
}