package app.stundenplan.ms.rats.ratsapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import java.util.List;




public class create_stundenplan_stunden_obtionen  extends AppCompatActivity {

    private List<Memory_Stunde> MemoryStundenListe = new ArrayList<>();
    int pos;
    int Stunde;
    private List<Memory_Stunde> WocheBStundenListe = new ArrayList<>();

    Toast toast = null;
    private static  final String[] Fächer = {
            "MathePhysikInformatik", "BioChemie", "Deutsch", "Englisch", "Französisch", "Latein", "Spanisch", "Italiensich", "Niederländisch", "Kunst", "Musik", "Literatur", "Geschichte", "Sozialwissenschaften", "Erdkunde", "Philosophie", "Pädagogik", "Religion (Ev.)","Religion (Kath.)", "Mathe", "Biologie", "Physik", "Chemie", "Informatik", "Sport", "Politik"
    };

    int Woche;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences settings = getSharedPreferences("RatsVertretungsPlanApp", 0);


        Intent intent = getIntent();
        Woche = intent.getExtras().getInt("Woche");
        pos = intent.getExtras().getInt("Position");
        final boolean zweiWöchentlich = intent.getExtras().getBoolean("ZweiWöchentlich");
        setContentView(R.layout.create_stundenplan_stunden_obtionen);


        super.onCreate(savedInstanceState);

        final   int MaxStunden = settings.getInt("MaxStunden",0);
        final Spinner KursartenSpinner = (Spinner) findViewById(R.id.kursartspinner);
        final Spinner WiederholungsSpinner = (Spinner) findViewById(R.id.wiederholungspinner) ;
        final Spinner MündlichSchriftlichSpinner = (Spinner) findViewById(R.id.MüdnlichSchriftlichspinner) ;
        final TextView DatumStunde = (TextView) findViewById(R.id.DatumStunde);
        final Spinner sRaumSchule = (Spinner) findViewById(R.id.RaumSpinner);
        final String Wochentag;
        final Spinner sHalle = (Spinner) findViewById(R.id.HalleSpinner);
        ConstraintLayout cFachEingabe = findViewById(R.id.FachEingabe);
        ConstraintLayout cKursEingabe = findViewById(R.id.KursEingabe);
        ConstraintLayout cLehrerEingabe = findViewById(R.id.LehrerEingabe);
        final ConstraintLayout cRaumEingabe = findViewById(R.id.RaumEingabe);
        final ConstraintLayout cUnterichtstartEingabe = findViewById(R.id.UnterichtstartEingabe);
        ConstraintLayout cDoppelstundeCheck = findViewById(R.id.DoppelstundeCheck);
        ConstraintLayout cWiederholungAuswahl = findViewById(R.id.WiederholungAuswahl);
        ConstraintLayout cMündlichSchriftlichAuswahl = findViewById(R.id.MündlicSchriflichtAuswahl);
        final ConstraintLayout cHalleEingabe = findViewById(R.id.HalleEingabe);
        cUnterichtstartEingabe.setVisibility(View.GONE);
        final String Stufe = settings.getString("Stufe", "DEFAULT");
        final List<String> kursarten = new ArrayList<String>();
        kursarten.add("Grundkurs");
        final EditText eKursnummer = findViewById(R.id.Kursnummer);
        final EditText eUnterichtbegin = findViewById(R.id.Unterichtbegin);
        final EditText eRaum = findViewById(R.id.Raum);
        final CheckBox eDoppelstunde = (CheckBox) findViewById(R.id.Doppelstunde);
        final EditText eLehrer = findViewById(R.id.Lehrer);
        final EditText eSchule = findViewById(R.id.Schule);
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
        DatumStunde.setText(Wochentag+": "+ Integer.toString(Stunde)+" Stunde");


        if(pos<MaxStunden*5-5){
            if(MemoryStundenListe.get(pos-5).getFach().equals(MemoryStundenListe.get(pos).getFach()) && !MemoryStundenListe.get(pos-5).isFreistunde()){
                eDoppelstunde.setChecked(true);
                DatumStunde.setText(Wochentag+": "+ Integer.toString(Stunde)+ " & " + Integer.toString(Stunde+1)+" Stunde");
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

        final AutoCompleteTextView Fach = (AutoCompleteTextView) findViewById(R.id.FachTextView);

        Fach.setAdapter(adapter);
        Fach.setThreshold(1);


        if(!zweiWöchentlich){
            cWiederholungAuswahl.setVisibility(View.GONE);
        }

        if (!(Stufe.equals("EF") || Stufe.equals("Q1") || Stufe.equals("Q2"))){
            cMündlichSchriftlichAuswahl.setVisibility(View.GONE);
            cKursEingabe.setVisibility(View.GONE);


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

        if(pos<MaxStunden*5-5)
        if(!(MemoryStundenListe.get(pos-5).isFreistunde()) && ((!eDoppelstunde.isChecked() && MemoryStundenListe.get(pos-5).getFach().equals(WocheBStundenListe.get(pos-5).getFach())) ||(eDoppelstunde.isChecked() && MemoryStundenListe.get(pos-5).getFach().equals(WocheBStundenListe.get(pos-5).getFach())&& MemoryStundenListe.get(pos).getFach().equals(WocheBStundenListe.get(pos).getFach())))){
            Wochenwiederholung.add("Jede Woche");
            Wochenwiederholung.add("Alle 2 Wochen");
        }else{
            Wochenwiederholung.add("Alle 2 Wochen");
            Wochenwiederholung.add("Jede Woche");
        }


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
            MündlichSchriftlich.add("Mündl. / Schrift.");
            MündlichSchriftlich.add("Schriftlich");
            MündlichSchriftlich.add("Mündlich");
        }

        List<String> RaumSchule = new ArrayList<>();

        List<String> Halle = new ArrayList<>();
        Halle.add("kleine Halle");
        Halle.add("große Halle");


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

            eLehrer.setText(MemoryStundenListe.get(pos-5).getLehrer());
            Fach.setText(MemoryStundenListe.get(pos-5).getFach());

        }
        else{
            RaumSchule.add("Raum");
            RaumSchule.add("Schule");
        }



        cHalleEingabe.setVisibility(View.GONE);

        ArrayAdapter HalleAdapter =new ArrayAdapter(getBaseContext(),R.layout.spinner_item, Halle);
        HalleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter RaumSchuleAdapter =new ArrayAdapter(getBaseContext(),R.layout.spinner_item, RaumSchule);
        RaumSchuleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter KursartAdapter = new ArrayAdapter(getBaseContext(),R.layout.spinner_item,kursarten);
        KursartAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter MündlichSchrifltlichAdapter = new ArrayAdapter(getBaseContext(),R.layout.spinner_item,MündlichSchriftlich);
        MündlichSchrifltlichAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        ArrayAdapter WiederholungApapter = new ArrayAdapter(getBaseContext(),R.layout.spinner_item,Wochenwiederholung);
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
                    DatumStunde.setText(Wochentag+": "+ Integer.toString(Stunde)+ " & " + Integer.toString(Stunde+1)+" Stunde");
                }
                else
                {
                    DatumStunde.setText(Wochentag+": "+ Integer.toString(Stunde)+" Stunde");
                }
            }}
        );



        ImageButton delete = findViewById(R.id.delete);
        delete.setImageResource(R.drawable.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean Doppelstunde = eDoppelstunde.isChecked();
                String Wiederholung = WiederholungsSpinner.getSelectedItem().toString();


                SharedPreferences settings = getSharedPreferences("RatsVertretungsPlanApp", 0);
                Gson gson = new Gson();
                String json = settings.getString("Stundenliste", null);
                Type type = new TypeToken<ArrayList<Memory_Stunde>>() {}.getType();
                MemoryStundenListe = gson.fromJson(json , type);


                MemoryStundenListe.set(pos-5, new Memory_Stunde(true,  "", "", "", "", "", 0, false, 0));

                if(Doppelstunde){
                    MemoryStundenListe.set(pos, new Memory_Stunde(true,  "", "", "", "", "",0, false,0));
                }
                SharedPreferences.Editor editor = settings.edit();
                String json2 = gson.toJson( MemoryStundenListe);




                if(Wiederholung=="Jede Woche") {
                    editor.putString("Stundenliste", json2);
                    editor.putString("WocheBStundenListe", json2);
                }
                else{

                    if(Woche==1){
                        editor.putString("Stundenliste", json2);
                    }
                    else{
                        editor.putString("WocheBStundenListe", json2);
                    }
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
               String fach = Fach.getText().toString();
               String Kursart = KursartenSpinner.getSelectedItem().toString();
               int Kursnummer;
               String Unterichtbegin = eUnterichtbegin.getText().toString();
               String Bewertung = MündlichSchriftlichSpinner.getSelectedItem().toString();
               String Raum;
               String Lehrer = eLehrer.getText().toString();
               boolean Doppelstunde = eDoppelstunde.isChecked();
               String Wiederholung = WiederholungsSpinner.getSelectedItem().toString();
               String fachkürzel;
               int StartJahr = Integer.parseInt("0"+eUnterichtbegin.getText().toString());
               boolean Schriftlich = false;

               if(sRaumSchule.getSelectedItem().toString().equals("Schule")){
                   Raum = eSchule.getText().toString();
               }
               else{
                   Raum = eRaum.getText().toString();
                   if(!(Raum.equals(""))){
                       Raum="R"+Raum;
                   }
               }


               if(Bewertung == "Schriftlich" || (!(Stufe.equals("EF") || Stufe.equals("Q1") || Stufe.equals("Q2")) && (fach.equals("Deutsch") || fach.equals("Englisch") || fach.equals("Mathe") || fach.equals("MathePhysikInformatik") || fach.equals("BioChemie") || fach.equals("Spanisch") || fach.equals("Französisch") || fach.equals("Latein")))){
                   Schriftlich=true;
               }

               if(
                            (
                                     !(fach=="Spanisch" || fach=="Französisch" || fach=="Italiensich" || fach=="Niederländisch" )
                               ||
                                     ((fach=="Spanisch" || fach=="Französisch" || fach=="Italiensich" || fach=="Niederländisch" )&&!(Unterichtbegin==""))
                            )
                       &&
                            (
                                    !(Stufe.equals("EF") || Stufe.equals("Q1") || Stufe.equals("Q2"))
                               ||
                                    ((Stufe.equals("EF") || Stufe.equals("Q1") || Stufe.equals("Q2")) && !(eKursnummer.getText().toString().equals("")) && !(Bewertung == "Mündl. / Schrift."))
                            )
                       &&
                            !(fach=="")
                  )

               {

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

                   switch (fach){
                       case "Deutsch":
                           fachkürzel = "D";
                           break;
                       case "Englisch":
                           fachkürzel = "E";
                           break;
                       case "Französisch":
                           fachkürzel = "Fr";
                           break;
                       case "Latein":
                           fachkürzel = "La";
                           break;
                       case "Spanisch":
                           fachkürzel = "Spa";
                           break;
                       case "Italiensich":
                           fachkürzel = "It";
                           break;
                       case "Niederländisch":
                           fachkürzel = "Ni";
                           break;
                       case "Kunst":
                           fachkürzel = "Ku";
                           break;
                       case "Musik":
                           fachkürzel = "Mu";
                           break;
                       case "Literatur":
                           fachkürzel = "Li";
                           break;
                       case "Geschichte":
                           fachkürzel = "Ge";
                           break;
                       case "Sozialwissenschaften":
                           fachkürzel = "Sow";
                           break;
                       case "Erdkunde":
                           fachkürzel = "Erd";
                           break;
                       case "Philosophie":
                           fachkürzel = "Phi";
                           break;
                       case "Pädagogik":
                           fachkürzel = "Pä";
                           break;
                       case "Religion (Ev.)":
                           fachkürzel = "Re";
                           break;
                       case"Religion (Kath.)":
                           fachkürzel = "Re";
                           break;
                       case "Mathe":
                           fachkürzel = "M";
                           break;
                       case "Biologie":
                           fachkürzel = "Bi";
                           break;
                       case "Physik":
                           fachkürzel = "Ph";
                           break;
                       case "Chemie":
                           fachkürzel = "Ch";
                           break;
                       case "Informatik":
                           fachkürzel = "If";
                           break;
                       case "Sport":
                           fachkürzel = "Sp";
                           break;
                       case "Politik":
                           fachkürzel = "Pol";
                           break;
                       default:
                           fachkürzel = "non";
                           break;


                   }



                   SharedPreferences.Editor editor = settings.edit();

                   if(Wiederholung=="Jede Woche") {
                       MemoryStundenListe.set(pos-5, new Memory_Stunde(false, fach, fachkürzel,  Lehrer, Raum,Kursart,Kursnummer, Schriftlich,StartJahr));
                       WocheBStundenListe.set(pos-5, new Memory_Stunde(false, fach, fachkürzel,  Lehrer, Raum,Kursart,Kursnummer, Schriftlich,StartJahr));

                       if(Doppelstunde){
                           MemoryStundenListe.set(pos,  new Memory_Stunde(false, fach, fachkürzel,  Lehrer, Raum, Kursart,Kursnummer, Schriftlich,StartJahr));
                           WocheBStundenListe.set(pos,  new Memory_Stunde(false, fach, fachkürzel,  Lehrer, Raum, Kursart,Kursnummer, Schriftlich,StartJahr));
                       }

                       String jsonA = gson.toJson( MemoryStundenListe);
                       String jsonB = gson.toJson(WocheBStundenListe);

                       editor.putString("Stundenliste", jsonA);
                       editor.putString("WocheBStundenListe", jsonB);
                   }
                   else{

                       if(Woche==1){
                           MemoryStundenListe.set(pos-5, new Memory_Stunde(false, fach, fachkürzel,  Lehrer, Raum,Kursart,Kursnummer, Schriftlich,StartJahr));

                           if(Doppelstunde){
                               MemoryStundenListe.set(pos,  new Memory_Stunde(false, fach, fachkürzel,  Lehrer, Raum, Kursart,Kursnummer, Schriftlich,StartJahr));
                           }

                           String jsonA = gson.toJson( MemoryStundenListe);
                           editor.putString("Stundenliste", jsonA);
                       }
                       else{

                           if(Doppelstunde){
                               WocheBStundenListe.set(pos,  new Memory_Stunde(false, fach, fachkürzel,  Lehrer, Raum, Kursart,Kursnummer, Schriftlich, StartJahr));
                           }

                           WocheBStundenListe.set(pos-5, new Memory_Stunde(false, fach, fachkürzel,  Lehrer, Raum,Kursart,Kursnummer, Schriftlich, StartJahr));
                           String jsonB = gson.toJson(WocheBStundenListe);
                           editor.putString("WocheBStundenListe", jsonB);
                       }
                   }

                   editor.apply();
                   Intent i = new Intent(create_stundenplan_stunden_obtionen.this, create_stundenplan.class);
                   i.putExtra("Woche", Woche);
                   i.putExtra("Stufe", settings.getString("Stufe", null));
                   startActivity(i);
               }
               else
               {
                      toast = Toast.makeText(create_stundenplan_stunden_obtionen.this, "Du hast nicht alle Pflichtfleder ausgefüllt"+fach, Toast.LENGTH_SHORT);
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
               
                if(Fach.getText().toString().equals("Französisch") ||Fach.getText().toString().equals("Spanisch") || Fach.getText().toString().equals("Italiensich") || Fach.getText().toString().equals("Niederländisch")){
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
            }
        });

    }


}
