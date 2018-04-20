package app.stundenplan.ms.rats.ratsapp;

/**
 * Created by Ruben on 05.04.2018.
 */

public class create_stundenplan_createstunde {
    private int Wochentag;
    private int hoehe;
    private int breite;
    private int Woche;

    public create_stundenplan_createstunde(){
    }

    public create_stundenplan_createstunde( int pWochentag, int phoehe, int pbreite, int pWoche){
        Woche = pWoche;
        Wochentag = pWochentag;
        hoehe = phoehe;
        breite = pbreite;

    }


    public int getWoche() {
        return Woche;
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


