package com.example.riichicompanion;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.riichicompanion.handcalculation.Tile;

import java.util.ArrayList;

public class HandInputFragment extends Fragment {

    private static final int MELD_MARGIN = 24;

    //region Views

    ConstraintLayout clExposedTiles;
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

    private HandInputViewModel mViewModel;
    private ArrayList<ConstraintLayout> meldCls;
    private ImageButton winTileButton;

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

        meldCls = new ArrayList<>();
    }

    private void setImageButtonTags() {
        ib1m.setTag(R.id.tile_image_id, R.drawable._1m);
        ib1m.setTag(R.id.tile_object, new Tile(Tile.Suit.Man, 1));
        ib2m.setTag(R.id.tile_image_id, R.drawable._2m);
        ib2m.setTag(R.id.tile_object, new Tile(Tile.Suit.Man, 2));
        ib3m.setTag(R.id.tile_image_id, R.drawable._3m);
        ib3m.setTag(R.id.tile_object, new Tile(Tile.Suit.Man, 3));
        ib4m.setTag(R.id.tile_image_id, R.drawable._4m);
        ib4m.setTag(R.id.tile_object, new Tile(Tile.Suit.Man, 4));
        ib5m.setTag(R.id.tile_image_id, R.drawable._5m);
        ib5m.setTag(R.id.tile_object, new Tile(Tile.Suit.Man, 5));
        ib6m.setTag(R.id.tile_image_id, R.drawable._6m);
        ib6m.setTag(R.id.tile_object, new Tile(Tile.Suit.Man, 6));
        ib7m.setTag(R.id.tile_image_id, R.drawable._7m);
        ib7m.setTag(R.id.tile_object, new Tile(Tile.Suit.Man, 7));
        ib8m.setTag(R.id.tile_image_id, R.drawable._8m);
        ib8m.setTag(R.id.tile_object, new Tile(Tile.Suit.Man, 8));
        ib9m.setTag(R.id.tile_image_id, R.drawable._9m);
        ib9m.setTag(R.id.tile_object, new Tile(Tile.Suit.Man, 9));
        ib1p.setTag(R.id.tile_image_id, R.drawable._1p);
        ib1p.setTag(R.id.tile_object, new Tile(Tile.Suit.Pin, 1));
        ib2p.setTag(R.id.tile_image_id, R.drawable._2p);
        ib2p.setTag(R.id.tile_object, new Tile(Tile.Suit.Pin, 2));
        ib3p.setTag(R.id.tile_image_id, R.drawable._3p);
        ib3p.setTag(R.id.tile_object, new Tile(Tile.Suit.Pin, 3));
        ib4p.setTag(R.id.tile_image_id, R.drawable._4p);
        ib4p.setTag(R.id.tile_object, new Tile(Tile.Suit.Pin, 4));
        ib5p.setTag(R.id.tile_image_id, R.drawable._5p);
        ib5p.setTag(R.id.tile_object, new Tile(Tile.Suit.Pin, 5));
        ib6p.setTag(R.id.tile_image_id, R.drawable._6p);
        ib6p.setTag(R.id.tile_object, new Tile(Tile.Suit.Pin, 6));
        ib7p.setTag(R.id.tile_image_id, R.drawable._7p);
        ib7p.setTag(R.id.tile_object, new Tile(Tile.Suit.Pin, 7));
        ib8p.setTag(R.id.tile_image_id, R.drawable._8p);
        ib8p.setTag(R.id.tile_object, new Tile(Tile.Suit.Pin, 8));
        ib9p.setTag(R.id.tile_image_id, R.drawable._9p);
        ib9p.setTag(R.id.tile_object, new Tile(Tile.Suit.Pin, 9));
        ib1s.setTag(R.id.tile_image_id, R.drawable._1s);
        ib1s.setTag(R.id.tile_object, new Tile(Tile.Suit.Sou, 1));
        ib2s.setTag(R.id.tile_image_id, R.drawable._2s);
        ib2s.setTag(R.id.tile_object, new Tile(Tile.Suit.Sou, 2));
        ib3s.setTag(R.id.tile_image_id, R.drawable._3s);
        ib3s.setTag(R.id.tile_object, new Tile(Tile.Suit.Sou, 3));
        ib4s.setTag(R.id.tile_image_id, R.drawable._4s);
        ib4s.setTag(R.id.tile_object, new Tile(Tile.Suit.Sou, 4));
        ib5s.setTag(R.id.tile_image_id, R.drawable._5s);
        ib5s.setTag(R.id.tile_object, new Tile(Tile.Suit.Sou, 5));
        ib6s.setTag(R.id.tile_image_id, R.drawable._6s);
        ib6s.setTag(R.id.tile_object, new Tile(Tile.Suit.Sou, 6));
        ib7s.setTag(R.id.tile_image_id, R.drawable._7s);
        ib7s.setTag(R.id.tile_object, new Tile(Tile.Suit.Sou, 7));
        ib8s.setTag(R.id.tile_image_id, R.drawable._8s);
        ib8s.setTag(R.id.tile_object, new Tile(Tile.Suit.Sou, 8));
        ib9s.setTag(R.id.tile_image_id, R.drawable._9s);
        ib9s.setTag(R.id.tile_object, new Tile(Tile.Suit.Sou, 9));
        ibEast.setTag(R.id.tile_image_id, R.drawable.east_tile);
        ibEast.setTag(R.id.tile_object, new Tile(Wind.East));
        ibSouth.setTag(R.id.tile_image_id, R.drawable.south_tile);
        ibSouth.setTag(R.id.tile_object, new Tile(Wind.South));
        ibWest.setTag(R.id.tile_image_id, R.drawable.west_tile);
        ibWest.setTag(R.id.tile_object, new Tile(Wind.West));
        ibNorth.setTag(R.id.tile_image_id, R.drawable.north_tile);
        ibNorth.setTag(R.id.tile_object, new Tile(Wind.North));
        ibGreen.setTag(R.id.tile_image_id, R.drawable.green_dragon);
        ibGreen.setTag(R.id.tile_object, new Tile(Tile.Dragon.Green));
        ibRed.setTag(R.id.tile_image_id, R.drawable.red_dragon);
        ibRed.setTag(R.id.tile_object, new Tile(Tile.Dragon.Red));
        ibWhite.setTag(R.id.tile_image_id, R.drawable.white_dragon);
        ibWhite.setTag(R.id.tile_object, new Tile(Tile.Dragon.White));
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

        if (tbChii.isChecked())
            addChii(ib);
        else if (tbPon.isChecked())
            addPon(ib);
        else if (tbKan.isChecked())
            addOpenKan(ib);
        else if (tbClosedKan.isChecked())
            addClosedKan(ib);
        else
            addConcealedTile(ib);
    }

    private void addChii(ImageButton tileButtonClicked) {

    }

    private void addPon(ImageButton tileButtonClicked) {
        int drawableId = (int) tileButtonClicked.getTag(R.id.tile_image_id);
        ConstraintLayout ponLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.chii_and_pon_cl, clExposedTiles, false);
        ponLayout.setId(View.generateViewId());

        for (int i = 0; i < ponLayout.getChildCount(); i++) {
            ImageView iv;
            try {
                iv = (ImageView) ponLayout.getChildAt(i);
            } catch (Exception e) { continue; }

            iv.setImageResource(drawableId);
        }

        addMeldConstraintLayout(ponLayout);
    }

    private void addOpenKan(ImageButton tileButtonClicked) {
        int drawableId = (int) tileButtonClicked.getTag(R.id.tile_image_id);
        ConstraintLayout openKanLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.open_kan_cl, clExposedTiles, false);
        openKanLayout.setId(View.generateViewId());

        for (int i = 0; i < openKanLayout.getChildCount(); i++) {
            ImageView iv;
            try {
                iv = (ImageView) openKanLayout.getChildAt(i);
            } catch (Exception e) { continue; }

            iv.setImageResource(drawableId);
        }

        addMeldConstraintLayout(openKanLayout);
    }

    private void addClosedKan(ImageButton tileButtonClicked) {
        int drawableId = (int) tileButtonClicked.getTag(R.id.tile_image_id);
        ConstraintLayout closedKanLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.closed_kan_cl, clExposedTiles, false);
        closedKanLayout.setId(View.generateViewId());

        for (int i = 1; i < closedKanLayout.getChildCount() - 1; i++) {
            ImageView iv;
            try {
                iv = (ImageView) closedKanLayout.getChildAt(i);
            } catch (Exception e) { continue; }

            iv.setImageResource(drawableId);
        }

        addMeldConstraintLayout(closedKanLayout);
    }

    private void addMeldConstraintLayout(ConstraintLayout cl) {
        clExposedTiles.addView(cl);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(clExposedTiles);

        switch (meldCls.size()) {
            case 0:
                constraintSet.connect(cl.getId(), ConstraintSet.START, clExposedTiles.getId(), ConstraintSet.START, MELD_MARGIN);
                constraintSet.connect(cl.getId(), ConstraintSet.TOP,  clExposedTiles.getId(), ConstraintSet.TOP, MELD_MARGIN);
                break;
            case 1:
                constraintSet.connect(cl.getId(), ConstraintSet.START, meldCls.get(0).getId(), ConstraintSet.END, MELD_MARGIN);
                constraintSet.connect(cl.getId(), ConstraintSet.TOP, meldCls.get(0).getId(), ConstraintSet.TOP, 0);
                break;
            case 2:
                constraintSet.connect(cl.getId(), ConstraintSet.START, clExposedTiles.getId(), ConstraintSet.START, MELD_MARGIN);
                constraintSet.connect(cl.getId(), ConstraintSet.TOP, meldCls.get(0).getId(), ConstraintSet.BOTTOM, MELD_MARGIN);
                break;
            case 3:
                constraintSet.connect(cl.getId(), ConstraintSet.START, meldCls.get(2).getId(), ConstraintSet.END, MELD_MARGIN);
                constraintSet.connect(cl.getId(), ConstraintSet.TOP, meldCls.get(2).getId(), ConstraintSet.TOP, 0);
                break;
        }

        constraintSet.applyTo(clExposedTiles);
        meldCls.add(cl);
    }

    private void removeMeld(View clClicked) {

    }

    private void addConcealedTile(ImageButton tileButtonClicked) {
        int drawableId = (int) tileButtonClicked.getTag(R.id.tile_image_id);
        Tile tile = (Tile) tileButtonClicked.getTag(R.id.tile_object);

        ImageButton btn = (ImageButton) getLayoutInflater().inflate(R.layout.view_tile_button, trConcealed1, false);
        btn.setImageResource(drawableId);
        btn.setTag(R.id.tile_object, tile);
        btn.setOnClickListener(this::removeFromConcealed);
        setAsWinTileButton(btn);

        if (trConcealed1.getChildCount() == 0) {
            trConcealed1.addView(btn);
            return;
        }

        Tile otherTile;

        for (int i = 0; i < trConcealed1.getChildCount(); i++) {
            otherTile = (Tile) trConcealed1.getChildAt(i).getTag(R.id.tile_object);

            if (tile.compareTo(otherTile) < 0) {
                trConcealed1.addView(btn, i);

                if (trConcealed1.getChildCount() > 9) {
                    View lastViewInRow1 = trConcealed1.getChildAt(trConcealed1.getChildCount() - 1);
                    trConcealed1.removeView(lastViewInRow1);
                    trConcealed2.addView(lastViewInRow1, 0);
                }

                return;
            }
        }

        if (trConcealed1.getChildCount() < 9) {
            trConcealed1.addView(btn);
            return;
        }

        // If we reach this point, the tile must be added to the second row

        if (trConcealed2.getChildCount() == 0) {
            trConcealed2.addView(btn);
            return;
        }

        for (int i = 0; i < trConcealed2.getChildCount(); i++) {
            otherTile = (Tile) trConcealed2.getChildAt(i).getTag(R.id.tile_object);

            if (tile.compareTo(otherTile) < 0) {
                trConcealed2.addView(btn, i);
                return;
            }
        }

        trConcealed2.addView(btn);
    }

    private void setAsWinTileButton(ImageButton ib) {
        ib.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B9F6CA")));

        if (winTileButton != null)
            winTileButton.setImageTintList(null);

        winTileButton = ib;
    }

    private void removeFromConcealed(View viewToRemove) {
        for (int i = 0; i < trConcealed1.getChildCount(); i++) {
            if (trConcealed1.getChildAt(i) == viewToRemove) {
                trConcealed1.removeViewAt(i);

                if (trConcealed2.getChildCount() > 0) {
                    View view = trConcealed2.getChildAt(0);
                    trConcealed2.removeViewAt(0);
                    trConcealed1.addView(view);
                }
            }
        }

        for (int i = 0; i < trConcealed2.getChildCount(); i++) {
            if (trConcealed2.getChildAt(i) == viewToRemove)
                trConcealed2.removeViewAt(i);
        }

        if (viewToRemove == winTileButton) {
            if (trConcealed1.getChildCount() > 0)
                setAsWinTileButton((ImageButton) trConcealed1.getChildAt(0));
            else
                winTileButton = null;
        }
    }
}