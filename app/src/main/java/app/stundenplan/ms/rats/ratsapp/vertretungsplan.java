package app.stundenplan.ms.rats.ratsapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class vertretungsplan extends AppCompatActivity {


    SharedPreferences settings;
    static ProgressBar progressBar2;
    public String AktiveTap;
    static fragment_noten childnotenfragment;
    static SharedPreferences prefs;
    fragment_parent_stundenplan childstundenplanfragment;
    Fragment stundenplanfragment;
    FrameLayout SimpleFrameLayout;
    TabLayout tablayout;
    TextView Title;
    public static int Height;
    static boolean stundenplan_active;
    fragment_website websitefragment;
    boolean created = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        settings = getSharedPreferences("RatsVertretungsPlanApp", 0);
        stundenplan_active = true;
        setContentView(R.layout.activity_vertretungsplan);

        Intent intent = getIntent();
        String Stufe = intent.getExtras().getString("Stufe").toUpperCase();

        if (!(Stufe.equals("EXISTINGSTUNDE"))) {
            if (Stufe.charAt(0) != '0' && !(Stufe.equals("EF") || Stufe.equals("Q1") || Stufe.equals("Q2"))) {
                Stufe = "0" + Stufe;
            }
            progressBar2 = findViewById(R.id.progressBar2);
            try {
                SharedPreferences settings1 = getSharedPreferences("RatsVertretungsPlanApp", 0);

                if (!settings1.edit().putString("Stufe", Stufe).commit()) finish();

            } catch (ClassCastException e) {
                //Falls settings.contains eine exception auslöst
            }
        }
        settings.getInt("Height", 0);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(vertretungsplan.this);

        LayoutInflater inflater = vertretungsplan.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.datenschutz, null);

        prefs = getSharedPreferences("RatsVertretungsPlanApp", 0);

        if (!prefs.contains("datenschutzerklärung_zustimmung")) {
            dialogBuilder.setView(dialogView);
            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.setCancelable(false);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ich habe die Datenschutzerklärung gelesen und bin einverstanden", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("datenschutzerklärung_zustimmung", true);
                    editor.apply();
                    dialogInterface.dismiss();
                }
            });
            alertDialog.show();
        }

        SimpleFrameLayout = findViewById(R.id.simpleframelayout);
        tablayout = findViewById(R.id.tablayout);

        final TabLayout.Tab StundenplanTab = tablayout.newTab();
        StundenplanTab.setIcon(R.drawable.stundenplaninacctive);
        tablayout.addTab(StundenplanTab);

        final TabLayout.Tab VertretungsplanTab = tablayout.newTab();
        VertretungsplanTab.setIcon(R.drawable.vertretungsplaninactive);
        tablayout.addTab(VertretungsplanTab);

        final TabLayout.Tab NotenTab = tablayout.newTab();
        NotenTab.setIcon(R.drawable.noteninactive);
        tablayout.addTab(NotenTab);

        final TabLayout.Tab WebsiteTab = tablayout.newTab();
        WebsiteTab.setIcon(R.drawable.webiteinactive);
        tablayout.addTab(WebsiteTab);

        final Fragment vertretungsplanfragment = new fragment_vertretungsplan();
        websitefragment = new fragment_website();
        final Fragment notenfragment = new fragment_kalender();

        SharedPreferences settings3 = getSharedPreferences("RatsVertretungsPlanApp", 0);
        if (settings3.contains("Stundenliste")) {
            childstundenplanfragment = new fragment_parent_stundenplan();
            stundenplanfragment = childstundenplanfragment;

        } else {
            stundenplanfragment = new fragment_no_existing_stundenplan();
        }


        Title = findViewById(R.id.title);


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.simpleframelayout, stundenplanfragment, "stundenplan");
        ft.hide(stundenplanfragment);

        ft.add(R.id.simpleframelayout, vertretungsplanfragment, "vertretungsplan");
        ft.hide(vertretungsplanfragment);

        ft.add(R.id.simpleframelayout, notenfragment);
        ft.hide(notenfragment);

        ft.add(R.id.simpleframelayout, websitefragment);
        ft.hide(websitefragment);


        if (settings.contains("Stundenliste")) {
            ft.show(stundenplanfragment);
            AktiveTap = "Stundenplan";
            Title.setText("Stundenplan");
            StundenplanTab.setIcon(R.drawable.stundenplanacctive);
        }else{
            ft.show(vertretungsplanfragment);
            AktiveTap = "Vertretungsplan";
            AktiveTap = "Vertretungsplan";
            StundenplanTab.setIcon(R.drawable.vertretungsplanactive);
        }

        if (intent.getExtras().containsKey("Tab")) {
            switch (intent.getStringExtra("Tab")) {
                case "Stundenplan":
                    ft.show(stundenplanfragment);
                    Title.setText("Stundenplan");
                    AktiveTap = "Stundenplan";
                    break;
                case "Vertretungsplan":
                    ft.show(vertretungsplanfragment);
                    Title.setText("Vertretungsplan");
                    AktiveTap = "Vertretungsplan";
                    break;
                case "Noten/Arbeiten":
                    ft.show(notenfragment);
                    Title.setText("Kalender");
                    AktiveTap = "Noten/Arbeiten";
                    break;
                case "Homepage":
                    ft.show(websitefragment);
                    Title = findViewById(R.id.title);
                    Title.setText("Homepage");
                    AktiveTap = "Homepage";
                    break;
            }
        }


        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

        ImageButton settingsbutton = findViewById(R.id.imageButton2);

        settingsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(vertretungsplan.this, Settings.class);
                i.putExtra("Tab", AktiveTap);
                startActivity(i);
            }
        });


        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            public boolean noten_created = false;
            public boolean website_created = false;
            public boolean vertretungsplan_created = false;


            @SuppressLint("SetTextI18n")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(AktiveTap.equals("Noten/Arbeiten")) {
                    refreshStundenplan();

                }

                FragmentManager fm;
                FragmentTransaction ft;
                    fm = getSupportFragmentManager();
                    ft = fm.beginTransaction();
                    switch (AktiveTap) {
                        case "Stundenplan":
                            StundenplanTab.setIcon(R.drawable.stundenplaninactive);
                            ft.hide(stundenplanfragment);
                            stundenplan_active = false;
                            break;
                        case "Vertretungsplan":
                            VertretungsplanTab.setIcon(R.drawable.vertretungsplaninactive);
                            ft.hide(vertretungsplanfragment);
                            break;
                        case "Noten/Arbeiten":
                            fm = getSupportFragmentManager();
                            ft = fm.beginTransaction();
                            NotenTab.setIcon(R.drawable.noteninactive);
                            ft.hide(notenfragment);
                            break;
                        case "Homepage":
                            WebsiteTab.setIcon(R.drawable.webiteinactive);
                            ft.hide(websitefragment);
                            break;
                    }




                switch (tab.getPosition()) {
                    case 0:
                        ft.show(stundenplanfragment);
                        AktiveTap = "Stundenplan";
                        Title.setText("Stundenplan");
                        stundenplan_active = true;
                        break;

                    case 1:
                        ft.show(vertretungsplanfragment);
                        AktiveTap = "Vertretungsplan";
                        Title.setText("Vertretungsplan");
                        break;

                    case 2:
                        ft.show(notenfragment);
                        AktiveTap = "Noten/Arbeiten";
                        Title.setText("Kalender");
                        break;

                    case 3:
                        ft.show(websitefragment);
                        AktiveTap = "Homepage";
                        Title.setText("Homepage");
                        break;

                    default:

                        break;
                }


                if (AktiveTap == "Stundenplan") {
                    StundenplanTab.setIcon(R.drawable.stundenplanacctive);
                    if (!(childstundenplanfragment == null)) {
                        //childstundenplanfragment.timereload();
                        while (!VertretungsPlanMethoden.downloadedDaten && !VertretungsPlanMethoden.offline) {
                        }
                    }
                } else if (AktiveTap == "Vertretungsplan") {
                    VertretungsplanTab.setIcon(R.drawable.vertretungsplanactive);
                } else if (AktiveTap == "Noten/Arbeiten") {
                    NotenTab.setIcon(R.drawable.notenactive);
                } else if (AktiveTap == "Homepage") {
                    WebsiteTab.setIcon(R.drawable.webiteactive);
                }


                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });


        if (!settings.contains("Stundenliste")) {
            TabLayout.Tab tab = tablayout.getTabAt(1);
            tab.select();
        }


        if (intent.getExtras().containsKey("Tab")) {

            if (intent.getStringExtra("Tab").equals("Stundenplan")) {
                TabLayout.Tab tab = tablayout.getTabAt(0);
                tab.select();
            }

            if (intent.getStringExtra("Tab").equals("Vertretungsplan")) {
                TabLayout.Tab tab = tablayout.getTabAt(1);
                tab.select();
            }

            if (intent.getStringExtra("Tab").equals("Noten/Arbeiten")) {
                TabLayout.Tab tab = tablayout.getTabAt(2);
                tab.select();
            }

            if (intent.getStringExtra("Tab").equals("Homepage")) {
                TabLayout.Tab tab = tablayout.getTabAt(3);
                tab.select();
            }
        }


    }


    public void refreshStundenplan() {

        if (settings.contains("Stundenliste")){
            ((fragment_parent_stundenplan) stundenplanfragment).reload();
        }

        //Löst NullPointerExeption aus
        /*
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(fm.findFragmentByTag("stundenplan"));
        ft.commit();
        ft = fm.beginTransaction();
        if (settings.contains("Stundenliste")) {
            childstundenplanfragment = new fragment_parent_stundenplan();
            stundenplanfragment = childstundenplanfragment;
        } else {
            stundenplanfragment = new fragment_no_existing_stundenplan();
        }

        ft.add(R.id.simpleframelayout, stundenplanfragment, "stundenplan");
        ft.show(fm.findFragmentByTag("AktiveTap"));
        ft.show(stundenplanfragment);
        ft.commit();
     */
     }

    @Override
    public void onBackPressed() {
        if (AktiveTap.equals("Homepage")) {
            websitefragment.back();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(created){

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


            SimpleFrameLayout = findViewById(R.id.simpleframelayout);
            tablayout = findViewById(R.id.tablayout);

            tablayout.removeAllTabs();
            tablayout.clearOnTabSelectedListeners();
            SimpleFrameLayout.removeAllViews();


            final TabLayout.Tab StundenplanTab = tablayout.newTab();
            StundenplanTab.setIcon(R.drawable.stundenplaninactive);
            tablayout.addTab(StundenplanTab);

            final TabLayout.Tab VertretungsplanTab = tablayout.newTab();
            VertretungsplanTab.setIcon(R.drawable.vertretungsplaninactive);
            tablayout.addTab(VertretungsplanTab);

            final TabLayout.Tab NotenTab = tablayout.newTab();
            NotenTab.setIcon(R.drawable.noteninactive);
            tablayout.addTab(NotenTab);

            final TabLayout.Tab WebsiteTab = tablayout.newTab();
            WebsiteTab.setIcon(R.drawable.webiteinactive);
            tablayout.addTab(WebsiteTab);

            final Fragment vertretungsplanfragment = new fragment_vertretungsplan();
            websitefragment = new fragment_website();
            final Fragment notenfragment = new fragment_kalender();


            SharedPreferences settings3 = getSharedPreferences("RatsVertretungsPlanApp", 0);
            if (settings3.contains("Stundenliste")) {
                childstundenplanfragment = new fragment_parent_stundenplan();
                stundenplanfragment = childstundenplanfragment;

            } else {
                stundenplanfragment = new fragment_no_existing_stundenplan();
            }


            Title = findViewById(R.id.title);


            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            ft.add(R.id.simpleframelayout, stundenplanfragment, "stundenplan");
            ft.hide(stundenplanfragment);

            ft.add(R.id.simpleframelayout, vertretungsplanfragment, "vertretungsplan");
            ft.hide(vertretungsplanfragment);

            ft.add(R.id.simpleframelayout, notenfragment);
            ft.hide(notenfragment);

            ft.add(R.id.simpleframelayout, websitefragment);
            ft.hide(websitefragment);


            switch (AktiveTap) {
                    case "Stundenplan":
                        ft.show(stundenplanfragment);
                        Title.setText("Stundenplan");
                        tablayout.getTabAt(0).select();
                        StundenplanTab.setIcon(R.drawable.stundenplanacctive);
                        break;
                    case "Vertretungsplan":
                        ft.show(vertretungsplanfragment);
                        Title.setText("Vertretungsplan");
                        tablayout.getTabAt(1).select();
                        VertretungsplanTab.setIcon(R.drawable.vertretungsplanactive);
                        break;
                    case "Noten/Arbeiten":
                        ft.show(notenfragment);
                        Title.setText("Kalender");
                        tablayout.getTabAt(2).select();
                        NotenTab.setIcon(R.drawable.notenactive);
                        break;
                    case "Homepage":
                        ft.show(websitefragment);
                        Title = findViewById(R.id.title);
                        Title.setText("Homepage");
                        tablayout.getTabAt(3).select();
                        WebsiteTab.setIcon(R.drawable.webiteactive);
                        break;
                }

            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.setCustomAnimations(0,0);
            ft.commit();

            tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                public boolean noten_created = false;
                public boolean website_created = false;
                public boolean vertretungsplan_created = false;


                @SuppressLint("SetTextI18n")
                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();

                    switch (AktiveTap) {
                        case "Stundenplan":
                            StundenplanTab.setIcon(R.drawable.stundenplaninactive);
                            ft.hide(stundenplanfragment);
                            stundenplan_active = false;
                            break;
                        case "Vertretungsplan":
                            VertretungsplanTab.setIcon(R.drawable.vertretungsplaninactive);
                            ft.hide(vertretungsplanfragment);
                            break;
                        case "Noten/Arbeiten":
                            NotenTab.setIcon(R.drawable.noteninactive);
                            ft.hide(notenfragment);
                            break;
                        case "Homepage":
                            WebsiteTab.setIcon(R.drawable.webiteinactive);
                            ft.hide(websitefragment);
                            break;
                    }

                    switch (tab.getPosition()) {
                        case 0:
                            ft.show(stundenplanfragment);
                            AktiveTap = "Stundenplan";
                            Title.setText("Stundenplan");
                            stundenplan_active = true;
                            break;

                        case 1:
                            ft.show(vertretungsplanfragment);
                            AktiveTap = "Vertretungsplan";
                            Title.setText("Vertretungsplan");
                            break;

                        case 2:
                            ft.show(notenfragment);
                            AktiveTap = "Noten/Arbeiten";
                            Title.setText("Kalender");
                            break;

                        case 3:
                            ft.show(websitefragment);
                            AktiveTap = "Homepage";
                            Title.setText("Homepage");
                            break;

                        default:

                            break;
                    }


                    if (AktiveTap == "Stundenplan") {
                        StundenplanTab.setIcon(R.drawable.stundenplanacctive);
                        if (!(childstundenplanfragment == null)) {
                            //childstundenplanfragment.timereload();
                            while (!VertretungsPlanMethoden.downloadedDaten && !VertretungsPlanMethoden.offline) {
                            }
                        }
                    } else if (AktiveTap == "Vertretungsplan") {
                        VertretungsplanTab.setIcon(R.drawable.vertretungsplanactive);
                    } else if (AktiveTap == "Noten/Arbeiten") {
                        NotenTab.setIcon(R.drawable.notenactive);
                    } else if (AktiveTap == "Homepage") {
                        WebsiteTab.setIcon(R.drawable.webiteactive);
                    }

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


            //Alternative Option
            /*
            Intent i = new Intent(vertretungsplan.this, vertretungsplan.class);
            i.putExtra("Tab", AktiveTap);
            i.putExtra("Stufe", "EXISTINGSTUNDE");
            startActivity(i);
            */


        }else{
            created = true;
        }

    }

    public static int getheight() {
        return Height;
    }


    public static void notenreload() {
        if (!(childnotenfragment == null)) {
            childnotenfragment.newnumbers();
        }
    }





}








