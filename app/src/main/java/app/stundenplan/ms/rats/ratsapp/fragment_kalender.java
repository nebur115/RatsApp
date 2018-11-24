package app.stundenplan.ms.rats.ratsapp;

import android.support.v7.widget.helper.ItemTouchHelper;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class fragment_kalender extends Fragment {
    public String[] Fächer = {"Bio", "Bio Chemie", "Chemie", "Deutsch", "Englisch", "Erdkunde", "ev. Religion", "Französisch", "Geschichte", "Italienisch", "Informatik", "Informatische Grundbildung", "kath. Religion", "Kunst", "Latein", "Literatur", "Mathematik", "MathePhysikInformatik", "Musik", "Niederländisch", "Pädagogik", "Physik", "Politik", "Philosophie", "Praktische Philosophie", "Spanisch", "Sport", "Sozialwissenschaften"};
    TextView ActiveDateTextView;
    RecyclerView recyclerView;
    FloatingActionButton button;
    List<kalender_event> kalenderListe;
    String DateAddition;
    Spinner Type;
    ConstraintLayout cTitel;
    ConstraintLayout cFach;
    ConstraintLayout cDatum;
    ConstraintLayout cZeitraum;
    ConstraintLayout cNotiz;
    AutoCompleteTextView Fach;
    EditText Titel;
    TextView Datum;
    TextView ZeitraumBeginn;
    TextView ZeitraumEnde;
    EditText Notiz;
    private LinearLayoutManager linearLayoutManager;
    private KalenderAdapter Adapter;
    private DatePickerDialog.OnDateSetListener mDateListener;
    List<Object> ItemList = new ArrayList<>();

    public fragment_kalender() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.kalender_fragment, container, false);


        recyclerView = view.findViewById(R.id.recyler_calender);
        button = view.findViewById(R.id.addbutton);

        linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);

        init();





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.create_event_alertdialog, null);
                dialogBuilder.setView(dialogView);
                AlertDialog alertDialog = dialogBuilder.create();

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (Type.getSelectedItemPosition()==2){

                        }else{
                            int position = 0;

                                for (int j = 0; j<kalenderListe.size(); j++) {
                                    position = j;
                                    if(Integer.parseInt(kalenderListe.get(j).getDate().replace(".", "")) > Integer.parseInt(Datum.getText().toString().replace(".", ""))){
                                        j = kalenderListe.size();
                                        if(position>0) {
                                            position--;
                                        }
                                    }
                                }

                            String EventType = Type.getSelectedItem().toString();
                            if(!(Titel.getText().toString().equals(""))){
                                EventType = Titel.getText().toString();
                            }
                            kalenderListe.add(position, new kalender_event(EventType, Fach.getText().toString(), Notiz.getText().toString(), Datum.getText().toString().replace("\\D+", "")));
                        }
                        SharedPreferences preferences = (getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0));


                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<kalender_event>>() {
                        }.getType();

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("Kalender", gson.toJson(kalenderListe));
                        editor.apply();
                        dialogInterface.dismiss();
                        init();


                    }
                }); alertDialog.show();

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, Fächer);

                List<String> EventTypes = new ArrayList<>();
                EventTypes.add("Hausaufgabe");
                EventTypes.add("Klausur");
                EventTypes.add("Ferien");
                EventTypes.add("Freier Tag");
                EventTypes.add("Sonstiges");

                ArrayAdapter EventType = new ArrayAdapter(getContext(), R.layout.kalender_spinner_item, EventTypes);
                EventType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                Type = alertDialog.findViewById(R.id.type);

                cTitel = alertDialog.findViewById(R.id.cTitel);
                cFach = alertDialog.findViewById(R.id.cFach);
                cDatum = alertDialog.findViewById(R.id.cDatum);
                cZeitraum = alertDialog.findViewById(R.id.cZeitRaum);
                cNotiz = alertDialog.findViewById(R.id.cNotiz);

                Fach = alertDialog.findViewById(R.id.fach);
                Titel = alertDialog.findViewById(R.id.titel);
                Datum = alertDialog.findViewById(R.id.datum);
                ZeitraumBeginn = alertDialog.findViewById(R.id.vom);
                ZeitraumEnde = alertDialog.findViewById(R.id.bis);
                Notiz = alertDialog.findViewById(R.id.Notiz);


                Type.setAdapter(EventType);
                cZeitraum.setVisibility(View.GONE);
                cTitel.setVisibility(View.GONE);
                final Calendar cal = Calendar.getInstance();
                Datum.setText(Integer.toString(cal.get(Calendar.DAY_OF_MONTH)) + "." + Integer.toString(cal.get(Calendar.MONTH) + 1) + "." + Integer.toString(cal.get(Calendar.YEAR)));
                ZeitraumBeginn.setText("vom:  " + Integer.toString(cal.get(Calendar.DAY_OF_MONTH)) + "." + Integer.toString(cal.get(Calendar.MONTH) + 1) + "." + Integer.toString(cal.get(Calendar.YEAR)));
                cal.add(Calendar.DATE, 14);
                ZeitraumEnde.setText("bis:  " + Integer.toString(cal.get(Calendar.DAY_OF_MONTH)) + "." + Integer.toString(cal.get(Calendar.MONTH) + 1) + "." + Integer.toString(cal.get(Calendar.YEAR)));
                cal.add(Calendar.DATE, -14);

                Type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (i) {
                            case 0:
                                cZeitraum.setVisibility(View.GONE);
                                cTitel.setVisibility(View.GONE);
                                cFach.setVisibility(View.VISIBLE);
                                cDatum.setVisibility(View.VISIBLE);
                                break;
                            case 1:
                                cZeitraum.setVisibility(View.GONE);
                                cTitel.setVisibility(View.GONE);
                                cFach.setVisibility(View.VISIBLE);
                                cDatum.setVisibility(View.VISIBLE);
                                break;
                            case 2:
                                cZeitraum.setVisibility(View.VISIBLE);
                                cTitel.setVisibility(View.VISIBLE);
                                cFach.setVisibility(View.GONE);
                                cDatum.setVisibility(View.GONE);
                                break;
                            case 3:
                                cZeitraum.setVisibility(View.GONE);
                                cTitel.setVisibility(View.VISIBLE);
                                cFach.setVisibility(View.GONE);
                                cDatum.setVisibility(View.VISIBLE);
                                break;
                            case 4:
                                cZeitraum.setVisibility(View.GONE);
                                cTitel.setVisibility(View.VISIBLE);
                                cFach.setVisibility(View.VISIBLE);
                                cDatum.setVisibility(View.VISIBLE);
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                Datum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActiveDateTextView = Datum;
                        DateAddition = "";
                        int day = Integer.parseInt(Datum.getText().toString().substring(0, 2));
                        int month = Integer.parseInt(Datum.getText().toString().substring(3, 5)) - 1;
                        int year = Integer.parseInt(Datum.getText().toString().substring(6, 10));

                        DatePickerDialog dialog = new DatePickerDialog(view.getContext(), mDateListener, year, month, day);
                        dialog.getDatePicker().setMinDate(cal.getTime().getTime());
                        dialog.show();
                        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {

                            }
                        });
                    }
                });


                ZeitraumBeginn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActiveDateTextView = ZeitraumBeginn;
                        DateAddition = "vom:  ";
                        int day = Integer.parseInt(ActiveDateTextView.getText().toString().substring(6, 2 + 6));
                        int month = Integer.parseInt(ActiveDateTextView.getText().toString().substring(3 + 6, 5 + 6)) - 1;
                        int year = Integer.parseInt(ActiveDateTextView.getText().toString().substring(6 + 6, 10 + 6));


                        DatePickerDialog dialog = new DatePickerDialog(view.getContext(), mDateListener, year, month, day);
                        dialog.getDatePicker().setMinDate(cal.getTime().getTime());
                        dialog.show();
                        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {

                            }
                        });
                    }
                });


                ZeitraumEnde.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActiveDateTextView = ZeitraumEnde;
                        DateAddition = "bis:  ";
                        int day = Integer.parseInt(ActiveDateTextView.getText().toString().substring(6, 2 + 6));
                        int month = Integer.parseInt(ActiveDateTextView.getText().toString().substring(3 + 6, 5 + 6)) - 1;
                        int year = Integer.parseInt(ActiveDateTextView.getText().toString().substring(6 + 6, 10 + 6));

                        DatePickerDialog dialog = new DatePickerDialog(view.getContext(), mDateListener, year, month, day);
                        dialog.getDatePicker().setMinDate(cal.getTime().getTime());
                        dialog.show();
                        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {

                            }
                        });
                    }
                });


                Fach.setAdapter(adapter);
                Fach.setThreshold(1);

                mDateListener = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int pyear, int pmonth, int pday) {
                        String month;
                        String day;

                        if (pmonth < 9) {
                            month = "0" + Integer.toString(pmonth + 1);
                        } else {
                            month = Integer.toString(pmonth + 1);
                        }

                        if (pday < 10) {
                            day = "0" + Integer.toString(pday);
                        } else {
                            day = Integer.toString(pday);
                        }

                        ActiveDateTextView.setText(DateAddition + day + "." + month + "." + Integer.toString(pyear));
                    }
                };

            }
        });
        return view;
    }

    private void init() {

        SharedPreferences preferences = (getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0));

        String json;
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<kalender_event>>() {
        }.getType();

        json = preferences.getString("Kalender", "");

        if (!json.equals("")) {
            kalenderListe = gson.fromJson(json, type);
        } else {
            kalenderListe = new ArrayList<>();
        }


        Adapter = new KalenderAdapter(getActivity());
        recyclerView.setAdapter(Adapter);
        ItemList = new ArrayList<>();
        String CurrentDate;


        if (kalenderListe.size() > 0) {
            CurrentDate = kalenderListe.get(0).getDate();
            ItemList.add(new Datum(CurrentDate));
            for (int i = 0; kalenderListe.size() > i; i++) {
                if ((kalenderListe.get(i).getDate()).equals(CurrentDate)) {
                    ItemList.add(kalenderListe.get(i));
                } else {
                    CurrentDate = kalenderListe.get(i).getDate();
                    ItemList.add(new Datum(CurrentDate));
                    ItemList.add(kalenderListe.get(i));
                }
            }

        }
        Adapter.setitemFeed(ItemList);
        Adapter.notifyDataSetChanged();

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {


        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

            return false;
        }

    @Override
    public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (ItemList.get(viewHolder.getAdapterPosition ()) instanceof kalender_event) return super.getSwipeDirs(recyclerView, viewHolder);
        return 0;

    }



        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition ();

            if(ItemList.get(position) instanceof kalender_event){
                boolean Datum1;
                if(position+1<ItemList.size()){
                Datum1 =ItemList.get(position+1) instanceof Datum;
                }else{
                    Datum1= true;
                }

                boolean Datum2 =ItemList.get(position-1) instanceof Datum;
                ItemList.remove(position);
                Adapter.notifyItemRemoved(position);

                if(Datum1 && Datum2){
                    ItemList.remove(position-1);
                    Adapter.notifyItemRemoved(position-1);

                }

                SharedPreferences preferences = (getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0));


                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = preferences.edit();

                        List<kalender_event> Temp =new ArrayList<>();

                        for(int i = 0; i<ItemList.size(); i++){
                            if(ItemList.get(i) instanceof kalender_event){
                                Temp.add((kalender_event)ItemList.get(i));
                            }
                        }
                        editor.putString("Kalender", gson.toJson(Temp));
                        editor.apply();

            }
        }
        };


    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
    itemTouchHelper.attachToRecyclerView(recyclerView);

    }




}


