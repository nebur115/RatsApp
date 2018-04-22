package app.stundenplan.ms.rats.ratsapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class fragment_vertretungsplan extends Fragment {


    public fragment_vertretungsplan() {

    }

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Object> ItemList = new ArrayList<>();
    private ItemAdapter ItemAdapter;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.vertretungsplan_fragment, container, false);

        recyclerView = view.findViewById(R.id.Recycler_View);


        linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);


        init();
        return view;
    }

    private void init() {
        ItemAdapter = new ItemAdapter(getActivity());
        recyclerView.setAdapter(ItemAdapter);
        VertretungsPlanMethoden.context = this;
        VertretungsPlanMethoden.VertretungsPlan(ItemList, this.getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0), false, this);

        ItemAdapter.setitemFeed(ItemList);
        ItemAdapter.notifyDataSetChanged();
    }

    public void reload(boolean AlleStunden){
        while(!VertretungsPlanMethoden.downloadedDaten) {}
        ItemList.clear();
        VertretungsPlanMethoden.VertretungsPlan(ItemList ,this.getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0), AlleStunden, null);
        ItemAdapter.notifyDataSetChanged();
    }

}