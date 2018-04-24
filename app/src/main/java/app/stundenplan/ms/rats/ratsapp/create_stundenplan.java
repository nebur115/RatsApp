package app.stundenplan.ms.rats.ratsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ruben on 02.04.2018.
 */

public class create_stundenplan extends AppCompatActivity {

    public int shownWeek;
    private List<Memory_Stunde> WocheAStundenListe = new ArrayList<>();
    private List<Memory_Stunde> WocheBStundenListe = new ArrayList<>();

    boolean Zweiwöchentlich;
    int MaxStunden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_stundenplan);

        Intent intent = getIntent();
        int iWoche = intent.getExtras().getInt("Woche");
        final FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();

        final SharedPreferences settings = getSharedPreferences("RatsVertretungsPlanApp", 0);

        final ImageButton ungrade_woche_button = (ImageButton) findViewById(R.id.ungrade_woche_button);
        final ImageButton grade_woche_button = (ImageButton) findViewById(R.id.grade_woche_button);
        final TextView Woche = findViewById(R.id.Woche);

        final String Stufe = settings.getString("Stufe", null);



        Zweiwöchentlich = settings.getBoolean("zweiWöchentlich", false);
        MaxStunden = settings.getInt("MaxStunden",0);


        final Fragment UngradeWoche ;

        final Fragment GradeWoche = new create_stundenplan_stundenplan(MaxStunden, Zweiwöchentlich, 1);

        if(!Zweiwöchentlich){
            ungrade_woche_button.setVisibility(View.GONE);
            grade_woche_button.setVisibility(View.GONE);
            UngradeWoche = null;
            Woche.setVisibility(View.GONE);
            ft.add(R.id.stundenplan_create_framelayout, GradeWoche);
        }
        else
        {
            UngradeWoche = new create_stundenplan_stundenplan(MaxStunden, Zweiwöchentlich, 2);
            if(iWoche==1){
                ft.add(R.id.stundenplan_create_framelayout, UngradeWoche);
                ft.hide(UngradeWoche);
                ft.add(R.id.stundenplan_create_framelayout, GradeWoche);
                grade_woche_button.setVisibility(View.GONE);
            }
            else{
                ungrade_woche_button.setVisibility(View.GONE);
                ft.add(R.id.stundenplan_create_framelayout, GradeWoche);
                ft.hide(GradeWoche);
                ft.add(R.id.stundenplan_create_framelayout, UngradeWoche);
                Woche.setText("ungrade Woche");

            }

        }

        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

        ImageButton Speichern = (ImageButton) findViewById(R.id.Speichern);

        Speichern.setImageResource(R.drawable.tick);
        ImageButton Einstellungen = (ImageButton) findViewById(R.id.einstellungen);


        Einstellungen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(create_stundenplan.this, create_stundenplan_obtionen.class);
                startActivity(i);
            }
        });


        Speichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String jsona;
                String jsonb;
                Gson gson = new Gson();
                int länge = MaxStunden*5;

                    jsona = settings.getString("Stundenliste", null);
                    Type type = new TypeToken<ArrayList<Memory_Stunde>>() {}.getType();

                    if(Zweiwöchentlich){
                        Gson gsona = new Gson();
                        jsonb = settings.getString("WocheBStundenListe", null);
                        WocheBStundenListe = gsona.fromJson(jsonb , type);
                        länge = MaxStunden*10;
                    }

                WocheAStundenListe = gson.fromJson(jsona , type);








                Intent i = new Intent(create_stundenplan.this, loading.class);
                i.putExtra("Stufe", Stufe);
                startActivity(i);
            }
        });




        ungrade_woche_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm2 = getSupportFragmentManager();
                FragmentTransaction ft2 = fm2.beginTransaction();
                ft2.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                ft2.show(UngradeWoche);
                ft2.hide(GradeWoche);
                ft2.commitNow();
                Woche.setText("ungrade Woche");
                grade_woche_button.setVisibility(View.VISIBLE);
                ungrade_woche_button.setVisibility(View.GONE);


            }
        });

        grade_woche_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm2 = getSupportFragmentManager();
                FragmentTransaction ft2 = fm2.beginTransaction();
                ft2.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                ft2.hide(UngradeWoche);
                ft2.show(GradeWoche);
                ft2.commitNow();
                Woche.setText("grade Woche");
                grade_woche_button.setVisibility(View.GONE);
                ungrade_woche_button.setVisibility(View.VISIBLE);

            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}
