package app.stundenplan.ms.rats.ratsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

public class QPhase_adapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> liste;
    private Context context;
    private final static int TYPE_OVERVIEW=1, TYPE_SELTORROW=2;



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
