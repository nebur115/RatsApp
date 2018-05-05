package app.stundenplan.ms.rats.ratsapp;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class noten_viewholder extends RecyclerView.ViewHolder{
    Spinner tMuendlich1;
    Spinner tMuendlich2;
    Spinner tSchriftlich2;
    Spinner tSchriftlich1;
    TextView tFach;
    TextView tGesammt;
    ConstraintLayout cExtender;
    ConstraintLayout cSchriftlich1;
    ConstraintLayout cSchrifltich2;
    ImageView arrow;
    ConstraintLayout Main;

    public noten_viewholder(View View) {
        super(View);
        cExtender = View.findViewById(R.id.extender);
        tFach = View.findViewById(R.id.fachText);
        Main = View.findViewById(R.id.main);
        cSchriftlich1 = View.findViewById(R.id.Quartal1Schritflich);
        cSchrifltich2 = View.findViewById(R.id.Quartal2Schritflich);
        tGesammt = View.findViewById(R.id.GesammtText);
        tSchriftlich1 = View.findViewById(R.id.Schriftlich1);
        tSchriftlich2 = View.findViewById(R.id.Schriftlich2);
        tMuendlich1 = View.findViewById(R.id.Muendlich1);
        tMuendlich2 = View.findViewById(R.id.Muendlich2);
    }

    public void showDetails(Memory_NotenKlausuren Object){
        boolean schrifltich = Object.isMuendlichschrifltich();
        int Muendlich1 = Object.getMuendlich1();
        int Muendlich2 = Object.getMuendlich2();
        int Schriftlich1 = Object.getSchriftlich1();
        int Schriftlich2 = Object.getSchriftlich2();
        int Gesammt = 0;
        int Datum1 = Object.getDatum1();
        int Datum2 = Object.getDatum2();
        String Fach = Object.getFach();


        List<String> sMuendlich1 = Standart();
        List<String> sMuendlich2 = Standart();
        List<String> sSchriftlich1 = Standart();
        List<String> sSchriftlich2 = Standart();

        if(Muendlich1==0){
            sMuendlich1.add(0," --- ");
        }else{
            sMuendlich1.add(0,PunktezuNote(Muendlich1));
            sMuendlich1.remove(16-Muendlich1);
            sMuendlich1.add(1," --- ");
        }

        if(Muendlich2==0){
            sMuendlich2.add(0," --- ");
        }else{
            sMuendlich2.add(0,PunktezuNote(Muendlich2));
            sMuendlich2.remove(16-Muendlich2);
            sMuendlich2.add(1," --- ");
        }

        if(Schriftlich1==0){
            sSchriftlich1.add(0," --- ");
        }else{
            sSchriftlich1.add(0,PunktezuNote(Schriftlich1));
            sSchriftlich1.remove(16-Schriftlich1);
            sSchriftlich1.add(1," --- ");
        }

        if(Schriftlich2==0){
            sSchriftlich2.add(0," --- ");
        }else{
            sSchriftlich2.add(0,PunktezuNote(Schriftlich2));
            sSchriftlich2.remove(16-Schriftlich2);
            sSchriftlich2.add(1," --- ");
        }

        ArrayAdapter aSchriftlich1 = new ArrayAdapter(itemView.getContext(), R.layout.spinner_item, sSchriftlich1);
        aSchriftlich1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tSchriftlich1.setAdapter(aSchriftlich1);

        ArrayAdapter aSchrifltich2 = new ArrayAdapter(itemView.getContext(), R.layout.spinner_item, sSchriftlich2);
        aSchrifltich2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tSchriftlich2.setAdapter(aSchrifltich2);

        ArrayAdapter aMuendlich1 = new ArrayAdapter(itemView.getContext(), R.layout.spinner_item, sMuendlich1);
        aMuendlich1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tMuendlich1.setAdapter(aMuendlich1);

        ArrayAdapter aMuendlich2 = new ArrayAdapter(itemView.getContext(), R.layout.spinner_item, sMuendlich2);
        aMuendlich2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tMuendlich2.setAdapter(aMuendlich2);

        tFach.setText(Fach);

        if(!schrifltich) {
            cSchriftlich1.setVisibility(View.GONE);
            cSchrifltich2.setVisibility(View.GONE);
        }

        if(!(Gesammt==0)){
            tGesammt.setText(Integer.toString(Gesammt));
        }else if(!(Muendlich1+Muendlich2+Schriftlich1+Schriftlich2==0)){

        }else{
            tGesammt.setText("");
        }


        Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cExtender.getVisibility()==View.GONE){
                    cExtender.setVisibility(View.VISIBLE);

                }else{
                    cExtender.setVisibility(View.GONE);
                }



            }
        });

    }

    private List<String> Standart(){
        List<String> Temp = new ArrayList<>();

        Temp.add(" 1+ ");
        Temp.add(" 1 ");
        Temp.add(" 1- ");
        Temp.add(" 2+ ");
        Temp.add(" 2 ");
        Temp.add(" 2- ");
        Temp.add(" 3+ ");
        Temp.add(" 3 ");
        Temp.add(" 3- ");
        Temp.add(" 4+ ");
        Temp.add(" 4 ");
        Temp.add(" 4- ");
        Temp.add(" 5+ ");
        Temp.add(" 5 ");
        Temp.add(" 5- ");
        Temp.add(" 6 ");

        return Temp;
    }


    private String PunktezuNote(int pPunkte){
        switch (pPunkte){
            case 1:
                return "6";
            case 2:
                return "5-";
            case 3:
                return "5";
            case 4:
                return "5+";
            case 5:
                return "4-";
            case 6:
                return "4";
            case 7:
                return "4+";

            case 8:
                return "3-";

            case 9:
                return "3";

            case 10:
                return "3+";
            case 11:
                return "2-";
            case 12:
                return "2";
            case 13:
                return "2+";
            case 14:
                return  "1-";
            case 15:
                return "1";
            case 16:
                return "1+";
                default:
                    return " --- ";
        }
    }
}
