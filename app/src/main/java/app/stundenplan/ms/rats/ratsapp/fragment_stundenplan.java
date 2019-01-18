package app.stundenplan.ms.rats.ratsapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class fragment_stundenplan extends Fragment  {
    public int Height = vertretungsplan.getheight();
    List<Memory_Stunde> MemoryStundenListe = new ArrayList<>();
    int Week;
    boolean isCreated = false;
    private List<Boolean> lastShowStunde = new ArrayList<>();
    private List<Boolean> lastDoppelStunde = new ArrayList<>();



    Stundenplanadapter mystundenplanadapter;
    RecyclerView stundenplan_recyclerView;
    private List<Object> StundenListe = new ArrayList<>();

    static fragment_stundenplan newInstance(int pWeek) {
        fragment_stundenplan fragment = new fragment_stundenplan();
        Bundle bundle = new Bundle();
        bundle.putInt("pWeek", pWeek);

        fragment.setArguments(bundle);
        return fragment;
    }


    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            Week = bundle.getInt("pWeek");
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.stundenplan_fragment, container, Boolean.parseBoolean(null));


        stundenplan_recyclerView = view.findViewById(R.id.recyclerview_stundenplan);

        readBundle(getArguments());

        init();

        StaggeredGridLayoutManager mystaggeredGridLayoutManager = new StaggeredGridLayoutManager(5, 1);

        stundenplan_recyclerView.setLayoutManager(mystaggeredGridLayoutManager);
        stundenplan_recyclerView.setNestedScrollingEnabled(false);


        mystundenplanadapter = new Stundenplanadapter(getActivity(), StundenListe);

        stundenplan_recyclerView.setAdapter(mystundenplanadapter);



        return view;
    }

    public void init() {
        VertretungsPlanMethoden.stundenplanfrag = this;
        int menuHeight = 60 + 50 + 35;
        List<Memory_NotenKlausuren> SchnittNotenList = new ArrayList<>();
        String DateMonday;
        String DateTuesday;
        String DateWednesday;
        String DateThurstday;
        String DateFriday;

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();


        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case 2:
                calendar.add(Calendar.DATE, (7 * Week));
                break;

            case 3:
                calendar.add(Calendar.DATE, -1 + (7 * Week));
                break;

            case 4:
                calendar.add(Calendar.DATE, -2 + (7 * Week));
                break;

            case 5:
                calendar.add(Calendar.DATE, -3 + (7 * Week));
                break;

            case 6:
                calendar.add(Calendar.DATE, -4 + (7 * Week));
                break;

            case 7:
                calendar.add(Calendar.DATE, 2 + (7 * Week));
                break;

            case 1:
                calendar.add(Calendar.DATE, 1 + (7 * Week));
                break;

            default:
                Toast.makeText(getActivity(), Integer.toString(day), Toast.LENGTH_SHORT).show();
                calendar.add(Calendar.DATE, (7 * Week));
                break;
        }


        DateMonday = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.DATE, 1);

        int WeekofYear = calendar.get(Calendar.WEEK_OF_YEAR);

        DateTuesday = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.DATE, 1);
        DateWednesday = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.DATE, 1);
        DateThurstday = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.DATE, 1);
        DateFriday = dateFormat.format(calendar.getTime());


        SharedPreferences settings = getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0);

        if (settings.getString("Stufe", "").equals("Q1") || settings.getString("Stufe", "").equals("Q2")) {

            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Memory_NotenKlausuren>>() {}.getType();

            if(settings.contains("NotenKlausurenQ11")){


            List<Memory_NotenKlausuren> NotenKlausurenQ11;
            List<Memory_NotenKlausuren> NotenKlausurenQ12;
            List<Memory_NotenKlausuren> NotenKlausurenQ21;
            List<Memory_NotenKlausuren> NotenKlausurenQ22;

            String json11 = settings.getString("NotenKlausurenQ11", null);
            String json12 = settings.getString("NotenKlausurenQ12", null);
            String json21 = settings.getString("NotenKlausurenQ21", null);
            String json22 = settings.getString("NotenKlausurenQ22", null);

            NotenKlausurenQ11 = gson.fromJson(json11, type);
            NotenKlausurenQ12 = gson.fromJson(json12, type);
            NotenKlausurenQ21 = gson.fromJson(json21, type);
            NotenKlausurenQ22 = gson.fromJson(json22, type);

            SchnittNotenList.addAll(NotenKlausurenQ11);
            SchnittNotenList.addAll(NotenKlausurenQ12);
            SchnittNotenList.addAll(NotenKlausurenQ21);
            SchnittNotenList.addAll(NotenKlausurenQ22);

            }

        } else {
            String json = settings.getString("NotenKlausuren", null);
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Memory_NotenKlausuren>>() {}.getType();
            SchnittNotenList = gson.fromJson(json, type);
        }

        int StundenAnzahl = settings.getInt("MaxStunden", 11);
        Height = settings.getInt("Height", 0);


        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        float dpHeight = (((Height / displayMetrics.density) - menuHeight) / StundenAnzahl);

        int itemWidth = displayMetrics.widthPixels / 5;
        int itemHeight = (int) ((dpHeight * displayMetrics.density) + 0.49);

        if (!isCreated) {
            StundenListe.add(new Spalte("Montag", DateMonday, itemWidth, day == 2 && Week == 0));
            StundenListe.add(new Spalte("Dienstag", DateTuesday, itemWidth, day == 3 && Week == 0));
            StundenListe.add(new Spalte("Mittwoch", DateWednesday, itemWidth, day == 4 && Week == 0));
            StundenListe.add(new Spalte("Donnerstag", DateThurstday, itemWidth, day == 5 && Week == 0));
            StundenListe.add(new Spalte("Freitag", DateFriday, itemWidth, day == 6 && Week == 0));

            isCreated = true;
            String json;
            Gson gson = new Gson();


            if (WeekofYear % 2 == 0 || !settings.getBoolean("zweiWöchentlich", false)) {

                json = settings.getString("Stundenliste", null);

            } else {


                json = settings.getString("WocheBStundenListe", null);

            }


            Type type = new TypeToken<ArrayList<Memory_Stunde>>() {
            }.getType();
            MemoryStundenListe = gson.fromJson(json, type);


            int MaxStunden = settings.getInt("MaxStunden", 0);
            for (int i = 0; i < MaxStunden * 5; i++) {
                Boolean Raumwechsel = false;
                Boolean Lehrerwechsel = false;
                Boolean Entfaellt = false;
                Boolean Klausur = false;
                Boolean Veranstalltung = false;
                String Kurs = "";
                String Datum = "";
                Boolean Schriftlich;

                Boolean Today = false;
                String Wochentag = null;
                switch (i % 5) {
                    case 0:
                        Wochentag = "Montag";
                        Datum = DateMonday;
                        Today = day == 2 && Week == 0;
                        break;
                    case 1:
                        Wochentag = "Dienstag";
                        Datum = DateTuesday;
                        Today = day == 3 && Week == 0;
                        break;
                    case 2:
                        Wochentag = "Mittwoch";
                        Datum = DateWednesday;
                        Today = day == 4 && Week == 0;
                        break;
                    case 3:
                        Wochentag = "Donnerstag";
                        Datum = DateThurstday;
                        Today = day == 5 && Week == 0;
                        break;
                    case 4:
                        Wochentag = "Freitag";
                        Datum = DateFriday;
                        Today = day == 6 && Week == 0;
                        break;
                }




                int Stunde = (i - (i % 5)) / 5;


                String Fach = MemoryStundenListe.get(i).getFachkürzel();

                String NächsteStunde;
                Boolean NächsteFreistunde;

                if (i < MaxStunden * 5 - 5) {
                    NächsteStunde = MemoryStundenListe.get(i + 5).getFachkürzel();
                    NächsteFreistunde = MemoryStundenListe.get(i + 5).isFreistunde();
                } else {
                    NächsteStunde = "Stundenplanende";
                    NächsteFreistunde = false;
                }

                String Lehrer = MemoryStundenListe.get(i).getLehrer();
                String Raum = MemoryStundenListe.get(i).getRaum();
                Boolean Freistunde = MemoryStundenListe.get(i).isFreistunde();
                Boolean DoppelStunde = (!Freistunde && Fach.equals(NächsteStunde)) || (Freistunde && NächsteFreistunde);
                Boolean ShowStunde;

                Calendar timecalendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("HHmm", Locale.getDefault());
                int time = Integer.parseInt(mdformat.format(timecalendar.getTime()));


                Boolean aktiveStunde = false;

                if (Today) {
                    switch (Stunde) {
                        case 0:
                            aktiveStunde = time >= 745 && (time <= 830 || (time < 920 && DoppelStunde));

                            break;
                        case 1:
                            aktiveStunde = time >= 835 && (time <= 920 || (time < 1025 && DoppelStunde));

                            break;
                        case 2:
                            aktiveStunde = time >= 940 && (time <= 1025 || (time < 1115 && DoppelStunde));

                            break;
                        case 3:
                            aktiveStunde = time >= 1030 && (time <= 1115 || (time < 1215 && DoppelStunde));

                            break;
                        case 4:
                            aktiveStunde = time >= 1130 && (time <= 1215 || (time < 1300 && DoppelStunde));

                            break;
                        case 5:
                            aktiveStunde = time >= 1215 && (time <= 1300 || (time < 1345 && DoppelStunde));

                            break;
                        case 6:
                            aktiveStunde = time >= 1315 && (time <= 1400 || (time < 1445 && DoppelStunde));
                            break;

                        case 7:
                            aktiveStunde = time >= 1400 && (time <= 1445 || (time < 1530 && DoppelStunde));
                            break;

                        case 8:
                            aktiveStunde = time >= 1445 && (time <= 1530 || (time < 1615 && DoppelStunde));
                            break;

                        case 9:
                            aktiveStunde = time >= 1530 && (time <= 1615 || (time < 1700 && DoppelStunde));

                            break;
                        case 10:
                            aktiveStunde = time >= 1615 && (time <= 1700 || (time < 1745 && DoppelStunde));

                            break;
                        case 11:
                            aktiveStunde = time >= 1700 && (time <= 1745 || (time < 1830 && DoppelStunde));
                            break;
                        case 12:
                            aktiveStunde = time >= 1745 && (time <= 1830 || (time < 1915 && DoppelStunde));
                            break;
                        case 13:
                            aktiveStunde = time >= 1830 && (time <= 1915 || (time < 2000 && DoppelStunde));
                            break;
                        default:
                            aktiveStunde = false;
                            break;
                    }
                }

                if (i >= 5) {
                    ShowStunde = !lastDoppelStunde.get(i - 5);
                } else {
                    ShowStunde = true;
                }

                if (i >= 5) {
                    if (lastShowStunde.get(i - 5)) {
                        ShowStunde = true;
                    }
                }

                int Day = i % 5;

                if (i < (MaxStunden * 5 - 5)) {
                    if (Freistunde) {
                        for (int j = 0; j < 5; j++) {
                            if (!((MemoryStundenListe.get(i - Day + j).isFreistunde() && MemoryStundenListe.get(i - Day + 5 + j).isFreistunde()) || (!MemoryStundenListe.get(i - Day + j).isFreistunde() && !MemoryStundenListe.get(i - Day + 5 + j).isFreistunde()))) {
                                DoppelStunde = false;
                            }
                        }
                    }

                }

                Kurs = MemoryStundenListe.get(i).getKürzel();
                Schriftlich = MemoryStundenListe.get(i).isSchriftlich();


                Lehrerwechsel = false;
                Raumwechsel = false;
                Entfaellt = false;
                Veranstalltung = false;

                //Kalender events

                boolean kalender_imStundenplan = settings.getBoolean("kalenderImStundenplan", true);
                if(kalender_imStundenplan) {
                    String kalender_json = settings.getString("Kalender", "");
                    List<kalender_event> kalenderListe;
                    if (!kalender_json.equals("")) {

                        Type kalender_type = new TypeToken<ArrayList<kalender_event>>() {
                        }.getType();
                        kalenderListe = gson.fromJson(kalender_json, kalender_type);
                        String Ferienbeginn = "";
                        for (int j = 0; j < kalenderListe.size(); j++) {
                            if (kalenderListe.get(j).getDate().replace(".20", ".").replace(".0", ".").equals(Datum.replace(".0", "."))) {
                                if (kalenderListe.get(j).getType().equals("Freier Tag")) {
                                    Entfaellt = true;

                                } else if (kalenderListe.get(j).getFach().equals(MemoryStundenListe.get(i).getFach())) {
                                    if (kalenderListe.get(j).getType().equals("Hausaufgabe")) {
                                        Lehrerwechsel = true;
                                        Lehrer = "HA";


                                    } else if (kalenderListe.get(j).getType().equals("Klausur")) {
                                        Klausur = true;
                                    }
                                }

                            }
                            if (kalenderListe.get(j).getType().equals("Ferienbeginn")) {
                                Ferienbeginn = kalenderListe.get(j).getDate();
                            }
                            if (kalenderListe.get(j).getType().equals("Ferienende") && !(Ferienbeginn.equals(""))) {
                                if (DateStringinDateInt(Datum) <= DateStringinDateInt(kalenderListe.get(j).getDate()) && DateStringinDateInt(Ferienbeginn) <= DateStringinDateInt(Datum)) {
                                    Entfaellt = true;
                                }
                            }

                        }

                    }
                }






                if (settings.getBoolean("VertretungsplanInStundenplanAnzeigen", true)) {
                    try {
                        VertretungsStunde s = VertretungsPlanMethoden.kursInfo(settings, Kurs, Datum);
                        switch (s.type) {
                            case 2:
                                Klausur = true;
                                if (!Raum.equals(s.raum))
                                    Raum = s.raum;
                                if (!Schriftlich)
                                    Entfaellt = true;
                                break;
                            case 1:
                                Lehrerwechsel = true;
                                Lehrer = s.lehrer;
                                if (!Raum.equals(s.raum))
                                    Raum = s.raum;
                                break;
                            case 3:
                                Raumwechsel = true;
                                Raum = s.raum;
                                break;
                            case 4:
                                Entfaellt = true;
                                break;
                            case 5:
                                Veranstalltung = true;
                                break;
                        }
                    }catch (NullPointerException e){

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                }


                if(!(SchnittNotenList == null)) {
                    for (int j = 0; SchnittNotenList.size() > j; j++) {
                        if (SchnittNotenList.get(j).getFach().equals(MemoryStundenListe.get(i).getFach())) {
                            if (Integer.toString(SchnittNotenList.get(j).getDatum1()).replace("20", "").equals(Datum.replace(".", ""))) {
                                Klausur = true;
                            }

                            if (Integer.toString(SchnittNotenList.get(j).getDatum2()).replace("20", "").equals(Datum.replace(".", ""))) {
                                Klausur = true;
                            }

                            if (Integer.toString(SchnittNotenList.get(j).getDatum3()).replace("20", "").equals(Datum.replace(".", ""))) {
                                Klausur = true;
                            }
                        }
                    }
                }

                if (ShowStunde && !Freistunde) {
                    StundenListe.add(new Stunde(Wochentag, DoppelStunde, itemHeight, itemWidth, dpHeight, Fach, Lehrer, Raum, Raumwechsel, Lehrerwechsel, Entfaellt, Klausur, Veranstalltung, aktiveStunde));
                } else if (ShowStunde) {
                    StundenListe.add(new Freistunde(Wochentag, DoppelStunde, itemHeight, itemWidth));
                }

                lastDoppelStunde.add(DoppelStunde);
                lastShowStunde.add(!ShowStunde);

            }


        }
    }



    public void reload() {
        isCreated = false;
        StundenListe.clear();
        init();
        mystundenplanadapter.notifyDataSetChanged();


    }


    public void timereload() {
        @Nullable
        SharedPreferences preferences = (getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0));
        if (!(preferences == null)) {
            SharedPreferences settings = getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0);

            int time1 = settings.getInt("Time", 0);
            Calendar timecalendar = Calendar.getInstance();
            SimpleDateFormat mdformat = new SimpleDateFormat("HHmm", Locale.getDefault());
            SimpleDateFormat dateformat = new SimpleDateFormat("D", Locale.getDefault());
            int time2 = Integer.parseInt(mdformat.format(timecalendar.getTime()));
            int day1 = settings.getInt("Day", 0);
            int day2 = Integer.parseInt(dateformat.format(timecalendar.getTime()));

            if ((time1 < 745 && time2 >= 745) || (time1 < 835 && time2 >= 835) || (time1 < 920 && time2 >= 920) || (time1 < 940 && time2 >= 940) || (time1 < 1025 && time2 >= 1025) || (time1 < 1030 && time2 >= 1030) || (time1 < 1115 && time2 >= 1115) || (time1 < 1130 && time2 >= 1130) || (time1 < 1215 && time2 >= 1215) || (time1 < 1300 && time2 >= 1300) || (time1 < 1315 && time2 >= 1315) || (time1 < 1400 && time2 >= 1400) || (time1 < 1445 && time2 >= 1445) || (time1 < 1530 && time2 >= 1530) || (time1 < 1615 && time2 >= 1615) || (time1 < 1700 && time2 >= 1700) || (time1 < 1830 && time2 >= 1830) || (time1 < 1915 && time2 >= 1915) || !(day1 == day2)) {
                isCreated = false;
                StundenListe.clear();
                init();
                mystundenplanadapter.notifyDataSetChanged();
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("Time", time2);
                editor.putInt("Day", day2);
                editor.apply();
            }

        }
    }


    public int DateStringinDateInt(String input){
        if(!(input.equals(""))){
        int out = 10000*Integer.parseInt(input.substring(input.length()-2, input.length()));
        int pos = 0;
        if((input.charAt(2) == '.')){
           out = out + Integer.parseInt(input.substring(0, 2));
           pos = 3;

        }else{
        out = out +  Integer.parseInt(input.substring(0, 1));
        pos = 2;
        }

        if((input.charAt(pos+2) == '.')){
           out = out + 100*Integer.parseInt(input.substring(pos, pos+2));


        }else{
        out = out +  100*Integer.parseInt(input.substring(pos, pos+1));

        }
        return out;

        }
return 0;

    }

}

