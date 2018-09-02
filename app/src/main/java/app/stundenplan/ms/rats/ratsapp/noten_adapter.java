package app.stundenplan.ms.rats.ratsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class noten_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> liste;
    private Context context;
    private final static int TYPE_NORMAL=1, TYPE_PLACEHOLDER=2;





    noten_adapter(Context context, List<Object> Liste){
        this.context=context;
        this.liste = Liste;
    }

    public void setliste(List<Object> pListe){
        liste= pListe;
    }



    @Override
    public int getItemViewType(int position){
        if(liste.get(position) instanceof Memory_NotenKlausuren){
            return TYPE_NORMAL;
        }else if(liste.get(position) instanceof noten_placeholder) {
            return TYPE_PLACEHOLDER;
        }
        return -1;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder rvh;
        int layout = 0;
        switch (viewType){
            case TYPE_NORMAL:
                layout = R.layout.noten_item;
                View notenview = LayoutInflater
                        .from(parent.getContext())
                        .inflate(layout,parent,false);
                rvh = new noten_viewholder(notenview);
                break;
            case TYPE_PLACEHOLDER:
                layout = R.layout.noten_placeholder_viewholder;
                View placeholderview = LayoutInflater
                        .from(parent.getContext())
                        .inflate(layout,parent,false);
                rvh = new noten_placeholder_viewholder(placeholderview);
                break;
            default:
                rvh=null;
                break;
        }
        return rvh;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = holder.getItemViewType();
        if(liste.get(position) instanceof Memory_NotenKlausuren) {
                Memory_NotenKlausuren object = (Memory_NotenKlausuren) liste.get(position);
                ((noten_viewholder) holder).showDetails(object);
        }
    }


    @Override
    public int getItemCount() {
        return this.liste.size();
    }
}
