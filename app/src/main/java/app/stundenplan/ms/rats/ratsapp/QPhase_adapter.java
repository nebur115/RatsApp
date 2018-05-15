package app.stundenplan.ms.rats.ratsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class QPhase_adapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> liste;
    private Context context;
    private final static int TYPE_OVERVIEW=1, TYPE_SELECTOR=2;

    public QPhase_adapter(Context pcontext, List<Object> pListe){
        context=pcontext;
        liste=pListe;
    }


    public void setliste(List<Object> pListe){
        liste= pListe;
    }


    @Override
    public int getItemViewType(int position){
        if(liste.get(position) instanceof QPhase_Overview){
            return TYPE_OVERVIEW;
        }else if(liste.get(position) instanceof QPhase_selector) {
            return TYPE_SELECTOR;
        }
        return -1;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder rvh;
        int layout = 0;
        switch (viewType){
            case TYPE_OVERVIEW:
                layout = R.layout.qphase_overview;
                View overview = LayoutInflater
                        .from(parent.getContext())
                        .inflate(layout,parent,false);
                rvh = new QPhase_Overview_Viewholder(overview);
                break;
            case TYPE_SELECTOR:
                layout = R.layout.qphase_selectorrow;
                View selectorrow = LayoutInflater
                        .from(parent.getContext())
                        .inflate(layout,parent,false);
                rvh = new QPhase_SELECTOR_Viewholder(selectorrow);
                break;
            default:
                rvh=null;
                break;
        }
        return rvh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        if(liste.get(position) instanceof QPhase_selector) {
            QPhase_selector object = (QPhase_selector) liste.get(position);
            ((QPhase_SELECTOR_Viewholder) holder).showDetails(object);
        }
    }

    @Override
    public int getItemCount() {
        return this.liste.size();
    }
}
