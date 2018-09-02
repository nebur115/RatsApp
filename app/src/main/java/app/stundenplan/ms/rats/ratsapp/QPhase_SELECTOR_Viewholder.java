package app.stundenplan.ms.rats.ratsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class QPhase_SELECTOR_Viewholder extends RecyclerView.ViewHolder{
    AutoCompleteTextView tFach;
    TextView tQ11;
    TextView tQ12;
    TextView tQ21;
    TextView tQ22;
    ConstraintLayout cQ11;
    ConstraintLayout cQ12;
    ConstraintLayout cQ21;
    ConstraintLayout cQ22;
    ArrayAdapter<String> adapter;
    Context context;

    public String[] Fächer = {  "Bio",  "Bio Chemie",   "Chemie",   "Deutsch",  "Englisch", "Erdkunde", "ev. Religion",
            "Französisch",  "Geschichte",   "Italienisch",  "Informatik",   "Informatische Grundbildung", "kath. Religion", "Kunst", "Latein", "Literatur", "Mathematik", "MathePhysikInformatik", "Musik", "Niederländisch",
            "Pädagogik", "Physik", "Politik", "Philosophie", "Praktische Philosophie", "Spanisch", "Sport", "Sozialwissenschaften"};

    public QPhase_SELECTOR_Viewholder(View itemView) {
        super(itemView);
        tFach = itemView.findViewById(R.id.Fach);
        tQ11 = itemView.findViewById(R.id.Q11);
        tQ12 = itemView.findViewById(R.id.Q12);
        tQ21 = itemView.findViewById(R.id.Q21);
        tQ22 = itemView.findViewById(R.id.Q22);

        cQ11 = itemView.findViewById(R.id.cQ11);
        cQ12 = itemView.findViewById(R.id.cQ12);
        cQ21 = itemView.findViewById(R.id.cQ21);
        cQ22 = itemView.findViewById(R.id.cQ22);

        adapter = new ArrayAdapter<String>(itemView.getContext(), android.R.layout.simple_dropdown_item_1line, Fächer);
        context = itemView.getContext();
    }

    public void showDetails(QPhase_selector pSelector){

        String sQ11 = pSelector.getQ11();
        String sQ12 = pSelector.getQ12();
        String sQ21 = pSelector.getQ21();
        String sQ22 = pSelector.getQ22();
        String sFach = pSelector.getFach();

        SharedPreferences setting = context.getSharedPreferences("RatsVertretungsPlanApp", 0);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Memory_NotenKlausuren>>() {}.getType();

        String jsonQ11 = setting.getString("NotenKlausurenQ11", null);
        String jsonQ12 = setting.getString("NotenKlausurenQ12", null);
        String jsonQ21 = setting.getString("NotenKlausurenQ21", null);
        String jsonQ22 = setting.getString("NotenKlausurenQ21", null);


        List<Memory_NotenKlausuren> NotenListQ11 = new ArrayList<>();
        List<Memory_NotenKlausuren> NotenListQ12 = new ArrayList<>();
        List<Memory_NotenKlausuren> NotenListQ21 = new ArrayList<>();
        List<Memory_NotenKlausuren> NotenListQ22 = new ArrayList<>();


        NotenListQ11 = gson.fromJson(jsonQ11, type);
        NotenListQ12 = gson.fromJson(jsonQ12, type);
        NotenListQ21 = gson.fromJson(jsonQ21, type);
        NotenListQ22 = gson.fromJson(jsonQ22, type);


        if(!(NotenListQ11==null)){
            if(NotenListQ11.size()>getAdapterPosition()-1){

                if(!(NotenListQ11.get(getAdapterPosition() - 1) == null)) {
                 sQ11 =NotenListQ11.get(getAdapterPosition()-1).getKursart();
                }

                if(!(NotenListQ12.get(getAdapterPosition() - 1) == null)) {
                    sQ12 = NotenListQ12.get(getAdapterPosition() - 1).getKursart();
                }

                if(!(NotenListQ21.get(getAdapterPosition() - 1) == null)) {
                    sQ21 =NotenListQ21.get(getAdapterPosition()-1).getKursart();
                }

                if(!(NotenListQ22.get(getAdapterPosition() - 1) == null)) {
                    sQ22 = NotenListQ22.get(getAdapterPosition() - 1).getKursart();
                }

                if(!(NotenListQ11.get(getAdapterPosition() - 1) == null)) {
                    sFach = NotenListQ22.get(getAdapterPosition() - 1).getFach();
                }


            }
        }



        tFach.setText(sFach);
        tQ11.setText(sQ11);
        tQ12.setText(sQ12);
        tQ21.setText(sQ21);
        tQ22.setText(sQ22);




        tFach.setAdapter(adapter);
        tFach.setThreshold(1);

        cQ11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tFach.getText().toString().equals("")){
                    switch(tQ11.getText().toString()){
                        case"---":
                            tQ11.setText("mdl.");
                            break;
                        case "mdl.":
                            tQ11.setText("schr.");
                            break;
                        case "schr.":
                            tQ11.setText("LK");
                            break;
                        case "LK":
                            tQ11.setText("ZK");
                            break;
                        case "ZK":
                            tQ11.setText("---");
                            break;
                    }
                    save();
                }
            }
        });


        cQ12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tFach.getText().toString().equals("")) {
                    switch (tQ12.getText().toString()) {
                        case "---":
                            tQ12.setText("mdl.");
                            break;
                        case "mdl.":
                            tQ12.setText("schr.");
                            break;
                        case "schr.":
                            tQ12.setText("LK");
                            break;
                        case "LK":
                            tQ12.setText("ZK");
                            break;
                        case "ZK":
                            tQ12.setText("---");
                    }
                    save();
                }
            }
        });


        cQ21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tFach.getText().toString().equals("")) {
                    switch (tQ21.getText().toString()) {
                        case "---":
                            tQ21.setText("mdl.");
                            break;
                        case "mdl.":
                            tQ21.setText("schr.");
                            break;
                        case "schr.":
                            tQ21.setText("LK");
                            break;
                        case "LK":
                            tQ21.setText("ZK");
                            break;
                        case "ZK":
                            tQ21.setText("---");
                    }
                    save();
                }
            }
        });


        cQ22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tFach.getText().toString().equals("")) {
                    switch (tQ22.getText().toString()) {
                        case "---":
                            tQ22.setText("mdl.");
                            break;
                        case "mdl.":
                            tQ22.setText("schr.");
                            break;
                        case "schr.":
                            tQ22.setText("LK");
                            break;
                        case "LK":
                            tQ22.setText("ZK");
                            break;
                        case "ZK":
                            tQ22.setText("---");
                    }
                    save();
                }
            }
        });

        tFach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(tFach.getText().toString().equals("")){
                    tQ11.setText("");
                    tQ12.setText("");
                    tQ21.setText("");
                    tQ22.setText("");
                }else{
                    if(tQ11.getText().toString().equals("")){
                    tQ11.setText("---");
                    tQ12.setText("---");
                    tQ21.setText("---");
                    tQ22.setText("---");
                    }
                }
                save();
            }
        });
    }

    private void save(){

        SharedPreferences setting = context.getSharedPreferences("RatsVertretungsPlanApp", 0);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Memory_NotenKlausuren>>() {}.getType();

        String jsonQ11 = setting.getString("NotenKlausurenQ11", null);
        String jsonQ12 = setting.getString("NotenKlausurenQ12", null);
        String jsonQ21 = setting.getString("NotenKlausurenQ21", null);
        String jsonQ22 = setting.getString("NotenKlausurenQ22", null);

        List<Memory_NotenKlausuren> NotenListQ11 = new ArrayList<>();
        List<Memory_NotenKlausuren> NotenListQ12 = new ArrayList<>();
        List<Memory_NotenKlausuren> NotenListQ21 = new ArrayList<>();
        List<Memory_NotenKlausuren> NotenListQ22 = new ArrayList<>();

        NotenListQ11 = gson.fromJson(jsonQ11, type);
        NotenListQ12 = gson.fromJson(jsonQ12, type);
        NotenListQ21 = gson.fromJson(jsonQ21, type);
        NotenListQ22 = gson.fromJson(jsonQ22, type);


        switch (tQ11.getText().toString()){
            case "---":
                NotenListQ11.get(getAdapterPosition()-1).setFindetStatt(false);
                NotenListQ11.get(getAdapterPosition()-1).setFach(tFach.getText().toString());
                NotenListQ11.get(getAdapterPosition()-1).setWertung(0);
                NotenListQ11.get(getAdapterPosition()-1).setKursart("---");
                break;
            case "mdl.":
                NotenListQ11.get(getAdapterPosition()-1).setFindetStatt(true);
                NotenListQ11.get(getAdapterPosition()-1).setFach(tFach.getText().toString());
                NotenListQ11.get(getAdapterPosition()-1).setWertung(1);
                NotenListQ11.get(getAdapterPosition()-1).setKursart("mdl.");
                NotenListQ11.get(getAdapterPosition()-1).setSchrifltich(false);
                break;
            case "schr.":
                NotenListQ11.get(getAdapterPosition()-1).setFindetStatt(true);
                NotenListQ11.get(getAdapterPosition()-1).setFach(tFach.getText().toString());
                NotenListQ11.get(getAdapterPosition()-1).setWertung(1);
                NotenListQ11.get(getAdapterPosition()-1).setKursart("schr.");
                NotenListQ11.get(getAdapterPosition()-1).setSchrifltich(true);
                break;
            case "LK":
                NotenListQ11.get(getAdapterPosition()-1).setFindetStatt(true);
                NotenListQ11.get(getAdapterPosition()-1).setFach(tFach.getText().toString());
                NotenListQ11.get(getAdapterPosition()-1).setWertung(2);
                NotenListQ11.get(getAdapterPosition()-1).setKursart("LK");
                NotenListQ11.get(getAdapterPosition()-1).setSchrifltich(true);
                break;
            case "ZK":
                NotenListQ11.get(getAdapterPosition()-1).setFindetStatt(true);
                NotenListQ11.get(getAdapterPosition()-1).setFach(tFach.getText().toString());
                NotenListQ11.get(getAdapterPosition()-1).setWertung(1);
                NotenListQ11.get(getAdapterPosition()-1).setKursart("ZK");
                NotenListQ11.get(getAdapterPosition()-1).setSchrifltich(false);
        }

        switch (tQ12.getText().toString()){
            case "---":
                NotenListQ12.get(getAdapterPosition()-1).setFindetStatt(false);
                NotenListQ12.get(getAdapterPosition()-1).setFach(tFach.getText().toString());
                NotenListQ12.get(getAdapterPosition()-1).setWertung(0);
                NotenListQ12.get(getAdapterPosition()-1).setKursart("---");
                break;
            case "mdl.":
                NotenListQ12.get(getAdapterPosition()-1).setFindetStatt(true);
                NotenListQ12.get(getAdapterPosition()-1).setFach(tFach.getText().toString());
                NotenListQ12.get(getAdapterPosition()-1).setWertung(1);
                NotenListQ12.get(getAdapterPosition()-1).setKursart("mdl.");
                NotenListQ12.get(getAdapterPosition()-1).setSchrifltich(false);
                break;
            case "schr.":
                NotenListQ12.get(getAdapterPosition()-1).setFindetStatt(true);
                NotenListQ12.get(getAdapterPosition()-1).setFach(tFach.getText().toString());
                NotenListQ12.get(getAdapterPosition()-1).setWertung(1);
                NotenListQ12.get(getAdapterPosition()-1).setKursart("schr.");
                NotenListQ12.get(getAdapterPosition()-1).setSchrifltich(true);
                break;
            case "LK":
                NotenListQ12.get(getAdapterPosition()-1).setFindetStatt(true);
                NotenListQ12.get(getAdapterPosition()-1).setFach(tFach.getText().toString());
                NotenListQ12.get(getAdapterPosition()-1).setWertung(2);
                NotenListQ12.get(getAdapterPosition()-1).setKursart("LK");
                NotenListQ12.get(getAdapterPosition()-1).setSchrifltich(true);
                break;
            case "ZK":
                NotenListQ12.get(getAdapterPosition()-1).setFindetStatt(true);
                NotenListQ12.get(getAdapterPosition()-1).setFach(tFach.getText().toString());
                NotenListQ12.get(getAdapterPosition()-1).setWertung(1);
                NotenListQ12.get(getAdapterPosition()-1).setKursart("ZK");
                NotenListQ12.get(getAdapterPosition()-1).setSchrifltich(false);
        }


        switch (tQ21.getText().toString()){
            case "---":
                NotenListQ21.get(getAdapterPosition()-1).setFindetStatt(false);
                NotenListQ21.get(getAdapterPosition()-1).setFach(tFach.getText().toString());
                NotenListQ21.get(getAdapterPosition()-1).setWertung(0);
                NotenListQ21.get(getAdapterPosition()-1).setKursart("---");
                break;
            case "mdl.":
                NotenListQ21.get(getAdapterPosition()-1).setFindetStatt(true);
                NotenListQ21.get(getAdapterPosition()-1).setFach(tFach.getText().toString());
                NotenListQ21.get(getAdapterPosition()-1).setWertung(1);
                NotenListQ21.get(getAdapterPosition()-1).setKursart("mdl.");
                NotenListQ21.get(getAdapterPosition()-1).setSchrifltich(false);
                break;
            case "schr.":
                NotenListQ21.get(getAdapterPosition()-1).setFindetStatt(true);
                NotenListQ21.get(getAdapterPosition()-1).setFach(tFach.getText().toString());
                NotenListQ21.get(getAdapterPosition()-1).setWertung(1);
                NotenListQ21.get(getAdapterPosition()-1).setKursart("schr.");
                NotenListQ21.get(getAdapterPosition()-1).setSchrifltich(true);
                break;
            case "LK":
                NotenListQ21.get(getAdapterPosition()-1).setFindetStatt(true);
                NotenListQ21.get(getAdapterPosition()-1).setFach(tFach.getText().toString());
                NotenListQ21.get(getAdapterPosition()-1).setWertung(2);
                NotenListQ21.get(getAdapterPosition()-1).setKursart("LK");
                NotenListQ21.get(getAdapterPosition()-1).setSchrifltich(true);
                break;
            case "ZK":
                NotenListQ21.get(getAdapterPosition()-1).setFindetStatt(true);
                NotenListQ21.get(getAdapterPosition()-1).setFach(tFach.getText().toString());
                NotenListQ21.get(getAdapterPosition()-1).setWertung(1);
                NotenListQ21.get(getAdapterPosition()-1).setKursart("ZK");
                NotenListQ21.get(getAdapterPosition()-1).setSchrifltich(false);
        }


        switch (tQ22.getText().toString()){
            case "---":
                NotenListQ22.get(getAdapterPosition()-1).setFindetStatt(false);
                NotenListQ22.get(getAdapterPosition()-1).setFach(tFach.getText().toString());
                NotenListQ22.get(getAdapterPosition()-1).setWertung(0);
                NotenListQ22.get(getAdapterPosition()-1).setKursart("---");
                break;
            case "mdl.":
                NotenListQ22.get(getAdapterPosition()-1).setFindetStatt(true);
                NotenListQ22.get(getAdapterPosition()-1).setFach(tFach.getText().toString());
                NotenListQ22.get(getAdapterPosition()-1).setWertung(1);
                NotenListQ22.get(getAdapterPosition()-1).setKursart("mdl.");
                NotenListQ22.get(getAdapterPosition()-1).setSchrifltich(false);
                break;
            case "schr.":
                NotenListQ22.get(getAdapterPosition()-1).setFindetStatt(true);
                NotenListQ22.get(getAdapterPosition()-1).setFach(tFach.getText().toString());
                NotenListQ22.get(getAdapterPosition()-1).setWertung(1);
                NotenListQ22.get(getAdapterPosition()-1).setKursart("schr.");
                NotenListQ22.get(getAdapterPosition()-1).setSchrifltich(true);
                break;
            case "LK":
                NotenListQ22.get(getAdapterPosition()-1).setFindetStatt(true);
                NotenListQ22.get(getAdapterPosition()-1).setFach(tFach.getText().toString());
                NotenListQ22.get(getAdapterPosition()-1).setWertung(2);
                NotenListQ22.get(getAdapterPosition()-1).setKursart("LK");
                NotenListQ22.get(getAdapterPosition()-1).setSchrifltich(true);
                break;
            case "ZK":
                NotenListQ22.get(getAdapterPosition()-1).setFindetStatt(true);
                NotenListQ22.get(getAdapterPosition()-1).setFach(tFach.getText().toString());
                NotenListQ22.get(getAdapterPosition()-1).setWertung(1);
                NotenListQ22.get(getAdapterPosition()-1).setKursart("ZK");
                NotenListQ22.get(getAdapterPosition()-1).setSchrifltich(false);
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
}
