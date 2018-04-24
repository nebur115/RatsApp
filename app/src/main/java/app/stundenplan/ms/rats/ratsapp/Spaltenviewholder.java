package app.stundenplan.ms.rats.ratsapp;

import android.graphics.Color;
import android.graphics.Typeface;
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
        boolean Today = pSpalte.isToday();


        if(tWochentag == "Dienstag" || tWochentag == "Donnerstag"){
            Frame.setBackgroundResource(R.drawable.date_dark);
        }
        else
        {
            Frame.setBackgroundResource(R.drawable.date_light);
        }

        Datum.setText(tDatum);
        Wochentag.setText(tWochentag);

        if(Today){
            Datum.setTextColor(Color.parseColor("#000000"));
            Datum.setTypeface(Typeface.DEFAULT_BOLD);
            Wochentag.setTextSize(14);
            Wochentag.setTextColor(Color.parseColor("#000000"));
        }

        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) Frame.getLayoutParams();

        layoutParams.width = tBreite;

        Frame.setLayoutParams(layoutParams);

    }
}
