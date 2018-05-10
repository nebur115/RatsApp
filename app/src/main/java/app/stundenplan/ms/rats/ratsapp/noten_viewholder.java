package app.stundenplan.ms.rats.ratsapp;

import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class noten_viewholder extends RecyclerView.ViewHolder{
    Spinner tMuendlich1;
    Spinner tMuendlich2;
    Spinner tSchriftlich2;
    Spinner tSchriftlich1;
    Spinner tZeugnis;
    TextView tFach;
    TextView tGesammt;
    ConstraintLayout cExtender;
    ConstraintLayout cSchriftlich1;
    ConstraintLayout cSchrifltich2;
    ImageView arrow;
    ConstraintLayout Main;
    TextView tDate1;
    TextView tDate2;
    private DatePickerDialog.OnDateSetListener mDate1Listener;
    private DatePickerDialog.OnDateSetListener mDate2Listener;
    Context context;
    View viewholder;
    ConstraintLayout everything;
    boolean schrifltich;
    int Zeugnisnote;
    int Schriftlich2;
    int Schriftlich1;
    int Muendlich1;
    int Muendlich2;
    boolean alreadyshown;
    int Datum1;
    int Datum2;
    String Fach;
    boolean Shown;

    public noten_viewholder(View View) {
        super(View);
        context = itemView.getContext();
        cExtender = View.findViewById(R.id.extender);
        tFach = View.findViewById(R.id.fachText);
        Main = View.findViewById(R.id.main);
        cSchriftlich1 = View.findViewById(R.id.Quartal1Schritflich);
        cSchrifltich2 = View.findViewById(R.id.Quartal2Schritflich);
        tGesammt = View.findViewById(R.id.GesammtText);
        tSchriftlich1 = View.findViewById(R.id.Schriftlich1);
        tSchriftlich2 = View.findViewById(R.id.Schriftlich2);
        tMuendlich1 = View.findViewById(R.id.Muendlich1);
        tMuendlich2 = View.findViewById(R.id.Muendlich2);
        tZeugnis = View.findViewById(R.id.Zeugnisnote);
        tDate1 = View.findViewById(R.id.klasur1Date);
        tDate2 = View.findViewById(R.id.klasur2Date);
        arrow = View.findViewById(R.id.arrow);
        everything = View.findViewById(R.id.everything);
        viewholder = View;
    }

    public void showDetails(Memory_NotenKlausuren Object){


        SharedPreferences setting = context.getSharedPreferences("RatsVertretungsPlanApp", 0);
        List<Memory_NotenKlausuren> NotenList = new ArrayList<>();
        String json;
        Gson gson = new Gson();
        json = setting.getString("NotenKlausuren", null);
        Type type = new TypeToken<ArrayList<Memory_NotenKlausuren>>() {}.getType();
        NotenList = gson.fromJson(json , type);

        Object = NotenList.get(getAdapterPosition()-1);

        schrifltich = Object.isMuendlichschrifltich();
        Muendlich1 = Object.getMuendlich1();
        Muendlich2 = Object.getMuendlich2();
        Schriftlich1 = Object.getSchriftlich1();
        Schriftlich2 = Object.getSchriftlich2();
        Zeugnisnote = Object.getZeugnis();
        Datum1 = Object.getDatum1();
        Datum2 = Object.getDatum2();
        Fach = Object.getFach();
        alreadyshown = true;


        if(!(Datum1 ==0)){
                String Date1 = Integer.toString(Datum1);
                if(Datum1<10000000){
                    Date1 = "0" + Integer.toString(Datum1);
                }
                String Day = Date1.substring(0,2);
                String Month = Date1.substring(2,4);
                String Year = Date1.substring(4,8);
                tDate1.setText(Day + "." + Month + "." + Year);
               }else{
                tDate1.setText("01.01.2000");
            }




        if(!(Datum2 ==0)){
            String Date2 = Integer.toString(Datum2);
            if(Datum2<10000000){
                Date2 = "0" + Integer.toString(Datum2);
            }
            String Day = Date2.substring(0,2);
            String Month = Date2.substring(2,4);
            String Year = Date2.substring(4,8);
            tDate2.setText(Day + "." + Month + "." + Year);
        }else{
            tDate2.setText("01.01.2000");
        }





        List<String> sMuendlich1 = Standart();
        List<String> sMuendlich2 = Standart();
        List<String> sSchriftlich1 = Standart();
        List<String> sSchriftlich2 = Standart();
        List<String> sZeugnisnote = Standart();





        ArrayAdapter aSchriftlich1 = new ArrayAdapter(itemView.getContext(), R.layout.spinner_item, sSchriftlich1);
        aSchriftlich1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tSchriftlich1.setAdapter(aSchriftlich1);
        if(!(Schriftlich1==0)) {
            tSchriftlich1.setSelection(17 - Schriftlich1);
        }

        ArrayAdapter aSchrifltich2 = new ArrayAdapter(itemView.getContext(), R.layout.spinner_item, sSchriftlich2);
        aSchrifltich2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tSchriftlich2.setAdapter(aSchrifltich2);
        if(!(Schriftlich2==0)) {
            tSchriftlich2.setSelection(17 - Schriftlich2);
        }

        ArrayAdapter aMuendlich1 = new ArrayAdapter(itemView.getContext(), R.layout.spinner_item, sMuendlich1);
        aMuendlich1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tMuendlich1.setAdapter(aMuendlich1);
        if(!(Muendlich1==0)) {
            tMuendlich1.setSelection(17 - Muendlich1);
        }

        ArrayAdapter aMuendlich2 = new ArrayAdapter(itemView.getContext(), R.layout.spinner_item, sMuendlich2);
        aMuendlich2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tMuendlich2.setAdapter(aMuendlich2);
        if(!(Muendlich2==0)) {
            tMuendlich2.setSelection(17 - Muendlich2);
        }

        ArrayAdapter aZeugnis = new ArrayAdapter(itemView.getContext(), R.layout.spinner_item, sZeugnisnote);
        aZeugnis.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tZeugnis.setAdapter(aZeugnis);
        if(!(Zeugnisnote==0)){
        tZeugnis.setSelection(17-Zeugnisnote);
        }

        tFach.setText(Fach);

        if(!schrifltich) {
            cSchriftlich1.setVisibility(View.GONE);
            cSchrifltich2.setVisibility(View.GONE);
        }else{
            cSchriftlich1.setVisibility(View.VISIBLE);
            cSchrifltich2.setVisibility(View.VISIBLE);
        }

        GesammtNote(Zeugnisnote,Muendlich1,Muendlich2,Schriftlich1,Schriftlich2);

        cExtender.setVisibility(View.GONE);

        tMuendlich1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!(Muendlich1==NotezuPunkte(tMuendlich1.getSelectedItem().toString()))){
                    Save(getAdapterPosition()-1);
                }
                GesammtNote(NotezuPunkte(tZeugnis.getSelectedItem().toString()),NotezuPunkte(tMuendlich1.getSelectedItem().toString()),NotezuPunkte(tMuendlich2.getSelectedItem().toString()),NotezuPunkte(tSchriftlich1.getSelectedItem().toString()),NotezuPunkte(tSchriftlich2.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tMuendlich2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!(Muendlich2==NotezuPunkte(tMuendlich2.getSelectedItem().toString()))) {
                    Save(getAdapterPosition() - 1);
                }
                GesammtNote(NotezuPunkte(tZeugnis.getSelectedItem().toString()),NotezuPunkte(tMuendlich1.getSelectedItem().toString()),NotezuPunkte(tMuendlich2.getSelectedItem().toString()),NotezuPunkte(tSchriftlich1.getSelectedItem().toString()),NotezuPunkte(tSchriftlich2.getSelectedItem().toString()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tSchriftlich1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!(Schriftlich2==NotezuPunkte(tSchriftlich1.getSelectedItem().toString()))){
                    Save(getAdapterPosition()-1);
                }
                GesammtNote(NotezuPunkte(tZeugnis.getSelectedItem().toString()),NotezuPunkte(tMuendlich1.getSelectedItem().toString()),NotezuPunkte(tMuendlich2.getSelectedItem().toString()),NotezuPunkte(tSchriftlich1.getSelectedItem().toString()),NotezuPunkte(tSchriftlich2.getSelectedItem().toString()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tSchriftlich2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!(Schriftlich2==NotezuPunkte(tSchriftlich2.getSelectedItem().toString()))){
                    Save(getAdapterPosition()-1);
                }

                GesammtNote(NotezuPunkte(tZeugnis.getSelectedItem().toString()),NotezuPunkte(tMuendlich1.getSelectedItem().toString()),NotezuPunkte(tMuendlich2.getSelectedItem().toString()),NotezuPunkte(tSchriftlich1.getSelectedItem().toString()),NotezuPunkte(tSchriftlich2.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tZeugnis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!(Zeugnisnote==NotezuPunkte(tZeugnis.getSelectedItem().toString()))){
                Save(getAdapterPosition()-1);
                }

                GesammtNote(NotezuPunkte(tZeugnis.getSelectedItem().toString()),NotezuPunkte(tMuendlich1.getSelectedItem().toString()),NotezuPunkte(tMuendlich2.getSelectedItem().toString()),NotezuPunkte(tSchriftlich1.getSelectedItem().toString()),NotezuPunkte(tSchriftlich2.getSelectedItem().toString()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        tDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year;
                int month;
                int day;
                if(tDate2.getText().toString().equals("01.01.2000")){
                    Calendar cal = Calendar.getInstance();
                    year = cal.get(Calendar.YEAR);
                    month = cal.get(Calendar.MONTH);
                    day = cal.get(Calendar.DAY_OF_MONTH);
                }else{
                    day = Integer.parseInt(tDate2.getText().toString().substring(0,2));
                    month = Integer.parseInt(tDate2.getText().toString().substring(3,5))-1;
                    year = Integer.parseInt(tDate2.getText().toString().substring(6,10));
                }



                DatePickerDialog dialog = new DatePickerDialog(
                        view.getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDate2Listener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        Save(getAdapterPosition()-1);
                    }
                });

            }
        });



        tDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year;
                int month;
                int day;
                if(tDate1.getText().toString().equals("01.01.2000")){
                    Calendar cal = Calendar.getInstance();
                    year = cal.get(Calendar.YEAR);
                    month = cal.get(Calendar.MONTH);
                    day = cal.get(Calendar.DAY_OF_MONTH);
                }else{
                    day = Integer.parseInt(tDate1.getText().toString().substring(0,2));
                    month = Integer.parseInt(tDate1.getText().toString().substring(3,5))-1;
                    year = Integer.parseInt(tDate1.getText().toString().substring(6,10));
                }
                DatePickerDialog dialog = new DatePickerDialog(
                        view.getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDate1Listener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        Save(getAdapterPosition()-1);
                    }
                });

            }
        });



        mDate2Listener  = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int pyear, int pmonth, int pday) {
                String year = Integer.toString(pyear);
                String month;
                String day;

                if(pmonth<9){
                    month = "0"+Integer.toString(pmonth+1);
                }else {
                    month = Integer.toString(pmonth+1);
                }

                if(pday<10){
                    day = "0"+Integer.toString(pday);
                }else {
                    day = Integer.toString(pday);
                }
                tDate2.setText(day+"."+month+"."+year);

            }
        };



        mDate1Listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int pyear, int pmonth, int pday) {
                String year = Integer.toString(pyear);
                String month;
                String day;

                if(pmonth<9){
                    month = "0"+Integer.toString(pmonth+1);
                }else {
                    month = Integer.toString(pmonth+1);
                }

                if(pday<10){
                    day = "0"+Integer.toString(pday);
                }else {
                    day = Integer.toString(pday);
                }
                tDate1.setText(day+"."+month+"."+year);
            }
        };



        Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cExtender.getVisibility()==View.GONE){
                    Shown = true;

                    cExtender.setVisibility(View.VISIBLE);

                    cExtender.clearAnimation();
                    arrow.clearAnimation();
                    RotateAnimation animrdown = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.6f);
                    animrdown.setInterpolator(new LinearInterpolator());
                    animrdown.setDuration(330);
                    animrdown.setFillAfter(true);
                    arrow.setAnimation(animrdown);

                    if(!schrifltich) {
                        cSchriftlich1.setVisibility(View.GONE);
                        cSchrifltich2.setVisibility(View.GONE);
                    }else{
                        cSchriftlich1.setVisibility(View.VISIBLE);
                        cSchrifltich2.setVisibility(View.VISIBLE);
                    }


                    ObjectAnimator animation = ObjectAnimator.ofFloat(cExtender, "translationY",  -1000f , 0f);
                    animation.setDuration(330);
                    animation.start();


                    Shown = false;

                }else{
                    arrow.clearAnimation();
                    cExtender.clearAnimation();
                    RotateAnimation animrup = new RotateAnimation(180f, 0f, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.6f);
                    animrup.setInterpolator(new LinearInterpolator());
                    animrup.setDuration(330);
                    animrup.setFillAfter(true);
                    animrup.setRepeatMode(1);
                    arrow.setAnimation(animrup);
                    cExtender.setVisibility(View.GONE);


                    if(!schrifltich) {
                        cSchriftlich1.setVisibility(View.GONE);
                        cSchrifltich2.setVisibility(View.GONE);
                    }else{
                        cSchriftlich1.setVisibility(View.VISIBLE);
                        cSchrifltich2.setVisibility(View.VISIBLE);
                    }


                }
            }
        });

    }



    private List<String> Standart(){
        List<String> Temp = new ArrayList<>();

        Temp.add("---");
        Temp.add("1+ ");
        Temp.add("1  ");
        Temp.add("1- ");
        Temp.add("2+ ");
        Temp.add("2  ");
        Temp.add("2- ");
        Temp.add("3+ ");
        Temp.add("3  ");
        Temp.add("3- ");
        Temp.add("4+ ");
        Temp.add("4  ");
        Temp.add("4- ");
        Temp.add("5+ ");
        Temp.add("5  ");
        Temp.add("5- ");
        Temp.add("6  ");

        return Temp;
    }


    private String PunktezuNote(int pPunkte){
        switch (pPunkte){
            case 1:
                return "6  ";
            case 2:
                return "5- ";
            case 3:
                return "5  ";
            case 4:
                return "5+ ";
            case 5:
                return "4- ";
            case 6:
                return "4  ";
            case 7:
                return "4+ ";

            case 8:
                return "3- ";

            case 9:
                return "3  ";

            case 10:
                return "3+ ";
            case 11:
                return "2- ";
            case 12:
                return "2  ";
            case 13:
                return "2+ ";
            case 14:
                return  "1- ";
            case 15:
                return "1  ";
            case 16:
                return "1+ ";
                default:
                    return "---";
        }
    }

    private void Save(int pos){
        if(!Shown){
        if(pos<0){
            pos = 0;
        }
        int saveMuendlich1 = 0 ;
        int saveMuendlich2 = 0;
        int saveSchriftlich1 = 0;
        int saveSchriftlich2 = 0;
        int saveDate1 = 0;
        int saveDate2 = 0;
        int saveZeugnis = 0;

        if(!tMuendlich1.getSelectedItem().toString().equals("---")){
            String StringSaveZeugnis = tMuendlich1.getSelectedItem().toString();
            saveMuendlich1 = NotezuPunkte(tMuendlich1.getSelectedItem().toString());
        }

        if(!tZeugnis.getSelectedItem().toString().equals("---")){
            saveZeugnis = NotezuPunkte(tZeugnis.getSelectedItem().toString());
        }

        if(!tMuendlich2.getSelectedItem().toString().equals("---")){
            saveMuendlich2 = NotezuPunkte(tMuendlich2.getSelectedItem().toString());
        }

        if(!tSchriftlich1.getSelectedItem().toString().equals("---")){
            saveSchriftlich1 = NotezuPunkte(tSchriftlich1.getSelectedItem().toString());
        }

        if(!tSchriftlich2.getSelectedItem().toString().equals("---")){
            saveSchriftlich2 = NotezuPunkte(tSchriftlich2.getSelectedItem().toString());
        }
        if(!tSchriftlich2.getSelectedItem().toString().equals("---")){
            saveSchriftlich2 = NotezuPunkte(tSchriftlich2.getSelectedItem().toString());
        }


        if(!(tDate1.getText()=="01.01.2000")){
            saveDate1= Integer.parseInt(tDate1.getText().toString().replace(".",""));
        }

        if(!(tDate2.getText()=="01.01.2000")){
            saveDate2= Integer.parseInt(tDate2.getText().toString().replace(".",""));
        }

        if(!schrifltich){
            saveSchriftlich1 = 0;
            saveSchriftlich2 = 0;
        }


        SharedPreferences setting = context.getSharedPreferences("RatsVertretungsPlanApp", 0);
        List<Memory_NotenKlausuren> NotenList = new ArrayList<>();
        String json;
        Gson gson = new Gson();
        json = setting.getString("NotenKlausuren", null);
        Type type = new TypeToken<ArrayList<Memory_NotenKlausuren>>() {}.getType();
        NotenList = gson.fromJson(json , type);

        NotenList.get(pos).setSchriftlich1(saveSchriftlich1);
        NotenList.get(pos).setMuendlich1(saveMuendlich1);
        NotenList.get(pos).setDatum1(saveDate1);
        NotenList.get(pos).setDatum2(saveDate2);
        NotenList.get(pos).setSchriftlich2(saveSchriftlich2);
        NotenList.get(pos).setMuendlich2(saveMuendlich2);
        NotenList.get(pos).setZeugnis(saveZeugnis);

        SharedPreferences.Editor editor = setting.edit();
        String savejson = gson.toJson(NotenList);
        editor.putString("NotenKlausuren", savejson);
        editor.apply();

        vertretungsplan.notenreload();

    }
    }

    private int NotezuPunkte(String pNote){
        switch (pNote){
            case "6  ":
                return 1;
            case "5- ":
                return 2;
            case "5  ":
                return 3;
            case "5+ ":
                return 4;
            case "4- ":
                return 5;
            case "4  ":
                return 6;
            case "4+ ":
                return 7;
            case "3- ":
                return 8;
            case "3  ":
                return 9;
            case "3+ ":
                return 10;
            case "2- ":
                return 11;
            case "2  ":
                return 12;
            case "2+ ":
                return 13;
            case "1- ":
                return 14;
            case "1  ":
                return 15;
            case "1+ ":
                return 16;
            default:
                return 0;

        }
    }

    private void GesammtNote(int pZeugnisNote, int pMuendlich1, int pMuendlich2, int pSchriftlich1, int pSchriftlich2){

        if(!(pZeugnisNote==0)){
            tGesammt.setText(PunktezuNote(pZeugnisNote));
        }else if(!(pMuendlich1+pMuendlich2+pSchriftlich1+pSchriftlich2==0)){
            int MuendlichEingetragen = 0;
            int SchriftlichEingetragen = 0;
            double Muendlich = 0;
            double Schriftlich = 0;
            int alleeingetragen = 0;

            if(!(pMuendlich1==0)){
                MuendlichEingetragen++;
                Muendlich = Muendlich + pMuendlich1-1;
            }

            if(!(pMuendlich2==0)){
                MuendlichEingetragen++;
                Muendlich = Muendlich + pMuendlich2-1;
            }

            if(!(pSchriftlich1==0)){
                SchriftlichEingetragen++;
                Schriftlich = Schriftlich + pSchriftlich1-1;
            }

            if(!(pSchriftlich2==0)){
                SchriftlichEingetragen++;
                Schriftlich = Schriftlich + pSchriftlich2-1;
            }

            if(!(MuendlichEingetragen==0)){
                Muendlich = Muendlich/MuendlichEingetragen;
            }

            if(!(SchriftlichEingetragen==0) && (schrifltich)){
                Schriftlich = Schriftlich/SchriftlichEingetragen;
            }else {
                Schriftlich = 0;
            }

            if(!schrifltich && !(SchriftlichEingetragen==0)){
                SchriftlichEingetragen=0;
            }


            if(!(SchriftlichEingetragen==0)){
                alleeingetragen++;
            }

            if(!(MuendlichEingetragen==0)){
                alleeingetragen++;
            }

            String Gesammt = (PunktezuNote((int) (((Muendlich+Schriftlich)/alleeingetragen)+1)));

            tGesammt.setText(Gesammt);

        }else{
            tGesammt.setText("");
        }

    }

}
