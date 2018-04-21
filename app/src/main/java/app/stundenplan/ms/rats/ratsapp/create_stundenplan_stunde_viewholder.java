package app.stundenplan.ms.rats.ratsapp;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


/**
 * Created by Ruben on 03.04.2018.
 */

public class create_stundenplan_stunde_viewholder extends RecyclerView.ViewHolder{

    int Woche;
    public ConstraintLayout Stunde;
    public TextView Fach;

    public create_stundenplan_stunde_viewholder(View create_stundenplan_stundenView){
        super(create_stundenplan_stundenView);
        Stunde = create_stundenplan_stundenView.findViewById(R.id.create_stunden_holder);
        Fach = create_stundenplan_stundenView.findViewById(R.id.Fach);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                int pos = getAdapterPosition();
                Context context = v.getContext();
                Intent i = new Intent(context, create_stundenplan_stunden_obtionen.class);
                i.putExtra("Position", pos);
                i.putExtra("Woche", Woche);
                i.putExtra("ZweiWöchentlich", create_stundenplan_stundenplan.isZweiWöchentlich());
                context.startActivity(i);
            }
        });
    }





    public void showCreate_stundenplan_stunde_viewholderdetails(create_stundenplan_stunde pcreate_stundenplan_stunde){
        int thoehe= pcreate_stundenplan_stunde.getHoehe();
        int tbreite = pcreate_stundenplan_stunde.getBreite();
        int tWochentag = pcreate_stundenplan_stunde.getWochentag();
        String tFach = pcreate_stundenplan_stunde.getFach();
        Woche = pcreate_stundenplan_stunde.getWoche();

        Fach.setText(tFach);


        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) Stunde.getLayoutParams();

        layoutParams.height = thoehe;
        layoutParams.width = tbreite;



        Stunde.setLayoutParams(layoutParams);

        if(tWochentag == 4 || tWochentag == 2){
            Stunde.setBackgroundResource(R.drawable.stundenplan_dark);
        }
        else
        {
            Stunde.setBackgroundResource(R.drawable.stundenplan_light);
        }



    }




}

