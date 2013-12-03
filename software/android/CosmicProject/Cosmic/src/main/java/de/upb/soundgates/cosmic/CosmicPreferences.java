package de.upb.soundgates.cosmic;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by posewsky on 03.12.13.
 */
public class CosmicPreferences {
    public static final String PREFS_NAME = "CosmicPrefs";

    private Context context;

    public CosmicPreferences(Context context) {
        this.context = context;
    }


    public void remove(String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        editor.commit();
    }


    public String getString(String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        String ret = settings.getString(key, "");
        android.util.Log.e("Cosmic Pref", ret);
        return (ret=="") ? null : ret;
    }

    public void putString(String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public int getInt(String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        return settings.getInt(key, -1);
    }

    public void putInt(String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
    }
}
