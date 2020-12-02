package com.example.riichicompanion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class HandInputFragment extends Fragment {

    private HandInputViewModel mViewModel;

    //region Views

    TableRow trConcealed1;
    TableRow trConcealed2;
    ToggleButton tbChii;
    ToggleButton tbPon;
    ToggleButton tbKan;
    ToggleButton tbClosedKan;
    ImageButton ib1m;
    ImageButton ib2m;
    ImageButton ib3m;
    ImageButton ib4m;
    ImageButton ib5m;
    ImageButton ib6m;
    ImageButton ib7m;
    ImageButton ib8m;
    ImageButton ib9m;
    ImageButton ib1p;
    ImageButton ib2p;
    ImageButton ib3p;
    ImageButton ib4p;
    ImageButton ib5p;
    ImageButton ib6p;
    ImageButton ib7p;
    ImageButton ib8p;
    ImageButton ib9p;
    ImageButton ib1s;
    ImageButton ib2s;
    ImageButton ib3s;
    ImageButton ib4s;
    ImageButton ib5s;
    ImageButton ib6s;
    ImageButton ib7s;
    ImageButton ib8s;
    ImageButton ib9s;
    ImageButton ibEast;
    ImageButton ibSouth;
    ImageButton ibWest;
    ImageButton ibNorth;
    ImageButton ibGreen;
    ImageButton ibRed;
    ImageButton ibWhite;

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

        trConcealed1 = view.findViewById(R.id.trConcealed1);
        trConcealed2 = view.findViewById(R.id.trConcealed2);
        tbChii = view.findViewById(R.id.tbChii);
        tbPon = view.findViewById(R.id.tbPon);
        tbKan = view.findViewById(R.id.tbKan);
        tbClosedKan = view.findViewById(R.id.tbClosedKan);
        ib1m = view.findViewById(R.id.ib1m);
        ib2m = view.findViewById(R.id.ib2m);
        ib3m = view.findViewById(R.id.ib3m);
        ib4m = view.findViewById(R.id.ib4m);
        ib5m = view.findViewById(R.id.ib5m);
        ib6m = view.findViewById(R.id.ib6m);
        ib7m = view.findViewById(R.id.ib7m);
        ib8m = view.findViewById(R.id.ib8m);
        ib9m = view.findViewById(R.id.ib9m);
        ib1p = view.findViewById(R.id.ib1p);
        ib2p = view.findViewById(R.id.ib2p);
        ib3p = view.findViewById(R.id.ib3p);
        ib4p = view.findViewById(R.id.ib4p);
        ib5p = view.findViewById(R.id.ib5p);
        ib6p = view.findViewById(R.id.ib6p);
        ib7p = view.findViewById(R.id.ib7p);
        ib8p = view.findViewById(R.id.ib8p);
        ib9p = view.findViewById(R.id.ib9p);
        ib1s = view.findViewById(R.id.ib1s);
        ib2s = view.findViewById(R.id.ib2s);
        ib3s = view.findViewById(R.id.ib3s);
        ib4s = view.findViewById(R.id.ib4s);
        ib5s = view.findViewById(R.id.ib5s);
        ib6s = view.findViewById(R.id.ib6s);
        ib7s = view.findViewById(R.id.ib7s);
        ib8s = view.findViewById(R.id.ib8s);
        ib9s = view.findViewById(R.id.ib9s);
        ibEast = view.findViewById(R.id.ibEast);
        ibSouth = view.findViewById(R.id.ibSouth);
        ibWest = view.findViewById(R.id.ibWest);
        ibNorth = view.findViewById(R.id.ibNorth);
        ibGreen = view.findViewById(R.id.ibGreen);
        ibRed = view.findViewById(R.id.ibRed);
        ibWhite = view.findViewById(R.id.ibWhite);

        //endregion

        setImageButtonTags();
        setButtonListeners();
    }

    private void setImageButtonTags() {
        ib1m.setTag(R.id.tile_image_id, R.drawable._1m);
        ib2m.setTag(R.id.tile_image_id, R.drawable._2m);
        ib3m.setTag(R.id.tile_image_id, R.drawable._3m);
        ib4m.setTag(R.id.tile_image_id, R.drawable._4m);
        ib5m.setTag(R.id.tile_image_id, R.drawable._5m);
        ib6m.setTag(R.id.tile_image_id, R.drawable._6m);
        ib7m.setTag(R.id.tile_image_id, R.drawable._7m);
        ib8m.setTag(R.id.tile_image_id, R.drawable._8m);
        ib9m.setTag(R.id.tile_image_id, R.drawable._9m);
        ib1p.setTag(R.id.tile_image_id, R.drawable._1p);
        ib2p.setTag(R.id.tile_image_id, R.drawable._2p);
        ib3p.setTag(R.id.tile_image_id, R.drawable._3p);
        ib4p.setTag(R.id.tile_image_id, R.drawable._4p);
        ib5p.setTag(R.id.tile_image_id, R.drawable._5p);
        ib6p.setTag(R.id.tile_image_id, R.drawable._6p);
        ib7p.setTag(R.id.tile_image_id, R.drawable._7p);
        ib8p.setTag(R.id.tile_image_id, R.drawable._8p);
        ib9p.setTag(R.id.tile_image_id, R.drawable._9p);
        ib1s.setTag(R.id.tile_image_id, R.drawable._1s);
        ib2s.setTag(R.id.tile_image_id, R.drawable._2s);
        ib3s.setTag(R.id.tile_image_id, R.drawable._3s);
        ib4s.setTag(R.id.tile_image_id, R.drawable._4s);
        ib5s.setTag(R.id.tile_image_id, R.drawable._5s);
        ib6s.setTag(R.id.tile_image_id, R.drawable._6s);
        ib7s.setTag(R.id.tile_image_id, R.drawable._7s);
        ib8s.setTag(R.id.tile_image_id, R.drawable._8s);
        ib9s.setTag(R.id.tile_image_id, R.drawable._9s);
        ibEast.setTag(R.id.tile_image_id, R.drawable.east_tile);
        ibSouth.setTag(R.id.tile_image_id, R.drawable.south_tile);
        ibWest.setTag(R.id.tile_image_id, R.drawable.west_tile);
        ibNorth.setTag(R.id.tile_image_id, R.drawable.north_tile);
        ibGreen.setTag(R.id.tile_image_id, R.drawable.green_dragon);
        ibRed.setTag(R.id.tile_image_id, R.drawable.red_dragon);
        ibWhite.setTag(R.id.tile_image_id, R.drawable.white_dragon);
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

        ib1m.setOnClickListener(this::onTileButtonClicked);
        ib2m.setOnClickListener(this::onTileButtonClicked);
        ib3m.setOnClickListener(this::onTileButtonClicked);
        ib4m.setOnClickListener(this::onTileButtonClicked);
        ib5m.setOnClickListener(this::onTileButtonClicked);
        ib6m.setOnClickListener(this::onTileButtonClicked);
        ib7m.setOnClickListener(this::onTileButtonClicked);
        ib8m.setOnClickListener(this::onTileButtonClicked);
        ib9m.setOnClickListener(this::onTileButtonClicked);
        ib1p.setOnClickListener(this::onTileButtonClicked);
        ib2p.setOnClickListener(this::onTileButtonClicked);
        ib3p.setOnClickListener(this::onTileButtonClicked);
        ib4p.setOnClickListener(this::onTileButtonClicked);
        ib5p.setOnClickListener(this::onTileButtonClicked);
        ib6p.setOnClickListener(this::onTileButtonClicked);
        ib7p.setOnClickListener(this::onTileButtonClicked);
        ib8p.setOnClickListener(this::onTileButtonClicked);
        ib9p.setOnClickListener(this::onTileButtonClicked);
        ib1s.setOnClickListener(this::onTileButtonClicked);
        ib2s.setOnClickListener(this::onTileButtonClicked);
        ib3s.setOnClickListener(this::onTileButtonClicked);
        ib4s.setOnClickListener(this::onTileButtonClicked);
        ib5s.setOnClickListener(this::onTileButtonClicked);
        ib6s.setOnClickListener(this::onTileButtonClicked);
        ib7s.setOnClickListener(this::onTileButtonClicked);
        ib8s.setOnClickListener(this::onTileButtonClicked);
        ib9s.setOnClickListener(this::onTileButtonClicked);
        ibEast.setOnClickListener(this::onTileButtonClicked);
        ibSouth.setOnClickListener(this::onTileButtonClicked);
        ibWest.setOnClickListener(this::onTileButtonClicked);
        ibNorth.setOnClickListener(this::onTileButtonClicked);
        ibGreen.setOnClickListener(this::onTileButtonClicked);
        ibRed.setOnClickListener(this::onTileButtonClicked);
        ibWhite.setOnClickListener(this::onTileButtonClicked);
    }

    private void onTileButtonClicked(View view) {
        ImageButton ib;
        try {
            ib = (ImageButton) view;
        } catch (Exception e) { return; }

        if (tbChii.isChecked()) {

        }
        else if (tbPon.isChecked()) {

        }
        else if (tbKan.isChecked()) {

        }
        else if (tbClosedKan.isChecked()) {

        }
        else
            addConcealedTile(ib);
    }

    private void addConcealedTile(ImageButton tileButtonClicked) {
        ImageButton btn = (ImageButton) getLayoutInflater().inflate(R.layout.view_tile_button, trConcealed1, false);
        btn.setImageResource((int)tileButtonClicked.getTag(R.id.tile_image_id));
        trConcealed1.addView(btn);
    }
}