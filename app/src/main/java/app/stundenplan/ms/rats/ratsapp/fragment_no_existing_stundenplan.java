package app.stundenplan.ms.rats.ratsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * Created by User on 02.04.2018.
 */

public class fragment_no_existing_stundenplan extends Fragment {

    public fragment_no_existing_stundenplan() {
    }


    @Override
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        View view = inflater.inflate(R.layout.fragment_button_create_stundenplan, container,Boolean.parseBoolean(null));
        Button button = view.findViewById(R.id.button_create_stundenplan);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getContext(), create_stundenplan_obtionen.class);
                startActivity(i);
            }
        });

        return view;
}

}
