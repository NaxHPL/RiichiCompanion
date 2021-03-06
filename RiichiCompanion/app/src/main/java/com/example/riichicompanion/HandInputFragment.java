package com.example.riichicompanion;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
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

import com.example.riichicompanion.handcalculation.Dragon;
import com.example.riichicompanion.handcalculation.Hand;
import com.example.riichicompanion.handcalculation.Meld;
import com.example.riichicompanion.handcalculation.Suit;
import com.example.riichicompanion.handcalculation.Tile;

import java.util.ArrayList;
import java.util.HashMap;

public class HandInputFragment extends Fragment {

    private static final HashMap<String, Integer> tileDrawableIds = new HashMap<String, Integer>() {{
        put("1m", R.drawable._1m);
        put("2m", R.drawable._2m);
        put("3m", R.drawable._3m);
        put("4m", R.drawable._4m);
        put("5m", R.drawable._5m);
        put("6m", R.drawable._6m);
        put("7m", R.drawable._7m);
        put("8m", R.drawable._8m);
        put("9m", R.drawable._9m);
        put("1p", R.drawable._1p);
        put("2p", R.drawable._2p);
        put("3p", R.drawable._3p);
        put("4p", R.drawable._4p);
        put("5p", R.drawable._5p);
        put("6p", R.drawable._6p);
        put("7p", R.drawable._7p);
        put("8p", R.drawable._8p);
        put("9p", R.drawable._9p);
        put("1s", R.drawable._1s);
        put("2s", R.drawable._2s);
        put("3s", R.drawable._3s);
        put("4s", R.drawable._4s);
        put("5s", R.drawable._5s);
        put("6s", R.drawable._6s);
        put("7s", R.drawable._7s);
        put("8s", R.drawable._8s);
        put("9s", R.drawable._9s);
        put("E", R.drawable.east_tile);
        put("S", R.drawable.south_tile);
        put("W", R.drawable.west_tile);
        put("N", R.drawable.north_tile);
        put("g", R.drawable.green_dragon);
        put("r", R.drawable.red_dragon);
        put("w", R.drawable.white_dragon);
    }};

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

    private ImageButton[] tileButtons;
    private HandInputViewModel viewModel;
    private ArrayList<ConstraintLayout> meldCls;
    private ImageButton winTileButton;
    private Hand hand;
    private int meldMargin;

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
        viewModel = new ViewModelProvider(requireActivity()).get(HandInputViewModel.class);
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

        hand = new Hand();
        meldCls = new ArrayList<>();
        tileButtons = new ImageButton[] {
            ib1m, ib2m, ib3m, ib4m, ib5m, ib6m, ib7m, ib8m, ib9m,
            ib1p, ib2p, ib3p, ib4p, ib5p, ib6p, ib7p, ib8p, ib9p,
            ib1s, ib2s, ib3s, ib4s, ib5s, ib6s, ib7s, ib8s, ib9s,
            ibEast, ibSouth, ibWest, ibNorth, ibGreen, ibRed, ibWhite
        };
        meldMargin = (int) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            8f,
            getActivity().getResources().getDisplayMetrics()
        );

        setImageButtonTags();
        setButtonListeners();
    }

    private void setImageButtonTags() {
        ib1m.setTag(R.id.tile_object, new Tile(Suit.Man, 1));
        ib2m.setTag(R.id.tile_object, new Tile(Suit.Man, 2));
        ib3m.setTag(R.id.tile_object, new Tile(Suit.Man, 3));
        ib4m.setTag(R.id.tile_object, new Tile(Suit.Man, 4));
        ib5m.setTag(R.id.tile_object, new Tile(Suit.Man, 5));
        ib6m.setTag(R.id.tile_object, new Tile(Suit.Man, 6));
        ib7m.setTag(R.id.tile_object, new Tile(Suit.Man, 7));
        ib8m.setTag(R.id.tile_object, new Tile(Suit.Man, 8));
        ib9m.setTag(R.id.tile_object, new Tile(Suit.Man, 9));
        ib1p.setTag(R.id.tile_object, new Tile(Suit.Pin, 1));
        ib2p.setTag(R.id.tile_object, new Tile(Suit.Pin, 2));
        ib3p.setTag(R.id.tile_object, new Tile(Suit.Pin, 3));
        ib4p.setTag(R.id.tile_object, new Tile(Suit.Pin, 4));
        ib5p.setTag(R.id.tile_object, new Tile(Suit.Pin, 5));
        ib6p.setTag(R.id.tile_object, new Tile(Suit.Pin, 6));
        ib7p.setTag(R.id.tile_object, new Tile(Suit.Pin, 7));
        ib8p.setTag(R.id.tile_object, new Tile(Suit.Pin, 8));
        ib9p.setTag(R.id.tile_object, new Tile(Suit.Pin, 9));
        ib1s.setTag(R.id.tile_object, new Tile(Suit.Sou, 1));
        ib2s.setTag(R.id.tile_object, new Tile(Suit.Sou, 2));
        ib3s.setTag(R.id.tile_object, new Tile(Suit.Sou, 3));
        ib4s.setTag(R.id.tile_object, new Tile(Suit.Sou, 4));
        ib5s.setTag(R.id.tile_object, new Tile(Suit.Sou, 5));
        ib6s.setTag(R.id.tile_object, new Tile(Suit.Sou, 6));
        ib7s.setTag(R.id.tile_object, new Tile(Suit.Sou, 7));
        ib8s.setTag(R.id.tile_object, new Tile(Suit.Sou, 8));
        ib9s.setTag(R.id.tile_object, new Tile(Suit.Sou, 9));
        ibEast.setTag(R.id.tile_object, new Tile(Wind.East));
        ibSouth.setTag(R.id.tile_object, new Tile(Wind.South));
        ibWest.setTag(R.id.tile_object, new Tile(Wind.West));
        ibNorth.setTag(R.id.tile_object, new Tile(Wind.North));
        ibGreen.setTag(R.id.tile_object, new Tile(Dragon.Green));
        ibRed.setTag(R.id.tile_object, new Tile(Dragon.Red));
        ibWhite.setTag(R.id.tile_object, new Tile(Dragon.White));
    }

    private void setButtonListeners() {
        tbChii.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                tbPon.setChecked(false);
                tbKan.setChecked(false);
                tbClosedKan.setChecked(false);

                enableValidChiiTiles();
            }
            else if (!tbPon.isChecked() && !tbKan.isChecked() && !tbClosedKan.isChecked())
                enableValidConcealedTiles();
        });
        tbPon.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                tbChii.setChecked(false);
                tbKan.setChecked(false);
                tbClosedKan.setChecked(false);

                enableValidPonTiles();
            }
            else if (!tbChii.isChecked() && !tbKan.isChecked() && !tbClosedKan.isChecked())
                enableValidConcealedTiles();
        });
        tbKan.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                tbChii.setChecked(false);
                tbPon.setChecked(false);
                tbClosedKan.setChecked(false);

                enableValidKanTiles();
            }
            else if (!tbChii.isChecked() && !tbPon.isChecked() && !tbClosedKan.isChecked())
                enableValidConcealedTiles();
        });
        tbClosedKan.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                tbChii.setChecked(false);
                tbPon.setChecked(false);
                tbKan.setChecked(false);

                enableValidKanTiles();
            }
            else if (!tbChii.isChecked() && !tbPon.isChecked() && !tbKan.isChecked())
                enableValidConcealedTiles();
        });

        for (ImageButton ib : tileButtons)
            ib.setOnClickListener(this::onTileButtonClicked);
    }

    private void onTileButtonClicked(View view) {
        ImageButton ib;
        try {
            ib = (ImageButton) view;
        } catch (Exception e) { return; }

        if (tbChii.isChecked()) {
            addChii(ib);
            enableValidChiiTiles();
        }
        else if (tbPon.isChecked()) {
            addPon(ib);
            enableValidPonTiles();
        }
        else if (tbKan.isChecked()) {
            addOpenKan(ib);
            enableValidKanTiles();
        }
        else if (tbClosedKan.isChecked()) {
            addClosedKan(ib);
            enableValidKanTiles();
        }
        else {
            addConcealedTile(ib);
            enableValidConcealedTiles();
        }
    }

    private void enableValidChiiTiles() {
        if (hand.getTotalTileCount() >= 12) {
            for (ImageButton ib : tileButtons)
                setImageButtonEnabled(ib, false);
            return;
        }

        Tile tile;
        Meld chiiMeld;
        for (ImageButton ib : tileButtons) {
            tile = (Tile) ib.getTag(R.id.tile_object);
            chiiMeld = tile.getChii();

            if (chiiMeld == null) {
                setImageButtonEnabled(ib, false);
                continue;
            }

            boolean isValidChii = true;

            for (Tile t : chiiMeld.getTiles()) {
                if (hand.getTileCount(t.getStringRep()) >= 4)
                    isValidChii = false;
            }

            setImageButtonEnabled(ib, isValidChii);
        }
    }

    private void enableValidPonTiles() {
        if (hand.getTotalTileCount() >= 12) {
            for (ImageButton ib : tileButtons)
                setImageButtonEnabled(ib, false);
            return;
        }

        Tile tile;
        for (ImageButton ib : tileButtons) {
            tile = (Tile) ib.getTag(R.id.tile_object);
            setImageButtonEnabled(ib, hand.getTileCount(tile.getStringRep()) <= 1);
        }
    }

    private void enableValidKanTiles() {
        if (hand.getTotalTileCount() >= 12) {
            for (ImageButton ib : tileButtons)
                setImageButtonEnabled(ib, false);
            return;
        }

        Tile tile;
        for (ImageButton ib : tileButtons) {
            tile = (Tile) ib.getTag(R.id.tile_object);
            setImageButtonEnabled(ib, hand.getTileCount(tile.getStringRep()) == 0);
        }
    }

    private void enableValidConcealedTiles() {
        if (hand.getTotalTileCount() >= 14) {
            for (ImageButton ib : tileButtons)
                setImageButtonEnabled(ib, false);
            return;
        }

        Tile tile;
        for (ImageButton ib : tileButtons) {
            tile = (Tile) ib.getTag(R.id.tile_object);
            setImageButtonEnabled(ib, hand.getTileCount(tile.getStringRep()) <= 3);
        }
    }

    private void addChii(ImageButton tileButtonClicked) {
        Meld chiiMeld = ((Tile) tileButtonClicked.getTag(R.id.tile_object)).getChii();

        if (chiiMeld == null)
            return;

        ConstraintLayout chiiLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.chii_and_pon_cl, clExposedTiles, false);
        chiiLayout.setId(View.generateViewId());
        chiiLayout.setTag(R.id.meld_object, chiiMeld);

        Tile[] chiiTiles = chiiMeld.getTiles();

        for (int i = 0; i < chiiLayout.getChildCount(); i++) {
            ImageView iv;

            try {
                iv = (ImageView) chiiLayout.getChildAt(i);
            } catch (Exception e) { continue; }

            Integer drawableId = tileDrawableIds.get(chiiTiles[i].getStringRep());

            if (drawableId == null)
                continue;

            iv.setImageResource(drawableId);
        }

        addMeldConstraintLayout(chiiLayout);
        hand.addMeld(chiiMeld);
        viewModel.setHand(hand);
    }

    private void addPon(ImageButton tileButtonClicked) {
        Tile tile = (Tile) tileButtonClicked.getTag(R.id.tile_object);
        Meld ponMeld = tile.getPon();
        Integer drawableId = tileDrawableIds.get(tile.getStringRep());

        if (drawableId == null)
            return;

        ConstraintLayout ponLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.chii_and_pon_cl, clExposedTiles, false);
        ponLayout.setId(View.generateViewId());
        ponLayout.setTag(R.id.meld_object, ponMeld);

        for (int i = 0; i < ponLayout.getChildCount(); i++) {
            ImageView iv;
            try {
                iv = (ImageView) ponLayout.getChildAt(i);
            } catch (Exception e) { continue; }

            iv.setImageResource(drawableId);
        }

        addMeldConstraintLayout(ponLayout);
        hand.addMeld(ponMeld);
        viewModel.setHand(hand);
    }

    private void addOpenKan(ImageButton tileButtonClicked) {
        Tile tile = (Tile) tileButtonClicked.getTag(R.id.tile_object);
        Meld kanMeld = tile.getKan();
        Integer drawableId = tileDrawableIds.get(tile.getStringRep());

        if (drawableId == null)
            return;

        ConstraintLayout openKanLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.open_kan_cl, clExposedTiles, false);
        openKanLayout.setId(View.generateViewId());
        openKanLayout.setTag(R.id.meld_object, kanMeld);

        for (int i = 0; i < openKanLayout.getChildCount(); i++) {
            ImageView iv;
            try {
                iv = (ImageView) openKanLayout.getChildAt(i);
            } catch (Exception e) { continue; }

            iv.setImageResource(drawableId);
        }

        addMeldConstraintLayout(openKanLayout);
        hand.addMeld(kanMeld);
        viewModel.setHand(hand);
    }

    private void addClosedKan(ImageButton tileButtonClicked) {
        Tile tile = (Tile) tileButtonClicked.getTag(R.id.tile_object);
        Meld closedKanMeld = tile.getClosedKan();
        Integer drawableId = tileDrawableIds.get(tile.getStringRep());

        if (drawableId == null)
            return;

        ConstraintLayout closedKanLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.closed_kan_cl, clExposedTiles, false);
        closedKanLayout.setId(View.generateViewId());
        closedKanLayout.setTag(R.id.meld_object, closedKanMeld);

        for (int i = 1; i < closedKanLayout.getChildCount() - 1; i++) {
            ImageView iv;
            try {
                iv = (ImageView) closedKanLayout.getChildAt(i);
            } catch (Exception e) { continue; }

            iv.setImageResource(drawableId);
        }

        addMeldConstraintLayout(closedKanLayout);
        hand.addMeld(closedKanMeld);
        viewModel.setHand(hand);
    }

    private void addMeldConstraintLayout(ConstraintLayout cl) {
        clExposedTiles.addView(cl);
        meldCls.add(cl);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(clExposedTiles);

        switch (meldCls.size()) {
            case 1:
                constraintSet.connect(cl.getId(), ConstraintSet.START, clExposedTiles.getId(), ConstraintSet.START, meldMargin);
                constraintSet.connect(cl.getId(), ConstraintSet.TOP,  clExposedTiles.getId(), ConstraintSet.TOP, meldMargin);
                break;
            case 2:
                constraintSet.connect(cl.getId(), ConstraintSet.START, meldCls.get(0).getId(), ConstraintSet.END, meldMargin);
                constraintSet.connect(cl.getId(), ConstraintSet.TOP, meldCls.get(0).getId(), ConstraintSet.TOP, 0);
                break;
            case 3:
                constraintSet.connect(cl.getId(), ConstraintSet.START, clExposedTiles.getId(), ConstraintSet.START, meldMargin);
                constraintSet.connect(cl.getId(), ConstraintSet.TOP, meldCls.get(0).getId(), ConstraintSet.BOTTOM, meldMargin);
                break;
            case 4:
                constraintSet.connect(cl.getId(), ConstraintSet.START, meldCls.get(2).getId(), ConstraintSet.END, meldMargin);
                constraintSet.connect(cl.getId(), ConstraintSet.TOP, meldCls.get(2).getId(), ConstraintSet.TOP, 0);
                break;
        }

        constraintSet.applyTo(clExposedTiles);
        cl.setOnClickListener(this::removeMeld);
    }

    private void removeMeld(View clClicked) {
        clExposedTiles.removeView(clClicked);
        meldCls.remove(clClicked);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(clExposedTiles);

        for (int i = 1; i <= meldCls.size(); i++) {
            switch (i) {
                case 1:
                    constraintSet.connect(meldCls.get(0).getId(), ConstraintSet.START, clExposedTiles.getId(), ConstraintSet.START, meldMargin);
                    constraintSet.connect(meldCls.get(0).getId(), ConstraintSet.TOP,  clExposedTiles.getId(), ConstraintSet.TOP, meldMargin);
                    break;
                case 2:
                    constraintSet.connect(meldCls.get(1).getId(), ConstraintSet.START, meldCls.get(0).getId(), ConstraintSet.END, meldMargin);
                    constraintSet.connect(meldCls.get(1).getId(), ConstraintSet.TOP, meldCls.get(0).getId(), ConstraintSet.TOP, 0);
                    break;
                case 3:
                    constraintSet.connect(meldCls.get(2).getId(), ConstraintSet.START, clExposedTiles.getId(), ConstraintSet.START, meldMargin);
                    constraintSet.connect(meldCls.get(2).getId(), ConstraintSet.TOP, meldCls.get(0).getId(), ConstraintSet.BOTTOM, meldMargin);
                    break;
            }
        }

        constraintSet.applyTo(clExposedTiles);

        hand.removeMeld((Meld)clClicked.getTag(R.id.meld_object));
        viewModel.setHand(hand);

        if (tbChii.isChecked())
            enableValidChiiTiles();
        else if (tbPon.isChecked())
            enableValidPonTiles();
        else if (tbKan.isChecked() || tbClosedKan.isChecked())
            enableValidKanTiles();
        else
            enableValidConcealedTiles();
    }

    private void addConcealedTile(ImageButton tileButtonClicked) {
        Tile tile = (Tile) tileButtonClicked.getTag(R.id.tile_object);
        Integer drawableId = tileDrawableIds.get(tile.getStringRep());

        if (drawableId == null)
            return;

        ImageButton btn = (ImageButton) getLayoutInflater().inflate(R.layout.view_tile_button, trConcealed1, false);
        btn.setImageResource(drawableId);
        btn.setTag(R.id.tile_object, tile);
        btn.setOnClickListener(this::removeFromConcealed);

        hand.setWinTile(tile);
        if (winTileButton != null)
            hand.addConcealedTile((Tile)winTileButton.getTag(R.id.tile_object));
        viewModel.setHand(hand);

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
            if (trConcealed1.getChildCount() > 0) {
                ImageButton ibToSetAsWinTile;

                if (trConcealed2.getChildCount() > 0)
                    ibToSetAsWinTile = (ImageButton) trConcealed2.getChildAt(trConcealed2.getChildCount() - 1);
                else
                    ibToSetAsWinTile = (ImageButton) trConcealed1.getChildAt(trConcealed1.getChildCount() - 1);

                Tile newWinTile = (Tile)ibToSetAsWinTile.getTag(R.id.tile_object);

                hand.removeConcealedTile(newWinTile);
                hand.setWinTile(newWinTile);

                setAsWinTileButton(ibToSetAsWinTile);
            }
            else {
                winTileButton = null;
                hand.setWinTile(null);
            }
        }
        else
            hand.removeConcealedTile((Tile)viewToRemove.getTag(R.id.tile_object));

        viewModel.setHand(hand);

        if (tbChii.isChecked())
            enableValidChiiTiles();
        else if (tbPon.isChecked())
            enableValidPonTiles();
        else if (tbKan.isChecked() || tbClosedKan.isChecked())
            enableValidKanTiles();
        else
            enableValidConcealedTiles();
    }

    private void setImageButtonEnabled(ImageButton ib, boolean enabled) {
        ib.setEnabled(enabled);
        ib.setAlpha(enabled ? 1.0f : 0.5f);
    }
}