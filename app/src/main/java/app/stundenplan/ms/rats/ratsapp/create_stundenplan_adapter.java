package app.stundenplan.ms.rats.ratsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 04.04.2018.
 */

public class create_stundenplan_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Object> StundenListe = new ArrayList<>();
    private Context context;
    private final static int TYPE_NoStunde=1, TYPE_StundeCreate=2, TYPE_Tag=3;

    public create_stundenplan_adapter(Context context, List<Object> pstundenListe){
        this.context = context;
        this.StundenListe = pstundenListe;
    }


    @Override
    public int getItemViewType(int position) {

        if (StundenListe.get(position) instanceof create_stundenplan_createstunde) {
            return TYPE_NoStunde;
        } else if (StundenListe.get(position) instanceof create_stundenplan_stunde) {
            return TYPE_StundeCreate;
        } else if (StundenListe.get(position) instanceof create_stundenplan_tag){
            return TYPE_Tag;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder rvh;
        View layoutView;


        switch(viewType) {
            case TYPE_NoStunde:
                layoutView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.create_stundenplan_stunde, parent, false);
                rvh = new create_stundenplan_viewholder_createstunde(layoutView);
                break;
            case TYPE_StundeCreate:
                layoutView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.create_stundenplan_createdstunde, parent, false);
                rvh = new create_stundenplan_stunde_viewholder(layoutView);
                break;
            case TYPE_Tag:
                layoutView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.create_stundenplan_tag, parent, false);
                rvh = new create_stundenplan_tag_viewholder(layoutView);
                break;
            default:
                rvh=null;
                break;
        }

        return rvh;
    }




    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder myholder, int position){
        int viewType = myholder.getItemViewType();
        switch (viewType){
            case TYPE_NoStunde:
                create_stundenplan_createstunde create_stundenplan_createstunde = (create_stundenplan_createstunde) StundenListe.get(position);
                ((create_stundenplan_viewholder_createstunde)myholder).showCreate_stundenplan_createstundendetails(create_stundenplan_createstunde);
                break;

            case TYPE_StundeCreate:
                create_stundenplan_stunde create_stundenplan_stunde = (create_stundenplan_stunde) StundenListe.get(position);
                ((create_stundenplan_stunde_viewholder)myholder).showCreate_stundenplan_stunde_viewholderdetails(create_stundenplan_stunde);
                break;

            case TYPE_Tag:
                create_stundenplan_tag create_stundenplan_tag = (create_stundenplan_tag) StundenListe.get(position);
                ((create_stundenplan_tag_viewholder)myholder).show_create_stundenplan_tag_viewholderdetails(create_stundenplan_tag);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return this.StundenListe.size();
    }


}
