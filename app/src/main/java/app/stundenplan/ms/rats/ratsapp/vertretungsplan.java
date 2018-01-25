package app.stundenplan.ms.rats.ratsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;


public class vertretungsplan extends AppCompatActivity {

    FrameLayout SimpleFrameLayout;
    TabLayout tablayout;
    TextView Title;




    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertretungsplan);


        Title = (TextView)findViewById(R.id.title);
        Title.setText("Stundenplan");



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


        ImageButton btnlogin = findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vertretungsplan.super.onBackPressed();



            }
        });



        SimpleFrameLayout  = (FrameLayout) findViewById(R.id.simpleframelayout);
        tablayout = (TabLayout) findViewById(R.id.tablayout);

        final TabLayout.Tab StundenplanTab= tablayout.newTab();
        StundenplanTab.setIcon(R.drawable.stundenplanacctive);
        tablayout.addTab(StundenplanTab);

        final TabLayout.Tab VertretungsplanTab= tablayout.newTab();
        VertretungsplanTab.setIcon(R.drawable.vertretungsplaninactive);
        tablayout.addTab(VertretungsplanTab);

        final TabLayout.Tab NotenTab= tablayout.newTab();
        NotenTab.setIcon(R.drawable.noteninactive);
        tablayout.addTab(NotenTab);

        final TabLayout.Tab KalenderTab= tablayout.newTab();
        KalenderTab.setIcon(R.drawable.kalenderinaktive);
        tablayout.addTab(KalenderTab);

        final TabLayout.Tab WebsiteTab= tablayout.newTab();
        WebsiteTab.setIcon(R.drawable.webiteinactive);
        tablayout.addTab(WebsiteTab);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.simpleframelayout, new fragment_stundenplan());
        ft.commit();

        final String[] AktiveTap = {"Stundenplan"};




        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (AktiveTap[0] == "Stundenplan"){
                    StundenplanTab.setIcon(R.drawable.stundenplaninactive);
                }
                else if(AktiveTap[0] == "Vertretungsplan") {
                    VertretungsplanTab.setIcon(R.drawable.vertretungsplaninactive);
                }
                else if(AktiveTap[0] == "Noten/Arbeiten") {
                    NotenTab.setIcon(R.drawable.noteninactive);
                }
                else if(AktiveTap[0] == "Kalender") {
                    KalenderTab.setIcon(R.drawable.kalenderinaktive);
                }
                else if(AktiveTap[0] == "Homepage") {
                    WebsiteTab.setIcon(R.drawable.webiteinactive);
                }



                Fragment fragment = new fragment_stundenplan();
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new fragment_stundenplan();
                        AktiveTap[0] = "Stundenplan";
                        Title.setText("Stundenplan");
                        break;
                    case 1:
                        fragment = new fragment_vertretungsplan();
                        AktiveTap[0] = "Vertretungsplan";
                        Title.setText("Vertretungsplan");
                        break;
                    case 2:
                        fragment = new fragment_noten();
                        AktiveTap[0] = "Noten/Arbeiten";
                        Title.setText("Noten/Arbeiten");
                        break;
                    case 3:
                        fragment = new fragment_kalender();
                        AktiveTap[0] = "Kalender";
                        Title.setText("Kalender");

                        break;
                    case 4:
                        fragment = new fragment_website();
                        AktiveTap[0] = "Homepage";
                        Title.setText("Homepage");
                        break;
                    default: fragment = new fragment_stundenplan();
                        Title.setText("Stundenplan");
                        Title.setText("Stundenplan");
                        break;
                }


                if (AktiveTap[0] == "Stundenplan"){
                    StundenplanTab.setIcon(R.drawable.stundenplanacctive);
                }
                else if(AktiveTap[0] == "Vertretungsplan") {
                    VertretungsplanTab.setIcon(R.drawable.vertretungsplanactive);
                }
                else if(AktiveTap[0] == "Noten/Arbeiten") {
                    NotenTab.setIcon(R.drawable.notenactive);
                }
                else if(AktiveTap[0] == "Kalender") {
                    KalenderTab.setIcon(R.drawable.kalenderaktive);
                }
                else if(AktiveTap[0] == "Homepage") {
                    WebsiteTab.setIcon(R.drawable.webiteactive);
                }



                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleframelayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }


        });




    }


    public void onBackPressed()
    {

    }


}

