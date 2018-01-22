package app.stundenplan.ms.rats.ratsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Stufenwahl extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stufenwahl);


        //Überprüft ob Stufe schon einmal eingestellt wurde

        try{
            SharedPreferences settings = getSharedPreferences("RatsVertretungsPlanApp", 0);
            if(settings.contains("Stufe")){
                Intent i = new Intent(Stufenwahl.this, vertretungsplan.class);
                i.putExtra("Stufe", settings.getString("Stufe", "DEFAULT"));
                startActivity(i);
            }
        }catch(ClassCastException e){
            finish();
        }

        final EditText Textfeld = findViewById(R.id.editText);
        final Button button = findViewById(R.id.button);
        final TextView fehler;
        fehler = findViewById(R.id.fehler);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!Textfeld.getText().toString().equals("")){
                    //Wenn Textfeld nicht leer ist
                    Intent i = new Intent(Stufenwahl.this, vertretungsplan.class);
                    i.putExtra("Stufe", Textfeld.getText().toString());
                    fehler.setVisibility(View.INVISIBLE);
                    startActivity(i);
                }else{


                    fehler.setVisibility(View.VISIBLE);

                }

            }
        });
    }




}