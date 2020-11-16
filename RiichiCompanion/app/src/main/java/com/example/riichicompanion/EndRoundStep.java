package com.example.riichicompanion;

import androidx.core.util.Consumer;

public class EndRoundStep {

    private final String promptText;
    private final VoidFunction onStepStart;
    private final Consumer<Player> onPlayerSelected;
    private final VoidFunction onStepEnd;
    private final EndRoundStep nextStep;

    public EndRoundStep(String promptText, VoidFunction onStepStart, Consumer<Player> onPlayerSelected,
                        VoidFunction onStepEnd, EndRoundStep nextStep) {
        this.promptText = promptText;
        this.onStepStart = onStepStart;
        this.onPlayerSelected = onPlayerSelected;
        this.onStepEnd = onStepEnd;
        this.nextStep = nextStep;
    }

    public String getPromptText() {
        return promptText;
    }

    public void doOnStepStart() {
        onStepStart.run();
    }

    public void doOnPlayerSelected(Player player) {
        onPlayerSelected.accept(player);
    }

    public void doOnStepEnd() {
        onStepEnd.run();
    }

    public EndRoundStep getNextStep() {
        return nextStep;
    }

}
