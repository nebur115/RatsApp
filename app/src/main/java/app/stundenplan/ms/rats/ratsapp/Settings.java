package app.stundenplan.ms.rats.ratsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class Settings extends PreferenceActivity {

    SharedPreferences settings;
    Preference CreateStundenplan;
    CheckBoxPreference StundenVertretungsplanAnzeigen;
    CheckBoxPreference AutomatischStummschalten;
    Preference WochenTausch;
    Preference EditStundenplan;
    Preference LoescheStundenplan;
    CheckBoxPreference AlleKurse;
    CheckBoxPreference PlusMinusMitrechnen;
    Preference QPlanaendern;
    EditTextPreference Stufe;
    Preference AllesLoeschen;
    Preference Copyright;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.app_preferences);

        settings = getSharedPreferences("RatsVertretungsPlanApp", 0);
        CreateStundenplan = findPreference("CreateStundenplan");
        StundenVertretungsplanAnzeigen = (CheckBoxPreference) findPreference("StundenplanVertretungsplananzeigen");
        AutomatischStummschalten = (CheckBoxPreference) findPreference("AutomatischStummschalten");
        WochenTausch = findPreference("Wochentausch");
        EditStundenplan = findPreference("EditStundenplan");
        LoescheStundenplan = findPreference("LöscheStundenplan");
        AlleKurse = (CheckBoxPreference) findPreference("AlleKurse");
        PlusMinusMitrechnen = (CheckBoxPreference) findPreference("Plusminusmitrechnen");
        QPlanaendern = findPreference("QPlanändern");
        Stufe = (EditTextPreference) findPreference("Stufe");
        AllesLoeschen = findPreference("Alles löschen");
        Copyright = findPreference("Copyright");

        CreateStundenplan.setIntent(new Intent(Settings.this, create_stundenplan_obtionen.class));
        
    }

}

