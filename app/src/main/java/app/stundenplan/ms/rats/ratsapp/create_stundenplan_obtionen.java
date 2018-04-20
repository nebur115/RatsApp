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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 03.04.2018.
 */

public class create_stundenplan_obtionen  extends AppCompatActivity {
    private List<Object> StundenListe = new ArrayList<>();
    private List<Object> WocheBStundenListe = new ArrayList<>();
    int Woche;

    @Override
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        setContentView(R.layout.create_stundenplan_obtions);


        final EditText MaxStunden = findViewById(R.id.editText4);
        final Switch switch_Zweiwöchentlich = findViewById(R.id.switch2);
        Button button = findViewById(R.id.create_stundenplan_button);

        final SharedPreferences settings = getSharedPreferences("RatsVertretungsPlanApp",0);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!MaxStunden.getText().toString().equals("") && Integer.parseInt(MaxStunden.getText().toString()) >= 6 && Integer.parseInt(MaxStunden.getText().toString()) <= 13) {


                    for(int i=0; i<Integer.parseInt(MaxStunden.getText().toString())*5; i++) {
                        StundenListe.add(new Memory_Stunde(true, "","","", "", "","", false));
                    }

                    SharedPreferences.Editor editor = settings.edit();

                    boolean ZweiWöchentlich = switch_Zweiwöchentlich.isChecked();

                    if(ZweiWöchentlich){
                        for(int i=0; i<Integer.parseInt(MaxStunden.getText().toString())*5; i++) {
                            WocheBStundenListe.add(new Memory_Stunde(true, "","","", "", "","", false));
                        }
                        Gson bGson = new Gson();
                        String bJson = bGson.toJson(WocheBStundenListe);
                        editor.putString("WocheBStundenListe", bJson);
                    }

                    Gson gson = new Gson();
                    String json = gson.toJson(StundenListe);
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
