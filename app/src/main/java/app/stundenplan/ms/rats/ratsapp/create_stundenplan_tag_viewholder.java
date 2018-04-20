package app.stundenplan.ms.rats.ratsapp;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by User on 05.04.2018.
 */

public class create_stundenplan_tag_viewholder extends RecyclerView.ViewHolder{
    public ConstraintLayout Frame;
    public TextView Wochentag;
    public TextView Datum;


    public create_stundenplan_tag_viewholder(View SpaltenView){
        super(SpaltenView);
        Frame = SpaltenView.findViewById(R.id.frame);
        Wochentag = SpaltenView.findViewById(R.id.DayWeek);
        Datum = SpaltenView.findViewById(R.id.Date);

    }

    public void show_create_stundenplan_tag_viewholderdetails(create_stundenplan_tag pcreate_stundenplan_tag){
        int tWochentag = pcreate_stundenplan_tag.getWochentag();

        int tBreite = pcreate_stundenplan_tag.getBreite();


        if(tWochentag == 2 || tWochentag == 4){
            Frame.setBackgroundResource(R.drawable.date_dark);
        }
        else
        {
            Frame.setBackgroundResource(R.drawable.date_light);
        }


        switch(tWochentag){
            case 1:
                Wochentag.setText("Montag");
                break;
            case 2:
                Wochentag.setText("Dienstag");
                break;
            case 3:
                Wochentag.setText("Mittwoch");
                break;
            case 4:
                Wochentag.setText("Donnerstag");
                break;
            case 5:
                Wochentag.setText("Freitag");
                break;
            default:
                Wochentag.setText("");

        }

        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) Frame.getLayoutParams();

        layoutParams.width = tBreite;

        Frame.setLayoutParams(layoutParams);

    }
}
