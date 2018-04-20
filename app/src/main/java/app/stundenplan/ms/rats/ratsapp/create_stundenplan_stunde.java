package app.stundenplan.ms.rats.ratsapp;

/**
 * Created by User on 04.04.2018.
 */

public class create_stundenplan_stunde {
    private int Wochentag;
    private int hoehe;
    private int breite;
    private String Fach;
    private int Woche;

    public create_stundenplan_stunde(){
    }

    public create_stundenplan_stunde( int pWochentag, int phoehe, int pbreite, String pFach, int pWoche){
        Fach = pFach;
        Wochentag = pWochentag;
        hoehe = phoehe;
        breite = pbreite;
        Woche = pWoche;

    }

    public int getWoche() {
        return Woche;
    }

    public String getFach() {
        return Fach;
    }

    public void setWochentag(int wochentag) {
        Wochentag = wochentag;
    }

    public void setHoehe(int hoehe) {
        this.hoehe = hoehe;
    }

    public void setBreite(int breite) {
        this.breite = breite;
    }

    public int getWochentag() {
        return Wochentag;
    }

    public int getHoehe() {
        return hoehe;
    }

    public int getBreite() {
        return breite;
    }
}


