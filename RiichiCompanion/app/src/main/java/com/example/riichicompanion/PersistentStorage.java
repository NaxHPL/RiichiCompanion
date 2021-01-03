package com.example.riichicompanion;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.UUID;

public class PersistentStorage {

    public static void saveGame(Context context, Game game) {
        ArrayList<Game> savedGames = retrieveSavedGames(context);
        boolean foundGameWithSameID = false;

        for (int i = 0; i < savedGames.size(); i++) {
            if (savedGames.get(i).getGameID().equals(game.getGameID())) {
                savedGames.set(i, game);
                foundGameWithSameID = true;
                break;
            }
        }

        if (!foundGameWithSameID)
            savedGames.add(game);

        saveGames(context, savedGames);
    }

    public static ArrayList<Game> retrieveSavedGames(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
            context.getString(R.string.saved_games_file_name),
            Context.MODE_PRIVATE
        );
        String gameAsJson = prefs.getString(
            context.getString(R.string.saved_games_key),
            context.getString(R.string.saved_games_default_value)
        );

        try {
            return new Gson().fromJson(
                gameAsJson,
                new TypeToken<ArrayList<Game>>(){}.getType()
            );
        }
        catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public static void deleteGame(Context context, Game gameToDelete) {
        ArrayList<Game> savedGames = retrieveSavedGames(context);

        for (int i = 0; i < savedGames.size(); i++) {
            if (savedGames.get(i).getGameID().equals(gameToDelete.getGameID())) {
                savedGames.remove(i);
                break;
            }
        }

        saveGames(context, savedGames);
    }

    private static void saveGames(Context context, ArrayList<Game> games) {
        String json = new Gson().toJson(
            games,
            new TypeToken<ArrayList<Game>>(){}.getType()
        );

        context.getSharedPreferences(context.getString(R.string.saved_games_file_name), Context.MODE_PRIVATE)
            .edit()
            .putString(context.getString(R.string.saved_games_key), json)
            .apply();
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

        try {
            return ThemeOption.valueOf(strTheme);
        } catch (IllegalArgumentException e) {
            saveThemeOption(context, ThemeOption.Dark);
            return ThemeOption.Dark;
        }
    }
}
