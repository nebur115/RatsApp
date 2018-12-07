package app.stundenplan.ms.rats.ratsapp;

import android.content.SharedPreferences;
import android.os.HandlerThread;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;


/**
 * Created by Marius on 16.03.2018.
 */

public class VertretungsPlanMethoden {

    /**
     * Created by Marius on 13.03.2018.
     */
    public static Obtionen option;
    public static boolean all;
    public static String Ziel;
    public static List<Object> itemlist;
    public static boolean downloadedDaten = false;
    public static fragment_vertretungsplan context = null;
    public static String input = "";
    public static final int ANZAHL_LANGEKURSE = 5;
    public static boolean offline = false;
    public static String[][] replacements = {
            {"M/PH/IF", "Mathe Physik Informatik"},
            {"BI/CH", "Bio Chemie"},
            {"IFGR", "Informatische Grundbildung"},
            {"ERG D", "Ergänzung Deutsch"}, {"ERG E", "Ergänzung Englisch"}, {"ERG M", "Ergänzung Mathe"},
            {"BI", "Bio"}, {"CH", "Chemie"}, {"Ek", "Erdkunde"}, {"ER", "ev. Religion"}, {"Ge", "Geschichte"}, {"If", "Informatik"}, {"KR", "kath. Religion"},
            {"Ku", "Kunst"}, {"Li", "Literatur"}, {"Mu", "Musik"}, {"Pa", "Pädagogik"}, {"Ph", "Physik"}, {"PK", "Politik"}, {"PL", "Philosophie"},
            {"PP", "Praktische Philosophie"},{"Sp", "Sport"}, {"Sw", "Sozialwiss."},
            {"I", "Italienisch"},{"D", "Deutsch"}, {"S", "Spanisch"}, {"F", "Französisch"}, {"M", "Mathe"}, {"N", "Niederländisch"}, {"L", "Latein"}, {"E", "Englisch"}};
    public static fragment_stundenplan stundenplanfrag;
    public static int changed = -1;

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
            if(in.length<2)
                throw new Exception();
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
            changed = 1;
            return output;
        } else {
            changed = 0;
            return new String[]{""};
        }
    }


    /**
     * Leitet den gesamten Download der VertretungsPlanDaten
     */
    public static void downloadDaten(SharedPreferences share, boolean einsparen) {


        //Variablen werden Deklariert und zum Teil initialisiert
        String request = "https://rats-ms.de/services/stupla_s/output.php";
        SpeicherVerwaltung s;

        //Vereinfachter Zugriff auf die SharedPreference
        s = new SpeicherVerwaltung(share);
        //Holt die beiden Variablen aus der SharedPreference
        if (einsparen) {
            try {
                request += "?Stand=" + s.getString("Stand");
            } catch (Exception e) {}
        }
        try {
            String[] y = htmlGetVertretung(request);
            if (y.length > 0) {
                if (!y[0].equals("") && !y[1].equals("") && y[1] != null) {
                    s.setString("Stand", y[0]);
                    s.setString("VertretungsPlanInhalt", y[1]);
                }
            }

        }
        catch(Exception e){
            offline = true;
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
    private static void zeigeDaten(List<Object> ItemList, SharedPreferences share, String stufe, boolean AlleKlassen) {

        //Variablen
        String Inhalt = new SpeicherVerwaltung(share).getString("VertretungsPlanInhalt");
        HashSet<String> meineKurse = (HashSet<String>) share.getStringSet("Kursliste", new HashSet<String>());

        HashSet<String> manuellnichtmeineKurse = (HashSet<String>) share.getStringSet("ManuellNichtMeineKurse", new HashSet<String>());
        HashSet<String> manuellmeineKurse = (HashSet<String>) share.getStringSet("ManuellmeineKurse", new HashSet<String>());


        String[] lines = Inhalt.split("\n");
        String temp = "";
        int row = 0;
        if (stufe != null) {
            option = new Obtionen(stufe);
            ItemList.add(option);
            String s ="";

            while (row + 12 < lines.length) {
                if (nachGestern(lines[row + 13])) {
                    if (AlleKlassen || ( manuellmeineKurse.contains(lines[row + 7].replace("  ", " ").toUpperCase())||(meineKurse.contains(lines[row + 7].replace("  ", " ").toUpperCase()) && (!(manuellnichtmeineKurse.contains(lines[row + 7].replace("  ", " ").toUpperCase()))))) || !share.contains("Stundenliste") || !(share.getBoolean("AlleKurseAnzeigen", share.contains("Kursliste")))) {
                        if (lines[row + 2].contains(stufe)) {
                            if (!temp.equals(lines[row+13]) || !temp.contains(lines[row+13])) {
                                ItemList.add(new Datum(lines[row + 12] + " " + lines[row + 13]));
                                temp = lines[row + 13];
                            }
                            if (lines[row + 9].contains("Abiturklausur") || lines[row + 9].contains("Klausur") ||lines[row+9].contains("klausur"))
                                ItemList.add(new Ereignis(schreibeAus(lines[row + 7], stufe), lines[row + 7], lines[row + 10], lines[row + 9] + " " + lines[row + 6], lines[row + 4], R.drawable.klausur));
                            else
                                switch (lines[row + 8]) {
                                    case "2":
                                        ItemList.add(new Ereignis(schreibeAus(lines[row + 7], stufe), lines[row + 7], lines[row + 10], lines[row + 9], lines[row + 4], R.drawable.ausrufezeichen));
                                        break;
                                    case "1":
                                        if(!lines[row+3].contains(lines[row+4]))
                                            ItemList.add(new Ereignis(schreibeAus(lines[row + 7], stufe), lines[row + 7], lines[row + 10], lines[row + 9] + " " + lines[row + 6], lines[row + 3], R.drawable.lehrerwechsel));
                                        else
                                            ItemList.add(new Ereignis(schreibeAus(lines[row + 7], stufe), lines[row + 7], lines[row + 10], lines[row + 9] + " " + lines[row + 6], lines[row + 3], R.drawable.raumwechsel));
                                        break;
                                    case "0":
                                        ItemList.add(new Ereignis(schreibeAus(lines[row + 7], stufe), lines[row + 7], lines[row + 10], lines[row + 9], "", R.drawable.entfaellt));
                                        break;
                                    default:
                                        ItemList.add(new Ereignis(schreibeAus(lines[row + 7], stufe), lines[row + 7], lines[row + 10], lines[row + 9], "", R.drawable.ausrufezeichen));
                                        break;
                                }

                        }
                    }
                }
                row += 13;
            }
        }
    }


    private static boolean nachGestern(String Tag) {
        try {
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
            Date result = df.parse(Tag);
            return result.after(yesterday());
        } catch (Exception e) {
            return true;
        }
    }

    private static Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    public static String schreibeAus(String Fach, String Stufe) {
        String regex;
        if (Stufe.equals("EF") || Stufe.equals("Q1") || Stufe.equals("Q2"))
            regex = "( )+";
        else
            regex = "(-)+";
        //Schreibt das Fach aus
        int row = 0;
        String[] input = Fach.split(regex);
        if (input.length > 1) {
            for (String[] replacement : replacements) {
                input[0] = input[0].toUpperCase().replaceAll(replacement[0].toUpperCase(), replacement[1]);
                if (input[0].length() > 2 && row > ANZAHL_LANGEKURSE)
                    break;
                row++;
            }
            input[0] = input[0].replaceAll("[0-9]+", "");
            return input[0];
        }

        //Falls ein Fehler eintritt
        if (Stufe.equals("EF") || Stufe.equals("Q1") || Stufe.equals("Q2")) {
            return Fach;
        }
        for (String[] replacement : replacements) {
            Fach = Fach.replaceAll(replacement[0].toUpperCase(), replacement[1]);
            if (Fach.length() > 2 && row > ANZAHL_LANGEKURSE)
                break;
            row++;
        }

        return Fach;
    }

    public static VertretungsStunde kursInfo(SharedPreferences share, String kurs, String Datum) {
        //Variablen
        String Inhalt = new SpeicherVerwaltung(share).getString("VertretungsPlanInhalt");
        String stufe = new SpeicherVerwaltung(share).getString("Stufe");
        String[] lines = Inhalt.split("\n");
        int row = 0;
        //Suche nach den der gegebenen Stunde
        while (row + 12 < lines.length) {
            if (lines[row + 7].replace("  ", " ").toUpperCase().equals(kurs)) {
                if (lines[row + 2].contains(stufe)){
                    if (isSameDate(lines[row+13], Datum)) {
                        //Gibt ein Vertretungsstundenobjekt von der gewünschten Stunde zurück
                        return new VertretungsStunde(Arrays.copyOfRange(lines, row + 2, row + 14));
                    }
                }
            }
            row += 13;
        }
        return null;
    }

    public static boolean isSameDate(String datum1, String datum2){
        try {
            Date date1 = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN).parse(datum1);
            Date date2 = new SimpleDateFormat("dd.MM.yy", Locale.GERMAN).parse(datum2);
            return (!date1.after(date2)&&!date1.before(date2));
        } catch (Exception e) {
            System.out.println("Fuck");
            return false;
        }
    }

}
