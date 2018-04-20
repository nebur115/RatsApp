package app.stundenplan.ms.rats.ratsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Marius on 12.03.2018.
 */

public class SpeicherVerwaltung {
    private final SharedPreferences sharedPrefs;

    public String getString(String Speicher) throws Exception {
        if (sharedPrefs.contains(Speicher)) {
            return sharedPrefs.getString(Speicher, "DEFAULT");
        } else {
            return "";
        }

    }

    public void setString(String Speicher, String Inhalt) throws Exception {
        if (!sharedPrefs.edit().putString(Speicher, Inhalt).commit()) {
            throw new Exception();
        }
    }

    public SpeicherVerwaltung(SharedPreferences s) {
        this.sharedPrefs = s;
    }
}
