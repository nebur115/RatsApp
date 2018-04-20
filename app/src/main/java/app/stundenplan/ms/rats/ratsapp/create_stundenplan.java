package app.stundenplan.ms.rats.ratsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * Created by User on 02.04.2018.
 */

public class create_stundenplan extends AppCompatActivity {

    public int shownWeek;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_stundenplan);

        Intent intent = getIntent();
        intent.getExtras().getInt("Woche");
        final FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();

        SharedPreferences settings = getSharedPreferences("RatsVertretungsPlanApp", 0);

        final ImageButton ungrade_woche_button = (ImageButton) findViewById(R.id.ungrade_woche_button);
        final ImageButton grade_woche_button = (ImageButton) findViewById(R.id.grade_woche_button);
        final TextView Woche = findViewById(R.id.Woche);

        final String Stufe = settings.getString("Stufe", null);



        Boolean Zweiwöchentlich = settings.getBoolean("zweiWöchentlich", false);
        int MaxStunden = settings.getInt("MaxStunden",0);
        grade_woche_button.setVisibility(View.GONE);

       final Fragment UngradeWoche ;

        if(!Zweiwöchentlich){
            ungrade_woche_button.setVisibility(View.GONE);
            UngradeWoche = null;
            Woche.setVisibility(View.GONE);
        }
        else
        {
            UngradeWoche = new create_stundenplan_stundenplan(MaxStunden, Zweiwöchentlich, 2);
            ft.add(R.id.stundenplan_create_framelayout, UngradeWoche);
            ft.hide(UngradeWoche);

        }

        final Fragment GradeWoche = new create_stundenplan_stundenplan(MaxStunden, Zweiwöchentlich, 1);
        ft.add(R.id.stundenplan_create_framelayout, GradeWoche);

        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

        ImageButton Speichern = (ImageButton) findViewById(R.id.Speichern);




        Speichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent i = new Intent(create_stundenplan.this, loading.class);
                i.putExtra("Stufe", Stufe);
                startActivity(i);
            }
        });




        ungrade_woche_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm2 = getSupportFragmentManager();
                FragmentTransaction ft2 = fm2.beginTransaction();
                ft2.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                ft2.show(UngradeWoche);
                ft2.hide(GradeWoche);
                ft2.commitNow();
                Woche.setText("ungrade Woche");
                grade_woche_button.setVisibility(View.VISIBLE);
                ungrade_woche_button.setVisibility(View.GONE);


            }
        });

        grade_woche_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm2 = getSupportFragmentManager();
                FragmentTransaction ft2 = fm2.beginTransaction();
                ft2.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                ft2.hide(UngradeWoche);
                ft2.show(GradeWoche);
                ft2.commitNow();
                Woche.setText("grade Woche");
                grade_woche_button.setVisibility(View.GONE);
                ungrade_woche_button.setVisibility(View.VISIBLE);

            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}
