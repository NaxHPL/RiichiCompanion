package com.example.riichicompanion;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Locale;

public class HandScoreDialog extends AppCompatDialogFragment {

    public interface HandScoreDialogListener {
        void onHandScoreConfirm(HandScore hs);
        void onHandScoreDismiss();
    }

    private TextView tvHan;
    private TextView tvHanLabel;
    private TextView tvFu;
    private TextView tvFuLabel;
    private TextView tvYakuman;
    private TextView tvYakumanLabel;
    private Button btnDecreaseHan;
    private Button btnDecreaseFu;
    private Button btnDecreaseYakuman;
    private Button btnIncreaseHan;
    private Button btnIncreaseFu;
    private Button btnIncreaseYakuman;
    private CheckBox chkYakuman;

    private final WinType winType;
    private HandScoreDialogListener listener;
    private Button positiveButton;

    public HandScoreDialog(WinType winType) {
        this.winType = winType;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_hand_score, null);

        builder.setView(view)
            .setNegativeButton("Cancel", (dialog, which) -> listener.onHandScoreDismiss())
            .setPositiveButton("Confirm", (dialog, which) -> {
                HandScore hs;

                if (chkYakuman.isChecked())
                    hs = new HandScore(getYakuman());
                else
                    hs = new HandScore(getHan(), getFu());

                listener.onHandScoreConfirm(hs);
            });

        initializeViews(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener((dialog) -> positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE));

        return alertDialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (HandScoreDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement HandScoreDialogListener");
        }
    }

    private void initializeViews(View view) {
        tvHan = view.findViewById(R.id.tvHan);
        tvHanLabel = view.findViewById(R.id.tvHanLabel);
        tvFu = view.findViewById(R.id.tvFu);
        tvFuLabel = view.findViewById(R.id.tvFuLabel);
        tvYakuman = view.findViewById(R.id.tvYakuman);
        tvYakumanLabel = view.findViewById(R.id.tvYakumanLabel);

        btnDecreaseHan = view.findViewById(R.id.btnDecreaseHan);
        btnDecreaseHan.setOnClickListener((v) -> decreaseHan());

        btnDecreaseFu = view.findViewById(R.id.btnDecreaseFu);
        btnDecreaseFu.setOnClickListener((v) -> decreaseFu());

        btnDecreaseYakuman = view.findViewById(R.id.btnDecreaseYakuman);
        btnDecreaseYakuman.setOnClickListener((v) -> decreaseYakuman());

        btnIncreaseHan = view.findViewById(R.id.btnIncreaseHan);
        btnIncreaseHan.setOnClickListener((v) -> increaseHan());

        btnIncreaseFu = view.findViewById(R.id.btnIncreaseFu);
        btnIncreaseFu.setOnClickListener((v) -> increaseFu());

        btnIncreaseYakuman = view.findViewById(R.id.btnIncreaseYakuman);
        btnIncreaseYakuman.setOnClickListener((v) -> increaseYakuman());

        chkYakuman = view.findViewById(R.id.chkYakuman);
        chkYakuman.setOnCheckedChangeListener((btn, checked) -> {
            enableHan(!checked);
            enableFu(!checked);
            showYakuman(checked);
            disablePositiveButtonIfInvalidScore();
        });
    }

    private int getHan() {
        return Integer.parseInt(tvHan.getText().toString());
    }

    private int getFu() {
        return Integer.parseInt(tvFu.getText().toString());
    }

    private int getYakuman() {
        return Integer.parseInt(tvYakuman.getText().toString());
    }

    private void enableHan(boolean enabled) {
        tvHanLabel.setEnabled(enabled);
        btnDecreaseHan.setEnabled(enabled);
        btnIncreaseHan.setEnabled(enabled);
        tvHan.setEnabled(enabled);
    }

    private void enableFu(boolean enabled) {
        tvFuLabel.setEnabled(enabled);
        btnDecreaseFu.setEnabled(enabled);
        btnIncreaseFu.setEnabled(enabled);
        tvFu.setEnabled(enabled);
    }

    private void showYakuman(boolean show) {
        int visibility = show ? View.VISIBLE : View.GONE;

        tvYakumanLabel.setVisibility(visibility);
        tvYakuman.setVisibility(visibility);
        btnDecreaseYakuman.setVisibility(visibility);
        btnIncreaseYakuman.setVisibility(visibility);
    }

    private void increaseHan() {
        tvHan.setText(String.format(Locale.getDefault(), "%d", getHan() + 1));
        disablePositiveButtonIfInvalidScore();
    }

    private void decreaseHan() {
        int han = getHan();
        if (han > 1)
            tvHan.setText(String.format(Locale.getDefault(), "%d", han - 1));

        disablePositiveButtonIfInvalidScore();
    }

    private void increaseFu() {
        int fu = getFu();

        if (fu < 110) {
            if (fu == 20)
                fu = 25;
            else if (fu == 25)
                fu = 30;
            else
                fu += 10;

            tvFu.setText(String.format(Locale.getDefault(), "%d", fu));
        }

        disablePositiveButtonIfInvalidScore();
    }

    private void decreaseFu() {
        int fu = getFu();

        if (fu > 20) {
            if (fu == 30)
                fu = 25;
            else if (fu == 25)
                fu = 20;
            else
                fu -= 10;

            tvFu.setText(String.format(Locale.getDefault(), "%d", fu));
        }

        disablePositiveButtonIfInvalidScore();
    }

    private void increaseYakuman() {
        tvYakuman.setText(String.format(Locale.getDefault(), "%d", getYakuman() + 1));
    }

    private void decreaseYakuman() {
        int yakuman = getYakuman();
        if (yakuman > 1)
            tvYakuman.setText(String.format(Locale.getDefault(), "%d", yakuman - 1));
    }

    private void disablePositiveButtonIfInvalidScore() {
        if (!chkYakuman.isChecked()) {
            int han = getHan();
            int fu = getFu();

            if (han == 1 && fu <= 25) {
                positiveButton.setEnabled(false);
                return;
            }
            else if (han == 2 && fu == 25 && winType == WinType.Tsumo) {
                positiveButton.setEnabled(false);
                return;
            }
        }

        positiveButton.setEnabled(true);
    }
}
