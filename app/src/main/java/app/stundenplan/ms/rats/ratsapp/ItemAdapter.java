package app.stundenplan.ms.rats.ratsapp;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int TYPE_EREIGNIS=1,TYPE_Obtionen=2, TYPE_Datum=3;
    private List<Object> itemFeed = new ArrayList();
    private Context context;




    public ItemAdapter(Context context){
        this.context=context;
    }

    public void setitemFeed(List<Object> itemFeed){
        this.itemFeed = itemFeed;
    }


    @Override
    public int getItemViewType(int position) {

        if (itemFeed.get(position) instanceof Ereignis) {
            return TYPE_EREIGNIS;
        } else if (itemFeed.get(position) instanceof Obtionen) {
            return TYPE_Obtionen;
        } else if (itemFeed.get(position) instanceof Datum){
            return TYPE_Datum;
        }
        return -1;
    }
    



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = 0;
        RecyclerView.ViewHolder viewHolder;
        switch (viewType){
            case TYPE_EREIGNIS:
                layout=R.layout.ereignis_list_row;
                View ereignisView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(layout, parent, false);
                viewHolder=new ereignisViewHolder(ereignisView);
                break;
            case TYPE_Obtionen:
                layout=R.layout.vertretungsplan_obtions;
                final View ObtionenView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(layout, parent, false);
                viewHolder=new obtionenViewHolder(ObtionenView);
                break;
            case TYPE_Datum:
                layout=R.layout.vertretungsplan_date;
                View DateView= LayoutInflater
                        .from(parent.getContext())
                        .inflate(layout, parent, false);
                viewHolder= new DatumViewHolder(DateView);
                break;
            default:
                viewHolder=null;
                break;
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        int viewType=holder.getItemViewType();
        switch (viewType){
            case TYPE_EREIGNIS:
                Ereignis ereignis=(Ereignis) itemFeed.get(position);
                ((ereignisViewHolder)holder).showereignisDetails(ereignis);
                break;
            case TYPE_Obtionen:
                Obtionen Obtionen=(Obtionen)itemFeed.get(position);
                ((obtionenViewHolder)holder).showObtionenDetails(Obtionen);
                break;
            case TYPE_Datum:
                Datum datum=(Datum)itemFeed.get(position);
                ((DatumViewHolder)holder).showDatumDetails(datum);
                break;
        }
    }

    @Override
    public int getItemCount(){return itemFeed.size();}




    public class ereignisViewHolder extends RecyclerView.ViewHolder {
        private TextView fach;
        private TextView kurs;
        private TextView stunde;
        private TextView grund;
        private TextView lehrer;
        private ImageView zeichen;

        public ereignisViewHolder(View itemView) {
            super(itemView);

            fach =  itemView.findViewById(R.id.Stunde_Fach);
            kurs = itemView.findViewById(R.id.Kurs);
            stunde = itemView.findViewById(R.id.Stunde);
            grund = itemView.findViewById(R.id.Grund);
            lehrer = itemView.findViewById(R.id.FachTextView);
            zeichen = itemView.findViewById(R.id.Zeichen);

            
            
        }

        public void showereignisDetails(Ereignis ereignis){

            String Fach = ereignis.getFach();
            String Kurs = ereignis.getKurs();
            String Stunde = ereignis.getStunde();
            String Grund = ereignis.getGrund();
            String Lehrer = ereignis.getLehrer();

            fach.setText(Fach);
            kurs.setText(Kurs);
            stunde.setText(Stunde);
            grund.setText(Grund);
            lehrer.setText(Lehrer);
            zeichen.setImageResource(ereignis.getZeichen());
        }
    }


    public static class obtionenViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView editText;



        public obtionenViewHolder(View itemView) {
            super(itemView);
            editText = (EditText) itemView.findViewById(R.id.editText2);
            itemView.setOnClickListener(this);
        }

        public void onClick(View view){
            ConstraintLayout trigger = itemView.findViewById(R.id.trigger);
            RelativeLayout ZeigObtionsConstraint = itemView.findViewById(R.id.ZeigOptionsConstraint);
            ZeigObtionsConstraint.setVisibility(View.VISIBLE);
            trigger.setVisibility(View.INVISIBLE);
        }

        public void showObtionenDetails(Obtionen obtionen){
            String EditText = obtionen.getEditText();
            editText.setHint(EditText);
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