package app.stundenplan.ms.rats.ratsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by User on 27.03.2018.
 */

public class loading extends AppCompatActivity {

    String Stufe;
    Date currentTime;
    SharedPreferences pref;
    EditText Passworteingabe;
    String PasswordAnswer;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        currentTime = Calendar.getInstance().getTime();
        pref = getSharedPreferences("RatsVertretungsPlanApp", 0);
        setContentView(R.layout.loading);

        Intent intent = getIntent();
        Stufe  = intent.getExtras().getString("Stufe");

        if(pref.contains("Passwort")){  // Prüft ob Passwort bereits eingegeben wurde
            pass();
        }else {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(loading.this);

            LayoutInflater inflater = loading.this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.passwort_alertdialog, null);
            dialogBuilder.setView(dialogView);
            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.setCancelable(false);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(Passwort_Check(Passworteingabe.getText().toString().toLowerCase())){

                        //Sichert Passwort in der SharedPref.
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("Passwort", Passworteingabe.getText().toString().toLowerCase());
                        editor.apply();

                        //Lasst nutzer weiter gehen
                        pass();
                    }else{
                        Toast.makeText(getBaseContext(), "Falsches Passwort!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertDialog.show();
            Passworteingabe = alertDialog.findViewById(R.id.passwort);


        }


    }


    public boolean Passwort_Check(final String input) {
        final String Ziel = VertretungsPlanMethoden.baseDomain+"check.php";
        final SpeicherVerwaltung s = new SpeicherVerwaltung(pref);
        try {
            Thread download = new HandlerThread("DownloadHandler") {
                @Override
                public void run() {
                    try {
                        RequestBody rq = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("password", input)
                                .build();
                        Request request = new Request.Builder()
                                .url(Ziel).header("Content-Type", "application/json; charset=utf-8")
                                .post(rq)
                                .build();
                        PasswordAnswer = new OkHttpClient()
                                .newCall(request)
                                .execute()
                                .body()
                                .string();
                    } catch (Exception e) {
                        PasswordAnswer = "";
                    }
                }
            };

            download.start();
            download.join();
            if (PasswordAnswer.equals("true")) {
                return true;
            }
        }catch(Exception e) {}
        return false;
    }



    public void pass(){
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
                        i.putExtra("Date", currentTime);
                        startActivity(i);


                    }
                }, SPLASH_DISPLAY_LENGTH);
            }
        });



    }


}





