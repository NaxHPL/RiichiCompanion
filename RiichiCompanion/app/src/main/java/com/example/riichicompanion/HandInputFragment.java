package com.example.riichicompanion;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class HandInputFragment extends Fragment {

    private HandInputViewModel mViewModel;

    //region Views

    ConstraintLayout clExposedTiles;
    ConstraintLayout clConcealedTiles;
    ToggleButton tbChii;
    ToggleButton tbPon;
    ToggleButton tbKan;
    ToggleButton tbClosedKan;

    //endregion

    public static HandInputFragment newInstance() {
        return new HandInputFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hand_input, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HandInputViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //region Views

        clExposedTiles = view.findViewById(R.id.clExposedTiles);
        clConcealedTiles = view.findViewById(R.id.clConcealedTiles);
        tbChii = view.findViewById(R.id.tbChii);
        tbPon = view.findViewById(R.id.tbPon);
        tbKan = view.findViewById(R.id.tbKan);
        tbClosedKan = view.findViewById(R.id.tbClosedKan);

        //endregion

        setButtonListeners();
    }

    private void setButtonListeners() {
        tbChii.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                tbPon.setChecked(false);
                tbKan.setChecked(false);
                tbClosedKan.setChecked(false);
            }
        });
        tbPon.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                tbChii.setChecked(false);
                tbKan.setChecked(false);
                tbClosedKan.setChecked(false);
            }
        });
        tbKan.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                tbChii.setChecked(false);
                tbPon.setChecked(false);
                tbClosedKan.setChecked(false);
            }
        });
        tbClosedKan.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                tbChii.setChecked(false);
                tbPon.setChecked(false);
                tbKan.setChecked(false);
            }
        });
    }
}