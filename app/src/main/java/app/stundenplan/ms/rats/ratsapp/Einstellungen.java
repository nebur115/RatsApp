package app.stundenplan.ms.rats.ratsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class Einstellungen extends AppCompatActivity {

    ConstraintLayout cStundenplanBearbeiten;
    ConstraintLayout cStundenplanErstellen;
    ConstraintLayout cVertretungsplanAnzeigen;
    ConstraintLayout cHandyStummschalten;
    ConstraintLayout cInDerSchule;
    ConstraintLayout cWaehrendDesUnterichts;
    ConstraintLayout cWochenTauschen;
    ConstraintLayout cStundenplanLoeschen;
    Switch sHandyStummSchalten;
    CheckBox cBInDerSchule;
    CheckBox cBWaehrendDesUnterichts;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.einstellungen);
        cStundenplanBearbeiten = findViewById(R.id.cStundenplanBearbeiten);
        cStundenplanErstellen = findViewById(R.id.cStundenplanErstellen);
        cVertretungsplanAnzeigen = findViewById(R.id.cVertretungsplanAnzeigen);
        cHandyStummschalten = findViewById(R.id.cHandyStummschalten);
        cInDerSchule = findViewById(R.id.cInderSchule);
        cWaehrendDesUnterichts = findViewById(R.id.waehrenddesUnterichts);
        cWochenTauschen = findViewById(R.id.WocheTauschen);
        cStundenplanLoeschen = findViewById(R.id.cStundenLöschen);
        sHandyStummSchalten = findViewById(R.id.HandyAutomatischStummschalten);
        cBInDerSchule = findViewById(R.id.inderSchule);
        cBWaehrendDesUnterichts = findViewById(R.id.CBwaehrenddesUnterichts);

        final SharedPreferences settings = getSharedPreferences("RatsVertretungsPlanApp", 0);

        if (settings.contains("Stundenliste")) {

            cStundenplanErstellen.setVisibility(View.GONE);


            if (!settings.getBoolean("zweiWöchentlich", false)) {
                cWochenTauschen.setVisibility(View.GONE);
            } else {


                cWochenTauschen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences.Editor editor = settings.edit();

                        String jsona = settings.getString("Stundenliste", null);
                        String jsonb = settings.getString("WocheBStundenListe", null);

                        editor.putString("Stundenliste", jsonb);
                        editor.putString("WocheBStundenListe", jsona);
                        editor.apply();

                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                        Toast.makeText(getBaseContext(), "Woche A und B vertauscht", Toast.LENGTH_SHORT).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {

                            v.vibrate(100);
                        }


                    }
                });
            }

            if (settings.getBoolean("AutomatischStummschalten", true)) {
                sHandyStummSchalten.setChecked(true);
                cBInDerSchule.setTextColor(Color.parseColor("#353535"));
                cBWaehrendDesUnterichts.setTextColor(Color.parseColor("#353535"));
                cBInDerSchule.setChecked(settings.getBoolean("LocationBasedStumm", true));
                cBWaehrendDesUnterichts.setChecked(settings.getBoolean("StundenplanBasedStumm", true));
            } else {
                sHandyStummSchalten.setChecked(false);
                cBInDerSchule.setChecked(false);
                cBWaehrendDesUnterichts.setChecked(false);
                cBInDerSchule.setTextColor(Color.parseColor("#A4A4A4"));
                cBWaehrendDesUnterichts.setTextColor(Color.parseColor("#A4A4A4"));
            }


            cStundenplanLoeschen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog alertDialog = new AlertDialog.Builder(Einstellungen.this).create();
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
                }
            });


            cBInDerSchule.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(!sHandyStummSchalten.isChecked()){
                        cBInDerSchule.setChecked(false);
                    }else{
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("LocationBasedStumm", b);
                        editor.apply();
                        if(!b && !cBWaehrendDesUnterichts.isChecked()){
                            cBWaehrendDesUnterichts.setChecked(true);
                        }
                    }
                }
            });


            cBWaehrendDesUnterichts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(!sHandyStummSchalten.isChecked()){
                        cBWaehrendDesUnterichts.setChecked(false);
                    }else{
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("StundenplanBasedStumm", b);
                        editor.apply();
                        if(!b && !cBInDerSchule.isChecked()){
                            cBInDerSchule.setChecked(true);
                        }
                    }
                }
            });


            sHandyStummSchalten.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        cBInDerSchule.setTextColor(Color.parseColor("#353535"));
                        cBWaehrendDesUnterichts.setTextColor(Color.parseColor("#353535"));
                        cBInDerSchule.setChecked(settings.getBoolean("LocationBasedStumm", true));
                        cBWaehrendDesUnterichts.setChecked(settings.getBoolean("StundenplanBasedStumm", true));


                    } else {
                        cBInDerSchule.setChecked(false);
                        cBWaehrendDesUnterichts.setChecked(false);
                        cBInDerSchule.setTextColor(Color.parseColor("#A4A4A4"));
                        cBWaehrendDesUnterichts.setTextColor(Color.parseColor("#A4A4A4"));
                    }
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean("AutomatischStummschalten", b);
                    editor.apply();
                }
            });


            cStundenplanBearbeiten.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Einstellungen.this, create_stundenplan.class);
                    i.putExtra("Woche", 1);
                    startActivity(i);
                }
            });


        } else {
            cStundenplanBearbeiten.setVisibility(View.GONE);
            cVertretungsplanAnzeigen.setVisibility(View.GONE);
            cHandyStummschalten.setVisibility(View.GONE);
            cInDerSchule.setVisibility(View.GONE);
            cWaehrendDesUnterichts.setVisibility(View.GONE);
            cWochenTauschen.setVisibility(View.GONE);
            cStundenplanLoeschen.setVisibility(View.GONE);

            cStundenplanErstellen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Einstellungen.this, create_stundenplan_obtionen.class);
                    startActivity(i);
                }
            });
        }


    }

}
