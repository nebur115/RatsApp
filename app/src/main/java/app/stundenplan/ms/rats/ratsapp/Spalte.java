package app.stundenplan.ms.rats.ratsapp;

/**
 * Created by User on 28.03.2018.
 */

public class Spalte {
    String Wochentag;
    String Datum;
    int Breite;
    boolean Today;

    public boolean isToday() {
        return Today;
    }

    public Spalte(){

    }

    public void setToday(boolean today) {
        Today = today;
    }

    public Spalte(String pWochentag, String pDatum, int pBreite, boolean pToday){
        Wochentag = pWochentag;
        Datum = pDatum;
        Breite = pBreite;
        Today = pToday;

    }

    public void setBreite(int breite) {
        Breite = breite;
    }

    public int getBreite() {

        return Breite;
    }

    public void setWochentag(String wochentag) {
        Wochentag = wochentag;
    }

    public void setDatum(String datum) {
        Datum = datum;
    }

    public String getWochentag() {

        return Wochentag;
    }

    public String getDatum() {
        return Datum;
    }
}
