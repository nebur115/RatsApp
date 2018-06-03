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
import android.widget.ImageView;
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
    TextView tNote;
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
    double Rounded = 0.0;
    ImageView Arrow;
    ConstraintLayout PunkteBruch;
    double MaxPunkte;
    double Punkte;
    TextView MoeglichePunkte;
    TextView EreichtePunkte;

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
        PunkteBruch = view.findViewById(R.id.PunkteBruch);
        Arrow = view.findViewById(R.id.tendenz);
        MoeglichePunkte = view.findViewById(R.id.MoeglichePunkt);
        EreichtePunkte = view.findViewById(R.id.EreichtePunkte);

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

        tSchnitt = view.findViewById(R.id.schnitt);
        tNote = view.findViewById(R.id.schelchtmoeglich);
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

        Rounded = 0.0;
        int anzahlNoten = 0;
        int anzahlAll = 0;
        int summeNoten = 0;
        MaxPunkte = 0;
        Punkte = 0;

        SharedPreferences setting = this.getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0);


        if(!(setting.getString("Stufe",null).toUpperCase().equals("Q1") || setting.getString("Stufe",null).toUpperCase().equals("Q2"))) {


            String json = setting.getString("NotenKlausuren", null);
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Memory_NotenKlausuren>>() {}.getType();
            SchnittNotenList = gson.fromJson(json, type);

            for (int i = 0; i < SchnittNotenList.size(); i++) {
                int Wertung = SchnittNotenList.get(i).getWertung();
                int Note = GesammtNote(SchnittNotenList.get(i).isMuendlichschrifltich(), SchnittNotenList.get(i).getZeugnis(), SchnittNotenList.get(i).getMuendlich1(), SchnittNotenList.get(i).getMuendlich2(), SchnittNotenList.get(i).getSchriftlich1(), SchnittNotenList.get(i).getSchriftlich2(), SchnittNotenList.get(i).getSchriftlich3());
                anzahlAll += Wertung;

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
            String jsonQ22 = setting.getString("NotenKlausurenQ22", null);

            NotenListQ11 = gson.fromJson(jsonQ11, type);
            NotenListQ12 = gson.fromJson(jsonQ12, type);
            NotenListQ21 = gson.fromJson(jsonQ21, type);
            NotenListQ22 = gson.fromJson(jsonQ22, type);

            SchnittNotenList.addAll(NotenListQ11);
            SchnittNotenList.addAll(NotenListQ12);
            SchnittNotenList.addAll(NotenListQ21);
            SchnittNotenList.addAll(NotenListQ22);

            for (int i = 0; i < SchnittNotenList.size(); i++) {
                if(SchnittNotenList.get(i).getFindetStatt()) {
                    int Wertung = SchnittNotenList.get(i).getWertung();
                    anzahlAll += Wertung;

                }

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
                    NoteQ11 = GesammtNote(NotenListQ11.get(i).isMuendlichschrifltich(), NotenListQ11.get(i).getZeugnis(), NotenListQ11.get(i).getMuendlich1(), NotenListQ11.get(i).getMuendlich2(), NotenListQ11.get(i).getSchriftlich1(), NotenListQ11.get(i).getSchriftlich2(), NotenListQ11.get(i).getSchriftlich3());
                    if(!(NoteQ11==0)){
                        AnzahlEingetragen++;
                        SummePunkte += NoteQ11-1;
                    }

                }


                if((NotenListQ12.get(i).getFindetStatt())){
                    Halbjahre++;
                    Wertung = NotenListQ12.get(i).getWertung();
                    NoteQ12 = GesammtNote(NotenListQ12.get(i).isMuendlichschrifltich(), NotenListQ12.get(i).getZeugnis(), NotenListQ12.get(i).getMuendlich1(), NotenListQ12.get(i).getMuendlich2(), NotenListQ12.get(i).getSchriftlich1(), NotenListQ12.get(i).getSchriftlich2(), NotenListQ12.get(i).getSchriftlich3());
                    if(!(NoteQ12==0)){
                        AnzahlEingetragen++;
                        SummePunkte += NoteQ12-1;
                    }
                }


                if((NotenListQ21.get(i).getFindetStatt())){
                    Halbjahre++;
                    Wertung = NotenListQ21.get(i).getWertung();
                    NoteQ21 = GesammtNote(NotenListQ21.get(i).isMuendlichschrifltich(), NotenListQ21.get(i).getZeugnis(), NotenListQ21.get(i).getMuendlich1(), NotenListQ21.get(i).getMuendlich2(), NotenListQ21.get(i).getSchriftlich1(), NotenListQ21.get(i).getSchriftlich2(), NotenListQ21.get(i).getSchriftlich3());
                    if(!(NoteQ21==0)){
                        AnzahlEingetragen++;
                        SummePunkte += NoteQ21-1;
                    }
                }


                if((NotenListQ22.get(i).getFindetStatt())){
                    Halbjahre++;
                    Wertung = NotenListQ22.get(i).getWertung();
                    NoteQ22 = GesammtNote(NotenListQ22.get(i).isMuendlichschrifltich(), NotenListQ22.get(i).getZeugnis(), NotenListQ22.get(i).getMuendlich1(), NotenListQ22.get(i).getMuendlich2(), NotenListQ22.get(i).getSchriftlich1(), NotenListQ22.get(i).getSchriftlich2(), NotenListQ22.get(i).getSchriftlich3());
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
            FinalSchnitt = Double.toString(PunktezuNote(summeNoten, anzahlNoten)) + "000";
            if(Rounded>0) {
                int ArrowWith = (int) Rounded * 100 / (anzahlNoten + 1) + 50;
                Arrow.getLayoutParams().width = ArrowWith;
                Arrow.setImageResource(R.drawable.tend_down);

            }else if(Rounded<0){
                int ArrowWith = (int) -Rounded * 100 / (anzahlNoten + 1) + 50;
                Arrow.getLayoutParams().width = ArrowWith;
                Arrow.setImageResource(R.drawable.tend_up);
            }else{
                Arrow.setImageResource(R.drawable.tendnon);
                Arrow.getLayoutParams().width = 100;
            }

            if(!(MaxPunkte==0)) {
                MoeglichePunkte.setText(Double.toString(MaxPunkte) + "Pkte.");
                EreichtePunkte.setText(Double.toString(Punkte) + "Pkte.");
            }else{
                MoeglichePunkte.setText("---- Pkte.");
                EreichtePunkte.setText("---- Pkte.");
            }

            tSchnitt.setText(FinalSchnitt.substring(0, 4));
            tNote.setText(PunktezuString((double) summeNoten/ (double) anzahlNoten));

        } else {
            tSchnitt.setText("N/A");
            tNote.setText("N/A");
            Arrow.setImageResource(R.drawable.tendnon);
            Arrow.getLayoutParams().width = 100;
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

    private int GesammtNote(boolean pschirftlich, int pZeugnisNote, int pMuendlich1, int pMuendlich2, int pSchriftlich1, int pSchriftlich2, int pSchriftlich3) {

        if(!(pZeugnisNote==0)){

            if(setting.getBoolean("+/-Mitrechen", (setting.getBoolean("+/-Mitrechen", setting.getString("Stufe", "").equals("Q1") ||setting.getString("Stufe", "").equals("Q2"))))){
                Arrow.setVisibility(View.GONE);
                PunkteBruch.setVisibility(View.VISIBLE);
                MaxPunkte += 15;
                Punkte += pZeugnisNote-1;
                return pZeugnisNote;
            }else {
                Arrow.setVisibility(View.VISIBLE);
                PunkteBruch.setVisibility(View.GONE);
                if (pZeugnisNote == 1) {
                    return 1;
                } else {
                    double Zwischenzahl = (double) pZeugnisNote / 3 + 0.49;
                    double ZwischenRounded = (Math.floor(Zwischenzahl)*3) - pZeugnisNote;
                    Rounded = Rounded + ZwischenRounded;
                    return (int) Math.floor(Zwischenzahl) * 3;
                }
            }

        }else if(!(pMuendlich1+pMuendlich2+pSchriftlich1+pSchriftlich2+pSchriftlich3==0)){
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

            if(!(pSchriftlich3==0)){
                SchriftlichEingetragen++;
                Schriftlich = Schriftlich + pSchriftlich3-1;
            }


            if(!(MuendlichEingetragen==0)){
                Muendlich = Muendlich/MuendlichEingetragen;
            }

            if(!(SchriftlichEingetragen==0)){
                Schriftlich = Schriftlich/SchriftlichEingetragen;
            }

            if(!(SchriftlichEingetragen==0)){
                alleeingetragen++;
            }

            if(!(MuendlichEingetragen==0)){
                alleeingetragen++;
            }

            if((((Muendlich+Schriftlich)/alleeingetragen))+1==1){
               return 1;
            }else{
                if(setting.getBoolean("+/-Mitrechen", (setting.getBoolean("+/-Mitrechen", setting.getString("Stufe", "").toUpperCase().equals("Q1") ||setting.getString("Stufe", "").toUpperCase().equals("Q2"))))){
                    Arrow.setVisibility(View.GONE);
                    PunkteBruch.setVisibility(View.VISIBLE);
                    MaxPunkte += 15;
                    Punkte += (Muendlich + Schriftlich) / alleeingetragen ;
                    return (int) (Muendlich + Schriftlich) / alleeingetragen + 1;
                }else{
                    double ZwischenRounded = (Math.floor((((Muendlich + Schriftlich) / alleeingetragen + 1)/3)+0.49)*3) - ((Muendlich + Schriftlich) / alleeingetragen +1);
                    Rounded = Rounded + ZwischenRounded;
                    Arrow.setVisibility(View.VISIBLE);
                    PunkteBruch.setVisibility(View.GONE);
                    return (int) ( Math.floor((((Muendlich + Schriftlich) / alleeingetragen + 1)/3)+0.49)*3);
                }
            }
        }else{

            if(setting.getBoolean("+/-Mitrechen", (setting.getBoolean("+/-Mitrechen", setting.getString("Stufe", "").toUpperCase().equals("Q1") ||setting.getString("Stufe", "").toUpperCase().equals("Q2"))))){
                Arrow.setVisibility(View.GONE);
                PunkteBruch.setVisibility(View.VISIBLE);
            }else{
                Arrow.setVisibility(View.VISIBLE);
                PunkteBruch.setVisibility(View.GONE);
            }
            return (0);
        }

    }

    private String PunktezuString(double Punkte){
      switch ((int) Math.round(Punkte)){
          case 0:
              return "6";
          case 1:
              return "5-";
          case 2:
              return "5";
          case 3:
              return "5+";
          case 4:
              return "4-";
          case 5:
              return "4";
          case 6:
              return "4+";
          case 7:
              return "3-";
          case 8:
              return "3";
          case 9:
              return "3+";
          case 10:
              return "2-";
          case 11:
              return "2";
          case 12:
              return "2+";
          case 13:
              return "1-";
          case 14:
              return "1";
          case 15:
              return "1+";
          default:
              return "";
        }
    }

}
