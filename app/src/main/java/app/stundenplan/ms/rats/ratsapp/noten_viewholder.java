package app.stundenplan.ms.rats.ratsapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
    }

    public void showDetails(Memory_NotenKlausuren Object){
        boolean schrifltich = Object.isMuendlichschrifltich();
        int Muendlich1 = Object.getMuendlich1();
        int Muendlich2 = Object.getMuendlich2();
        int Schriftlich1 = Object.getSchriftlich1();
        int Schriftlich2 = Object.getSchriftlich2();
        int Zeugnisnote = Object.getZeugnis();
        final int Datum1 = Object.getDatum1();
        int Datum2 = Object.getDatum2();
        String Fach = Object.getFach();


            if(!(Datum1 ==0)){
                String Date = Integer.toString(Datum1);
                if(Datum1<10000000){
                    Date = "0" + Integer.toString(Datum1);
                }
                String Day = Date.substring(0,2);
                String Month = Date.substring(2,4);
                String Year = Date.substring(4,8);
                tDate1.setText(Day + "." + Month + "." + Year);
               }else{
                tDate1.setText("01.01.2000");
            }



        if(Datum2<10000000){
            if(!(Datum2 ==0)){
                tDate2.setText("0" + Integer.toString(Datum2).substring(0,1) + "." + Integer.toString(Datum2).substring(2,3) + "." + Integer.toString(Datum2).substring(4,7));
            }else{
                tDate2.setText("01.01.2000");
            }
        }else{
            tDate2.setText(Integer.toString(Datum2).substring(0,1) + "." + Integer.toString(Datum2).substring(2,3) + "." + Integer.toString(Datum2).substring(4,7));

        }




        List<String> sMuendlich1 = Standart();
        List<String> sMuendlich2 = Standart();
        List<String> sSchriftlich1 = Standart();
        List<String> sSchriftlich2 = Standart();
        List<String> sZeugnisnote = Standart();

        if(Muendlich1==0){
            sMuendlich1.add(0," --- ");
        }else{
            sMuendlich1.add(0,PunktezuNote(Muendlich1));
            sMuendlich1.remove(16-Muendlich1);
            sMuendlich1.add(1," --- ");
        }

        if(Muendlich2==0){
            sMuendlich2.add(0," --- ");
        }else{
            sMuendlich2.add(0,PunktezuNote(Muendlich2));
            sMuendlich2.remove(16-Muendlich2);
            sMuendlich2.add(1," --- ");
        }

        if(Schriftlich1==0){
            sSchriftlich1.add(0," --- ");
        }else{
            sSchriftlich1.add(0,PunktezuNote(Schriftlich1));
            sSchriftlich1.remove(16-Schriftlich1);
            sSchriftlich1.add(1," --- ");
        }

        if(Schriftlich2==0){
            sSchriftlich2.add(0," --- ");
        }else{
            sSchriftlich2.add(0,PunktezuNote(Schriftlich2));
            sSchriftlich2.remove(16-Schriftlich2);
            sSchriftlich2.add(1," --- ");
        }

        if(Zeugnisnote==0){
            sZeugnisnote.add(0," --- ");
        }else {
            sZeugnisnote.add(0,PunktezuNote(Zeugnisnote));
            sZeugnisnote.remove(16-Zeugnisnote);
            sZeugnisnote.add(1," --- ");
        }





        ArrayAdapter aSchriftlich1 = new ArrayAdapter(itemView.getContext(), R.layout.spinner_item, sSchriftlich1);
        aSchriftlich1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tSchriftlich1.setAdapter(aSchriftlich1);

        ArrayAdapter aSchrifltich2 = new ArrayAdapter(itemView.getContext(), R.layout.spinner_item, sSchriftlich2);
        aSchrifltich2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tSchriftlich2.setAdapter(aSchrifltich2);

        ArrayAdapter aMuendlich1 = new ArrayAdapter(itemView.getContext(), R.layout.spinner_item, sMuendlich1);
        aMuendlich1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tMuendlich1.setAdapter(aMuendlich1);

        ArrayAdapter aMuendlich2 = new ArrayAdapter(itemView.getContext(), R.layout.spinner_item, sMuendlich2);
        aMuendlich2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tMuendlich2.setAdapter(aMuendlich2);

        ArrayAdapter aZeugnis = new ArrayAdapter(itemView.getContext(), R.layout.spinner_item, sZeugnisnote);
        aZeugnis.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tZeugnis.setAdapter(aZeugnis);

        tFach.setText(Fach);

        if(!schrifltich) {
            cSchriftlich1.setVisibility(View.GONE);
            cSchrifltich2.setVisibility(View.GONE);
        }

        if(!(Zeugnisnote==0)){
            tGesammt.setText(PunktezuNote(Zeugnisnote));
        }else if(!(Muendlich1+Muendlich2+Schriftlich1+Schriftlich2==0)){
            int MuendlichEingetragen = 0;
            int SchriftlichEingetragen = 0;
            int Muendlich = 0;
            int Schriftlich = 0;
            int alleeingetragen = 0;

            if(!(Muendlich1==0)){
                MuendlichEingetragen++;
                Muendlich = Muendlich + Muendlich1;
            }

            if(!(Muendlich2==0)){
                MuendlichEingetragen++;
                Muendlich = Muendlich+Muendlich2;
            }
            
            if(!(Schriftlich1==0)){
                SchriftlichEingetragen++;
                Schriftlich = Schriftlich+Schriftlich1;
            }

            if(!(Schriftlich2==0)){
                SchriftlichEingetragen++;
                Schriftlich = Schriftlich+Schriftlich2;
            }
            
            if(!(MuendlichEingetragen==0)){
                Muendlich = Muendlich/MuendlichEingetragen;
            }

            if(!(SchriftlichEingetragen==0)){
                Schriftlich = Schriftlich/SchriftlichEingetragen;
            }

            if(!(SchriftlichEingetragen==0)){
                alleeingetragen++;
            }

            if(!(MuendlichEingetragen==0)){
                alleeingetragen++;
            }

            tGesammt.setText(PunktezuNote((Muendlich+Schriftlich)/alleeingetragen));

            
        }else{
            tGesammt.setText("");
        }

        cExtender.setVisibility(View.GONE);




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
                        Save(getAdapterPosition());
                    }
                });

            }
        });


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
                    cExtender.setVisibility(View.VISIBLE);

                }else{
                    cExtender.setVisibility(View.GONE);
                }
            }
        });

    }



    private List<String> Standart(){
        List<String> Temp = new ArrayList<>();

        Temp.add(" 1+ ");
        Temp.add(" 1 ");
        Temp.add(" 1- ");
        Temp.add(" 2+ ");
        Temp.add(" 2 ");
        Temp.add(" 2- ");
        Temp.add(" 3+ ");
        Temp.add(" 3 ");
        Temp.add(" 3- ");
        Temp.add(" 4+ ");
        Temp.add(" 4 ");
        Temp.add(" 4- ");
        Temp.add(" 5+ ");
        Temp.add(" 5 ");
        Temp.add(" 5- ");
        Temp.add(" 6 ");

        return Temp;
    }


    private String PunktezuNote(int pPunkte){
        switch (pPunkte){
            case 1:
                return "6";
            case 2:
                return "5-";
            case 3:
                return "5";
            case 4:
                return "5+";
            case 5:
                return "4-";
            case 6:
                return "4";
            case 7:
                return "4+";

            case 8:
                return "3-";

            case 9:
                return "3";

            case 10:
                return "3+";
            case 11:
                return "2-";
            case 12:
                return "2";
            case 13:
                return "2+";
            case 14:
                return  "1-";
            case 15:
                return "1";
            case 16:
                return "1+";
                default:
                    return " --- ";
        }
    }

    private void Save(int pos){
        int saveMuendlich1 = 0 ;
        int saveMuendlich2 = 0;
        int saveSchriftlich1 = 0;
        int saveSchriftlich2 = 0;
        int saveDate1 = 0;
        int saveDate2 = 0;
        int saveZeugnis = 0;

        if(!tMuendlich1.getSelectedItem().toString().equals(" --- ")){
            saveMuendlich1 = NotezuPunkte(tMuendlich1.getSelectedItem().toString());
        }

        if(!tZeugnis.getSelectedItem().toString().equals(" --- ")){
            saveZeugnis = NotezuPunkte(tZeugnis.getSelectedItem().toString());
        }

        if(!tMuendlich2.getSelectedItem().toString().equals(" --- ")){
            saveMuendlich2 = NotezuPunkte(tMuendlich2.getSelectedItem().toString());
        }

        if(!tSchriftlich1.getSelectedItem().toString().equals(" --- ")){
            saveSchriftlich1 = NotezuPunkte(tSchriftlich1.getSelectedItem().toString());
        }

        if(!tSchriftlich2.getSelectedItem().toString().equals(" --- ")){
            saveSchriftlich2 = NotezuPunkte(tSchriftlich2.getSelectedItem().toString());
        }
        if(!tSchriftlich2.getSelectedItem().toString().equals(" --- ")){
            saveSchriftlich2 = NotezuPunkte(tSchriftlich2.getSelectedItem().toString());
        }


        if(!(tDate1.getText()=="01.01.2000")){
            saveDate1= Integer.parseInt(tDate1.getText().toString().replace(".",""));
        }

        if(!(tDate2.getText()=="01.01.2000")){
            saveDate2= Integer.parseInt(tDate2.getText().toString().replace(".",""));
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

    }

    private int NotezuPunkte(String pNote){
        switch (pNote){
            case "6":
                return 1;
            case "5-":
                return 2;
            case "5":
                return 3;
            case "5+":
                return 4;
            case "4-":
                return 5;
            case "4":
                return 6;
            case "4+":
                return 7;
            case "3-":
                return 8;
            case "3":
                return 9;
            case "3+":
                return 10;
            case "2-":
                return 11;
            case "2":
                return 12;
            case "2+":
                return 13;
            case "1-":
                return 14;
            case "1":
                return 15;
            case "1+":
                return 16;
            default:
                return 0;

        }
    }

}
