package app.stundenplan.ms.rats.ratsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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


        final SharedPreferences settings = getSharedPreferences("RatsVertretungsPlanApp", 0);

        if(settings.contains("Stundenliste")){
            cStundenplanErstellen.setVisibility(View.GONE);
            if(!settings.getBoolean("zweiWöchentlich", false)){
                cWochenTauschen.setVisibility(View.GONE);
            }

        }else{
            cStundenplanBearbeiten.setVisibility(View.GONE);
            cVertretungsplanAnzeigen.setVisibility(View.GONE);
            cHandyStummschalten.setVisibility(View.GONE);
            cInDerSchule.setVisibility(View.GONE);
            cWaehrendDesUnterichts.setVisibility(View.GONE);
            cWochenTauschen.setVisibility(View.GONE);
            cStundenplanLoeschen.setVisibility(View.GONE);
        }

        cStundenplanErstellen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Einstellungen.this, create_stundenplan_obtionen.class);
                startActivity(i);
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
                    v.vibrate(VibrationEffect.createOneShot(100,VibrationEffect.DEFAULT_AMPLITUDE));
                }else{

                    v.vibrate(100);
                }


            }
        });


    }

}
