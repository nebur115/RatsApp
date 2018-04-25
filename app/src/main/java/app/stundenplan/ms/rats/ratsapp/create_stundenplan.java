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
import java.util.Arrays;
import java.util.List;


/**
 * Created by Ruben on 02.04.2018.
 */

public class create_stundenplan extends AppCompatActivity {

    public int shownWeek;
    private List<Memory_Stunde> WocheAStundenListe = new ArrayList<>();
    private List<Memory_Stunde> WocheBStundenListe = new ArrayList<>();
    private List<String> Kursliste = new ArrayList<String>();
    String Stufe;

    boolean Zweiwöchentlich;
    int MaxStunden;

    public String[] Fächer = {"Bio","Bio Chemie","Deutsch","Englisch","Erdkunde","ev. Religion","Französisch","Geschichte","Italienisch","Informatig",
    "Informatorische Grundbildung", "kath. Religions", "Kunst", "Latein", "Literatur", "Mathe", "MathePhysikInformatik", "Musik", "Niederländisch",
    "Naturw. AG", "Pädagogik", "Physik", "Politik", "Philosophie", "Praktische Philosophie", "Spanisch", "Sport", "Sozialwissenschaften"};
    public String [] Kürzel = {"BI","BI/CH","D","E5","ER","F","Ge","I","If","IFGR","KR","Ku","L","Li","M","M/PH/INF","MU","N","NW AG","Pa",
    "Ph","PK","PL","PP","S","Sp","Sw"};


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

        Stufe = settings.getString("Stufe", null);



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


                jsona = settings.getString("Stundenliste", null);
                Type type = new TypeToken<ArrayList<Memory_Stunde>>() {}.getType();
                WocheAStundenListe = gson.fromJson(jsona , type);
                WerteWocheAus(WocheAStundenListe);

                if(Zweiwöchentlich){
                        Gson gsona = new Gson();
                        jsonb = settings.getString("WocheBStundenListe", null);
                        WocheBStundenListe = gsona.fromJson(jsonb , type);
                        WerteWocheAus(WocheBStundenListe);
                }

                Intent i = new Intent(create_stundenplan.this, loading.class);
                i.putExtra("Stufe", Stufe);
                startActivity(i);
            }
        });





    }

    @Override
    public void onBackPressed() {

    }


    private void WerteWocheAus(List<Memory_Stunde> pStundenListe){

        for(int i=0; i>MaxStunden*5; i++){



            if(!pStundenListe.get(i).isFreistunde()) {

                String Kursname;


                String Fach = pStundenListe.get(i).getFach();



                int Kursnummer = pStundenListe.get(i).getKursnummer();
                String Kursart = pStundenListe.get(i).getKursart();


                String Startjahr;


                if (Arrays.asList(Fächer).contains(Fach)) {

                    String shortFach = Kürzel[Arrays.asList(Fächer).indexOf(Fach)];


                    if (!(pStundenListe.get(i).getStartJahr() == 0)) {
                        Startjahr = Integer.toString(pStundenListe.get(i).getStartJahr()).substring(0,1);
                    } else {
                        Startjahr = "";
                    }


                    String shortKursart;

                    if (Stufe.equals("EF") || Stufe.equals("Q1") || Stufe.equals("Q2")) {
                        switch (Kursart) {
                            case "Grundkurs":
                                Kursname = shortFach + Startjahr + " G" + Integer.toString(Kursnummer);
                                break;
                            case "Leistungskurs":
                                Kursname = shortFach + Startjahr + " L" + Integer.toString(Kursnummer);
                                break;
                            case "Vertiefungsfach":
                                Kursname = "VT " + shortFach + Integer.toString(Kursnummer);
                                break;
                            case "Zusatzkurs":
                                Kursname = shortFach.substring(0, 1) + "Z G" + Integer.toString(Kursnummer);
                                break;
                            case "Ergänzung" :
                                Kursname = "ERG " + shortFach;
                                break;
                            default:
                                Kursname = shortFach + Startjahr + " G" + Integer.toString(Kursnummer);
                                break;
                        }
                    } else {
                        if (!(Kursnummer == 0)) {
                            Kursname = shortFach + Startjahr;
                        } else {
                            Kursname = shortFach + Startjahr + "-" + Kursnummer;
                        }
                    }

                } else {
                    Kursname = WocheAStundenListe.get(i).getKürzel();
                }


                if (!Kursliste.contains(Kursname) && !Kursname.equals("")) {
                    Kursliste.add(Kursname);
                }
            }

        }
    }
}
