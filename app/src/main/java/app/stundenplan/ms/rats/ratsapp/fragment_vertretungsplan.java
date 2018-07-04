package app.stundenplan.ms.rats.ratsapp;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class fragment_vertretungsplan extends Fragment {
    SwipeRefreshLayout mSwipeRefreshLayout;

    public fragment_vertretungsplan() {

    }

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Object> ItemList = new ArrayList<>();
    private ItemAdapter ItemAdapter;
    TextView textStand;
    SharedPreferences setting;

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

        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();

            }
        });


        init();
        return view;
    }

    private void init() {
        ItemAdapter = new ItemAdapter(getActivity());
        recyclerView.setAdapter(ItemAdapter);

        VertretungsPlanMethoden.context = this;

        setting = this.getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0);
        VertretungsPlanMethoden.all = !setting.contains("Stundenliste");
        VertretungsPlanMethoden.VertretungsPlan(ItemList, setting, setting.contains("Stundenliste"), this);

        ItemAdapter.setitemFeed(ItemList);
        ItemAdapter.notifyDataSetChanged();
    }

    public void reload(final boolean AlleStunden) {
        try {
            new AsyncTask<Void, Void, Void>(){
                @Override
                protected Void doInBackground(Void... voids) {
                    while (!VertretungsPlanMethoden.downloadedDaten & !VertretungsPlanMethoden.offline) {}
                    return null;
                }
                @Override
                public void onPostExecute(Void result) {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ItemAdapter.notifyDataSetChanged();
                            ItemList.clear();
                            VertretungsPlanMethoden.VertretungsPlan(ItemList, getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0), AlleStunden, null);
                        }
                    }, 0);
                    textStand.setText(new SpeicherVerwaltung(setting).getString("Stand").replace("Stand:", ""));
                }
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reload(final boolean AlleStunden, final String Stufe) {

        try {
            new AsyncTask<Void, Void, Void>(){
                @Override
                protected Void doInBackground(Void... voids) {
                    while (!VertretungsPlanMethoden.downloadedDaten & !VertretungsPlanMethoden.offline) {}
                    return null;
                }
                @Override
                public void onPostExecute(Void result) {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ItemList.clear();
                            VertretungsPlanMethoden.VertretungsPlan(ItemList, getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0), AlleStunden, null, Stufe);
                            ItemAdapter.notifyDataSetChanged();
                        }
                    }, 0);
                    textStand.setText(new SpeicherVerwaltung(setting).getString("Stand").replace("Stand:", ""));
                }
            }.execute();
        } catch(Exception e){
            System.out.println("Debug: Fehler reload");
        }
    }

        void refreshItems(){
        try{
            /*new Runnable() {
                @Override
                public void run() {
                    VertretungsPlanMethoden.downloadedDaten = false;
                    VertretungsPlanMethoden.offline = false;
                    VertretungsPlanMethoden.downloadDaten(setting, false);
                    while (!VertretungsPlanMethoden.downloadedDaten & !VertretungsPlanMethoden.offline) {
                    }
                    ItemList.clear();
                    VertretungsPlanMethoden.VertretungsPlan(ItemList, getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0), VertretungsPlanMethoden.all, null);
                    ItemAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }.run();*/
        new DownloadTask().execute();


        }catch(Exception e){
        e.printStackTrace();
        }

        }
public void Finished(){
        ItemAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
        }

class DownloadTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... voids) {
        VertretungsPlanMethoden.downloadedDaten = false;
        VertretungsPlanMethoden.offline = false;
        VertretungsPlanMethoden.downloadDaten(setting, false);
        while (!VertretungsPlanMethoden.downloadedDaten & !VertretungsPlanMethoden.offline) {
        }
        ItemList.clear();
        VertretungsPlanMethoden.VertretungsPlan(ItemList, getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0), VertretungsPlanMethoden.all, null);
        return null;
    }

    @Override
    public void onPostExecute(Void result) {
        Finished();
    }
}
}

