package app.stundenplan.ms.rats.ratsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ruben on 26.03.2018.
 */

public class Stundenplanadapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Object> StundenListe = new ArrayList<>();
    private Context context;
    private final static int TYPE_STUNDE=1, TYPE_FREISTUNDE=2, TYPE_Splate=3;



    public Stundenplanadapter(Context context, List<Object> pstundenListe){
        this.context = context;
        this.StundenListe = pstundenListe;
    }

    @Override
    public int getItemViewType(int position) {

        if (StundenListe.get(position) instanceof Stunde) {
            return TYPE_STUNDE;
        } else if (StundenListe.get(position) instanceof Freistunde) {
            return TYPE_FREISTUNDE;
        } else if (StundenListe.get(position) instanceof Spalte){
            return TYPE_Splate;
        }
        return -1;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder rvh;
        View layoutView;
        switch(viewType) {
            case TYPE_STUNDE:
                layoutView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.stunde, parent, false);
                rvh = new StundenplanViewHolder(layoutView);
                break;
            case TYPE_FREISTUNDE:
                layoutView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.freistunde, parent, false);
                rvh = new FreistundenViewHolder(layoutView);
                break;
            case TYPE_Splate:
                layoutView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.tag, parent, false);
                rvh = new Spaltenviewholder(layoutView);

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
            case TYPE_STUNDE:
                Stunde stunde=(Stunde) StundenListe.get(position);
                ((StundenplanViewHolder)myholder).showstundendetails(stunde);
                break;

            case TYPE_FREISTUNDE:
                Freistunde freistunde = (Freistunde) StundenListe.get(position);
                ((FreistundenViewHolder)myholder).showFreistundendetails(freistunde);
                break;
            case TYPE_Splate:
                Spalte spalte = (Spalte) StundenListe.get(position);
                ((Spaltenviewholder)myholder).showSpaltedetails(spalte);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return this.StundenListe.size();
    }

}

