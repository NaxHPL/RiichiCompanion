package com.example.riichicompanion;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class PersistentStorage {

    public static void saveOngoingGame(Context context, Game gameToSave) {
        Gson gson = new Gson();
        String json = gson.toJson(gameToSave);

        context.getSharedPreferences(context.getString(R.string.ongoing_game_file_name), Context.MODE_PRIVATE)
            .edit()
            .putString(context.getString(R.string.ongoing_game_key), json)
            .apply();
    }

    public static Game retrieveOngoingGame(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
            context.getString(R.string.ongoing_game_file_name),
            Context.MODE_PRIVATE
        );
        String gameAsJson = prefs.getString(
            context.getString(R.string.ongoing_game_key),
            context.getString(R.string.ongoing_game_default_value)
        );

        Gson gson = new Gson();

        try {
            return gson.fromJson(gameAsJson, Game.class);
        }
        catch (Exception ex) {
            return null;
        }
    }

    public static void saveKeepScreenOn(Context context, boolean keepScreenOn) {
        context.getSharedPreferences(context.getString(R.string.settings_file_name), Context.MODE_PRIVATE)
            .edit()
            .putBoolean(context.getString(R.string.keep_screen_on_key), keepScreenOn)
            .apply();
    }

    public static boolean getKeepScreenOn(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.settings_file_name), Context.MODE_PRIVATE);
        return prefs.getBoolean(context.getString(R.string.keep_screen_on_key), true);
    }

    public static void saveThemeOption(Context context, ThemeOption theme) {
        context.getSharedPreferences(context.getString(R.string.settings_file_name), Context.MODE_PRIVATE)
            .edit()
            .putString(context.getString(R.string.theme_option_key), theme.toString())
            .apply();
    }

    public static ThemeOption getThemeOption(Context context) {
            SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.settings_file_name), Context.MODE_PRIVATE);
            String strTheme = prefs.getString(context.getString(R.string.theme_option_key), "Dark");
            return ThemeOption.valueOf(strTheme);
    }
}
