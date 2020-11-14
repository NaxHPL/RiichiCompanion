package com.example.riichicompanion;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

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
}
