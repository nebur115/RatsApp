package app.stundenplan.ms.rats.ratsapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class vertretungsplan extends AppCompatActivity {



    static ProgressBar progressBar2;
    public String AktiveTap;
    public boolean noten_created =false;
    public boolean kalender_created = false;
    public boolean website_created = false;
    public boolean vertretungsplan_created = false;
    public boolean stundenplan_created = false;
    static fragment_noten childnotenfragment;

    fragment_parent_stundenplan childstundenplanfragment;
    Fragment stundenplanfragment;
    FrameLayout SimpleFrameLayout;
    TabLayout tablayout;
    TextView Title;
    public static int Height;
    static boolean stundenplan_active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences("RatsVertretungsPlanApp",0);
        stundenplan_active = true;
        setContentView(R.layout.activity_vertretungsplan);

        Intent intent = getIntent();
        String Stufe = intent.getExtras().getString("Stufe").toUpperCase();


        if(!(Stufe.equals("EXISTINGSTUNDE"))){
            if(Stufe.charAt(0) != '0' && !(Stufe.equals("EF") || Stufe.equals("Q1") || Stufe.equals("Q2"))){
                Stufe = "0"+ Stufe; }
            progressBar2 = findViewById(R.id.progressBar2);
            try{
                SharedPreferences settings1 = getSharedPreferences("RatsVertretungsPlanApp",0);
                if (!settings1.contains("Stufe")) {
                    if (!settings1.edit().putString("Stufe", Stufe).commit())
                        finish();
                }
            }catch(ClassCastException e){
                //Falls settings.contains eine exception auslöst
            }}
            settings.getInt("Height", 0);



        SimpleFrameLayout  = findViewById(R.id.simpleframelayout);
        tablayout = findViewById(R.id.tablayout);

        Stufe = settings.getString("Stufe", "");

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


        final Fragment vertretungsplanfragment = new fragment_vertretungsplan();
        final Fragment kalenderfragment = new fragment_kalender();
        final Fragment websitefragment = new fragment_website();

        final Fragment notenfragment;

        SharedPreferences settings3 = getSharedPreferences("RatsVertretungsPlanApp", 0);
            if(!(Stufe.equals("Q1") || Stufe.equals("Q2"))) {
                if (settings3.contains("Stundenliste")) {
                    childstundenplanfragment = new fragment_parent_stundenplan();
                    stundenplanfragment = childstundenplanfragment;
                    childnotenfragment = new fragment_noten();
                    notenfragment = childnotenfragment;
                } else {
                    stundenplanfragment = new fragment_no_existing_stundenplan();
                    notenfragment = new fragment_no_existing_stundenplan();

                }
            }else{
                if (settings3.contains("Stundenliste")) {
                    childstundenplanfragment = new fragment_parent_stundenplan();
                    stundenplanfragment = childstundenplanfragment;

                } else {
                    stundenplanfragment = new fragment_no_existing_stundenplan();
                }

                if(settings3.contains("NotenKlausuren")){
                    childnotenfragment = new fragment_noten();
                    notenfragment = childnotenfragment;
                }else{
                    notenfragment = new fragment_no_existing_QPhase();
                }
            }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.simpleframelayout, stundenplanfragment, "stundenplan");

        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();




        Title = findViewById(R.id.title);
        Title.setText("Stundenplan");

        AktiveTap = "Stundenplan";

        ImageButton settingsbutton = findViewById(R.id.imageButton2);

        settingsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(vertretungsplan.this, create_stundenplan.class);
                i.putExtra("Woche", 1);
                startActivity(i);
            }
        });



        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            public boolean noten_created =false;
            public boolean kalender_created = false;
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
                    case "Kalender":
                        KalenderTab.setIcon(R.drawable.kalenderinaktive);
                        ft.hide(kalenderfragment);
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
                        if(vertretungsplan_created){
                            ft.show(vertretungsplanfragment);
                        }
                        else{

                            ft.add(R.id.simpleframelayout, vertretungsplanfragment);
                            vertretungsplan_created = true;
                        }


                        AktiveTap = "Vertretungsplan";
                        Title.setText("Vertretungsplan");
                        break;

                    case 2:

                        if(noten_created){
                            ft.show(notenfragment);
                        }
                        else{
                            ft.add(R.id.simpleframelayout, notenfragment);
                            noten_created = true;
                        }

                        AktiveTap = "Noten/Arbeiten";
                        Title.setText("Klausuren und Arbeiten");
                        break;

                    case 3:

                        if(kalender_created){
                            ft.show(kalenderfragment);
                        }
                        else{
                            ft.add(R.id.simpleframelayout, kalenderfragment);
                            kalender_created =true;
                        }

                        AktiveTap = "Kalender";
                        Title.setText("Kalender");

                        break;
                    case 4:

                        if(website_created){
                            ft.show(websitefragment);
                        }
                        else{
                            ft.add(R.id.simpleframelayout, websitefragment);
                            website_created =true;
                        }
                        AktiveTap = "Homepage";
                        Title.setText("Homepage");
                        break;

                    default:
                        ft.show(stundenplanfragment);
                        Title.setText("Stundenplan");
                        AktiveTap = "Stundenplan";
                        break;
                }


                if (AktiveTap == "Stundenplan"){
                    StundenplanTab.setIcon(R.drawable.stundenplanacctive);
                    if(!(childstundenplanfragment==null)){
                    childstundenplanfragment.reload();}
                }
                else if(AktiveTap == "Vertretungsplan") {
                    VertretungsplanTab.setIcon(R.drawable.vertretungsplanactive);
                }
                else if(AktiveTap == "Noten/Arbeiten") {
                    NotenTab.setIcon(R.drawable.notenactive);
                }
                else if(AktiveTap == "Kalender") {
                    KalenderTab.setIcon(R.drawable.kalenderaktive);
                }
                else if(AktiveTap == "Homepage") {
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


        if(!settings.contains("Stundenliste")){
            TabLayout.Tab tab = tablayout.getTabAt(1);
            tab.select();
        }

    }

    @Override
    public void onBackPressed() {

    }

    public static boolean isStundenlanactive()  {
        return stundenplan_active;
    }
    public static int getheight()  {
        return Height;
    }




    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        SharedPreferences settings3 = getSharedPreferences("RatsVertretungsPlanApp",0);
        if (settings3.contains("Stundenliste")) {
            super.dispatchTouchEvent(ev);
            return OnSwipeTouchListener.getGestureDetector().onTouchEvent(ev);
        }
        else {
            return super.dispatchTouchEvent(ev);
        }

    }

    public static void zeigeLaden(){
        progressBar2.setVisibility(View.GONE);

    }

    public static void notenreload(){
        if(!(childnotenfragment==null)) {
            childnotenfragment.newnumbers();
        }
    }

    public static void versteckeLaden(){
        progressBar2.setVisibility(View.GONE);
    }



    protected void onResume() {

        if(!(childstundenplanfragment==null)) {
            childstundenplanfragment.reload();
        }

        super.onResume();



    }
}






