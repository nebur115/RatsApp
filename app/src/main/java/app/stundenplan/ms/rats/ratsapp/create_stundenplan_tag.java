package app.stundenplan.ms.rats.ratsapp;

/**
 * Created by User on 05.04.2018.
 */

public class create_stundenplan_tag {
    int Wochentag;
    int Breite;

    public create_stundenplan_tag() {

    }

    public create_stundenplan_tag(int pWochentag, int pBreite) {
        Wochentag = pWochentag;

        Breite = pBreite;
    }

    public void setBreite(int breite) {
        Breite = breite;
    }

    public int getBreite() {

        return Breite;
    }

    public void setWochentag(int wochentag) {
        Wochentag = wochentag;
    }

    public int getWochentag() {

        return Wochentag;
    }

}


