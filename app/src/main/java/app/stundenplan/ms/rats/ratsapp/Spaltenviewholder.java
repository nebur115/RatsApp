package app.stundenplan.ms.rats.ratsapp;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Ruben on 29.03.2018.
 */

public class Spaltenviewholder extends RecyclerView.ViewHolder {
    public ConstraintLayout Frame;
    public TextView Wochentag;
    public TextView Datum;

    public Spaltenviewholder(View SpaltenView){
        super(SpaltenView);
        Frame = SpaltenView.findViewById(R.id.frame);
        Wochentag = SpaltenView.findViewById(R.id.DayWeek);
        Datum = SpaltenView.findViewById(R.id.Date);

    }

    public void showSpaltedetails(Spalte pSpalte){
        String tWochentag = pSpalte.getWochentag();
        String tDatum = pSpalte.getDatum();
        int tBreite = pSpalte.getBreite();


        if(tWochentag == "Dienstag" || tWochentag == "Donnerstag"){
            Frame.setBackgroundResource(R.drawable.date_dark);
        }
        else
        {
            Frame.setBackgroundResource(R.drawable.date_light);
        }

        Datum.setText(tDatum);
        Wochentag.setText(tWochentag);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) Frame.getLayoutParams();

        layoutParams.width = tBreite;

        Frame.setLayoutParams(layoutParams);

    }
}
