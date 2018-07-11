package app.stundenplan.ms.rats.ratsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;


/**
 * Created by User on 27.03.2018.
 */

public class loading extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);


        Intent intent = getIntent();
        final String Stufe = intent.getExtras().getString("Stufe");



        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    VertretungsPlanMethoden.downloadDaten(getSharedPreferences("RatsVertretungsPlanApp", 0), true);
                    while (!VertretungsPlanMethoden.downloadedDaten & !VertretungsPlanMethoden.offline) {
                    }
                    while(VertretungsPlanMethoden.stundenplanfrag ==null || VertretungsPlanMethoden.changed == -1){}
                    return null;
                }

                @Override
                public void onPostExecute(Void result) {
                    if(VertretungsPlanMethoden.changed == 1)
                            VertretungsPlanMethoden.stundenplanfrag.reload();
                }

            }.execute();
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





