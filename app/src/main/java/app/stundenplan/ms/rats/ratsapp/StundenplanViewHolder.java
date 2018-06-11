package app.stundenplan.ms.rats.ratsapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Ruben on 27.03.2018.
 */

public class StundenplanViewHolder extends RecyclerView.ViewHolder {
    public TextView Lehrer;
    public TextView Fach;
    public TextView Raum;
    public ConstraintLayout Stunde;


    public StundenplanViewHolder(View StundenplanView){
        super(StundenplanView);
        Lehrer = StundenplanView.findViewById(R.id.Stunde_Lehrer);
        Fach = StundenplanView.findViewById(R.id.Stunde_Fach);
        Raum = StundenplanView.findViewById(R.id.Stunde_Raum);
        Stunde = (ConstraintLayout) StundenplanView.findViewById(R.id.stunden_holder);
    }

    public void showstundendetails(Stunde pStunde){
        String tLehrer = pStunde.getLehrer();
        String tRaum = pStunde.getRaum();
        String tFach = pStunde.getFach();
        int tdphoehe;
        int thoehe;
        int tbreite = pStunde.getBreite();
        boolean tdoppelstunde = pStunde.isDoppelstunde();
        String tWochentag = pStunde.getWochentag();

        boolean pnow = pStunde.isNow();
        boolean Raumwechsel = pStunde.isRaumwechsel();
        boolean Lehrerwechsel = pStunde.isLehrerwechsel();
        boolean Entfällt = pStunde.isEntfällt();
        boolean Klausur = pStunde.isKlausur();
        boolean Veranstalltung = pStunde.isVeranstalltung();





        if (tdoppelstunde){
            thoehe = pStunde.getHoehe()*2;
            tdphoehe  = (int) pStunde.getDphoehe()*2;
        }
        else {
            thoehe = pStunde.getHoehe();
            tdphoehe = (int) pStunde.getDphoehe();
        }


        if(tWochentag == "Dienstag" || tWochentag == "Donnerstag"){
            Stunde.setBackgroundResource(R.drawable.stundenplan_dark);
            if(Entfällt){
                Fach.setTextColor(Color.parseColor("#9d9d9d"));
                Raum.setTextColor(Color.parseColor("#9d9d9d"));
                Lehrer.setTextColor(Color.parseColor("#9d9d9d"));
            }
        }
        else
        {
            Stunde.setBackgroundResource(R.drawable.stundenplan_light);
            if(Entfällt){
                Fach.setTextColor(Color.parseColor("#cfcfcf"));
                Raum.setTextColor(Color.parseColor("#cfcfcf"));
                Lehrer.setTextColor(Color.parseColor("#cfcfcf"));
            }
        }


        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) Stunde.getLayoutParams();

        layoutParams.height = thoehe;
        layoutParams.width = tbreite;

        Stunde.setLayoutParams(layoutParams);


        if (tdphoehe<=75){
            Lehrer.setVisibility(View.GONE);
            if(Raumwechsel){
                tLehrer = tRaum;
                Lehrer.setTextColor(Color.parseColor("#B13333"));
            }
        }

        if (tdphoehe<=55){
            Raum.setVisibility(View.GONE);
        }

        if (tdphoehe>=95){
            Fach.setTextSize(35);
        }

        Lehrer.setText(tLehrer);
        Raum.setText(tRaum);
        Fach.setText(tFach);

        if(pnow){
            Lehrer.setTextColor(Color.parseColor("#000000"));
            Raum.setTextColor(Color.parseColor("#000000"));
            Fach.setTextColor(Color.parseColor("#000000"));
            Lehrer.setTypeface(Typeface.DEFAULT_BOLD);
            Raum.setTypeface(Typeface.DEFAULT_BOLD);
        }

        if(Raumwechsel){
            Raum.setTextColor(Color.parseColor("#B13333"));
        }

        if(Lehrerwechsel){
            Lehrer.setTextColor(Color.parseColor("#B13333"));
        }


        if(Veranstalltung){
            Fach.setTextColor(Color.parseColor("#EDAE33"));

        }

        if(Klausur){
            Fach.setTextColor(Color.parseColor("#6D0BAA"));
        }

    }


}