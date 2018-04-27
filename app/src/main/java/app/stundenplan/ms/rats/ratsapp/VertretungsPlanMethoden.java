package app.stundenplan.ms.rats.ratsapp;

import android.content.SharedPreferences;
import android.os.HandlerThread;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by Marius on 16.03.2018.
 */

public class VertretungsPlanMethoden {

    /**
     * Created by Marius on 13.03.2018.
     */
    public static Obtionen option;
    public static String Ziel;
    public static List<Object> itemlist;
    public static boolean downloadedDaten = false;
    public static fragment_vertretungsplan context = null;
    public static String input = "";
    public static String[][] replacements = {{"M/PH/IF", "Mathe Physik Informatik"},{"BI/CH","Bio Chemie"},{"BI","Bio"},{"CH","Chemie"},{"EK","Erdkunde"},{"ER","ev. Religion"}
    ,{"Ge", "Geschichte"},{"IFGR", "Informatische Grundbildung"},{"If", "Informatik"},{"KR","kath. Religion"},{"Ku","Kunst"},{"Li","Literatur"}
    ,{"Mu", "Musik"},{"Pa","Pädagogik"},{"Ph", "Physik"},{"PK","Politik"},{"PL","Philosophie"},{"PP","Praktische Philosophie"}
    ,{"Sp","Sport"},{"Sw","Sozialwissenschaften"},{"I","Italienisch"},{"D","Deutsch"},{"E","Englisch"},{"S","Spanisch"},{"F","Französisch"},{"M","Mathe"},{"N", "Niederländisch"},{"L","Latein"}};

    /**
     * Bereitet das ergebnis von htmlGet auf
     *
     * @param pZiel
     * @return
     * @throws Exception
     */
    public static String[] htmlGetVertretung(String pZiel) throws Exception {

        //Deklarieren + Initialisieren der Variablen
        String[] output = new String[2];

        //vertretungsplan.zeigeLaden();

        Ziel = pZiel;
        Thread download = new HandlerThread("DownloadHandler") {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder().url(Ziel).header("Content-Type", "application/json; charset=utf-8").build();
                    input = new OkHttpClient().newCall(request).execute().body().string();
                } catch (Exception e) {
                    input = "";
                }
            }
        };
        download.start();
        download.join();

        //vertretungsplan.versteckeLaden();
        //Wenn es geupdated werden soll
        if (!input.equals("FAIL") && !input.equals("Kein Update")) {

            //Teilt den input in die verschiedenen Zeilen auf
            String[] in = input.split("\n");

            //Initialisieren von weiteren Variablen
            boolean first = true;
            int row = 0;

            //Solange es weitere Zeilen gibt
            while (row < in.length) {
                if (first) {
                    first = false;
                    //Stand
                    output[0] = in[row];
                } else {
                    //Vertretungsstunden
                    output[1] += "\n" + in[row];
                }
                row++;
            }
            return output;
        } else {
            return new String[]{""};
        }
    }


    /**
     * Leitet den gesamten Download der VertretungsPlanDaten
     */
    public static void downloadDaten(SharedPreferences share) throws Exception {

        //Variablen werden Deklariert und zum Teil initialisiert
        String Stand;
        String request = "https://rats-ms.de/services/stupla_s/output.php";
        SpeicherVerwaltung s;

        //Vereinfachter Zugriff auf die SharedPreference
        s = new SpeicherVerwaltung(share);
        //Holt die beiden Variablen aus der SharedPreference
        /*
        Fehler mit dem Server muss nocheinmal mit Oeljeklaus reden
        try {
            Stand = s.getString("Stand");
        } catch (Exception e) {
            Stand = "";
        }

        if (Stand != null && !Stand.equals(""))
            request += "?Stand=" + Stand;
        */

        String[] y = htmlGetVertretung(request);
        if (y.length > 0) {
            if (!y[0].equals("") && !y[1].equals("") && y[1] != null) {
                s.setString("Stand", y[0]);
                s.setString("VertretungsPlanInhalt", y[1]);
            }
        }
        downloadedDaten = true;

    }

    /**
     * Downloaded und Zeigt die VertretungsPlanDaten an
     *
     * @param ItemList
     * @param s
     */
    public static void VertretungsPlan(List<Object> ItemList, SharedPreferences s, boolean AlleKlassen, fragment_vertretungsplan fragment) {
        itemlist = ItemList;
        try {
            String Stufe = new SpeicherVerwaltung(s).getString("Stufe");

            zeigeDaten(ItemList, s, Stufe, AlleKlassen);
            if (fragment != null) {
                context = fragment;
                fragment.reload(false);
            }
        } catch (Exception e) {
            ItemList.add(new Ereignis(e.getMessage(), e.getMessage(), e.getMessage(), e.getMessage(), e.getMessage(), R.drawable.entfaellt));
        }
    }
    public static void VertretungsPlan(List<Object> ItemList, SharedPreferences s, boolean AlleKlassen, fragment_vertretungsplan fragment, String Stufe) {
        itemlist = ItemList;
        try {
            zeigeDaten(ItemList, s, Stufe, AlleKlassen);
            if (fragment != null) {
                context = fragment;
                fragment.reload(false);
            }
        } catch (Exception e) {
            ItemList.add(new Ereignis(e.getMessage(), e.getMessage(), e.getMessage(), e.getMessage(), e.getMessage(), R.drawable.entfaellt));
        }
    }

    /**
     * Zeigt die Daten für den VertretungsPlan an
     *
     * @param ItemList
     * @param share
     * @param stufe
     * @throws Exception
     */
    private static void zeigeDaten(List<Object> ItemList, SharedPreferences share, String stufe, boolean AlleKlassen) throws Exception {
        //Variablen
        String Inhalt = new SpeicherVerwaltung(share).getString("VertretungsPlanInhalt");
        String[] lines = Inhalt.split("\n");
        String temp = "";
        int row = 0;

        if (stufe != null) {
            option = new Obtionen(stufe);
            ItemList.add(option);

            while (row + 12 < lines.length) {
                if (nachGestern(lines[row + 13])) {
                    if ((AlleKlassen || /*hier test ob Kurs vom User ist*/true) && lines[row + 2].contains(stufe)) {
                        if (!temp.equals(lines[row + 13])) {
                            ItemList.add(new Datum(lines[row + 12] + " " + lines[row + 13]));
                            temp = lines[row + 13];
                        }
                        switch (lines[row + 8]) {
                            case "2":
                                ItemList.add(new Ereignis(schreibeAus(lines[row + 7], 1, stufe), schreibeAus(lines[row + 7], 2, stufe), lines[row + 10], lines[row + 9], lines[row + 4], R.drawable.ausrufezeichen));
                                break;
                            case "1":
                                if (lines[row + 9].contains("Abiturklausur") || lines[row + 9].contains("Klausur"))
                                    ItemList.add(new Ereignis(schreibeAus(lines[row + 7], 1, stufe), schreibeAus(lines[row + 7], 2, stufe), lines[row + 10], lines[row + 9] + " " + lines[row + 6], lines[row + 4], R.drawable.klausur));
                                else
                                    ItemList.add(new Ereignis(schreibeAus(lines[row + 7], 1, stufe), schreibeAus(lines[row + 7], 2, stufe), lines[row + 10], lines[row + 9] + " " + lines[row + 6], lines[row + 4], R.drawable.raumwechsel));
                                break;
                            case "0":
                                ItemList.add(new Ereignis(schreibeAus(lines[row + 7], 1, stufe), schreibeAus(lines[row + 7], 2, stufe), lines[row + 10], lines[row + 9], "", R.drawable.entfaellt));
                                break;
                            default:
                                ItemList.add(new Ereignis(schreibeAus(lines[row + 7], 1, stufe), schreibeAus(lines[row + 7], 2, stufe), lines[row + 10], lines[row + 9], "", R.drawable.ausrufezeichen));
                                break;
                        }

                    }
                }
                row += 13;
            }
        }
    }

    private static boolean nachGestern(String Tag) {
        try{
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
            Date result =  df.parse(Tag);
            return result.after(yesterday());
        }catch(Exception e){
            return true;
        }
    }

    private static Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    public static String schreibeAus(String Fach, int mode, String Stufe) throws Exception {
        String regex;
        if(Stufe.equals("EF")||Stufe.equals("Q1") || Stufe.equals("Q2"))
            regex = "( )+";
        else
            regex = "(-)+";
        //Teilt den Kurs in Fach und Kursnummer
        String[] input = Fach.split(regex);
        if (input.length > 1) {
            if (mode == 1) {
                for (String[] replacement : replacements) {
                    input[0] = input[0].replaceAll(replacement[0].toUpperCase(), replacement[1]);
                    if (input[0].length() > 2)
                        break;
                }
                input[0].replaceAll("[0-9]+", "");
                return input[0];
            } else {
                //Kursnummer
                return input[1];
            }
        }
        //Falls ein Fehler eintritt
        if(Stufe.equals("EF")||Stufe.equals("Q1") || Stufe.equals("Q2")){

            if (mode == 1) {
                return Fach;
            } else {
                return "";
            }
        }
        int row = 0;
        if (mode == 1) {
            for (String[] replacement : replacements) {
                Fach = Fach.replaceAll(replacement[0].toUpperCase(), replacement[1]);
                if (Fach.length()>2 && row >1)
                    break;
                row++;
            }
            Fach.replaceAll("[0-9]+", "");
            return Fach;
        } else {
            return "";
        }
    }

}