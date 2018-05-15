package app.stundenplan.ms.rats.ratsapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class QPhase_SELECTOR_Viewholder extends RecyclerView.ViewHolder{
    AutoCompleteTextView tFach;
    TextView tQ11;
    TextView tQ12;
    TextView tQ21;
    TextView tQ22;

    public QPhase_SELECTOR_Viewholder(View itemView) {
        super(itemView);
        tFach = itemView.findViewById(R.id.Fach);
        tQ11 = itemView.findViewById(R.id.Q11);
        tQ12 = itemView.findViewById(R.id.Q12);
        tQ21 = itemView.findViewById(R.id.Q21);
        tQ22 = itemView.findViewById(R.id.Q22);
    }

    public void showDetails(QPhase_selector pSelector){
        tQ11.setText(pSelector.getQ11());
        tQ12.setText(pSelector.getQ12());
        tQ21.setText(pSelector.getQ21());
        tQ22.setText(pSelector.getQ22());

    }
}
