package app.stundenplan.ms.rats.ratsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class KalenderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final static int TYPE_EREIGNIS=1,TYPE_Datum=2;
    private List<Object> itemFeed = new ArrayList();
    private Context context;

    public void setitemFeed(List<Object> itemFeed){
        this.itemFeed = itemFeed;
    }

    public KalenderAdapter(Context context){
        this.context=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = 0;
        RecyclerView.ViewHolder viewHolder;

        switch (viewType) {
            case TYPE_EREIGNIS:
                layout = R.layout.ereignis_list_row;
                View eventView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
                viewHolder = new eventViewholder(eventView);
                break;
            case TYPE_Datum:
                layout = R.layout.vertretungsplan_date;
                View DateView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
                viewHolder = new DatumViewHolder(DateView);
                break;
            default:
                viewHolder = null;
                break;

        }
        return viewHolder;
    }


    @Override
    public int getItemViewType(int position) {

        if (itemFeed.get(position) instanceof kalender_event) {
            return TYPE_EREIGNIS;
        } else if (itemFeed.get(position) instanceof Datum){
            return TYPE_Datum;
        }
        return -1;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType=holder.getItemViewType();
        switch (viewType){
            case TYPE_EREIGNIS:
                kalender_event event=(kalender_event) itemFeed.get(position);
                ((eventViewholder)holder).showEventDeatils(event);
                break;
            case TYPE_Datum:
                Datum datum=(Datum)itemFeed.get(position);
                ((DatumViewHolder)holder).showDatumDetails(datum);
                break;

        }

    }

    @Override
    public int getItemCount() {
        return itemFeed.size();
    }


    public class eventViewholder extends RecyclerView.ViewHolder {

        private TextView fach;
        private TextView kurs;
        private TextView stunde;
        private TextView grund;
        private TextView lehrer;
        private ImageView zeichen;
        private ConstraintLayout frame;



        public eventViewholder(View itemView) {
            super(itemView);



            fach =  itemView.findViewById(R.id.Stunde_Fach);
            kurs = itemView.findViewById(R.id.Kurs);
            stunde = itemView.findViewById(R.id.Stunde);
            grund = itemView.findViewById(R.id.Grund);
            lehrer = itemView.findViewById(R.id.FachTextView);
            zeichen = itemView.findViewById(R.id.Zeichen);
            frame= itemView.findViewById(R.id.frame);

        }


        public void showEventDeatils(kalender_event event){

            final String Type = event.getType();
            final String Fach = event.getFach();
            final String  Description = event.getDescription();


            fach.setText(Fach + ": " + Type);
            kurs.setText("");
            stunde.setText("");
            grund.setText(Description);
            lehrer.setText("");

            //zeichen.setImageResource();

        }

    }

    public class DatumViewHolder extends RecyclerView.ViewHolder {

        private TextView tdatum;

        public DatumViewHolder(View itemView) {
            super(itemView);
            tdatum = itemView.findViewById(R.id.Datum);
        }

        public void showDatumDetails(Datum datum){
            String Datum = datum.getDate();
            tdatum.setText(Datum);
        }

    }

}
