package app.stundenplan.ms.rats.ratsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int TYPE_EREIGNIS=1,TYPE_Obtionen=2, TYPE_Datum=3;
    private List<Object> itemFeed = new ArrayList();
    private Context context;
    private int longClickDuration = 150;
    private boolean isLongPress = false;


    public ItemAdapter(Context context){
        this.context=context;
    }

    public void setitemFeed(List<Object> itemFeed){
        this.itemFeed = itemFeed;
    }


    @Override
    public int getItemViewType(int position) {

        if (itemFeed.get(position) instanceof Ereignis) {
            return TYPE_EREIGNIS;
        } else if (itemFeed.get(position) instanceof Obtionen) {
            return TYPE_Obtionen;
        } else if (itemFeed.get(position) instanceof Datum){
            return TYPE_Datum;
        }
        return -1;
    }
    



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = 0;
        RecyclerView.ViewHolder viewHolder;
        switch (viewType){
            case TYPE_EREIGNIS:
                layout=R.layout.ereignis_list_row;
                View ereignisView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(layout, parent, false);
                viewHolder=new ereignisViewHolder(ereignisView);
                break;
            case TYPE_Obtionen:
                layout=R.layout.vertretungsplan_obtions;
                final View ObtionenView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(layout, parent, false);
                viewHolder=new obtionenViewHolder(ObtionenView);
                break;
            case TYPE_Datum:
                layout=R.layout.vertretungsplan_date;
                View DateView= LayoutInflater
                        .from(parent.getContext())
                        .inflate(layout, parent, false);
                viewHolder= new DatumViewHolder(DateView);
                break;
            default:
                viewHolder=null;
                break;
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        int viewType=holder.getItemViewType();
        switch (viewType){
            case TYPE_EREIGNIS:
                Ereignis ereignis=(Ereignis) itemFeed.get(position);
                ((ereignisViewHolder)holder).showereignisDetails(ereignis);
                break;
            case TYPE_Obtionen:
                Obtionen Obtionen=(Obtionen)itemFeed.get(position);
                ((obtionenViewHolder)holder).showObtionenDetails(Obtionen);
                break;
            case TYPE_Datum:
                Datum datum=(Datum)itemFeed.get(position);
                ((DatumViewHolder)holder).showDatumDetails(datum);
                break;

        }
    }

    @Override
    public int getItemCount(){return itemFeed.size();}




    public class ereignisViewHolder extends RecyclerView.ViewHolder {
        private TextView fach;
        private TextView kurs;
        private TextView stunde;
        private TextView grund;
        private TextView lehrer;
        private ImageView zeichen;
        private ConstraintLayout frame;
        SharedPreferences settings;

        public ereignisViewHolder(View itemView) {
            super(itemView);

            fach =  itemView.findViewById(R.id.Stunde_Fach);
            kurs = itemView.findViewById(R.id.Kurs);
            stunde = itemView.findViewById(R.id.Stunde);
            grund = itemView.findViewById(R.id.Grund);
            lehrer = itemView.findViewById(R.id.FachTextView);
            zeichen = itemView.findViewById(R.id.Zeichen);
            frame= itemView.findViewById(R.id.frame);

            settings = context.getSharedPreferences("RatsVertretungsPlanApp", 0);

        }

        @SuppressLint("ClickableViewAccessibility")
        public void showereignisDetails(Ereignis ereignis){

            final String Fach = ereignis.getFach();
            final String Kurs = ereignis.getKurs();
            final String Stunde = ereignis.getStunde();
            final String Grund = ereignis.getGrund();
            final String Lehrer = ereignis.getLehrer();

            fach.setText(Fach);
            kurs.setText(Kurs);
            stunde.setText(Stunde);
            grund.setText(Grund);
            lehrer.setText(Lehrer);
            zeichen.setImageResource(ereignis.getZeichen());

            /*
            frame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                                    TextView tFach;
                                    TextView tKurs;
                                    TextView tRaum;
                                    TextView tLehrer;
                                    TextView tHinweis;
                                    final TextView tNichtmeinKurs;
                                    ConstraintLayout cnichtmeinkurs;
                                    settings = context.getSharedPreferences("RatsVertretungsPlanApp", 0);

                                    Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                                    vibrator.vibrate(50);
                                    final AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                                    LayoutInflater inflater = LayoutInflater.from(context);
                                    View mView = inflater.inflate(R.layout.vertretungsplan_popup, null);


                                    tFach = mView.findViewById(R.id.Titel);
                                    tKurs = mView.findViewById(R.id.Kurs);
                                    tLehrer = mView.findViewById(R.id.Lehrer);
                                    tHinweis = mView.findViewById(R.id.Hinweis);
                                    tNichtmeinKurs = mView.findViewById(R.id.NichtmeinKurs);
                                    cnichtmeinkurs = mView.findViewById(R.id.nichtmeinkurs);

                                    tFach.setText(Fach);
                                    tKurs.setText(Fach);
                                    tKurs.setText(Kurs);
                                    tLehrer.setText(Lehrer);
                                    tHinweis.setText(Grund);

                                    if(settings.contains("Kursliste")) {
                                        HashSet<String> meineKurse = (HashSet<String>) settings.getStringSet("Kursliste", new HashSet<String>());
                                        HashSet<String> manuellnichtmeineKurse = (HashSet<String>) settings.getStringSet("ManuellNichtMeineKurse", new HashSet<String>());
                                        HashSet<String> manuellmeineKurse = (HashSet<String>) settings.getStringSet("ManuellmeineKurse", new HashSet<String>());
                                        if (( manuellmeineKurse.contains(Kurs.replace("  ", " ").toUpperCase())||(meineKurse.contains(Kurs.replace("  ", " ").toUpperCase()) && !((manuellnichtmeineKurse.contains(Kurs.replace("  ", " ").toUpperCase())))))) {
                                            tNichtmeinKurs.setText("Dies ist nicht mein Kurs");
                                        }else{
                                            tNichtmeinKurs.setText("Dies ist mein Kurs");
                                        }
                                    }else{
                                        cnichtmeinkurs.setVisibility(View.GONE);
                                    }

                                    mBuilder.setView(mView);
                                    final AlertDialog dialog = mBuilder.create();

                                    dialog.show();

                                    cnichtmeinkurs.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (tNichtmeinKurs.getText() == "Dies ist nicht mein Kurs") {

                                                meinKurs(Kurs);
                                                dialog.dismiss();





                                            }else{


                                                dialog.dismiss();
                                                SharedPreferences settings1 = context.getSharedPreferences("RatsVertretungsPlanApp", 0);
                                                HashSet<String> nichtmeineKurse = new HashSet<String>();
                                                HashSet<String> meineKurse = new HashSet<String>();
                                                if(settings1.contains("ManuellmeineKurse")){
                                                    meineKurse = (HashSet<String>) settings1.getStringSet("ManuellmeineKurse", null);
                                                }

                                                if(settings1.contains("ManuellNichtMeineKurse")){
                                                    nichtmeineKurse = (HashSet<String>) settings1.getStringSet("ManuellNichtMeineKurse", null);
                                                }


                                                nichtmeineKurse.remove((Kurs.replace("  ", " ").toUpperCase()));
                                                meineKurse.add((Kurs.replace("  ", " ").toUpperCase()));


                                                SharedPreferences.Editor editor = settings1.edit();
                                                editor.putStringSet("ManuellmeineKurse", meineKurse);
                                                editor.putStringSet("ManuellNichtMeineKurse", nichtmeineKurse);
                                                editor.apply();
                                                VertretungsPlanMethoden.context.reload(true);
                                            }
                                        }
                                    });

                                }

            });{

            }
         */
        }


        public void meinKurs(String meinKurs){
            SharedPreferences settings1 = context.getSharedPreferences("RatsVertretungsPlanApp", 0);


            HashSet<String> meineKurse = (HashSet<String>) settings1.getStringSet("ManuellmeineKurse", new HashSet<String>());
            HashSet<String> nichtmeineKurse = (HashSet<String>) settings1.getStringSet("ManuellNichtMeineKurse", new HashSet<String>());


            nichtmeineKurse.add((meinKurs.replace("  ", " ").toUpperCase()));
            meineKurse.remove((meinKurs.replace("  ", " ").toUpperCase()));


            SharedPreferences.Editor editor = settings1.edit();
            editor.putStringSet("ManuellmeineKurse", meineKurse);
            editor.putStringSet("ManuellNichtMeineKurse", nichtmeineKurse);
            editor.apply();
            VertretungsPlanMethoden.context.reload(false);
        }
    }


    public class obtionenViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView editText;
        private Switch myswitch;
        SharedPreferences settings;

        obtionenViewHolder(View itemView) {
            super(itemView);
            settings = context.getSharedPreferences("RatsVertretungsPlanApp", 0);
            editText = (EditText) itemView.findViewById(R.id.editText2);
            myswitch = (Switch) itemView.findViewById(R.id.switch1);
            settings = context.getSharedPreferences("RatsVertretungsPlanApp",0);
            itemView.setOnClickListener(this);
            myswitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!myswitch.isChecked()){
                        if(settings.contains("Stundenliste")){
                            editText.setVisibility(View.GONE);
                            //Alle Stunden wurden deaktiviert

                            try {
                                VertretungsPlanMethoden.context.reload(false);
                            }catch (Exception e){
                                System.out.println(e.getMessage());
                            }
                        }
                        else{

                            Toast.makeText(context, "Du musst erst einen Stundenplan erstellen, um nur deine Stunden anzuzeigen", Toast.LENGTH_SHORT).show();
                            myswitch.setChecked(true);
                        }

                    }
                    else {
                        editText.setVisibility(View.VISIBLE);
                        try {
                            //Alle Stunden wurden aktiviert
                            VertretungsPlanMethoden.context.reload(true);
                        }catch(Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }
            });

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    //Teste ob editText.getText einer exestierenden Stufe entspricht und wenn ja Reload mit der Stufe.
                    //Die änderung darf nicht in die SharedPref. gehen.
                    String text;
                    if(editText.getText().toString().equals("")){
                        SharedPreferences settings = context.getSharedPreferences("RatsVertretungsPlanApp",0);
                        text = settings.getString("Stufe","");
                    }
                    else{
                        text = editText.getText().toString().toUpperCase();
                    }
                    VertretungsPlanMethoden.context.reload(true, text);
                }
            });


        }

        public void onClick(View view){
            ConstraintLayout trigger = itemView.findViewById(R.id.trigger);
            RelativeLayout ZeigObtionsConstraint = itemView.findViewById(R.id.ZeigOptionsConstraint);
            ZeigObtionsConstraint.setVisibility(View.VISIBLE);
            trigger.setVisibility(View.INVISIBLE);
            SharedPreferences settings = context.getSharedPreferences("RatsVertretungsPlanApp",0);
            myswitch.setChecked(!settings.contains("Stundenliste"));
            if(settings.contains("Stundenliste")){
                editText.setVisibility(View.GONE);
            }
        }




        public void showObtionenDetails(Obtionen obtionen){
            String EditText = obtionen.getEditText();
            editText.setHint(EditText);
            final SharedPreferences settings = context.getSharedPreferences("RatsVertretungsPlanApp",0);


        }

    }




    public class DatumViewHolder extends RecyclerView.ViewHolder {

        private TextView tdatum;

        public DatumViewHolder(View itemView) {
            super(itemView);
            tdatum = itemView.findViewById(R.id.Datum);
        }

        public void showDatumDetails(Datum datum){
            String Datum = datum.getDate();
            tdatum.setText(Datum);
        }
        
    }

}