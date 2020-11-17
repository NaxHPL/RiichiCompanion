package com.example.riichicompanion;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Locale;

public class GameSettingsDialog extends AppCompatDialogFragment {

    private Game game;

    public GameSettingsDialog(Game game) {
        this.game = game;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_game_settings, null);

        builder.setView(view)
               .setTitle(R.string.game_settings_dialog_title)
               .setPositiveButton(R.string.close, (dialog, which) -> {});

        initializeViews(view);
        return builder.create();
    }

    private void initializeViews(View view) {
        TextView tv = view.findViewById(R.id.tvInitialPointsGameSettings);
        tv.setText(String.format(Locale.getDefault(), "%d", game.getInitialPoints()));

        tv = view.findViewById(R.id.tvMinPointsToWinGameSettings);
        tv.setText(String.format(Locale.getDefault(), "%d", game.getMinPointsToWin()));

        tv = view.findViewById(R.id.tvGameLengthGameSettings);
        tv.setText(String.format(Locale.getDefault(), "%s", game.getGameLength().toString()));

        if (game.getNumberOfPlayers() == 3) {
            tv = view.findViewById(R.id.tvUseTsumoLossGameSettings);
            tv.setText(String.format(Locale.getDefault(), "%s", game.usesTsumoLoss() ? "Yes" : "No"));
        }
        else {
            tv = view.findViewById(R.id.tvUseTsumoLossGameSettingsLabel);
            tv.setVisibility(View.GONE);

            tv = view.findViewById(R.id.tvUseTsumoLossGameSettings);
            tv.setVisibility(View.GONE);
        }
    }
}
