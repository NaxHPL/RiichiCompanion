package com.example.riichicompanion;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.ViewCompat;

import com.example.riichicompanion.handcalculation.FuItem;
import com.example.riichicompanion.handcalculation.HandResponse;
import com.example.riichicompanion.handcalculation.WinConditions;
import com.example.riichicompanion.handcalculation.yaku.Yaku;

import java.util.Locale;

public class CalculatorResultsDialog extends AppCompatDialogFragment {

    public interface CalculatorResultsDialogListener {
        void onResultsConfirm(HandScore hs);
        void onResultsDismiss();
    }

    private ConstraintLayout clCalcResults;
    private TextView tvDealerAndWinType;
    private TextView tvHanFuAndYakuman;
    private TextView tvManganEtc;
    private TextView tvPoints;
    private TextView tvCalcResultsYaku;
    private TextView tvCalcResultsFu;

    private final HandResponse handResponse;
    private final WinConditions conditions;
    private CalculatorResultsDialogListener listener;

    public CalculatorResultsDialog(HandResponse handResponse, WinConditions conditions) {
        this.handResponse = handResponse;
        this.conditions = conditions;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_calculator_results, null);

        builder.setView(view)
            .setNegativeButton("Cancel", (dialog, which) -> listener.onResultsDismiss())
            .setPositiveButton("Confirm", (dialog, which) -> listener.onResultsConfirm(handResponse.getHandScore()));

        initializeViews(view);
        setHandScoreInformation();

        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener((dialog) -> {
            if (handResponse.getYaku().length == 0)
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        });

        return alertDialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (CalculatorResultsDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement CalculatorResultsDialogListener");
        }
    }

    private void initializeViews(View view) {
        clCalcResults = view.findViewById(R.id.clCalcResults);
        tvDealerAndWinType = view.findViewById(R.id.tvDealerAndWinType);
        tvHanFuAndYakuman = view.findViewById(R.id.tvHanFuAndYakuman);
        tvManganEtc = view.findViewById(R.id.tvManganEtc);
        tvPoints = view.findViewById(R.id.tvPoints);
        tvCalcResultsYaku = view.findViewById(R.id.tvCalcResultsYaku);
        tvCalcResultsFu = view.findViewById(R.id.tvCalcResultsFu);
    }

    private void setHandScoreInformation() {
        setDealerAndWinType();

        if (handResponse.getYaku().length == 0) {
            tvHanFuAndYakuman.setText(R.string.noYaku);
            tvManganEtc.setVisibility(View.GONE);
            tvPoints.setVisibility(View.GONE);
            tvCalcResultsYaku.setVisibility(View.GONE);
            setConnection(tvCalcResultsFu.getId(), ConstraintSet.TOP, tvHanFuAndYakuman.getId(), ConstraintSet.BOTTOM, 16);
        }
        else {
            setPointsInformation();
            setYakuInformation();
        }

        if (handResponse.getHandScore().getYakumans() > 0)
            tvCalcResultsFu.setVisibility(View.GONE);
        else
            setFuInformation();
    }

    private void setDealerAndWinType() {
        tvDealerAndWinType.setText(String.format(
            Locale.getDefault(),
            "%s %s",
            conditions.getSeatWind() == Wind.East ? "Dealer" : "Non-dealer",
            conditions.isTsumo() ? "tsumo" : "ron"
        ));
    }

    private void setPointsInformation() {
        HandScore handScore = handResponse.getHandScore();
        ScoreEntry scoreEntry = ScoringTable.getScoreEntry(handScore);

        if (handScore.getYakumans() > 0) {
            switch (handScore.getYakumans()) {
                case 1:
                    tvHanFuAndYakuman.setText(R.string.yakuman);
                    break;
                case 2:
                    tvHanFuAndYakuman.setText(R.string.doubleYakuman);
                    break;
                case 3:
                    tvHanFuAndYakuman.setText(R.string.tripleYakuman);
                    break;
                default:
                    tvHanFuAndYakuman.setText(String.format(
                        Locale.getDefault(),
                        "%d Yakuman",
                        handScore.getYakumans()
                    ));
                    break;
            }

            tvManganEtc.setVisibility(View.GONE);
            setConnection(tvPoints.getId(), ConstraintSet.TOP, tvHanFuAndYakuman.getId(), ConstraintSet.BOTTOM, 0);
        }
        else {
            tvHanFuAndYakuman.setText(String.format(
                Locale.getDefault(),
                "%d Han / %d Fu",
                handScore.getHan(),
                handScore.getFu()
            ));

            switch (scoreEntry.getNonDealerRon()) {
                case 8000:
                    tvManganEtc.setText(R.string.mangan);
                    break;
                case 12000:
                    tvManganEtc.setText(R.string.haneman);
                    break;
                case 16000:
                    tvManganEtc.setText(R.string.baiman);
                    break;
                case 24000:
                    tvManganEtc.setText(R.string.sanbaiman);
                    break;
                case 32000:
                    tvManganEtc.setText(R.string.kazoeYakuman);
                    break;
                default:
                    tvManganEtc.setVisibility(View.GONE);
                    setConnection(tvPoints.getId(), ConstraintSet.TOP, tvHanFuAndYakuman.getId(), ConstraintSet.BOTTOM, 0);
                    break;
            }
        }

        boolean isDealer = conditions.getSeatWind() == Wind.East;

        if (isDealer && conditions.isTsumo()) {
            tvPoints.setText(String.format(
                Locale.getDefault(),
                "%d points from all",
                scoreEntry.getDealerTsumo()
            ));
        }
        else if (isDealer && !conditions.isTsumo()) {
            tvPoints.setText(String.format(
                Locale.getDefault(),
                "%d points",
                scoreEntry.getDealerRon()
            ));
        }
        else if (!isDealer && conditions.isTsumo()) {
            tvPoints.setText(String.format(
                Locale.getDefault(),
                "%d / %d points",
                scoreEntry.getNonDealerTsumo().second,
                scoreEntry.getNonDealerTsumo().first
            ));
        }
        else {
            tvPoints.setText(String.format(
                Locale.getDefault(),
                "%d points",
                scoreEntry.getNonDealerRon()
            ));
        }
    }

    private void setYakuInformation() {
        TextView prevTextView = tvCalcResultsYaku;

        for (Yaku yaku : handResponse.getYaku()) {
            TextView tvYakuName = new TextView(getActivity());
            tvYakuName.setId(View.generateViewId());
            tvYakuName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

            TextView tvHanValue = new TextView(getActivity());
            tvHanValue.setId(View.generateViewId());
            tvHanValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

            clCalcResults.addView(tvYakuName);
            clCalcResults.addView(tvHanValue);

            tvYakuName.setText(yaku.getDisplayName());
            tvHanValue.setText(String.format(
                Locale.getDefault(),
                "%d %s",
                yaku.getYakumans() > 0 ? yaku.getYakumans() : yaku.getHan(),
                yaku.getYakumans() > 0 ? "Yakuman" : "Han"
            ));

            setConnection(tvYakuName.getId(), ConstraintSet.START, clCalcResults.getId(), ConstraintSet.START, 24);
            setConnection(tvYakuName.getId(), ConstraintSet.TOP, prevTextView.getId(), ConstraintSet.BOTTOM, 0);

            setConnection(tvHanValue.getId(), ConstraintSet.END, clCalcResults.getId(), ConstraintSet.END, 24);
            setConnection(tvHanValue.getId(), ConstraintSet.BASELINE, tvYakuName.getId(), ConstraintSet.BASELINE, 0);

            prevTextView = tvYakuName;
        }

        if (conditions.getDora() > 0) {
            TextView tvDoraYaku = new TextView(getActivity());
            tvDoraYaku.setId(View.generateViewId());
            tvDoraYaku.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

            TextView tvHanValue = new TextView(getActivity());
            tvHanValue.setId(View.generateViewId());
            tvHanValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

            clCalcResults.addView(tvDoraYaku);
            clCalcResults.addView(tvHanValue);

            tvDoraYaku.setText(R.string.dora);
            tvHanValue.setText(String.format(Locale.getDefault(), "%d Han", conditions.getDora()));

            setConnection(tvDoraYaku.getId(), ConstraintSet.START, clCalcResults.getId(), ConstraintSet.START, 24);
            setConnection(tvDoraYaku.getId(), ConstraintSet.TOP, prevTextView.getId(), ConstraintSet.BOTTOM, 0);

            setConnection(tvHanValue.getId(), ConstraintSet.END, clCalcResults.getId(), ConstraintSet.END, 24);
            setConnection(tvHanValue.getId(), ConstraintSet.BASELINE, tvDoraYaku.getId(), ConstraintSet.BASELINE, 0);

            prevTextView = tvDoraYaku;
        }

        setConnection(tvCalcResultsFu.getId(), ConstraintSet.TOP, prevTextView.getId(), ConstraintSet.BOTTOM, 16);
    }

    private void setFuInformation() {
        TextView prevTextView = tvCalcResultsFu;

        for (FuItem fuItem : handResponse.getFuItems()) {
            TextView tvFuName = new TextView(getActivity());
            tvFuName.setId(View.generateViewId());
            tvFuName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

            TextView tvFuValue = new TextView(getActivity());
            tvFuValue.setId(View.generateViewId());
            tvFuValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

            clCalcResults.addView(tvFuName);
            clCalcResults.addView(tvFuValue);

            tvFuName.setText(fuItem.getTitle());
            tvFuValue.setText(String.format(Locale.getDefault(), "%d Fu", fuItem.getValue()));

            setConnection(tvFuName.getId(), ConstraintSet.START, clCalcResults.getId(), ConstraintSet.START, 24);
            setConnection(tvFuName.getId(), ConstraintSet.TOP, prevTextView.getId(), ConstraintSet.BOTTOM, 0);

            setConnection(tvFuValue.getId(), ConstraintSet.END, clCalcResults.getId(), ConstraintSet.END, 24);
            setConnection(tvFuValue.getId(), ConstraintSet.BASELINE, tvFuName.getId(), ConstraintSet.BASELINE, 0);

            prevTextView = tvFuName;
        }
    }

    private void setConnection(int startID, int startSide, int endID, int endSide, float marginSp) {
        int marginPx = (int) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            (float) marginSp,
            getActivity().getResources().getDisplayMetrics()
        );

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(clCalcResults);
        constraintSet.connect(startID, startSide, endID, endSide, marginPx);
        constraintSet.applyTo(clCalcResults);
    }
}
