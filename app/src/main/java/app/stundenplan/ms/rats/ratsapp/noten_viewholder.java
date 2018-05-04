package app.stundenplan.ms.rats.ratsapp;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class noten_viewholder extends RecyclerView.ViewHolder{
    Spinner tMuendlich1;
    Spinner tMuendlich2;
    Spinner tSchriftlich2;
    Spinner tSchriftlich1;
    TextView tFach;
    ConstraintLayout cExtender;
    ConstraintLayout cSchriftlich1;
    ConstraintLayout cSchrifltich2;
    ImageButton arrow;
    ConstraintLayout Main;


    public noten_viewholder(View View) {
        super(View);
        cExtender = View.findViewById(R.id.extender);
        tFach = View.findViewById(R.id.fachText);
        Main = View.findViewById(R.id.main);
        cSchriftlich1 = View.findViewById(R.id.Quartal1Schritflich);
        cSchrifltich2 = View.findViewById(R.id.Quartal2Schritflich);


    }

    public void showDetails(Memory_NotenKlausuren Object){
        boolean schrifltich = Object.isMuendlichschrifltich();
        int Muendlich1 = Object.getMuendlich1();
        int Muendlich2 = Object.getMuendlich2();
        int Schriftlich1 = Object.getSchriftlich1();
        int Schriftlich2 = Object.getSchriftlich2();
        int Datum1 = Object.getDatum1();
        int Datum2 = Object.getDatum2();
        String Fach = Object.getFach();


        tFach.setText(Fach);
        if(!schrifltich) {
            cSchriftlich1.setVisibility(View.GONE);
            cSchrifltich2.setVisibility(View.GONE);
        }



    }
}
