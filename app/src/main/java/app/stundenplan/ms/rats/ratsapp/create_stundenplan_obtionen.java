package app.stundenplan.ms.rats.ratsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruben on 03.04.2018.
 */

public class create_stundenplan_obtionen  extends AppCompatActivity {
    private List<Object> StundenListe = new ArrayList<>();
    private List<Object> aStundenListe = new ArrayList<>();
    private List<Object> bStundenListe = new ArrayList<>();
    private List<Object> WocheBStundenListe = new ArrayList<>();

    @Override
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        setContentView(R.layout.create_stundenplan_obtions);


        final EditText MaxStunden = findViewById(R.id.editText4);
        final Switch switch_Zweiwöchentlich = findViewById(R.id.switch2);
        Button button = findViewById(R.id.create_stundenplan_button);

        final SharedPreferences settings = getSharedPreferences("RatsVertretungsPlanApp",0);


        if(settings.contains("MaxStunden")){
            MaxStunden.setText(Integer.toString(settings.getInt("MaxStunden", 0)));
        }
        if(settings.contains("zweiWöchentlich")){
            switch_Zweiwöchentlich.setChecked(settings.getBoolean("zweiWöchentlich", false));
        }

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!MaxStunden.getText().toString().equals("") && Integer.parseInt(MaxStunden.getText().toString()) >= 6 && Integer.parseInt(MaxStunden.getText().toString()) <= 13) {

                    if(!settings.contains("Stundenliste")) {
                        for (int i = 0; i < Integer.parseInt(MaxStunden.getText().toString()) * 5; i++) {
                            StundenListe.add(new Memory_Stunde(true, "", "", "", "", "", 0, false,0));
                        }
                    }
                    else{
                        String ajson;
                        String bjson;
                        Gson gson = new Gson();
                        ajson = settings.getString("Stundenliste", null);
                        Type type = new TypeToken<ArrayList<Memory_Stunde>>() {}.getType();
                        aStundenListe = gson.fromJson(ajson , type);


                        if(settings.getInt("MaxStunden", 0) > Integer.parseInt(MaxStunden.getText().toString())){

                            for(int j = 0; j < settings.getInt("MaxStunden", 0) - Integer.parseInt(MaxStunden.getText().toString()); j++){
                                aStundenListe.remove(settings.getInt("MaxStunden", 0)-j);
                            }

                        }
                        else if (settings.getInt("MaxStunden", 0) < Integer.parseInt(MaxStunden.getText().toString())){

                            for(int j = 0; j < settings.getInt("MaxStunden", 0) + Integer.parseInt(MaxStunden.getText().toString()); j++){
                                aStundenListe.add(new Memory_Stunde(true, "", "", "", "", "", 0, false,0));
                            }

                        }

                        if(settings.getBoolean("zweiWöchentlich", false)){
                            bjson = settings.getString("WocheBStundenListe", null);
                            bStundenListe = gson.fromJson(bjson , type);


                            if(settings.getInt("MaxStunden", 0) > Integer.parseInt(MaxStunden.getText().toString())){
                                for(int j = 0; j < settings.getInt("MaxStunden", 0) - Integer.parseInt(MaxStunden.getText().toString()); j++){
                                    bStundenListe.remove(settings.getInt("MaxStunden", 0)-j);
                                }
                            }
                            else if (settings.getInt("MaxStunden", 0) < Integer.parseInt(MaxStunden.getText().toString())){
                                for(int j = 0; j < settings.getInt("MaxStunden", 0) + Integer.parseInt(MaxStunden.getText().toString()); j++){
                                    bStundenListe.add(new Memory_Stunde(true, "", "", "", "", "", 0, false,0));
                                }
                            }
                        }
                    }

                    SharedPreferences.Editor editor = settings.edit();

                    boolean ZweiWöchentlich = switch_Zweiwöchentlich.isChecked();

                    if(ZweiWöchentlich) {
                        for (int i = 0; i < Integer.parseInt(MaxStunden.getText().toString()) * 5; i++) {
                            WocheBStundenListe.add(new Memory_Stunde(true, "", "", "", "", "", 0, false,0));

                            Gson bGson = new Gson();
                            String bJson;
                            if (!settings.contains("WocheBStundenListe")) {
                                bJson = bGson.toJson(WocheBStundenListe);

                            } else {
                                bJson = bGson.toJson(bStundenListe);
                            }
                            editor.putString("WocheBStundenListe", bJson);
                        }
                    }


                    Gson gson = new Gson();
                    String json;

                        if(!settings.contains("Stundenliste")){
                            json =  gson.toJson(StundenListe);

                        }
                        else{
                            json =  gson.toJson(aStundenListe);
                        }


                        editor.putString("WocheBStundenListe", json);
                    editor.putString("Stundenliste", json);

                    editor.apply();


                    settings.edit().putInt("MaxStunden", Integer.parseInt(MaxStunden.getText().toString())).apply();

                    settings.edit().putBoolean("zweiWöchentlich", ZweiWöchentlich).apply();

                    Intent i = new Intent(create_stundenplan_obtionen.this, create_stundenplan.class);
                    i.putExtra("Woche", 1);
                    startActivity(i);
                }
            }
        });

    }

}
