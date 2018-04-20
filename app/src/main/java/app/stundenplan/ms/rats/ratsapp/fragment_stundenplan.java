package app.stundenplan.ms.rats.ratsapp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
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


@SuppressLint("ValidFragment")
public class fragment_stundenplan extends Fragment {
    public int Height = vertretungsplan.getheight();
    private List<Memory_Stunde> MemoryStundenListe = new ArrayList<>();
    int Week;
    boolean isCreated = false;
    private List<Boolean> lastShowStunde = new ArrayList<>();
    private List<Boolean> lastDoppelStunde = new ArrayList<>();


    @SuppressLint("ValidFragment")
    public fragment_stundenplan(int pWeek) {
    Week = pWeek;
    }



    private List<Object> StundenListe = new ArrayList<>();


    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){



            View view = inflater.inflate(R.layout.stundenplan_fragment, container, Boolean.parseBoolean(null));

            final RecyclerView stundenplan_recyclerView;

            stundenplan_recyclerView = view.findViewById(R.id.recyclerview_stundenplan);


            StaggeredGridLayoutManager mystaggeredGridLayoutManager = new StaggeredGridLayoutManager(5, 1);

            stundenplan_recyclerView.setLayoutManager(mystaggeredGridLayoutManager);
            stundenplan_recyclerView.setNestedScrollingEnabled(false);
            int menuHeight = 64 + 50 + 40;
            int StundenAnzahl = 11;

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
            DateTuesday = dateFormat.format(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
            DateWednesday = dateFormat.format(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
            DateThurstday = dateFormat.format(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
            DateFriday = dateFormat.format(calendar.getTime());
            int Week = calendar.get(Calendar.WEEK_OF_YEAR);



        SharedPreferences settings = getActivity().getSharedPreferences("RatsVertretungsPlanApp", 0);

        Height = settings.getInt("Height", 0);


        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
            float dpHeight = (((Height / displayMetrics.density) - menuHeight) / StundenAnzahl);

            int itemWidth = displayMetrics.widthPixels / 5;
            int itemHeight = (int) ((dpHeight * displayMetrics.density) + 0.49);

            if(!isCreated) {
                StundenListe.add(new Spalte("Montag", DateMonday, itemWidth));
                StundenListe.add(new Spalte("Dienstag", DateTuesday, itemWidth));
                StundenListe.add(new Spalte("Mittwoch", DateWednesday, itemWidth));
                StundenListe.add(new Spalte("Donnerstag", DateThurstday, itemWidth));
                StundenListe.add(new Spalte("Freitag", DateFriday, itemWidth));

                isCreated = true;
                String json;
                Gson gson = new Gson();



                if (Week % 2 == 0 || !settings.getBoolean("zweiWöchentlich", true)) {

                    json = settings.getString("Stundenliste", null);

                } else {


                    json = settings.getString("WocheBStundenListe", null);

                }




                Type type = new TypeToken<ArrayList<Memory_Stunde>>() {}.getType();
                MemoryStundenListe = gson.fromJson(json , type);

                int MaxStunden = settings.getInt("MaxStunden",0);


                for(int i = 0; i<MaxStunden*5; i++) {
                    String Wochentag;
                    switch (i % 5) {
                        case 0:
                            Wochentag = "Montag";
                            break;
                        case 1:
                            Wochentag = "Dienstag";
                            break;
                        case 2:
                            Wochentag = "Mittwoch";
                            break;
                        case 3:
                            Wochentag = "Donnerstag";
                            break;
                        case 4:
                            Wochentag = "Freitag";
                            break;
                        default:
                            Wochentag = "Montag";
                            break;
                    }
                    String Fach = MemoryStundenListe.get(i).getFachkürzel();

                    String NächsteStunde;
                    Boolean NächsteFreistunde;
                    
                    if(i<MaxStunden*5-5){
                         NächsteStunde = MemoryStundenListe.get(i+5).getFachkürzel();
                         NächsteFreistunde = MemoryStundenListe.get(i+5).isFreistunde();
                    }
                    else{
                        NächsteStunde = "Stundenplanende";
                        NächsteFreistunde = false;
                    }

                    String Lehrer = MemoryStundenListe.get(i).getLehrer();
                    String Raum = MemoryStundenListe.get(i).getRaum();
                    Boolean Freistunde = MemoryStundenListe.get(i).isFreistunde();
                    Boolean DoppelStunde = (!Freistunde && Fach.equals(NächsteStunde)) || (Freistunde && NächsteFreistunde);
                    Boolean ShowStunde;

                    if(i>=5){
                        ShowStunde = !lastDoppelStunde.get(i-5);
                    }
                    else
                    {
                        ShowStunde = true;
                    }

                    if(i>=5)
                    {
                        if (lastShowStunde.get(i - 5)) {
                            ShowStunde = true;
                        }
                    }

                    int Day = i%5;

                    if(i<(MaxStunden*5-5)){
                        if (Freistunde) {
                            for(int j = 0; j<5; j++) {
                                if(!((MemoryStundenListe.get(i - Day + j).isFreistunde() && MemoryStundenListe.get(i -Day + 5 + j).isFreistunde()) || (!MemoryStundenListe.get(i - Day + j).isFreistunde() && !MemoryStundenListe.get(i -Day + 5 + j).isFreistunde()))){
                                    DoppelStunde = false;
                                }
                            }
                        }

                    }

                    if(ShowStunde && !Freistunde){
                        StundenListe.add(new Stunde(Wochentag, DoppelStunde, itemHeight, itemWidth, dpHeight, Fach, Lehrer, Raum));
                    }
                    else if(ShowStunde){
                        StundenListe.add(new Freistunde(Wochentag, DoppelStunde, itemHeight, itemWidth));
                    }

                    lastDoppelStunde.add(DoppelStunde);
                    lastShowStunde.add(!ShowStunde);

                }


            }
        Stundenplanadapter mystundenplanadapter = new Stundenplanadapter(getActivity(), StundenListe);

        stundenplan_recyclerView.setAdapter(mystundenplanadapter);

        return view;
    }

}
