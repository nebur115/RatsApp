package app.stundenplan.ms.rats.ratsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;


public class Settings extends PreferenceActivity {

    SharedPreferences settings;
    Preference CreateStundenplan;
    CheckBoxPreference StundenVertretungsplanAnzeigen;
    Preference WochenTausch;
    Preference EditStundenplan;
    Preference LoescheStundenplan;
    CheckBoxPreference AlleKurse;
    CheckBoxPreference PlusMinusMitrechnen;
    Preference QPlanaendern;
    EditTextPreference Stufe;
    Preference AllesLoeschen;
    Preference Copyright;
    PreferenceCategory Stundenplan;
    PreferenceCategory Vertretungsplan;
    PreferenceCategory Notenrechner;
    PreferenceCategory Sonstiges;
    String Tab = "";

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.app_preferences);

        Stundenplan = (PreferenceCategory) findPreference("Stundenplan");
        Vertretungsplan = (PreferenceCategory) findPreference("Vertretungsplan");
        Notenrechner = (PreferenceCategory) findPreference("Notenrechner");
        Sonstiges = (PreferenceCategory) findPreference("Sonstiges");

        settings = getSharedPreferences("RatsVertretungsPlanApp", 0);

        Intent intent = getIntent();
        if(!(intent.getExtras()==null)){
            Tab = intent.getExtras().getString("Tab");
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("Tab", Tab);
            editor.apply();
        }



        CreateStundenplan = findPreference("CreateStundenplan");
        StundenVertretungsplanAnzeigen = (CheckBoxPreference) findPreference("StundenplanVertretungsplananzeigen");
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

        Boolean helpboolean = settings.getBoolean("VertretungsplanInStundenplanAnzeigen", settings.contains("Stundenliste"));
        StundenVertretungsplanAnzeigen.setChecked(helpboolean);
        AlleKurse.setChecked(settings.getBoolean("AlleKurseAnzeigen", settings.contains("Kursliste")));
        PlusMinusMitrechnen.setChecked((settings.getBoolean("+/-Mitrechen", settings.getString("Stufe", "").equals("Q1") ||settings.getString("Stufe", "").equals("Q2") )));
        Stufe.setText(settings.getString("Stufe", ""));


        CreateStundenplan.setIntent(new Intent(Settings.this, create_stundenplan_obtionen.class));
        EditStundenplan.setIntent(new Intent(Settings.this, create_stundenplan.class).putExtra("Woche", 1));
        QPlanaendern.setIntent(new Intent(Settings.this, QPhase_create.class));


        if(settings.contains("Kursliste")){
            Stundenplan.removePreference(CreateStundenplan);
        }else{
            AlleKurse.setEnabled(false);
            StundenVertretungsplanAnzeigen.setEnabled(false);
            WochenTausch.setEnabled(false);
            EditStundenplan.setEnabled(false);
            LoescheStundenplan.setEnabled(false);
        }

        if(!(settings.getString("Stufe", "").equals("Q1") ||settings.getString("Stufe", "").equals("Q2"))){
            Notenrechner.removePreference(QPlanaendern);
        }


        Copyright.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(Settings.this,"In der Testverion sind die Copyrights noch nicht verfügbar", Toast.LENGTH_LONG).show();
                return false;
            }
        });

        StundenVertretungsplanAnzeigen.setOnPreferenceClickListener(new CheckBoxPreference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {

                    v.vibrate(50);
                }


                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("VertretungsplanInStundenplanAnzeigen", StundenVertretungsplanAnzeigen.isChecked());
                editor.apply();
                return false;
            }
        });

        WochenTausch.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                SharedPreferences.Editor editor = settings.edit();

                String jsona = settings.getString("Stundenliste", null);
                String jsonb = settings.getString("WocheBStundenListe", null);

                editor.putString("Stundenliste", jsonb);
                editor.putString("WocheBStundenListe", jsona);
                editor.apply();

                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                Toast.makeText(getBaseContext(), "Woche A und B vertauscht", Toast.LENGTH_SHORT).show();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {

                    v.vibrate(50);
                }

                return true;
            }
        });

        LoescheStundenplan.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {

                    v.vibrate(100);
                }

                AlertDialog alertDialog = new AlertDialog.Builder(Settings.this).create();
                alertDialog.setTitle("Achtung!");
                alertDialog.setMessage("Bist du dir sicher, dass du deinen Stundenplan löschen willst?");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        SharedPreferences.Editor editor = settings.edit();
                        editor.remove("Stundenliste");
                        editor.remove("WocheBStundenListe");
                        editor.remove("zweiWöchentlich");
                        editor.apply();
                    }
                });
                alertDialog.show();
                return false;
            }
        });

        AlleKurse.setOnPreferenceClickListener(new CheckBoxPreference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);



                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {

                    v.vibrate(50);
                }

                if(!settings.contains("Kursliste")){
                    AlleKurse.setChecked(true);
                }else{
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean("AlleKurseAnzeigen", AlleKurse.isChecked());
                    editor.apply();
                }

                return false;
            }
        });


        PlusMinusMitrechnen.setOnPreferenceClickListener(new CheckBoxPreference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {

                    v.vibrate(50);
                }

                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("+/-Mitrechen", PlusMinusMitrechnen.isChecked());
                editor.apply();

                return false;
            }
        });

        AllesLoeschen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {

                    v.vibrate(300);
                }

                AlertDialog alertDialog = new AlertDialog.Builder(Settings.this).create();
                alertDialog.setTitle("Achtung!");
                alertDialog.setMessage("Bist du dir sicher, dass alles zurücksetzen willst?");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        SharedPreferences.Editor editor = settings.edit();
                        editor.clear();
                        editor.apply();
                        Intent j = new Intent(Settings.this, loading.class);
                        startActivity(j);
                    }
                });
                alertDialog.show();
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {

        Tab = settings.getString("Tab", "");
        Intent i = new Intent(Settings.this, vertretungsplan.class);
        if(Stufe.getText().equals(settings.getString("Stufe",""))){
            i.putExtra("Stufe", "EXISTINGSTUNDE");
            i.putExtra("Tab", Tab);
        }else{
            i.putExtra("Stufe", Stufe.getText());
            i.putExtra("Tab", Tab);
        }


        startActivity(i);


    }

}

