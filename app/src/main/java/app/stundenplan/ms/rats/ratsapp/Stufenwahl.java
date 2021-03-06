package app.stundenplan.ms.rats.ratsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;


public class Stufenwahl extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stufenwahl);


        final EditText Textfeld = findViewById(R.id.editText);
        final Button button = findViewById(R.id.button);
        final TextView fehler;
        fehler = findViewById(R.id.fehler);


        //Überprüft ob Stufe schon einmal eingestellt wurde

        try{
            SharedPreferences settings = getSharedPreferences("RatsVertretungsPlanApp", 0);
            if(settings.contains("Stufe")){
                Intent i = new Intent(Stufenwahl.this, loading.class);
                i.putExtra("Stufe", "EXISTINGSTUNDE");
                startActivity(i);
            }
        }catch(ClassCastException e){
            finish();
        }



        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String Stufe = Textfeld.getText().toString();
                Calendar calendar = Calendar.getInstance();
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                int lastyear;


                if(month<7){
                    lastyear = year-1;
                }else{
                    lastyear = year;
                }
                SharedPreferences.Editor editor = getSharedPreferences("RatsVertretungsPlanApp", 0).edit();
                editor.putInt("year", lastyear);
                editor.apply();

                if(!Stufe.equals("")){
                    fehler.setVisibility(View.INVISIBLE);
                    ConstraintLayout frame = findViewById(R.id.testmain);
                    int height = frame.getHeight();
                    Intent i = new Intent(Stufenwahl.this, loading.class);
                    i.putExtra("Stufe", Stufe);
                    i.putExtra("Stufegewählt", true);
                    startActivity(i);
                }else{
                    fehler.setVisibility(View.VISIBLE);
                }

            }
        });







    }




}


