package app.stundenplan.ms.rats.ratsapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class fragment_vertretungsplan extends Fragment {

    public fragment_vertretungsplan() {

    }
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.vertretungsplan_fragment, container, false);



    }
}
