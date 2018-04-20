package app.stundenplan.ms.rats.ratsapp;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by User on 04.04.2018.
 */



public class create_stundenplan_viewholder_createstunde extends RecyclerView.ViewHolder {



    public ConstraintLayout Stunde;
    public int Woche;

    public create_stundenplan_viewholder_createstunde(View create_stundenplan_createstundenView){
        super(create_stundenplan_createstundenView);
        Stunde = create_stundenplan_createstundenView.findViewById(R.id.create_stunden_holder);

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





    public void showCreate_stundenplan_createstundendetails(create_stundenplan_createstunde pcreate_stundenplan_createstunde){
        int thoehe;
        int tbreite = pcreate_stundenplan_createstunde.getBreite();
        int tWochentag = pcreate_stundenplan_createstunde.getWochentag();
        Woche = pcreate_stundenplan_createstunde.getWoche();

        thoehe = pcreate_stundenplan_createstunde.getHoehe();

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