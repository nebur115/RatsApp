package app.stundenplan.ms.rats.ratsapp;

/**
 * Created by User on 28.03.2018.
 */

public class Spalte {
    String Wochentag;
    String Datum;
    int Breite;

    public Spalte(){

    }

    public Spalte(String pWochentag, String pDatum, int pBreite){
        Wochentag = pWochentag;
        Datum = pDatum;
        Breite = pBreite;
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
