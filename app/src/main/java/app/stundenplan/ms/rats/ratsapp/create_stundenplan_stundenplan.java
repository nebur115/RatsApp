package app.stundenplan.ms.rats.ratsapp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 04.04.2018.
 */

@SuppressLint("ValidFragment")
public class create_stundenplan_stundenplan extends Fragment {

    public static boolean ZweiWöchentlich;
    public RecyclerView stundenplan_recyclerView;
    public int Height;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Object> StundenListe = new ArrayList<>();
    private ItemAdapter ItemAdapter;
    private List<Memory_Stunde> MemoryStundenListe = new ArrayList<>();
    int Woche;

    int MaxStunden;

    @SuppressLint("ValidFragment")
    public create_stundenplan_stundenplan(int pMaxStunden, boolean pZweiWöchentlich, int pWoche){
        MaxStunden = pMaxStunden;
        ZweiWöchentlich = pZweiWöchentlich;
        Woche = pWoche;
    }

    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {




        View view = inflater.inflate(R.layout.create_stundenplan_stundenplan, container,Boolean.parseBoolean(null));

        int menuHeight = 56 + 30;
        int StundenAnzahl = MaxStunden;
        SharedPreferences settings = getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0);

        Height = settings.getInt("Height", 0);

        stundenplan_recyclerView = view.findViewById(R.id.myRecyckerview);

        String json;
        Gson gson = new Gson();
        if(Woche==1){
            json = settings.getString("Stundenliste", null);
        }
        else
        {
            json = settings.getString("WocheBStundenListe", null);
        }

        Type type = new TypeToken<ArrayList<Memory_Stunde>>() {}.getType();
        MemoryStundenListe = gson.fromJson(json , type);

        StaggeredGridLayoutManager mystaggeredGridLayoutManager = new StaggeredGridLayoutManager(5, 1);

        stundenplan_recyclerView.setLayoutManager(mystaggeredGridLayoutManager);
        stundenplan_recyclerView.setNestedScrollingEnabled(false);

        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        float dpHeight = (((Height / displayMetrics.density) - menuHeight) / StundenAnzahl);

        int itemWidth = displayMetrics.widthPixels / 5;
        int itemHeight = (int) ((dpHeight * displayMetrics.density) + 0.49);

        StundenListe.add(new create_stundenplan_tag(1, itemWidth));
        StundenListe.add(new create_stundenplan_tag(2, itemWidth));
        StundenListe.add(new create_stundenplan_tag(3, itemWidth));
        StundenListe.add(new create_stundenplan_tag(4, itemWidth));
        StundenListe.add(new create_stundenplan_tag(5, itemWidth));

        for(int i=0; i<MaxStunden*5; i++) {

                if(MemoryStundenListe.get(i).isFreistunde()){
                    StundenListe.add(new create_stundenplan_createstunde((i+1)%5, itemHeight, itemWidth, Woche));
                }
                else

                {
                    StundenListe.add(new create_stundenplan_stunde((i+1)%5, itemHeight, itemWidth, MemoryStundenListe.get(i).getFachkürzel(), Woche));
                }
        }
        final create_stundenplan_adapter mycreate_stundenplan_adapter = new create_stundenplan_adapter(getActivity(), StundenListe);


        stundenplan_recyclerView.setAdapter(mycreate_stundenplan_adapter);

        return view;

    }

    public static boolean isZweiWöchentlich(){
        return ZweiWöchentlich;
    }



}
