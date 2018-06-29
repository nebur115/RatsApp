package app.stundenplan.ms.rats.ratsapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class create_stundenplan_stunden_obtionen  extends AppCompatActivity {


    boolean zweiWöchentlich;
    private List<Memory_Stunde> MemoryStundenListe = new ArrayList<>();
    int pos;
    int Stunde;
    private List<Memory_Stunde> WocheBStundenListe = new ArrayList<>();
    int orgStunde;
    int orgPos;
    Boolean wasDopellstunde = false;
    Boolean wasWöchentlich = false;
    String kursname = "";
    Toast toast;
    String fach;
    String Kursart;
    int Kursnummer;
    String Unterichtbegin;
    String Bewertung;
    String Raum;
    String Lehrer;
    boolean Doppelstunde;
    String Wiederholung;
    String fachkürzel;
    int StartJahr;
    boolean Schriftlich;
    int MaxStunden;
    Spinner KursartenSpinner;
    Spinner WiederholungsSpinner;
    Spinner MündlichSchriftlichSpinner;
    TextView DatumStunde;
    Spinner sRaumSchule;
    Spinner sHalle;
    String Wochentag;
    ConstraintLayout cFachEingabe;
    ConstraintLayout cKursEingabe;
    ConstraintLayout cLehrerEingabe;
    ConstraintLayout cRaumEingabe;
    ConstraintLayout cUnterichtstartEingabe;
    ConstraintLayout cDoppelstundeCheck;
    ConstraintLayout cWiederholungAuswahl;
    ConstraintLayout cMündlichSchriftlichAuswahl;
    ConstraintLayout cHalleEingabe;
    String Stufe;
    List<String> kursarten;
    EditText eKursnummer;
    EditText eUnterichtbegin;
    EditText eRaum;
    CheckBox eDoppelstunde;
    EditText eLehrer;
    EditText eSchule;
    TextView textViewKursnummer;
    TextView textViewNr;

    public String[] Fächer = {  "Bio",  "Bio Chemie",   "Chemie",   "Deutsch",  "Englisch", "Erdkunde", "ev. Religion",
            "Französisch",  "Geschichte",   "Italienisch",  "Informatik",   "Informatische Grundbildung", "kath. Religion", "Kunst", "Latein", "Literatur", "Mathematik", "MathePhysikInformatik", "Musik", "Niederländisch",
            "Pädagogik", "Physik", "Politik", "Philosophie", "Praktische Philosophie", "Spanisch", "Sport", "Sozialwissenschaften"};

    public String [] Kürzel = { "Bio",  "BiCh",         "Ch",       "D",        "E",        "Erd",      "Reli",
            "Fr",           "Ge",           "It",           "Inf",          "Inf","Reli","Ku","La","Li","M","MPhI","Mu","Ni","Päd",
            "Phy","Po","Phil","PP","S","Sp","Sw"};

    int Woche;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences settings = getSharedPreferences("RatsVertretungsPlanApp", 0);


        Intent intent = getIntent();
        Woche = intent.getExtras().getInt("Woche");
        pos = intent.getExtras().getInt("Position");
        orgPos = pos;
        zweiWöchentlich = intent.getExtras().getBoolean("ZweiWöchentlich");
        setContentView(R.layout.create_stundenplan_stunden_obtionen);


        super.onCreate(savedInstanceState);

        textViewKursnummer = findViewById(R.id.textViewKursnummer);
        textViewNr = findViewById(R.id.textViewNr);
        MaxStunden = settings.getInt("MaxStunden",0);
        KursartenSpinner = findViewById(R.id.kursartspinner);
        WiederholungsSpinner = findViewById(R.id.wiederholungspinner);
        MündlichSchriftlichSpinner = findViewById(R.id.MüdnlichSchriftlichspinner);
        DatumStunde = findViewById(R.id.DatumStunde);
        sRaumSchule = findViewById(R.id.RaumSpinner);
        sHalle = findViewById(R.id.HalleSpinner);
        cFachEingabe = findViewById(R.id.FachEingabe);
        cKursEingabe = findViewById(R.id.KursEingabe);
        cLehrerEingabe = findViewById(R.id.LehrerEingabe);
        cRaumEingabe = findViewById(R.id.RaumEingabe);
        cUnterichtstartEingabe = findViewById(R.id.UnterichtstartEingabe);
        cDoppelstundeCheck = findViewById(R.id.DoppelstundeCheck);
        cWiederholungAuswahl = findViewById(R.id.WiederholungAuswahl);
        cMündlichSchriftlichAuswahl = findViewById(R.id.MündlicSchriflichtAuswahl);
        cHalleEingabe = findViewById(R.id.HalleEingabe);
        cUnterichtstartEingabe.setVisibility(View.GONE);
        Stufe = settings.getString("Stufe", "DEFAULT");
        kursarten = new ArrayList<String>();
        kursarten.add("Grundkurs");
        eKursnummer = findViewById(R.id.Kursnummer);
        eUnterichtbegin = findViewById(R.id.Unterichtbegin);
        eRaum = findViewById(R.id.Raum);
        eDoppelstunde = findViewById(R.id.Doppelstunde);
        eLehrer = findViewById(R.id.Lehrer);
        eSchule = findViewById(R.id.Schule);
        boolean hinweis;
        String json;
        Gson gson = new Gson();
        if(Woche==1){
            json = settings.getString("Stundenliste", null);
        }
        else{
            json = settings.getString("WocheBStundenListe", null);
        }



        Type type = new TypeToken<ArrayList<Memory_Stunde>>() {}.getType();
        MemoryStundenListe = gson.fromJson(json , type);


        if(zweiWöchentlich){
            String bjson;
            if(!(Woche==1)){
                bjson = settings.getString("Stundenliste", null);
            }
            else{
                bjson = settings.getString("WocheBStundenListe", null);
            }

            WocheBStundenListe = gson.fromJson(bjson, type);
        }

        Stunde=  ((pos/5)-((pos/5)%1));
        orgStunde = Stunde;

        if(pos>=10 && !MemoryStundenListe.get(pos-5).isFreistunde() && (pos<MaxStunden*5-5)){
            if((Stunde%2==0 || !(MemoryStundenListe.get(pos-5).getFach().equals(MemoryStundenListe.get(pos).getFach()))) && MemoryStundenListe.get(pos-5).getFach().equals(MemoryStundenListe.get(pos-10).getFach())){
                pos = pos-5;
                Stunde = Stunde-1;
            }
        }

        switch (pos%5){
            case 0:
                Wochentag = "Montag";
                break;
            case 1:
                Wochentag = "Dienstag";
                break;
            case 2:
                Wochentag = "Mittwoch";
                break;
            case 3:
                Wochentag = "Donnerstag";
                break;
            case 4:
                Wochentag = "Freitag";
                break;
            default:
                Wochentag = "Error: Modulo 5 >= 5";
        }
        DatumStunde.setText(Wochentag+": "+ Integer.toString(Stunde)+". Stunde");


        if(pos<MaxStunden*5-5){
            if(MemoryStundenListe.get(pos-5).getFach().equals(MemoryStundenListe.get(pos).getFach()) && !MemoryStundenListe.get(pos-5).isFreistunde()){
                eDoppelstunde.setChecked(true);
                wasDopellstunde = true;
                DatumStunde.setText(Wochentag+": "+ Integer.toString(Stunde)+ ". & " + Integer.toString(Stunde+1)+". Stunde");
            }

        }






        if(MemoryStundenListe.get(pos-5).getFach().equals("Französisch") ||MemoryStundenListe.get(pos-5).getFach().equals("Spanisch") || MemoryStundenListe.get(pos-5).getFach().equals("Italiensich") || MemoryStundenListe.get(pos-5).getFach().equals("Niederländisch")){
            cUnterichtstartEingabe.setVisibility(View.VISIBLE);
            eUnterichtbegin.setText(Integer.toString(MemoryStundenListe.get(pos-5).getStartJahr()));
        }
        else{
            cUnterichtstartEingabe.setVisibility(View.GONE);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Fächer);

        final AutoCompleteTextView Fach = findViewById(R.id.FachTextView);

        Fach.setAdapter(adapter);
        Fach.setThreshold(1);


        if(!zweiWöchentlich){
            cWiederholungAuswahl.setVisibility(View.GONE);
        }

        if (!(Stufe.equals("EF") || Stufe.equals("Q1") || Stufe.equals("Q2"))){
            cMündlichSchriftlichAuswahl.setVisibility(View.GONE);
            cKursEingabe.setVisibility(View.GONE);
            if(MemoryStundenListe.get(pos-5).isFreistunde()){
                eDoppelstunde.setChecked(true);
            }

        }
        else if(!(MemoryStundenListe.get(pos-5).getKursnummer()==0)){
            eKursnummer.setText(Integer.toString(MemoryStundenListe.get(pos-5).getKursnummer()));
        }


        if ((Stufe.equals("Q1") || Stufe.equals("Q2"))){
            kursarten.add("Leistungskurs");
            kursarten.add("Projektkurs");
            kursarten.add("Zusatzkurs");
        }

        if(Stufe.equals("EF") || Stufe.equals("Q1")){
            kursarten.add("Vertiefungsfach");
        }


        List<String> Wochenwiederholung = new ArrayList<String>();

        if(zweiWöchentlich){
            if(pos<MaxStunden*5-5){
                if(!(MemoryStundenListe.get(pos-5).isFreistunde()) && ((!eDoppelstunde.isChecked() && MemoryStundenListe.get(pos-5).getFach().equals(WocheBStundenListe.get(pos-5).getFach())) ||(eDoppelstunde.isChecked() && MemoryStundenListe.get(pos-5).getFach().equals(WocheBStundenListe.get(pos-5).getFach())&& MemoryStundenListe.get(pos).getFach().equals(WocheBStundenListe.get(pos).getFach())))){
                    wasWöchentlich = true;
                    Wochenwiederholung.add("Jede Woche");
                    Wochenwiederholung.add("Alle 2 Wochen");
                }else{
                    Wochenwiederholung.add("Alle 2 Wochen");
                    Wochenwiederholung.add("Jede Woche");
                }
            }else{
                Wochenwiederholung.add("Alle 2 Wochen");
                Wochenwiederholung.add("Jede Woche");
            }
        }

        hinweis = false;

        List<String> MündlichSchriftlich = new ArrayList<String>();

        if(!MemoryStundenListe.get(pos-5).isFreistunde()){
            if(MemoryStundenListe.get(pos-5).isSchriftlich()){
                MündlichSchriftlich.add("Schriftlich");
                MündlichSchriftlich.add("Mündlich");
            }
            else {
                MündlichSchriftlich.add("Mündlich");
                MündlichSchriftlich.add("Schriftlich");
            }
        }else {
            hinweis = true;
            MündlichSchriftlich.add("Mündlich");
            MündlichSchriftlich.add("Schriftlich");

        }

        List<String> RaumSchule = new ArrayList<>();

        List<String> Halle = new ArrayList<>();

        if(MemoryStundenListe.get(pos-5).getFach().equals("Sport") && MemoryStundenListe.get(pos-5).getRaum().equals("gr H")){
            Halle.add("große Halle");
            Halle.add("kleine Halle");
        }else{
            Halle.add("kleine Halle");
            Halle.add("große Halle");
        }



        if(!(MemoryStundenListe.get(pos-5).getRaum().equals(""))){
            if(MemoryStundenListe.get(pos-5).getRaum().substring(1).matches("[0-9]+")){
                eRaum.setText(MemoryStundenListe.get(pos-5).getRaum().substring(1));
                RaumSchule.add("Raum");
                RaumSchule.add("Schule");
                eRaum.setVisibility(View.VISIBLE);
                eSchule.setVisibility(View.GONE);

            }else {
                eSchule.setText(MemoryStundenListe.get(pos-5).getRaum());
                RaumSchule.add("Schule");
                RaumSchule.add("Raum");

                eRaum.setVisibility(View.GONE);
                eSchule.setVisibility(View.VISIBLE);
                if(!(MemoryStundenListe.get(pos-5).getRaum().equals(""))){
                    eSchule.setText("Rats");
                }else {
                    eSchule.setText(MemoryStundenListe.get(pos-5).getRaum());
                }

            }



        }
        else{
            RaumSchule.add("Raum");
            RaumSchule.add("Schule");
        }


        eLehrer.setText(MemoryStundenListe.get(pos-5).getLehrer());
        Fach.setText(MemoryStundenListe.get(pos-5).getFach());


        cHalleEingabe.setVisibility(View.GONE);



        if(Fach.getText().toString().equals("Französisch") ||Fach.getText().toString().equals("Spanisch") || Fach.getText().toString().equals("Italiensich") || Fach.getText().toString().equals("Niederländisch") || Fach.getText().toString().equals("Latein")){
            cUnterichtstartEingabe.setVisibility(View.VISIBLE);
        }
        else{
            cUnterichtstartEingabe.setVisibility(View.GONE);
        }

        if(Fach.getText().toString().equals("Sport")){
            cHalleEingabe.setVisibility(View.VISIBLE);
            cRaumEingabe.setVisibility(View.GONE);
        }
        else
        {cHalleEingabe.setVisibility(View.GONE);
            cRaumEingabe.setVisibility(View.VISIBLE);}

        if(!(Stufe.equals("EF") || Stufe.equals("Q1") || Stufe.equals("Q2"))&& (Fach.getText().toString().equals("Bio Chemie") || (Fach.getText().toString().equals("ev. Religion"))|| (Fach.getText().toString().equals("Französisch"))|| (Fach.getText().toString().equals("kath. Religion"))|| (Fach.getText().toString().equals("Latein")) ||(Fach.getText().toString().equals("Mathe Physik Informatik"))|| (Fach.getText().toString().equals("Philosophie"))|| (Fach.getText().toString().equals("Praktische Philosophie")) || (Fach.getText().toString().equals("Spanisch")))){
            cKursEingabe.setVisibility(View.VISIBLE);
            KursartenSpinner.setVisibility(View.GONE);
            textViewNr.setVisibility(View.GONE);
            textViewKursnummer.setVisibility(View.VISIBLE);
        }
        else if (!(Stufe.equals("EF") || Stufe.equals("Q1") || Stufe.equals("Q2"))){
            cKursEingabe.setVisibility(View.GONE);
        }

        ArrayAdapter HalleAdapter =new ArrayAdapter(getBaseContext(),R.layout.spinner_item, Halle);
        HalleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter RaumSchuleAdapter =new ArrayAdapter(getBaseContext(),R.layout.spinner_item, RaumSchule);
        RaumSchuleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter KursartAdapter = new ArrayAdapter(getBaseContext(),R.layout.spinner_item,kursarten);
        KursartAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter MündlichSchrifltlichAdapter = new ArrayAdapter(getBaseContext(),R.layout.spinner_item,MündlichSchriftlich);
        MündlichSchrifltlichAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter WiederholungApapter = new ArrayAdapter(getBaseContext(),R.layout.spinner_item,Wochenwiederholung);
        WiederholungApapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sHalle.setAdapter(HalleAdapter);
        sRaumSchule.setAdapter(RaumSchuleAdapter);
        MündlichSchriftlichSpinner.setAdapter(MündlichSchrifltlichAdapter);
        WiederholungsSpinner.setAdapter(WiederholungApapter);
        KursartenSpinner.setAdapter(KursartAdapter);



        sRaumSchule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (sRaumSchule.getSelectedItem().toString().equals("Schule")){
                    eRaum.setVisibility(View.GONE);
                    eSchule.setVisibility(View.VISIBLE);
                    eSchule.setText("Rats");
                }
                else{
                    eRaum.setVisibility(View.VISIBLE);
                    eSchule.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        eDoppelstunde.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked){
                    DatumStunde.setText(Wochentag+": "+ Integer.toString(Stunde)+ ". & " + Integer.toString(Stunde+1)+". Stunde");
                }
                else
                {
                    DatumStunde.setText(Wochentag+": "+ Integer.toString(orgStunde)+". Stunde");
                }
            }}
        );


        ImageButton delete = findViewById(R.id.delete);
        delete.setImageResource(R.drawable.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(zweiWöchentlich){
                    Wiederholung = WiederholungsSpinner.getSelectedItem().toString();
                }else{
                    Wiederholung = "Alle 2 Wochen";
                }


                boolean Doppelstunde = eDoppelstunde.isChecked();



                SharedPreferences settings = getSharedPreferences("RatsVertretungsPlanApp", 0);
                Gson gson = new Gson();
                String json = settings.getString("Stundenliste", null);

                Type type = new TypeToken<ArrayList<Memory_Stunde>>() {}.getType();
                MemoryStundenListe = gson.fromJson(json , type);


                MemoryStundenListe.set(pos-5, new Memory_Stunde(true,  "", "", "", "", "",0, false,0,""));

                if(Doppelstunde){
                    MemoryStundenListe.set(pos, new Memory_Stunde(true,  "", "", "", "", "",0, false,0,""));
                }

                if(zweiWöchentlich){
                    String bjson = settings.getString("WocheBStundenListe", null);

                    WocheBStundenListe = gson.fromJson(bjson, type);
                    WocheBStundenListe.set(pos-5, new Memory_Stunde(true,  "", "", "", "", "", 0, false, 0,""));


                    if(Doppelstunde){
                        WocheBStundenListe.set(pos, new Memory_Stunde(true,  "", "", "", "", "", 0, false, 0,""));

                    }

                }




                SharedPreferences.Editor editor = settings.edit();
                String jsona = gson.toJson( MemoryStundenListe);

                if(zweiWöchentlich){
                String jsonb = gson.toJson(WocheBStundenListe);



                    if(Wiederholung.equals("Jede Woche")) {
                        editor.putString("Stundenliste", jsona);
                        editor.putString("WocheBStundenListe", jsonb);
                    }
                    else{

                        if(Woche==1){
                            editor.putString("Stundenliste", jsona);
                        }
                        else{
                            editor.putString("WocheBStundenListe",  jsonb);
                        }
                    }

                }else {
                    editor.putString("Stundenliste", jsona);
                }




                editor.apply();
                Intent i = new Intent(create_stundenplan_stunden_obtionen.this, create_stundenplan.class);
                i.putExtra("Woche", Woche);
                startActivity(i);

            }
        });




        ImageButton save = findViewById(R.id.save);
        save.setImageResource(R.drawable.tick);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fach = Fach.getText().toString();
                Kursart = KursartenSpinner.getSelectedItem().toString();
                Unterichtbegin = eUnterichtbegin.getText().toString();
                Bewertung = MündlichSchriftlichSpinner.getSelectedItem().toString();
                Lehrer = eLehrer.getText().toString();
                Doppelstunde = eDoppelstunde.isChecked();
                if(zweiWöchentlich){
                    Wiederholung = WiederholungsSpinner.getSelectedItem().toString();
                }else{
                    Wiederholung = "Alle 2 Wochen";
                }

                StartJahr = Integer.parseInt("0"+eUnterichtbegin.getText().toString());


                Schriftlich = Bewertung.equals("Schriftlich") || (!(Stufe.equals("EF") || Stufe.equals("Q1") || Stufe.equals("Q2")) && (fach.equals("Deutsch") || fach.equals("Englisch") || fach.equals("Mathe") || fach.equals("MathePhysikInformatik") || fach.equals("BioChemie") || fach.equals("Spanisch") || fach.equals("Französisch") || fach.equals("Latein")));

                if(
                        (
                                !(fach.equals("Spanisch") || fach.equals("Französisch") || fach.equals("Italiensich") || fach.equals("Niederländisch") )
                                        ||
                                        ((fach.equals("Spanisch") || fach.equals("Französisch") || fach.equals("Italiensich" ) || fach.equals("Niederländisch") || fach.equals("Latein")&&!(Unterichtbegin=="")))
                        )
                                &&
                                (
                                        !(Bewertung == "Mündl. / Schrift.")
                                )
                                &&
                                (!(fach.equals("")))
                        )

                {

                    if(((!(!(Stufe.equals("EF") || Stufe.equals("Q1") || Stufe.equals("Q2"))&& (Fach.getText().toString().equals("Bio Chemie") || (Fach.getText().toString().equals("ev. Religion"))|| (Fach.getText().toString().equals("Französisch"))|| (Fach.getText().toString().equals("kath. Religion"))|| (Fach.getText().toString().equals("Latein")) ||(Fach.getText().toString().equals("Mathe Physik Informatik"))|| (Fach.getText().toString().equals("Philosophie"))|| (Fach.getText().toString().equals("Praktische Philosophie")) || (Fach.getText().toString().equals("Spanisch"))))) || ((!(Stufe.equals("EF") || Stufe.equals("Q1") || Stufe.equals("Q2"))&& (Fach.getText().toString().equals("Bio Chemie") || (Fach.getText().toString().equals("ev. Religion"))|| (Fach.getText().toString().equals("Französisch"))|| (Fach.getText().toString().equals("kath. Religion"))|| (Fach.getText().toString().equals("Latein")) ||(Fach.getText().toString().equals("Mathe Physik Informatik"))|| (Fach.getText().toString().equals("Philosophie"))|| (Fach.getText().toString().equals("Praktische Philosophie")) || (Fach.getText().toString().equals("Spanisch")))) && !(eKursnummer.getText().toString().equals(""))))
                            &&
                            (!(Stufe.equals("EF") || Stufe.equals("Q1") || Stufe.equals("Q2")) || ((Stufe.equals("EF") || Stufe.equals("Q1") || Stufe.equals("Q2")) && !(eKursnummer.getText().toString().equals(""))))) {


                        kursname = (MemoryStundenListe.get(pos - 5).getKürzel());

                        if (!Arrays.asList(Fächer).contains(fach) && (kursname == null || kursname.equals(""))) {

                            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(create_stundenplan_stunden_obtionen.this);
                            View mView = getLayoutInflater().inflate(R.layout.create_stundenplan_fachnichtbekannt_alert, null);
                            final EditText Kursname = mView.findViewById(R.id.Kursname);
                            Button Okay = mView.findViewById(R.id.Button);
                            mBuilder.setView(mView);
                            final AlertDialog dialog = mBuilder.create();
                            Okay.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (!Kursname.getText().toString().equals("")) {
                                        kursname = Kursname.getText().toString();
                                    }
                                    dialog.dismiss();
                                    DatenSpeichern();

                                }
                            });
                            dialog.show();
                        } else {
                            DatenSpeichern();
                        }
                    }
                    else{
                        AlertDialog alertDialog = new AlertDialog.Builder(create_stundenplan_stunden_obtionen.this).create();
                        alertDialog.setTitle("Achtung!");
                        alertDialog.setMessage("Du hast keine Kursnummer eingegeben. Dieses Fach wird deshalb nicht im Vertretungsplan unter 'Meine Kurse' angezeigt");
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Zurück", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ignorieren", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                eKursnummer.setText("0");
                                DatenSpeichern();
                            }
                        });
                        alertDialog.show();
                    }

                }
                else
                {
                    toast = Toast.makeText(create_stundenplan_stunden_obtionen.this, "Du hast nicht alle Pflichtfelder ausgefüllt", Toast.LENGTH_SHORT);
                    toast.show();
                }



            }
        });



        Fach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(Fach.getText().toString().equals("Französisch") ||Fach.getText().toString().equals("Spanisch") || Fach.getText().toString().equals("Italiensich") || Fach.getText().toString().equals("Niederländisch") || Fach.getText().toString().equals("Latein")){
                    cUnterichtstartEingabe.setVisibility(View.VISIBLE);
                }
                else{
                    cUnterichtstartEingabe.setVisibility(View.GONE);
                }


                if((!(Stufe.equals("EF") || Stufe.equals("Q1") || Stufe.equals("Q2"))&& (Fach.getText().toString().equals("Bio Chemie") || (Fach.getText().toString().equals("ev. Religion"))|| (Fach.getText().toString().equals("Französisch"))|| (Fach.getText().toString().equals("kath. Religion"))|| (Fach.getText().toString().equals("Latein")) ||(Fach.getText().toString().equals("Mathe Physik Informatik"))|| (Fach.getText().toString().equals("Philosophie"))|| (Fach.getText().toString().equals("Praktische Philosophie")) || (Fach.getText().toString().equals("Spanisch"))))){
                    cKursEingabe.setVisibility(View.VISIBLE);
                    KursartenSpinner.setVisibility(View.GONE);
                    textViewKursnummer.setVisibility(View.VISIBLE);
                }

                if(Fach.getText().toString().equals("Sport")){
                    cHalleEingabe.setVisibility(View.VISIBLE);
                    cRaumEingabe.setVisibility(View.GONE);
                }
                else
                {cHalleEingabe.setVisibility(View.GONE);
                    cRaumEingabe.setVisibility(View.VISIBLE);}

                if(!(Stufe.equals("EF") || Stufe.equals("Q1") || Stufe.equals("Q2"))&& (Fach.getText().toString().equals("Bio Chemie") || (Fach.getText().toString().equals("ev. Religion"))|| (Fach.getText().toString().equals("Französisch"))|| (Fach.getText().toString().equals("kath. Religion"))|| (Fach.getText().toString().equals("Latein")) ||(Fach.getText().toString().equals("Mathe Physik Informatik"))|| (Fach.getText().toString().equals("Philosophie"))|| (Fach.getText().toString().equals("Praktische Philosophie")) || (Fach.getText().toString().equals("Spanisch")))){
                    cKursEingabe.setVisibility(View.VISIBLE);
                    KursartenSpinner.setVisibility(View.GONE);
                    textViewNr.setVisibility(View.GONE);
                    textViewKursnummer.setVisibility(View.VISIBLE);
                }
                else if (!(Stufe.equals("EF") || Stufe.equals("Q1") || Stufe.equals("Q2"))){
                    cKursEingabe.setVisibility(View.GONE);
                }

            }

        });

    }









    private void DatenSpeichern(){
        if(sRaumSchule.getSelectedItem().toString().equals("Schule")){
            Raum = eSchule.getText().toString();
        }
        else{
            Raum = eRaum.getText().toString();
            if(!(Raum.equals(""))){
                Raum="R"+Raum;
            }
        }



        if ((Stufe.equals("EF") || Stufe.equals("Q1") || Stufe.equals("Q2"))){
            Kursnummer = Integer.parseInt(eKursnummer.getText().toString());
        }
        else
        {
            Kursnummer = 0;
        }

        SharedPreferences settings = getSharedPreferences("RatsVertretungsPlanApp", 0);
        Gson gson = new Gson();
        String json = settings.getString("Stundenliste", null);
        Type type = new TypeToken<ArrayList<Memory_Stunde>>() {}.getType();


        if(zweiWöchentlich){
            String bjson = settings.getString("WocheBStundenListe", null);
            WocheBStundenListe = gson.fromJson(bjson, type);
        }



        MemoryStundenListe = gson.fromJson(json , type);


        if(fach.equals("Sport")){
            if(sHalle.getSelectedItem().toString().equals("kleine Halle")) {
                Raum = "kl. Hl.";
            }
            else{
                Raum = "gr. Hl.";
            }
        }

        String fachkürzel;

        if(Arrays.asList(Fächer).contains(fach)){
            fachkürzel = Kürzel[Arrays.asList(Fächer).indexOf(fach)];
        }else{
            fachkürzel = fach.substring(0,2);
        }

        SharedPreferences.Editor editor = settings.edit();

        if(Wiederholung=="Jede Woche") {

            if(!Doppelstunde && wasDopellstunde) {
                if (pos == orgPos) {
                    MemoryStundenListe.set(pos, new Memory_Stunde(true, "", "", "", "", "", 0, false, 0, null));
                    WocheBStundenListe.set(pos, new Memory_Stunde(true, "", "", "", "", "", 0, false, 0, null));
                } else if (pos < orgPos) {
                    MemoryStundenListe.set(pos - 5, new Memory_Stunde(true, "", "", "", "", "", 0, false, 0, null));
                    WocheBStundenListe.set(pos - 5, new Memory_Stunde(true, "", "", "", "", "", 0, false, 0, null));
                    pos = pos + 5;
                }
            }


            MemoryStundenListe.set(pos-5, new Memory_Stunde(false, fach, fachkürzel,  Lehrer, Raum,Kursart,Kursnummer, Schriftlich,StartJahr, kursname));
            WocheBStundenListe.set(pos-5, new Memory_Stunde(false, fach, fachkürzel,  Lehrer, Raum,Kursart,Kursnummer, Schriftlich,StartJahr,kursname));

            if(Doppelstunde){
                MemoryStundenListe.set(pos,  new Memory_Stunde(false, fach, fachkürzel,  Lehrer, Raum, Kursart,Kursnummer, Schriftlich,StartJahr,kursname));
                WocheBStundenListe.set(pos,  new Memory_Stunde(false, fach, fachkürzel,  Lehrer, Raum, Kursart,Kursnummer, Schriftlich,StartJahr,kursname));
            }

            String jsonA = gson.toJson( MemoryStundenListe);
            String jsonB = gson.toJson(WocheBStundenListe);

            editor.putString("Stundenliste", jsonA);
            editor.putString("WocheBStundenListe", jsonB);
        }
        else{

            if(Woche==1){

                if(wasWöchentlich){
                    WocheBStundenListe.set(pos-5, new Memory_Stunde(true,  "", "", "", "", "",0, false,0,null));
                    if(Doppelstunde){
                        WocheBStundenListe.set(pos, new Memory_Stunde(true,  "", "", "", "", "",0, false,0,null));
                    }
                }

                if(!Doppelstunde && wasDopellstunde){
                    if(pos==orgPos){
                        MemoryStundenListe.set(pos, new Memory_Stunde(true,  "", "", "", "", "",0, false,0,null));
                    }
                    else if(pos<orgPos){
                        MemoryStundenListe.set(pos-5, new Memory_Stunde(true,  "", "", "", "", "",0, false,0,null));
                        pos = pos+5;
                    }

                }
                MemoryStundenListe.set(pos-5, new Memory_Stunde(false, fach, fachkürzel,  Lehrer, Raum,Kursart,Kursnummer, Schriftlich,StartJahr,kursname));

                if(Doppelstunde){
                    MemoryStundenListe.set(pos,  new Memory_Stunde(false, fach, fachkürzel,  Lehrer, Raum, Kursart,Kursnummer, Schriftlich,StartJahr,kursname));
                }
            }
            else{

                if(wasWöchentlich){
                    MemoryStundenListe.set(pos-5, new Memory_Stunde(true,  "", "", "", "", "",0, false,0,null));
                    if(Doppelstunde){
                        MemoryStundenListe.set(pos, new Memory_Stunde(true,  "", "", "", "", "",0, false,0,null));
                    }
                }

                if(!Doppelstunde && wasDopellstunde){
                    if(pos==orgPos){
                        WocheBStundenListe.set(pos, new Memory_Stunde(true,  "", "", "", "", "",0, false,0,null));
                    }
                    else if(pos<orgPos){
                        WocheBStundenListe.set(pos-5, new Memory_Stunde(true,  "", "", "", "", "",0, false,0,null));
                        pos = pos+5;
                    }

                }
                if(Doppelstunde){
                    WocheBStundenListe.set(pos,  new Memory_Stunde(false, fach, fachkürzel,  Lehrer, Raum, Kursart,Kursnummer, Schriftlich, StartJahr,kursname));
                }

                WocheBStundenListe.set(pos-5, new Memory_Stunde(false, fach, fachkürzel,  Lehrer, Raum,Kursart,Kursnummer, Schriftlich, StartJahr,kursname));

            }

            String jsonA = gson.toJson(MemoryStundenListe);
            editor.putString("Stundenliste", jsonA);
            String jsonB = gson.toJson(WocheBStundenListe);
            editor.putString("WocheBStundenListe", jsonB);

        }

        editor.apply();
        Intent i = new Intent(create_stundenplan_stunden_obtionen.this, create_stundenplan.class);
        i.putExtra("Woche", Woche);
        i.putExtra("Stufe", settings.getString("Stufe", null));
        startActivity(i);
    }
}



