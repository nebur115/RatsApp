package app.stundenplan.ms.rats.ratsapp;

/**
 * Created by User on 28.03.2018.
 */

public class Freistunde {
    private boolean doppelstunde;
    private String Wochentag;
    private int hoehe;
    private int breite;

    public Freistunde(){

    }

    public Freistunde( String pWochentag, boolean pdoppelstunde, int phoehe, int pbreite){
        doppelstunde = pdoppelstunde;
        Wochentag = pWochentag;
        hoehe = phoehe;
        breite = pbreite;

    }

    public void setDoppelstunde(boolean doppelstunde) {
        this.doppelstunde = doppelstunde;
    }

    public void setWochentag(String wochentag) {
        Wochentag = wochentag;
    }

    public void setHoehe(int hoehe) {
        this.hoehe = hoehe;
    }

    public void setBreite(int breite) {
        this.breite = breite;
    }

    public boolean isDoppelstunde() {
        return doppelstunde;
    }

    public String getWochentag() {
        return Wochentag;
    }

    public int getHoehe() {
        return hoehe;
    }

    public int getBreite() {
        return breite;
    }
}
