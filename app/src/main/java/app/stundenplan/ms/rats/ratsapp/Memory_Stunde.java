package app.stundenplan.ms.rats.ratsapp;

/**
 * Created by User on 13.04.2018.
 */

public class Memory_Stunde {
    private String Fach;
    private String Lehrer;
    private String Raum;
    private boolean Freistunde;
    private boolean Schriftlich;
    private String Kursart;
    private String Fachkürzel;
    private String Kurs;


    public Memory_Stunde(){}


    public Memory_Stunde(Boolean pFreistunde, String pFach, String pFachkürzel, String pKurs, String pLehrer, String pRaum, String pKurart,  Boolean pSchriftlich){
        Kursart = pKurart;
        Fachkürzel = pFachkürzel;
        Kurs = pKurs;
        Schriftlich = pSchriftlich;
        Fach = pFach;
        Lehrer = pLehrer;
        Raum = pRaum;
        Freistunde = pFreistunde;
    }

    public String getFachkürzel() {
        return Fachkürzel;
    }

    public String getKurs() {
        return Kurs;
    }

    public boolean isSchriftlich() {
        return Schriftlich;
    }

    public String getKursart(){return Kursart;}


    public boolean isFreistunde() {
        return Freistunde;
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
