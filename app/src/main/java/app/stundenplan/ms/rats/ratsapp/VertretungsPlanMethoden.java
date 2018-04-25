package app.stundenplan.ms.rats.ratsapp;

import android.content.SharedPreferences;
import android.os.HandlerThread;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.Date;
import java.util.List;


/**
 * Created by Marius on 16.03.2018.
 */

public class VertretungsPlanMethoden {

    /**
     * Created by Marius on 13.03.2018.
     */
    public static String Ziel;
    public static List<Object> itemlist;
    public static boolean downloadedDaten = false;
    public static fragment_vertretungsplan context = null;
    public static String input = "";
    public static String[][] replacements = {{"KU", "Kunst"}, {"E5", "Englisch"}, {"S6", "Spanisch"}, {"L6", "Latein"}, {"GE", "Geschichte"}, {"Sw", "Sozialwissenschaften"}, {"MU", "Musik"},
            {"If", "Informatik"},{"Pa", "Pädagogik"}, {"BI", "Biologie"}, {"Ek", "Erdkunde"}, {"PH", "Physik"}, {"ER", "ev. Religion"}, {"KR", "kath. Religion"}, {"D", "Deutsch"}, {"M", "Mathe"}};


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

        System.out.println("Debug: Inhalt von Input: "+input);
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



    public static String schreibeAus(String Fach, int mode) throws Exception {
        String[] input = Fach.split("( )+");
        if (input.length > 1) {
            if (mode == 1) {
                for (String[] replacement : replacements) {
                    input[0] = input[0].replaceAll(replacement[0], replacement[1]);
                    if(input[0].length()>2)
                        break;
                }
                return input[0];
            } else {
                return input[1];
            }
        }
        if (mode == 1) {
            return Fach;
        } else {
            return "";
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
            zeigeDaten(ItemList, s, new SpeicherVerwaltung(s).getString("Stufe"), AlleKlassen);
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
    public static void zeigeDaten(List<Object> ItemList, SharedPreferences share, String stufe, boolean AlleKlassen) throws Exception {
        //Variablen
        String Inhalt = new SpeicherVerwaltung(share).getString("VertretungsPlanInhalt");
        String[] lines = Inhalt.split("\n");
        String temp = "";
        int row = 0;

        if (stufe != null)
            ItemList.add(new Obtionen(stufe));


        while (row + 12 < lines.length) {
            if (stufe.equals(lines[row + 2]) || lines[row + 2].contains(stufe)) {
                if (!temp.equals(lines[row + 12]))
                    ItemList.add(new Datum(lines[row + 12] + " " + lines[row + 13]));
                temp = lines[row + 12];
                if (lines[row + 8].equals("2"))
                    ItemList.add(new Ereignis(schreibeAus(lines[row + 7], 1), schreibeAus(lines[row + 7], 2), lines[row + 10], lines[row + 9], lines[row + 4], R.drawable.ausrufezeichen));
                else if (lines[row + 8].equals("1")) {
                    if (lines[row + 9].contains("Abiturklausur") || lines[row + 9].contains("Klausur"))
                        ItemList.add(new Ereignis(schreibeAus(lines[row + 7], 1), schreibeAus(lines[row + 7], 2), lines[row + 10], lines[row + 9] + " " + lines[row + 6], lines[row + 4], R.drawable.klausur));
                    else
                        ItemList.add(new Ereignis(schreibeAus(lines[row + 7], 1), schreibeAus(lines[row + 7], 2), lines[row + 10], lines[row + 9] + " " + lines[row + 6], lines[row + 4], R.drawable.raumwechsel));
                } else if (lines[row + 8].equals("0"))
                    ItemList.add(new Ereignis(schreibeAus(lines[row + 7], 1), schreibeAus(lines[row + 7], 2), lines[row + 10], lines[row + 9], "", R.drawable.entfaellt));
                else
                    ItemList.add(new Ereignis(schreibeAus(lines[row + 7], 1), schreibeAus(lines[row + 7], 2), lines[row + 10], lines[row + 9], "", R.drawable.ausrufezeichen));

            }
            row += 13;
        }
    }


}