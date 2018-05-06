package app.stundenplan.ms.rats.ratsapp;

public class Memory_NotenKlausuren {
    private String Kursnummer;
    private String Fach;
    private boolean schrifltich;
    private int Muendlich1;
    private int Muendlich2;
    private int Schriftlich1;
    private int Schriftlich2;
    private int Datum1;
    private int Datum2;
    private  int Zeugnis;

    public Memory_NotenKlausuren(){

    }



    public Memory_NotenKlausuren(String pFach, String pKursnummer, boolean pSchriftlich, int pDatum1, int pdatum2, int pZeugnis){

        Datum1 = pDatum1;
        Datum2 = pdatum2;
        Fach = pFach;
        Kursnummer = pKursnummer;
        schrifltich = pSchriftlich;
        Zeugnis = pZeugnis;

    }

    public int getZeugnis() {
        return Zeugnis;
    }

    public void setZeugnis(int zeugnis) {
        Zeugnis = zeugnis;
    }

    public void setSchrifltich(boolean schrifltich) {
        this.schrifltich = schrifltich;
    }

    public void setDatum1(int datum1) {
        Datum1 = datum1;
    }

    public void setDatum2(int datum2) {
        Datum2 = datum2;
    }

    public boolean isSchrifltich() {
        return schrifltich;
    }

    public int getDatum1() {
        return Datum1;
    }

    public int getDatum2() {
        return Datum2;
    }

    public String getKursnummer() {
        return Kursnummer;
    }

    public String getFach() {
        return Fach;
    }

    public boolean isMuendlichschrifltich() {
        return schrifltich;
    }

    public int getMuendlich1() {
        return Muendlich1;
    }

    public int getMuendlich2() {
        return Muendlich2;
    }

    public int getSchriftlich1() {
        return Schriftlich1;
    }

    public int getSchriftlich2() {
        return Schriftlich2;
    }

    public void setKursnummer(String kursnummer) {
        Kursnummer = kursnummer;
    }

    public void setFach(String fach) {
        Fach = fach;
    }

    public void setMuendlichschrifltich(boolean muendlichschrifltich) {
        schrifltich = muendlichschrifltich;
    }

    public void setMuendlich1(int muendlich1) {
        Muendlich1 = muendlich1;
    }

    public void setMuendlich2(int muendlich2) {
        Muendlich2 = muendlich2;
    }

    public void setSchriftlich1(int schriftlich1) {
        Schriftlich1 = schriftlich1;
    }

    public void setSchriftlich2(int schriftlich2) {
        Schriftlich2 = schriftlich2;
    }
}
