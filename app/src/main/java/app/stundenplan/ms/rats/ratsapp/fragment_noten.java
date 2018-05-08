package app.stundenplan.ms.rats.ratsapp;

import android.content.SharedPreferences;
import android.os.Bundle;
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

    public fragment_noten() {

    }
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.noten_fragment,container,false);

        SharedPreferences setting = this.getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        mrecyclerView = view.findViewById(R.id.recyclerview_noten);

        tBestmoeglich = view.findViewById(R.id.bestmoeglich);
        tSchnitt = view.findViewById(R.id.schnitt);
        tSchlechtmoeglich = view.findViewById(R.id.schelchtmoeglich);

        mrecyclerView.setLayoutManager(linearLayoutManager);
        String json;
        Gson gson = new Gson();

        json = setting.getString("NotenKlausuren", null);
        Type type = new TypeToken<ArrayList<Memory_NotenKlausuren>>() {}.getType();
        NotenList = gson.fromJson(json , type);

        NotenList.add(0,new noten_placeholder());
        adapter = new noten_adapter(getActivity(),NotenList);


        mrecyclerView.setAdapter(adapter);

        reload();

        adapter.notifyDataSetChanged();

        return view;
    }

    public void reload(){
        SharedPreferences setting = this.getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0);
        String json;
        Gson gson = new Gson();
        json = setting.getString("NotenKlausuren", null);
        Type type = new TypeToken<ArrayList<Memory_NotenKlausuren>>() {}.getType();
        SchnittNotenList = gson.fromJson(json , type);

        int anzahlNoten = 0;
        int anzahlAll = 0;
        int summeNoten = 0;
        int summeSchlechteste = 0;
        int summeBeste = 0;
        double SchnittPunkte;

        for(int i=0; i<NotenList.size()-2;i++){
            int Wertung = SchnittNotenList.get(i).getWertung();
            int Note = GesammtNote(SchnittNotenList.get(i).isMuendlichschrifltich(),SchnittNotenList.get(i).getZeugnis(),SchnittNotenList.get(i).getMuendlich1(),SchnittNotenList.get(i).getMuendlich2(),SchnittNotenList.get(i).getSchriftlich1(),SchnittNotenList.get(i).getSchriftlich2());
            int Beste = BestNote(SchnittNotenList.get(i).isMuendlichschrifltich(),SchnittNotenList.get(i).getZeugnis(),SchnittNotenList.get(i).getMuendlich1(),SchnittNotenList.get(i).getMuendlich2(),SchnittNotenList.get(i).getSchriftlich1(),SchnittNotenList.get(i).getSchriftlich2());
            int Schlechteste =  SchlechtsteNote(SchnittNotenList.get(i).isMuendlichschrifltich(),SchnittNotenList.get(i).getZeugnis(),SchnittNotenList.get(i).getMuendlich1(),SchnittNotenList.get(i).getMuendlich2(),SchnittNotenList.get(i).getSchriftlich1(),SchnittNotenList.get(i).getSchriftlich2());


            anzahlAll++;
            summeBeste = summeBeste+ Beste/3;
                summeSchlechteste = summeSchlechteste+ Schlechteste;

            if(!(Note ==0)) {
                anzahlNoten = anzahlNoten + Wertung;
                summeNoten = summeNoten + Note;
            }

        }

        if(Double.toString(PunktezuNote(summeNoten/anzahlNoten)).length()>=4) {
            tSchnitt.setText(Double.toString(PunktezuNote(summeNoten / anzahlNoten)).substring(0, 4).replace(".", ","));
        }else {
            tSchnitt.setText(Double.toString(PunktezuNote(summeNoten / anzahlNoten)).replace(".", ","));

        }

        if(Double.toString(PunktezuNote(summeBeste/anzahlAll)).substring(0,4).length()>=4){
            tBestmoeglich.setText(Double.toString(PunktezuNote(summeBeste/anzahlAll)).substring(0,4).replace(".",","));
        }else {
            tBestmoeglich.setText(Double.toString(PunktezuNote(summeBeste/anzahlAll)).replace(".",","));
        }

        if(Double.toString(PunktezuNote(summeSchlechteste/anzahlAll)).length()>=4) {
            tSchlechtmoeglich.setText(Double.toString(PunktezuNote(summeSchlechteste / anzahlAll)).substring(0, 4).replace(".", ","));
        }else {
            tSchlechtmoeglich.setText(Double.toString(PunktezuNote(summeSchlechteste / anzahlAll)).replace(".", ","));
        }

    }

    private double PunktezuNote(double Punkte){
        if(!(Punkte==0)){
            return ((17-Punkte)/3);
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
            return (0);
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
