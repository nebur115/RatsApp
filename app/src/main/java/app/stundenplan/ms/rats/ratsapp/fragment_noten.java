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
    SharedPreferences setting;
    Type type;
    String StufeHalbjahr;

    public fragment_noten() {
    }


    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.noten_fragment,container,false);
        Stufenwahl = view.findViewById(R.id.Stufenwahl);

        setting = this.getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0);

        Stufe = setting.getString("Stufe","");
        linearLayoutManager = new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        mrecyclerView = view.findViewById(R.id.recyclerview_noten);

        String json;
        Gson gson = new Gson();



        type = new TypeToken<ArrayList<Memory_NotenKlausuren>>() {}.getType();


        Q11 = view.findViewById(R.id.cQ11);
        Q12 = view.findViewById(R.id.cQ12);
        Q21 = view.findViewById(R.id.cQ21);
        Q22 = view.findViewById(R.id.cQ22);

        if(Stufe.equals("Q1") || Stufe.equals("Q2")){

        Stufenwahl.setVisibility(View.VISIBLE);

        Calendar calendar = Calendar.getInstance();

        int month = calendar.get(Calendar.MONTH);


        if(Stufe.equals("Q1")){
            if(month>2 && month<7){
                StufeHalbjahr = "Q12";
                SharedPreferences.Editor editor = setting.edit();
                editor.putString("ShownHalbjahr", StufeHalbjahr);
                editor.apply();
                Q12.setBackgroundColor(Color.parseColor("#D8D8D8"));
                Q11.setBackgroundColor(Color.TRANSPARENT);
                Q21.setBackgroundColor(Color.TRANSPARENT);
                Q22.setBackgroundColor(Color.TRANSPARENT);
                json = setting.getString("NotenKlausurenQ11", null);
                NotenList = gson.fromJson(json , type);
                NotenList.add(0,new noten_placeholder());

            }else{
                StufeHalbjahr = "Q11";
                SharedPreferences.Editor editor = setting.edit();
                editor.putString("ShownHalbjahr", StufeHalbjahr);
                editor.apply();
                Q11.setBackgroundColor(Color.parseColor("#D8D8D8"));
                Q12.setBackgroundColor(Color.TRANSPARENT);
                Q21.setBackgroundColor(Color.TRANSPARENT);
                Q22.setBackgroundColor(Color.TRANSPARENT);
                json = setting.getString("NotenKlausurenQ12", null);
                NotenList = gson.fromJson(json , type);
                NotenList.add(0,new noten_placeholder());

            }
        }else{
            if(month>2 && month<7){
                StufeHalbjahr = "Q22";
                SharedPreferences.Editor editor = setting.edit();
                editor.putString("ShownHalbjahr", StufeHalbjahr);
                editor.apply();
                Q22.setBackgroundColor(Color.parseColor("#D8D8D8"));
                Q11.setBackgroundColor(Color.TRANSPARENT);
                Q12.setBackgroundColor(Color.TRANSPARENT);
                Q21.setBackgroundColor(Color.TRANSPARENT);
                json = setting.getString("NotenKlausurenQ21", null);
                NotenList = gson.fromJson(json , type);
                NotenList.add(0,new noten_placeholder());

            }else{
                StufeHalbjahr = "Q21";
                SharedPreferences.Editor editor = setting.edit();
                editor.putString("ShownHalbjahr", StufeHalbjahr);
                editor.apply();
                Q21.setBackgroundColor(Color.parseColor("#D8D8D8"));
                Q11.setBackgroundColor(Color.TRANSPARENT);
                Q12.setBackgroundColor(Color.TRANSPARENT);
                Q22.setBackgroundColor(Color.TRANSPARENT);
                json = setting.getString("NotenKlausurenQ22", null);
                NotenList = gson.fromJson(json , type);
                NotenList.add(0,new noten_placeholder());

        }}



        }else{
            Stufenwahl.setVisibility(View.GONE);
            json = setting.getString("NotenKlausuren", null);
            NotenList = gson.fromJson(json , type);
            NotenList.add(0,new noten_placeholder());
            StufeHalbjahr = "---";
        }

        tBestmoeglich = view.findViewById(R.id.bestmoeglich);
        tSchnitt = view.findViewById(R.id.schnitt);
        tSchlechtmoeglich = view.findViewById(R.id.schelchtmoeglich);
        mrecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new noten_adapter(getActivity(),NotenList);
        mrecyclerView.setAdapter(adapter);
        AktualisiereZahlen();
        adapter.notifyDataSetChanged();



        Q11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Object> NotenList11 = new ArrayList<>();
                Q11.setBackgroundColor(Color.parseColor("#D8D8D8"));
                Q12.setBackgroundColor(Color.TRANSPARENT);
                Q21.setBackgroundColor(Color.TRANSPARENT);
                Q22.setBackgroundColor(Color.TRANSPARENT);
                Gson gson = new Gson();
                setting = getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0);
                String json11 = setting.getString("NotenKlausurenQ11", null);
                NotenList11 = gson.fromJson(json11 , type);
                NotenList11.add(0,new noten_placeholder());
                adapter = new noten_adapter(getActivity(),NotenList11);
                mrecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                StufeHalbjahr = "Q11";

                SharedPreferences.Editor editor = setting.edit();
                editor.putString("ShownHalbjahr", StufeHalbjahr);
                editor.apply();
            }
        });

        Q12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Object> NotenList12 = new ArrayList<>();
                Q12.setBackgroundColor(Color.parseColor("#D8D8D8"));
                Q11.setBackgroundColor(Color.TRANSPARENT);
                Q21.setBackgroundColor(Color.TRANSPARENT);
                Q22.setBackgroundColor(Color.TRANSPARENT);
                Gson gson = new Gson();
                setting = getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0);
                String json12 = setting.getString("NotenKlausurenQ12", null);
                NotenList12 = gson.fromJson(json12 , type);
                NotenList12.add(0,new noten_placeholder());
                adapter = new noten_adapter(getActivity(),NotenList12);
                mrecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                StufeHalbjahr = "Q12";

                SharedPreferences.Editor editor = setting.edit();
                editor.putString("ShownHalbjahr", StufeHalbjahr);
                editor.apply();
            }
        });


        Q21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Object> NotenList21 = new ArrayList<>();
                Q21.setBackgroundColor(Color.parseColor("#D8D8D8"));
                Q11.setBackgroundColor(Color.TRANSPARENT);
                Q12.setBackgroundColor(Color.TRANSPARENT);
                Q22.setBackgroundColor(Color.TRANSPARENT);
                Gson gson = new Gson();
                String json21 = setting.getString("NotenKlausurenQ21", null);
                NotenList21 = gson.fromJson(json21 , type);
                NotenList21.add(0,new noten_placeholder());
                NotenList21.add(new noten_placeholder());
                adapter = new noten_adapter(getActivity(),NotenList21);
                mrecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                StufeHalbjahr = "Q21";

                SharedPreferences.Editor editor = setting.edit();
                editor.putString("ShownHalbjahr", StufeHalbjahr);
                editor.apply();
            }
        });


        Q22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Object> NotenList22 = new ArrayList<>();
                Q22.setBackgroundColor(Color.parseColor("#D8D8D8"));
                Q11.setBackgroundColor(Color.TRANSPARENT);
                Q12.setBackgroundColor(Color.TRANSPARENT);
                Q21.setBackgroundColor(Color.TRANSPARENT);
                Gson gson = new Gson();
                setting = getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0);
                String json22 = setting.getString("NotenKlausurenQ22", null);
                NotenList22 = gson.fromJson(json22 , type);
                NotenList22.add(0,new noten_placeholder());
                adapter = new noten_adapter(getActivity(),NotenList22);
                mrecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                StufeHalbjahr = "Q22";

                SharedPreferences.Editor editor = setting.edit();
                editor.putString("ShownHalbjahr", StufeHalbjahr);
                editor.apply();
            }
        });



        shown = true;

        return view;
    }

    public void AktualisiereZahlen(){

        int anzahlNoten = 0;
        int anzahlAll = 0;
        int summeNoten = 0;
        int summeSchlechteste = 0;
        int summeBeste = 0;
        double SchnittPunkte;

        SharedPreferences setting = this.getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0);


        if(!(setting.getString("Stufe",null).equals("Q1") || setting.getString("Stufe",null).equals("Q2"))) {


            String json = setting.getString("NotenKlausuren", null);
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Memory_NotenKlausuren>>() {}.getType();
            SchnittNotenList = gson.fromJson(json, type);


            for (int i = 0; i < SchnittNotenList.size(); i++) {
                int Wertung = SchnittNotenList.get(i).getWertung();
                int Note = GesammtNote(SchnittNotenList.get(i).isMuendlichschrifltich(), SchnittNotenList.get(i).getZeugnis(), SchnittNotenList.get(i).getMuendlich1(), SchnittNotenList.get(i).getMuendlich2(), SchnittNotenList.get(i).getSchriftlich1(), SchnittNotenList.get(i).getSchriftlich2());
                int Beste = BestNote(SchnittNotenList.get(i).isMuendlichschrifltich(), SchnittNotenList.get(i).getZeugnis(), SchnittNotenList.get(i).getMuendlich1(), SchnittNotenList.get(i).getMuendlich2(), SchnittNotenList.get(i).getSchriftlich1(), SchnittNotenList.get(i).getSchriftlich2());
                int Schlechteste = SchlechtsteNote(SchnittNotenList.get(i).isMuendlichschrifltich(), SchnittNotenList.get(i).getZeugnis(), SchnittNotenList.get(i).getMuendlich1(), SchnittNotenList.get(i).getMuendlich2(), SchnittNotenList.get(i).getSchriftlich1(), SchnittNotenList.get(i).getSchriftlich2());

                anzahlAll += Wertung;
                summeBeste = summeBeste + Beste * Wertung;
                summeSchlechteste = summeSchlechteste + Schlechteste * Wertung;


                if (!(Note == 0)) {
                    anzahlNoten = anzahlNoten + Wertung;
                    summeNoten = summeNoten + ((Note-1) * Wertung);
                }
            }


        }else{

            List<Memory_NotenKlausuren> NotenListQ11;
            List<Memory_NotenKlausuren> NotenListQ12;
            List<Memory_NotenKlausuren> NotenListQ21;
            List<Memory_NotenKlausuren> NotenListQ22;

            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Memory_NotenKlausuren>>() {
            }.getType();

            String jsonQ11 = setting.getString("NotenKlausurenQ11", null);
            String jsonQ12 = setting.getString("NotenKlausurenQ12", null);
            String jsonQ21 = setting.getString("NotenKlausurenQ21", null);
            String jsonQ22 = setting.getString("NotenKlausurenQ21", null);

            NotenListQ11 = gson.fromJson(jsonQ11, type);
            NotenListQ12 = gson.fromJson(jsonQ12, type);
            NotenListQ21 = gson.fromJson(jsonQ21, type);
            NotenListQ22 = gson.fromJson(jsonQ22, type);

            SchnittNotenList.addAll(NotenListQ11);
            SchnittNotenList.addAll(NotenListQ12);
            SchnittNotenList.addAll(NotenListQ21);
            SchnittNotenList.addAll(NotenListQ22);

            for (int i = 0; i < SchnittNotenList.size(); i++) {
                int Wertung = SchnittNotenList.get(i).getWertung();
                int Beste = BestNote(SchnittNotenList.get(i).isMuendlichschrifltich(), SchnittNotenList.get(i).getZeugnis(), SchnittNotenList.get(i).getMuendlich1(), SchnittNotenList.get(i).getMuendlich2(), SchnittNotenList.get(i).getSchriftlich1(), SchnittNotenList.get(i).getSchriftlich2());
                int Schlechteste = SchlechtsteNote(SchnittNotenList.get(i).isMuendlichschrifltich(), SchnittNotenList.get(i).getZeugnis(), SchnittNotenList.get(i).getMuendlich1(), SchnittNotenList.get(i).getMuendlich2(), SchnittNotenList.get(i).getSchriftlich1(), SchnittNotenList.get(i).getSchriftlich2());
                anzahlAll += Wertung;
                summeBeste = summeBeste + Beste * Wertung;
                summeSchlechteste = summeSchlechteste + Schlechteste * Wertung;

            }



            for (int i = 0; i < NotenListQ11.size(); i++) {
                int Halbjahre = 0;
                int Wertung = 1;
                int AnzahlEingetragen=0;
                int NoteQ11;
                int NoteQ12;
                int NoteQ21;
                int NoteQ22;
                int SummePunkte = 0;

                if((NotenListQ11.get(i).getFindetStatt())){
                    Halbjahre++;
                    Wertung = NotenListQ11.get(i).getWertung();
                    NoteQ11 = GesammtNote(NotenListQ11.get(i).isMuendlichschrifltich(), NotenListQ11.get(i).getZeugnis(), NotenListQ11.get(i).getMuendlich1(), NotenListQ11.get(i).getMuendlich2(), NotenListQ11.get(i).getSchriftlich1(), NotenListQ11.get(i).getSchriftlich2());
                    if(!(NoteQ11==0)){
                        AnzahlEingetragen++;
                        SummePunkte += NoteQ11-1;
                    }

                }


                if((NotenListQ12.get(i).getFindetStatt())){
                    Halbjahre++;
                    Wertung = NotenListQ12.get(i).getWertung();
                    NoteQ12 = GesammtNote(NotenListQ12.get(i).isMuendlichschrifltich(), NotenListQ12.get(i).getZeugnis(), NotenListQ12.get(i).getMuendlich1(), NotenListQ12.get(i).getMuendlich2(), NotenListQ12.get(i).getSchriftlich1(), NotenListQ12.get(i).getSchriftlich2());
                    if(!(NoteQ12==0)){
                        AnzahlEingetragen++;
                        SummePunkte += NoteQ12-1;
                    }
                }


                if((NotenListQ21.get(i).getFindetStatt())){
                    Halbjahre++;
                    Wertung = NotenListQ21.get(i).getWertung();
                    NoteQ21 = GesammtNote(NotenListQ21.get(i).isMuendlichschrifltich(), NotenListQ21.get(i).getZeugnis(), NotenListQ21.get(i).getMuendlich1(), NotenListQ21.get(i).getMuendlich2(), NotenListQ21.get(i).getSchriftlich1(), NotenListQ21.get(i).getSchriftlich2());
                    if(!(NoteQ21==0)){
                        AnzahlEingetragen++;
                        SummePunkte += NoteQ21-1;
                    }
                }


                if((NotenListQ21.get(i).getFindetStatt())){
                    Halbjahre++;
                    Wertung = NotenListQ22.get(i).getWertung();
                    NoteQ22 = GesammtNote(NotenListQ22.get(i).isMuendlichschrifltich(), NotenListQ22.get(i).getZeugnis(), NotenListQ22.get(i).getMuendlich1(), NotenListQ22.get(i).getMuendlich2(), NotenListQ22.get(i).getSchriftlich1(), NotenListQ22.get(i).getSchriftlich2());
                    if(!(NoteQ22==0)){
                        AnzahlEingetragen++;
                        SummePunkte += NoteQ22-1;
                    }
                }

                if(!(AnzahlEingetragen==0)) {
                    anzahlNoten = anzahlNoten + (Wertung*Halbjahre);
                    summeNoten += SummePunkte + (SummePunkte / AnzahlEingetragen * (Halbjahre - AnzahlEingetragen)) * Wertung;
                }


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

            switch (StufeHalbjahr){
                case "---":
                    json = setting.getString("NotenKlausuren", null);
                    break;
                case "Q11":
                    json = setting.getString("NotenKlausurenQ11", null);
                    break;
                case "Q12":
                    json = setting.getString("NotenKlausurenQ12", null);
                    break;
                case "Q21":
                    json = setting.getString("NotenKlausurenQ21", null);
                    break;
                case "Q22":
                    json = setting.getString("NotenKlausurenQ22", null);
                    break;
                default:
                    json = setting.getString("NotenKlausuren", null);
                    break;

            }
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
            return((int) (((Muendlich+Schriftlich)/alleeingetragen))+1);

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
