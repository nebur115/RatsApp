package app.stundenplan.ms.rats.ratsapp;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class fragment_noten extends Fragment {
    private RecyclerView mrecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Object> NotenList = new ArrayList<>();
    private List<Memory_NotenKlausuren> SchnittNotenList = new ArrayList<>();
    private noten_adapter adapter;
    TextView tBestmoeglich;
    TextView tSchnitt;
    TextView tSchlechtmoeglich;
    Boolean shown = false;
    String Stufe;
    ConstraintLayout Stufenwahl;
    ConstraintLayout Q11;
    ConstraintLayout Q12;
    ConstraintLayout Q21;
    ConstraintLayout Q22;

    public fragment_noten() {

    }
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.noten_fragment,container,false);
        Stufenwahl = view.findViewById(R.id.Stufenwahl);

        SharedPreferences setting = this.getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0);

        Stufe = setting.getString("Stufe","");
        linearLayoutManager = new LinearLayoutManager(getActivity());
        mrecyclerView = view.findViewById(R.id.recyclerview_noten);

        String json;
        Gson gson = new Gson();

        json = setting.getString("NotenKlausuren", null);
        Type type = new TypeToken<ArrayList<Memory_NotenKlausuren>>() {}.getType();
        NotenList = gson.fromJson(json , type);
        NotenList.add(0,new noten_placeholder());

        Q11 = view.findViewById(R.id.cQ11);
        Q12 = view.findViewById(R.id.cQ12);
        Q21 = view.findViewById(R.id.cQ21);
        Q22 = view.findViewById(R.id.cQ22);

        if(Stufe.equals("Q1") || Stufe.equals("Q2")){
            Stufenwahl.setVisibility(View.VISIBLE);
        }else{
            Stufenwahl.setVisibility(View.GONE);
        }

        Calendar calendar = Calendar.getInstance();

        int month = calendar.get(Calendar.MONTH);


        if(Stufe.equals("Q1")){
            if(month>2 && month<7){
                Q12.setBackgroundColor(Color.parseColor("#D8D8D8"));
                Q11.setBackgroundColor(Color.TRANSPARENT);
                Q21.setBackgroundColor(Color.TRANSPARENT);
                Q22.setBackgroundColor(Color.TRANSPARENT);
            }else{
                Q11.setBackgroundColor(Color.parseColor("#D8D8D8"));
                Q12.setBackgroundColor(Color.TRANSPARENT);
                Q21.setBackgroundColor(Color.TRANSPARENT);
                Q22.setBackgroundColor(Color.TRANSPARENT);
            }
        }else{
            if(month>2 && month<7){
                Q21.setBackgroundColor(Color.parseColor("#D8D8D8"));
                Q11.setBackgroundColor(Color.TRANSPARENT);
                Q12.setBackgroundColor(Color.TRANSPARENT);
                Q22.setBackgroundColor(Color.TRANSPARENT);
            }else{
                Q22.setBackgroundColor(Color.parseColor("#D8D8D8"));
                Q11.setBackgroundColor(Color.TRANSPARENT);
                Q12.setBackgroundColor(Color.TRANSPARENT);
                Q21.setBackgroundColor(Color.TRANSPARENT);
        }}

        Q11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Q11.setBackgroundColor(Color.parseColor("#D8D8D8"));
                Q12.setBackgroundColor(Color.TRANSPARENT);
                Q21.setBackgroundColor(Color.TRANSPARENT);
                Q22.setBackgroundColor(Color.TRANSPARENT);
            }
        });

        Q12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Q12.setBackgroundColor(Color.parseColor("#D8D8D8"));
                Q11.setBackgroundColor(Color.TRANSPARENT);
                Q21.setBackgroundColor(Color.TRANSPARENT);
                Q22.setBackgroundColor(Color.TRANSPARENT);
            }
        });


        Q21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Q21.setBackgroundColor(Color.parseColor("#D8D8D8"));
                Q11.setBackgroundColor(Color.TRANSPARENT);
                Q12.setBackgroundColor(Color.TRANSPARENT);
                Q22.setBackgroundColor(Color.TRANSPARENT);
            }
        });


        Q22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Q22.setBackgroundColor(Color.parseColor("#D8D8D8"));
                Q11.setBackgroundColor(Color.TRANSPARENT);
                Q12.setBackgroundColor(Color.TRANSPARENT);
                Q21.setBackgroundColor(Color.TRANSPARENT);
            }
        });


        tBestmoeglich = view.findViewById(R.id.bestmoeglich);
        tSchnitt = view.findViewById(R.id.schnitt);
        tSchlechtmoeglich = view.findViewById(R.id.schelchtmoeglich);
        mrecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new noten_adapter(getActivity(),NotenList);
        mrecyclerView.setAdapter(adapter);
        AktualisiereZahlen();

        adapter.notifyDataSetChanged();

        shown = true;

        return view;
    }

    public void AktualisiereZahlen(){

        SharedPreferences setting = this.getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0);
        String json = setting.getString("NotenKlausuren", null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Memory_NotenKlausuren>>() {
        }.getType();
        SchnittNotenList = gson.fromJson(json, type);


        int anzahlNoten = 0;
        int anzahlAll = 0;
        int summeNoten = 0;
        int summeSchlechteste = 0;
        int summeBeste = 0;
        double SchnittPunkte;

        for (int i = 0; i < SchnittNotenList.size(); i++) {
            int Wertung = SchnittNotenList.get(i).getWertung();
            int Note = GesammtNote(SchnittNotenList.get(i).isMuendlichschrifltich(), SchnittNotenList.get(i).getZeugnis(), SchnittNotenList.get(i).getMuendlich1(), SchnittNotenList.get(i).getMuendlich2(), SchnittNotenList.get(i).getSchriftlich1(), SchnittNotenList.get(i).getSchriftlich2());
            int Beste = BestNote(SchnittNotenList.get(i).isMuendlichschrifltich(), SchnittNotenList.get(i).getZeugnis(), SchnittNotenList.get(i).getMuendlich1(), SchnittNotenList.get(i).getMuendlich2(), SchnittNotenList.get(i).getSchriftlich1(), SchnittNotenList.get(i).getSchriftlich2());
            int Schlechteste = SchlechtsteNote(SchnittNotenList.get(i).isMuendlichschrifltich(), SchnittNotenList.get(i).getZeugnis(), SchnittNotenList.get(i).getMuendlich1(), SchnittNotenList.get(i).getMuendlich2(), SchnittNotenList.get(i).getSchriftlich1(), SchnittNotenList.get(i).getSchriftlich2());


            anzahlAll++;
            summeBeste = summeBeste + Beste;
            summeSchlechteste = summeSchlechteste + Schlechteste;

            if (!(Note == 0)) {
                anzahlNoten = anzahlNoten + Wertung;
                summeNoten = summeNoten + (Note * Wertung);
            }

        }

        if (!(anzahlNoten == 0)) {
            String FinalSchnitt;
            String FinalBestmoeglich;
            String FinalSchlechtmoeglich;


            FinalSchnitt = Double.toString(PunktezuNote(summeNoten, anzahlNoten)) + "000";


            FinalBestmoeglich = Double.toString(PunktezuNote(summeBeste, anzahlAll)) + "000";


            FinalSchlechtmoeglich = Double.toString(PunktezuNote(summeSchlechteste, anzahlAll)) + "000";

            tSchnitt.setText(FinalSchnitt.substring(0, 4));

            tBestmoeglich.setText(FinalBestmoeglich.substring(0, 4));

            tSchlechtmoeglich.setText(FinalSchlechtmoeglich.substring(0, 4));

        } else {
            tSchlechtmoeglich.setText("6");
            tBestmoeglich.setText("0,66");
            tSchnitt.setText("N/A");
        }
    }


    public void newnumbers() {
        if (shown) {
            SharedPreferences setting = this.getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0);
            String json;
            Gson gson = new Gson();

            json = setting.getString("NotenKlausuren", null);
            Type type = new TypeToken<ArrayList<Memory_NotenKlausuren>>() {}.getType();
            NotenList = gson.fromJson(json , type);
            NotenList.add(0,new noten_placeholder());
         

            SchnittNotenList = gson.fromJson(json, type);

            AktualisiereZahlen();

        }
    }

    private double PunktezuNote(int summe, int anzahl){
        if(!(summe==0)){
            return (double) ((17.00-((double) summe/ (double) anzahl))/3.00);
        }else{
            return 6;
        }

    }



    private int GesammtNote(boolean pschirftlich, int pZeugnisNote, int pMuendlich1, int pMuendlich2, int pSchriftlich1, int pSchriftlich2) {

        if(!(pZeugnisNote==0)){
            return(pZeugnisNote);
        }else if(!(pMuendlich1+pMuendlich2+pSchriftlich1+pSchriftlich2==0)){
            int MuendlichEingetragen = 0;
            int SchriftlichEingetragen = 0;
            double Muendlich = 0;
            double Schriftlich = 0;
            int alleeingetragen = 0;

            if(!(pMuendlich1==0)){
                MuendlichEingetragen++;
                Muendlich = Muendlich + pMuendlich1-1;
            }

            if(!(pMuendlich2==0)){
                MuendlichEingetragen++;
                Muendlich = Muendlich + pMuendlich2-1;
            }

            if(!(pSchriftlich1==0)){
                SchriftlichEingetragen++;
                Schriftlich = Schriftlich + pSchriftlich1-1;
            }

            if(!(pSchriftlich2==0)){
                SchriftlichEingetragen++;
                Schriftlich = Schriftlich + pSchriftlich2-1;
            }

            if(!(MuendlichEingetragen==0)){
                Muendlich = Muendlich/MuendlichEingetragen;
            }

            if(!(SchriftlichEingetragen==0) && (!pschirftlich)){
                Schriftlich = Schriftlich/SchriftlichEingetragen;
            }



            if(!(SchriftlichEingetragen==0)){
                alleeingetragen++;
            }

            if(!(MuendlichEingetragen==0)){
                alleeingetragen++;
            }
            return((int) (((Muendlich+Schriftlich)/alleeingetragen)));

        }else{
            return (0);
        }

    }

    private int BestNote(boolean pschirftlich, int pZeugnisNote, int pMuendlich1, int pMuendlich2, int pSchriftlich1, int pSchriftlich2) {

        if(!(pZeugnisNote==0)){
            return(pZeugnisNote);
        }else if(!(pMuendlich1+pMuendlich2+pSchriftlich1+pSchriftlich2==0)){

            int Schrifltich1 = 15;
            int Schriftlich2 = 15;
            int Muendlich1 = 15;
            int Muendlich2 = 15;
            double Muendlich;
            double Schriftlich;


            if(!(pMuendlich1==0)){
                Muendlich1 = pMuendlich1-1;
            }

            if(!(pMuendlich2==0)){
                Muendlich2 = pMuendlich2-1;
            }

            if(!(pSchriftlich1==0)){
                Schrifltich1 = pSchriftlich1-1;
            }

            if(!(pSchriftlich2==0)){
                Schriftlich2 = pSchriftlich2-1;
            }


            Muendlich = (Muendlich1+Muendlich2)/2;

            Schriftlich = (Schrifltich1+Schriftlich2)/2;


                if(pschirftlich){
                    return((int) (((Muendlich+Schriftlich)/2)));
                }else{
                    return((int) Muendlich);
                }


        }else{
            return (15);
        }

    }

    private int SchlechtsteNote(boolean pschirftlich, int pZeugnisNote, int pMuendlich1, int pMuendlich2, int pSchriftlich1, int pSchriftlich2) {

        if(!(pZeugnisNote==0)){
            return(pZeugnisNote);
        }else if(!(pMuendlich1+pMuendlich2+pSchriftlich1+pSchriftlich2==0)){

            int Schrifltich1 = 0;
            int Schriftlich2 = 0;
            int Muendlich1 = 0;
            int Muendlich2 = 0;
            double Muendlich;
            double Schriftlich;


            if(!(pMuendlich1==0)){
                Muendlich1 = pMuendlich1-1;
            }

            if(!(pMuendlich2==0)){
                Muendlich2 = pMuendlich2-1;
            }

            if(!(pSchriftlich1==0)){
                Schrifltich1 = pSchriftlich1-1;
            }

            if(!(pSchriftlich2==0)){
                Schriftlich2 = pSchriftlich2-1;
            }


            Muendlich = (Muendlich1+Muendlich2)/2;

            Schriftlich = (Schrifltich1+Schriftlich2)/2;


            if(pschirftlich){
                return((int) (((Muendlich+Schriftlich)/2)));
            }else{
                return((int) Muendlich);
            }


        }else{
            return (0);
        }

    }


}
