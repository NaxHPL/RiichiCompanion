package com.example.riichicompanion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.riichicompanion.handcalculation.Hand;

public class HandInputViewModel extends ViewModel {

    private final MutableLiveData<Hand> inputtedHand = new MutableLiveData<>();

    public void setHand(Hand hand) {
        inputtedHand.setValue(hand);
    }

    public LiveData<Hand> getHand() {
        return inputtedHand;
    }
}