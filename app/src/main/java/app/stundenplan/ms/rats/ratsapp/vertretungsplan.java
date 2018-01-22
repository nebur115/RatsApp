package app.stundenplan.ms.rats.ratsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class vertretungsplan extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertretungsplan);

        ImageButton btnlogin = (ImageButton)findViewById(R.id.btnlogin);



        Intent intent = getIntent();
        String Stufe = intent.getExtras().getString("Stufe");

        try{
            SharedPreferences settings = getSharedPreferences("RatsVertretungsPlanApp",0);
            if (!settings.contains("Stufe")) {
                if (!settings.edit().putString("Stufe", Stufe).commit())
                    finish();
            }
        }catch(ClassCastException e){
            //Falls settings.contains eine exception auslöst
        }

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();

            }
        });
    }

    public void openLogin() {
        super.onBackPressed();

    }

    @Override
    public void onBackPressed()
    {



    }




}
