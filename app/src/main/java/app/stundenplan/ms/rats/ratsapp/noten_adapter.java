package app.stundenplan.ms.rats.ratsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class noten_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> itemFeed = new ArrayList();
    private Context context;

    public noten_adapter(Context context, List<Object> Liste){
        this.context=context;
        itemFeed = Liste;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        int layout = R.layout.noten_item;
        View notenview = LayoutInflater
                .from(parent.getContext())
                .inflate(layout,parent,false);
        viewHolder = new noten_viewholder(notenview);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Memory_NotenKlausuren object = (Memory_NotenKlausuren) itemFeed.get(position);
        ((noten_viewholder)holder).showDetails(object);
    }


    @Override
    public int getItemCount() {
        return this.itemFeed.size();
    }
}
