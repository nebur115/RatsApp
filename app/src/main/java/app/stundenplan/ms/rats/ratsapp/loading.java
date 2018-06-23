package app.stundenplan.ms.rats.ratsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;

import java.util.HashSet;


/**
 * Created by User on 27.03.2018.
 */

public class loading extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);


        Intent intent = getIntent();
        final String Stufe = intent.getExtras().getString("Stufe");

        SharedPreferences shared1 = getSharedPreferences("ManuelleKurse", 0);

        SharedPreferences shared2 = getSharedPreferences("RatsVertretungsPlanApp",0);



        HashSet<String> meineKurse = (HashSet<String>) shared1.getStringSet("ManuellmeineKurse", new HashSet<String>());
        HashSet<String> nichtmeineKurse = (HashSet<String>) shared1.getStringSet("ManuellNichtMeineKurse", new HashSet<String>());

        SharedPreferences.Editor editor = shared2.edit();

        editor.putStringSet("ManuellmeineKurse", meineKurse);
        editor.putStringSet("ManuellNichtMeineKurse", nichtmeineKurse);

        editor.apply();


        try {
            Thread download = new HandlerThread("DownloadHandler") {
                @Override
                public void run() {
                    try{
                        VertretungsPlanMethoden.downloadDaten(getSharedPreferences("RatsVertretungsPlanApp", 0));
                    }catch(Exception e){
                        System.out.println("Debug: Fehler beim Download in loading.java");
                    }
                }
            };
            download.start();
        }catch(Exception e){

        }
        ConstraintLayout loading_frame = findViewById(R.id.loading_frame);
    final ViewTreeObserver observer = loading_frame.getViewTreeObserver();
                observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        final int SPLASH_DISPLAY_LENGTH = 10;

                        new Handler().postDelayed(new Runnable(){
                            @Override
                            public void run() {


                                ConstraintLayout loading_frame = findViewById(R.id.loading_frame);
                                int Height = loading_frame.getHeight();
                                final SharedPreferences settings = getSharedPreferences("RatsVertretungsPlanApp",0);
                                settings.edit().putInt("Height", Height).apply();
                                Intent i = new Intent(loading.this, vertretungsplan.class);
                                i.putExtra("Stufe", Stufe);

                                startActivity(i);


                            }
                        }, SPLASH_DISPLAY_LENGTH);
                    }
                });





    }


}





