package app.stundenplan.ms.rats.ratsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class QPhase_create extends AppCompatActivity {
    private RecyclerView mrecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Object> NotenKlausurenListe = new ArrayList<>();
    private QPhase_adapter adapter;
    List<Memory_NotenKlausuren> NotenListQ11 = new ArrayList<>();
    List<Memory_NotenKlausuren> NotenListQ12 = new ArrayList<>();
    List<Memory_NotenKlausuren> NotenListQ21 = new ArrayList<>();
    List<Memory_NotenKlausuren> NotenListQ22 = new ArrayList<>();
    ImageView Back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences setting = this.getSharedPreferences("RatsVertretungsPlanApp", 0);

        setContentView(R.layout.qphasen_planer);

        linearLayoutManager = new LinearLayoutManager(getBaseContext());
        mrecyclerView = findViewById(R.id.myRecyckerview);

        NotenKlausurenListe.add(new QPhase_Overview());

        Back = findViewById(R.id.imageView);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QPhase_create.this, Settings.class);
                startActivity(i);
            }
        });

        if(!setting.contains("NotenKlausurenQ11")){
            if(setting.contains("Faecher")){
                HashSet<String> set = (HashSet<String>) setting.getStringSet("Faecher", new HashSet<String>());
                Object[] Faecher = set.toArray();
                for(int i = 0; i<set.size();i++) {
                    NotenKlausurenListe.add(new QPhase_selector(Faecher[i].toString(), "---", "---", "---", "---"));
                    NotenListQ11.add(new Memory_NotenKlausuren(Faecher[i].toString(), "", false, 0,0,0,0));
                    NotenListQ11.get(i).setFindetStatt(false);
                    NotenListQ11.get(i).setKursart("---");
                }
            }
            NotenKlausurenListe.add(new QPhase_selector("", "", "", "", ""));
            NotenListQ11.add(new Memory_NotenKlausuren("", "", false, 0,0,0,0));
            NotenListQ11.get(NotenListQ11.size()-1).setFindetStatt(false);
            NotenListQ11.get(NotenListQ11.size()-1).setKursart("---");
            while(15>NotenKlausurenListe.size()){
                NotenKlausurenListe.add(new QPhase_selector("", "", "", "", ""));
                NotenListQ11.add(new Memory_NotenKlausuren("", "", false, 0,0,0,0));
                NotenListQ11.get(NotenListQ11.size()-1).setFindetStatt(false);
                NotenListQ11.get(NotenListQ11.size()-1).setKursart("---");
            }
            Gson gson = new Gson();
            SharedPreferences.Editor editor = setting.edit();
            String json = gson.toJson(NotenListQ11);
            editor.putString("NotenKlausurenQ11", json);
            editor.putString("NotenKlausurenQ12", json);
            editor.putString("NotenKlausurenQ21", json);
            editor.putString("NotenKlausurenQ22", json);
            editor.apply();

        }else{
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Memory_NotenKlausuren>>() {}.getType();

            String jsonQ11 = setting.getString("NotenKlausurenQ11", null);
            String jsonQ12 = setting.getString("NotenKlausurenQ12", null);
            String jsonQ21 = setting.getString("NotenKlausurenQ21", null);
            String jsonQ22 = setting.getString("NotenKlausurenQ21", null);

            NotenListQ11 = gson.fromJson(jsonQ11, type);
            NotenListQ12 = gson.fromJson(jsonQ12, type);
            NotenListQ21 = gson.fromJson(jsonQ21, type);
            NotenListQ22 = gson.fromJson(jsonQ22, type);

            for(int i = 0; NotenListQ11.size()>i; i++){
                if(NotenListQ11.get(i).getFach().equals("")){
                    NotenKlausurenListe.add(new QPhase_selector("", "", "", "", ""));
                }else{
                    NotenKlausurenListe.add(new QPhase_selector(NotenListQ11.get(i).getFach(), NotenListQ11.get(i).getKursart(), NotenListQ12.get(i).getKursart(), NotenListQ21.get(i).getKursart(), NotenListQ22.get(i).getKursart()));
                }
            }

            while(15>NotenKlausurenListe.size()){
                NotenKlausurenListe.add(new QPhase_selector("", "", "", "", ""));
                NotenListQ11.add(new Memory_NotenKlausuren("", "", false, 0,0,0,0));
                NotenListQ11.get(NotenListQ11.size()-1).setFindetStatt(false);
                NotenListQ11.get(NotenListQ11.size()-1).setKursart("---");
                NotenListQ12.add(new Memory_NotenKlausuren("", "", false, 0,0,0,0));
                NotenListQ12.get(NotenListQ11.size()-1).setFindetStatt(false);
                NotenListQ12.get(NotenListQ11.size()-1).setKursart("---");
                NotenListQ21.add(new Memory_NotenKlausuren("", "", false, 0,0,0,0));
                NotenListQ21.get(NotenListQ11.size()-1).setFindetStatt(false);
                NotenListQ21.get(NotenListQ11.size()-1).setKursart("---");
                NotenListQ22.add(new Memory_NotenKlausuren("", "", false, 0,0,0,0));
                NotenListQ22.get(NotenListQ11.size()-1).setFindetStatt(false);
                NotenListQ22.get(NotenListQ11.size()-1).setKursart("---");
            }

            SharedPreferences.Editor editor = setting.edit();
            String json1 = gson.toJson(NotenListQ11);
            String json2 = gson.toJson(NotenListQ12);
            String json3 = gson.toJson(NotenListQ21);
            String json4 = gson.toJson(NotenListQ22);
            editor.putString("NotenKlausurenQ11", json1);
            editor.putString("NotenKlausurenQ12", json2);
            editor.putString("NotenKlausurenQ21", json3);
            editor.putString("NotenKlausurenQ22", json4);
            editor.apply();
        }




        mrecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new QPhase_adapter(getBaseContext(), NotenKlausurenListe);
        mrecyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
