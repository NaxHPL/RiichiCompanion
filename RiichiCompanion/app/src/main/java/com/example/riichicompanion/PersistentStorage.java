package com.example.riichicompanion;

import android.content.Context;

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
}
