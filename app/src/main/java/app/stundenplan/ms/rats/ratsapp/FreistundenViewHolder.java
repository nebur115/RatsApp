package app.stundenplan.ms.rats.ratsapp;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by User on 28.03.2018.
 */

public class FreistundenViewHolder extends RecyclerView.ViewHolder {

    public ConstraintLayout Stunde;

    public FreistundenViewHolder(View FreistundenView){
        super(FreistundenView);
        Stunde = FreistundenView.findViewById(R.id.stunden_holder);
    }

    public void showFreistundendetails(Freistunde pFreistunde){
        int thoehe;
        int tbreite = pFreistunde.getBreite();
        boolean tdoppelstunde = pFreistunde.isDoppelstunde();
        String tWochentag = pFreistunde.getWochentag();

        if (tdoppelstunde){
            thoehe = pFreistunde.getHoehe()*2;

        }
        else {
            thoehe = pFreistunde.getHoehe();

        }

        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) Stunde.getLayoutParams();

        layoutParams.height = thoehe;
        layoutParams.width = tbreite;

        Stunde.setLayoutParams(layoutParams);

        if(tWochentag == "Dienstag" || tWochentag == "Donnerstag"){
            Stunde.setBackgroundResource(R.drawable.stundenplan_dark);
        }
        else
        {
            Stunde.setBackgroundResource(R.drawable.stundenplan_light);
        }


    }


}
