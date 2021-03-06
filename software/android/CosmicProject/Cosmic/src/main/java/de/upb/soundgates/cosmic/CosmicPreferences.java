package de.upb.soundgates.cosmic;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created by posewsky on 03.12.13.
 */
public class CosmicPreferences {
    public static final String PREFS_NAME = "CosmicPrefs";
    private static CosmicPreferences instance = null;

    private Context context;

    public static CosmicPreferences getInstance(Context context) {
        if(instance == null) {
            if(context == null)
                return null;
            instance = new CosmicPreferences(context);
            return instance;
        }
        return instance;
    }

    private CosmicPreferences(Context context) {
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
        return (ret=="") ? null : ret;
    }

    public void putString(String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public Set<String> getStringSet(String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        Set<String> ret = settings.getStringSet(key, null);
        if(ret == null)
            ret = new LinkedHashSet<String>();
        return ret;
    }

    public void putStringSet(String key, Set<String> value) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet(key, value);
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
