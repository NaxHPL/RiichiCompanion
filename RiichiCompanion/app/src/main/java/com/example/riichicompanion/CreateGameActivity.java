package com.example.riichicompanion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class CreateGameActivity extends AppCompatActivity {

    private TextView tvInitialPoints;
    private TextView tvMinPointsToWin;
    private TextView tvTsumoLoss;
    private EditText etEastPlayer;
    private EditText etSouthPlayer;
    private EditText etWestPlayer;
    private EditText etNorthPlayer;
    private Spinner spinGameLength;
    private ToggleButton tbUseTsumoLoss;
    private FloatingActionButton fabRemoveFourthPlayer;
    private Button btnCreate;
    private Button btnAddPlayer;
    private ConstraintLayout constraintLayout;
    private ConstraintSet constraintSet;
    private boolean threePlayerGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        tvInitialPoints = findViewById(R.id.tvInitialPoints);
        tvMinPointsToWin = findViewById(R.id.tvMinPointsToWin);
        tvTsumoLoss = findViewById(R.id.tvTsumoLoss);
        etEastPlayer = findViewById(R.id.etEastPlayer);
        etSouthPlayer = findViewById(R.id.etSouthPlayer);
        etWestPlayer = findViewById(R.id.etWestPlayer);
        etNorthPlayer = findViewById(R.id.etNorthPlayer);
        spinGameLength = findViewById(R.id.spinGameLength);
        tbUseTsumoLoss = findViewById(R.id.tbUseTsumoLoss);
        fabRemoveFourthPlayer = findViewById(R.id.fabRemoveFourthPlayer);
        btnCreate = findViewById(R.id.btnCreate);
        btnAddPlayer = findViewById(R.id.btnAddPlayer);
        constraintLayout = findViewById(R.id.constraintLayoutCreateGame);
        constraintSet = new ConstraintSet();
        threePlayerGame = false;
    }

    public void changeInitialPoints(int change) {
        int initialPoints = Integer.parseInt(tvInitialPoints.getText().toString());
        tvInitialPoints.setText(String.format(Locale.getDefault(), "%d", initialPoints + change));
    }

    public void changeMinPointsToWin(int change) {
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
            threePlayerGame ? 3 : 4,
            getGameLength(),
            useTsumoLoss()
        );

        PersistentStorage.saveOngoingGame(this, game);

        Intent intent = new Intent(this, ScoreTrackerActivity.class);
        intent.putExtra(ScoreTrackerActivity.GAME_TO_SHOW, game);
        startActivity(intent);

        finish();
    }

    private String getEastPlayerName() {
        String name = etEastPlayer.getText().toString().trim();
        return name.isEmpty() ? "East" : name;
    }

    private String getSouthPlayerName() {
        String name = etSouthPlayer.getText().toString().trim();
        return name.isEmpty() ? "South" : name;
    }

    private String getWestPlayerName() {
        String name = etWestPlayer.getText().toString().trim();
        return name.isEmpty() ? "West" : name;
    }

    private String getNorthPlayerName() {
        String name = etNorthPlayer.getText().toString().trim();
        return name.isEmpty() ? "North" : name;
    }

    private int getInitialPoints() {
        return Integer.parseInt(tvInitialPoints.getText().toString());
    }

    private int getMinPointsToWin() {
        return Integer.parseInt(tvMinPointsToWin.getText().toString());
    }

    private GameLength getGameLength() {
        String selectedValue = spinGameLength.getSelectedItem().toString();
        return GameLength.valueOf(selectedValue);
    }

    private boolean useTsumoLoss() {
        return tbUseTsumoLoss.isChecked();
    }

    public void removeFourthPlayer(View view) {
        fabRemoveFourthPlayer.setVisibility(View.INVISIBLE);
        etNorthPlayer.setVisibility(View.INVISIBLE);
        btnAddPlayer.setVisibility(View.VISIBLE);

        tvTsumoLoss.setVisibility(View.VISIBLE);
        tbUseTsumoLoss.setVisibility(View.VISIBLE);

        constraintSet.clone(constraintLayout);
        constraintSet.connect(
            btnCreate.getId(), ConstraintSet.TOP,
            tbUseTsumoLoss.getId(), ConstraintSet.BOTTOM
        );
        constraintSet.applyTo(constraintLayout);

        threePlayerGame = true;
    }

    public void addFourthPlayer(View view) {
        fabRemoveFourthPlayer.setVisibility(View.VISIBLE);
        etNorthPlayer.setVisibility(View.VISIBLE);
        btnAddPlayer.setVisibility(View.INVISIBLE);

        tvTsumoLoss.setVisibility(View.INVISIBLE);
        tbUseTsumoLoss.setVisibility(View.INVISIBLE);

        constraintSet.clone(constraintLayout);
        constraintSet.connect(
            btnCreate.getId(), ConstraintSet.TOP,
            spinGameLength.getId(), ConstraintSet.BOTTOM
        );
        constraintSet.applyTo(constraintLayout);

        threePlayerGame = false;
    }
}