package app.stundenplan.ms.rats.ratsapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class fragment_noten extends Fragment {
    private RecyclerView mrecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Object> NotenList = new ArrayList<>();
    private noten_adapter adapter;


    public fragment_noten() {

    }
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.noten_fragment,container,false);

        SharedPreferences setting = this.getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        mrecyclerView = view.findViewById(R.id.recyclerview_noten);

        mrecyclerView.setLayoutManager(linearLayoutManager);
        String json;
        Gson gson = new Gson();

        json = setting.getString("NotenKlausuren", null);
        Type type = new TypeToken<ArrayList<Memory_NotenKlausuren>>() {}.getType();
        NotenList = gson.fromJson(json , type);

        adapter = new noten_adapter(getActivity(),NotenList);


        mrecyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        return view;
    }
}
