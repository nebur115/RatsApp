package app.stundenplan.ms.rats.ratsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class QPhase_create extends AppCompatActivity {
    private RecyclerView mrecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Object> NotenKlausurenListe = new ArrayList<>();
    private QPhase_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.qphasen_planer);

        linearLayoutManager = new LinearLayoutManager(getBaseContext());
        mrecyclerView = findViewById(R.id.myRecyckerview);

        NotenKlausurenListe.add(new QPhase_Overview());

        for(int i = 0; i<12;i++) {
            NotenKlausurenListe.add(new QPhase_selector("Deutsch", "schr.", "schr.", "schr.", "schr."));
        }


        mrecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new QPhase_adapter(getBaseContext(), NotenKlausurenListe);
        mrecyclerView.setAdapter(adapter);
    }
}
