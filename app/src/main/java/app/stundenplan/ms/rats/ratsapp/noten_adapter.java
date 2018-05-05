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

    noten_adapter(Context context, List<Object> Liste){
        this.context=context;
        this.liste = Liste;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder rvh;
        int layout = R.layout.noten_item;
        View notenview = LayoutInflater
                .from(parent.getContext())
                .inflate(layout,parent,false);
        rvh = new noten_viewholder(notenview);
        return rvh;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Memory_NotenKlausuren object = (Memory_NotenKlausuren) liste.get(position);
        ((noten_viewholder)holder).showDetails(object);
    }


    @Override
    public int getItemCount() {
        return this.liste.size();
    }
}
