package app.stundenplan.ms.rats.ratsapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class fragment_vertretungsplan extends Fragment {


    public fragment_vertretungsplan() {

    }

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Object> ItemList = new ArrayList<>();
    private ItemAdapter ItemAdapter;
    TextView textStand;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.vertretungsplan_fragment, container, false);

        recyclerView = view.findViewById(R.id.Recycler_View);

        textStand = view.findViewById(R.id.textDatum);
        linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);


        init();
        return view;
    }

    private void init() {
        ItemAdapter = new ItemAdapter(getActivity());
        recyclerView.setAdapter(ItemAdapter);

        VertretungsPlanMethoden.context = this;

        SharedPreferences setting = this.getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0);
        VertretungsPlanMethoden.VertretungsPlan(ItemList, setting, setting.contains("Stundenliste"), this);

        ItemAdapter.setitemFeed(ItemList);
        ItemAdapter.notifyDataSetChanged();
    }

    public void reload(final boolean AlleStunden) {
        try {
            while (!VertretungsPlanMethoden.downloadedDaten) {}
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run(){
                    ItemAdapter.notifyDataSetChanged();
                    ItemList.clear();
                    VertretungsPlanMethoden.VertretungsPlan(ItemList, getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0), AlleStunden, null);

                    //Das hier aufrufen um Stand richtig anzuzeigen
                    textStand.setText("24.05.18 13:17");
                }
                                },0);


        }catch(Exception e){
            System.out.println("Debug: Fehler reload");
        }
    }
    public void reload(final boolean AlleStunden, final String Stufe) {

        try {
            while (!VertretungsPlanMethoden.downloadedDaten) {}
              final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run(){
                    ItemList.clear();
                    VertretungsPlanMethoden.VertretungsPlan(ItemList, getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0), AlleStunden, null, Stufe);
                    ItemAdapter.notifyDataSetChanged();
                }
            },0);

        }catch(Exception e){
            System.out.println("Debug: Fehler reload");
        }
    }
}