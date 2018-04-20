package app.stundenplan.ms.rats.ratsapp;

/**
 * Created by Ruben on 27.03.2018.
 */

public class Stunde {


    private boolean doppelstunde;
    private int hoehe;
    private int breite;
    private String Fach;
    private String Lehrer;
    private String Raum;
    private float dphoehe;
    private String Wochentag;


    public Stunde(){}


    public Stunde(String pWochentag,  boolean pdoppelstunde, int phoehe, int pbreite, float pdphoehe, String pFach, String pLehrer, String pRaum){

        doppelstunde = pdoppelstunde;
        hoehe = phoehe;
        breite = pbreite;
        Fach = pFach;
        Lehrer = pLehrer;
        Raum = pRaum;
        dphoehe = pdphoehe;
        Wochentag = pWochentag;
    }

    public void setWochentag(String pWochentag) {
        this.Wochentag = pWochentag;
    }
    public float getDphoehe() {
        return dphoehe;
    }

    public String getWochentag() {
        return Wochentag;
    }


    public void setDphoehe(float dphoehe) {
        this.dphoehe = dphoehe;
    }
    public boolean isDoppelstunde() {
        return doppelstunde;
    }

    public int getHoehe() {
        return hoehe;
    }

    public int getBreite() {
        return breite;
    }

    public String getFach() {
        return Fach;
    }

    public String getLehrer() {
        return Lehrer;
    }

    public String getRaum() {
        return Raum;
    }



    public void setDoppelstunde(boolean doppelstunde) {
        this.doppelstunde = doppelstunde;
    }

    public void setHoehe(int hoehe) {
        this.hoehe = hoehe;
    }

    public void setBreite(int breite) {
        this.breite = breite;
    }

    public void setFach(String fach) {
        Fach = fach;
    }

    public void setLehrer(String lehrer) {
        Lehrer = lehrer;
    }

    public void setRaum(String raum) {
        Raum = raum;
    }


}
