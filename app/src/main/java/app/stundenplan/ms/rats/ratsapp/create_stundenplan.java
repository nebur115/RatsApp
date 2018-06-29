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
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by Ruben on 02.04.2018.
 */

public class create_stundenplan extends AppCompatActivity {

    public int shownWeek;
    private boolean Overwrite;
    private List<Memory_Stunde> WocheAStundenListe = new ArrayList<>();
    private List<Memory_Stunde> WocheBStundenListe = new ArrayList<>();
    private List<String> Kursliste = new ArrayList<String>();
    private List<String> FachListe = new ArrayList<String>();
    private List<Memory_NotenKlausuren> OldNotenKlausurenListe = new ArrayList<>();
    private List<Memory_NotenKlausuren> NotenKlausurenListe = new ArrayList<>();
    String Stufe;
    boolean Zweiwöchentlich;
    int MaxStunden;

    public String[] Fächer = {  "Bio",      "Bio Chemie",   "Chemie",   "Deutsch",  "Englisch", "Erdkunde",
            "ev. Religion",     "Französisch",  "Geschichte","Italienisch","Informatik",
            "Informatische Grundbildung", "kath. Religion", "Kunst", "Latein", "Literatur", "Mathematik", "MathePhysikInformatik", "Musik", "Niederländisch",
            "Naturw. AG", "Pädagogik", "Physik", "Politik", "Philosophie", "Praktische Philosophie", "Spanisch", "Sport", "Sozialwissenschaften"};
    public String [] Kürzel = { "BI",       "BI/CH",        "Ch",       "D",        "E5",       "EK",
            "ER",               "F","Ge","I","If","IFGR","KR","Ku","L","Li","M","M/PH/INF","MU","N","NW AG","Pa",
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

        final ImageButton ungrade_woche_button = findViewById(R.id.ungrade_woche_button);
        final ImageButton grade_woche_button = findViewById(R.id.grade_woche_button);
        final TextView Woche = findViewById(R.id.Woche);

        Stufe = settings.getString("Stufe", null);

        Overwrite = settings.contains("NotenKlausuren");
        if(Overwrite){
            String json;
            Gson gson = new Gson();
            json = settings.getString("NotenKlausuren", null);
            Type type = new TypeToken<ArrayList<Memory_NotenKlausuren>>() {}.getType();
            OldNotenKlausurenListe = gson.fromJson(json , type);
        }


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
                Woche.setText("ungerade Woche");
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
                Woche.setText("gerade Woche");
                grade_woche_button.setVisibility(View.GONE);
                ungrade_woche_button.setVisibility(View.VISIBLE);

            }
        });

        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

        ImageButton Speichern = findViewById(R.id.Speichern);

        Speichern.setImageResource(R.drawable.tick);
        ImageButton Einstellungen = findViewById(R.id.einstellungen);


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
                String jsonb = "";
                Gson gson = new Gson();

                jsona = settings.getString("Stundenliste", null);
                Type type = new TypeToken<ArrayList<Memory_Stunde>>() {}.getType();
                WocheAStundenListe = gson.fromJson(jsona , type);
                WocheAStundenListe = WerteWocheAus(WocheAStundenListe);
                jsona = gson.toJson(WocheAStundenListe);

                if(Zweiwöchentlich){
                    Gson gsona = new Gson();
                    jsonb = settings.getString("WocheBStundenListe", null);
                    WocheBStundenListe = gsona.fromJson(jsonb , type);
                    WocheBStundenListe = WerteWocheAus(WocheBStundenListe);
                    jsonb = gson.toJson(WocheBStundenListe);
                }


                // Speichert  Kursliste (Liste (Strings)) in Shared Pref. als HashSet
                SharedPreferences.Editor editor = settings.edit();
                Set<String> Kurse  = new HashSet<String>(Kursliste);
                Set<String> Faecher  = new HashSet<String>(FachListe);
                String json = gson.toJson(NotenKlausurenListe);

                if(!(Stufe.equals("Q1") || Stufe.equals("Q2"))){
                    editor.putString("NotenKlausuren", json);
                }
                if(settings.contains("ManuellmeineKurse")){
                    HashSet<String> meineKurse = new HashSet<String>();
                    editor.putStringSet("ManuellmeineKurse", meineKurse);
                    editor.putStringSet("ManuellNichtMeineKurse", meineKurse);
                }


                editor.putString("Stundenliste", jsona);
                if(Zweiwöchentlich){
                    editor.putString("WocheBStundenListe", jsonb);
                }
                editor.putStringSet("Faecher", Faecher);
                editor.putStringSet("Kursliste", Kurse);
                editor.apply();




                Intent i = new Intent(create_stundenplan.this, Settings.class);
                startActivity(i);
            }
        });





    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(create_stundenplan.this, Settings.class);
        startActivity(i);
    }


    private List<Memory_Stunde> WerteWocheAus(List<Memory_Stunde> pStundenListe){

        for(int i=0; i<pStundenListe.size(); i++){

            if(!pStundenListe.get(i).isFreistunde()) {

                String Kursname = "";
                String Fach = pStundenListe.get(i).getFach();
                int Kursnummer = pStundenListe.get(i).getKursnummer();
                String Kursart = pStundenListe.get(i).getKursart();
                String Startjahr;


                if (Arrays.asList(Fächer).contains(Fach)) {

                    String shortFach = Kürzel[Arrays.asList(Fächer).indexOf(Fach)];

                    if (!(pStundenListe.get(i).getStartJahr() == 0)) {
                        Startjahr = Integer.toString((pStundenListe.get(i).getStartJahr()) % 10);
                    } else {
                        Startjahr = "";
                    }

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
                            case "Ergänzung":
                                Kursname = "ERG " + shortFach;
                                break;
                            default:
                                Kursname = shortFach + Startjahr + " G" + Integer.toString(Kursnummer);
                                break;
                        }
                    } else {
                        if (!(Kursnummer == 0)) {
                            Kursname = shortFach + Startjahr + "-" + Kursnummer;
                        } else {
                            Kursname = shortFach + Startjahr;
                        }
                    }

                } else {
                    Kursname = pStundenListe.get(i).getKürzel();
                }

                pStundenListe.get(i).setKürzel(Kursname.toUpperCase());

                Boolean Existing = false;
                Boolean Vorhanden = false;

                for (int j = 0; j < OldNotenKlausurenListe.size(); j++) {
                    if (OldNotenKlausurenListe.get(j).getFach().equals(Fach)) {
                        Existing = true;
                    }
                }

                    for (int j = 0; j < NotenKlausurenListe.size(); j++){
                    if (NotenKlausurenListe.get(j).getFach().equals(Fach)){
                        Vorhanden = true;
                    }
                }


                if (!Kursliste.contains(Kursname.toUpperCase()) && !Kursname.equals("")) {
                    Kursliste.add(Kursname.toUpperCase());
                    FachListe.add(Fach);
                }


                if (!Vorhanden) {
                    if (!Overwrite && !Existing) {
                        NotenKlausurenListe.add(new Memory_NotenKlausuren(Fach, Kursname, pStundenListe.get(i).isSchriftlich(), 0, 0, 0, 1));
                    } else {

                        int Stelle = -1;

                        for (int j = 0; j < OldNotenKlausurenListe.size(); j++) {

                            if (OldNotenKlausurenListe.get(j).getFach().equals(Fach)) {
                                Stelle = j;

                            }
                        }
                        if (Stelle == -1) {
                            NotenKlausurenListe.add(new Memory_NotenKlausuren(Fach, Kursname, pStundenListe.get(i).isSchriftlich(), 0, 0, 0, 1));
                        } else {
                            NotenKlausurenListe.add(new Memory_NotenKlausuren(Fach, Kursname, pStundenListe.get(i).isSchriftlich(), 0, 0, 0, 1));
                            NotenKlausurenListe.get(NotenKlausurenListe.size() - 1).setMuendlich1(OldNotenKlausurenListe.get(Stelle).getMuendlich1());
                            NotenKlausurenListe.get(NotenKlausurenListe.size() - 1).setMuendlich2(OldNotenKlausurenListe.get(Stelle).getMuendlich2());
                            NotenKlausurenListe.get(NotenKlausurenListe.size() - 1).setSchriftlich1(OldNotenKlausurenListe.get(Stelle).getSchriftlich1());
                            NotenKlausurenListe.get(NotenKlausurenListe.size() - 1).setSchriftlich2(OldNotenKlausurenListe.get(Stelle).getSchriftlich2());
                            NotenKlausurenListe.get(NotenKlausurenListe.size() - 1).setDatum1(OldNotenKlausurenListe.get(Stelle).getDatum1());
                            NotenKlausurenListe.get(NotenKlausurenListe.size() - 1).setDatum2(OldNotenKlausurenListe.get(Stelle).getDatum2());
                            NotenKlausurenListe.get(NotenKlausurenListe.size() - 1).setZeugnis(OldNotenKlausurenListe.get(Stelle).getZeugnis());

                        }

                    }

                }
            }
        }

        return pStundenListe;
    }

}
