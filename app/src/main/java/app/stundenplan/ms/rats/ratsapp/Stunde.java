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
    private boolean Raumwechsel;
    private boolean Lehrerwechsel;
    private boolean Entfällt;
    private boolean Klausur;
    private boolean Veranstalltung;


    public Stunde(){}


    public Stunde(String pWochentag,  boolean pdoppelstunde, int phoehe, int pbreite, float pdphoehe, String pFach, String pLehrer, String pRaum, boolean pRaumwechsel,boolean pLehrerwechel,boolean pEntfällt, boolean pKlausur, boolean pVeranstalltung){

        doppelstunde = pdoppelstunde;
        hoehe = phoehe;
        breite = pbreite;
        Fach = pFach;
        Lehrer = pLehrer;
        Raum = pRaum;
        dphoehe = pdphoehe;
        Wochentag = pWochentag;
        Raumwechsel = pRaumwechsel;
        Lehrerwechsel = pLehrerwechel;
        Entfällt = pEntfällt;
        Klausur = pKlausur;
        Veranstalltung = pVeranstalltung;
    }


    public void setRaumwechsel(boolean raumwechsel) {
        Raumwechsel = raumwechsel;
    }

    public void setLehrerwechsel(boolean lehrerwechsel) {
        Lehrerwechsel = lehrerwechsel;
    }

    public void setEntfällt(boolean entfällt) {
        Entfällt = entfällt;
    }

    public void setKlausur(boolean klausur) {
        Klausur = klausur;
    }

    public void setVeranstalltung(boolean veranstalltung) {
        Veranstalltung = veranstalltung;
    }

    public boolean isRaumwechsel() {
        return Raumwechsel;
    }

    public boolean isLehrerwechsel() {
        return Lehrerwechsel;
    }

    public boolean isEntfällt() {
        return Entfällt;
    }

    public boolean isKlausur() {
        return Klausur;
    }

    public boolean isVeranstalltung() {
        return Veranstalltung;
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
