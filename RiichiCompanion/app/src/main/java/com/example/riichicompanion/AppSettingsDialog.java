package com.example.riichicompanion;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AppSettingsDialog extends AppCompatDialogFragment {

    private Switch switchKeepScreenOn;
    private Spinner spinTheme;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_app_settings, null);

        builder.setView(view)
            .setTitle(R.string.app_settings_dialog_title)
            .setPositiveButton(R.string.save, (dialog, which) -> saveSettings(view.getContext()))
            .setNegativeButton(R.string.cancel, (dialog, which) -> {});

        initializeViews(view);
        return builder.create();
    }

    private void initializeViews(View view) {
        switchKeepScreenOn = view.findViewById(R.id.switchKeepScreenOn);
        switchKeepScreenOn.setChecked(PersistentStorage.getKeepScreenOn(view.getContext()));

        spinTheme = view.findViewById(R.id.spinTheme);
        spinTheme.setSelection(PersistentStorage.getThemeOption(view.getContext()).ordinal());
    }

    private void saveSettings(Context context) {
        PersistentStorage.saveKeepScreenOn(context, switchKeepScreenOn.isChecked());

        ThemeOption oldTheme = PersistentStorage.getThemeOption(context);
        ThemeOption newTheme = ThemeOption.valueOf(spinTheme.getSelectedItem().toString());

        if (newTheme != oldTheme) {
            PersistentStorage.saveThemeOption(context, newTheme);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Please restart the app to apply the theme change.")
                   .setPositiveButton("Got it", ((dialog, which) -> {}));

            builder.create().show();
        }
    }
}
