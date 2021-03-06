package app.stundenplan.ms.rats.ratsapp;

public class VertretungsStunde {
    public String Klasse;
    public String lehrer;
    public String urspr_lehrer;
    public String fach;
    public String raum;
    public String statt;
    public String status;
    public String hinweis;
    public String std;
    public String wochennr;
    public String wochentag;
    public String datum;
    public int type = 0;

    public VertretungsStunde(String[] in) {
        Klasse = in[0];
        lehrer = in[1];
        urspr_lehrer = in[2];
        fach = in[3];
        raum = in[4];
        statt = in[5];
        status = in[6];
        hinweis = in[7];
        std = in[8];
        wochennr = in[9];
        wochentag = in[10];
        datum = in[11];
        if (in[7].contains("Abiturklausur") || in[7].contains("Klausur") || in[7].contains("klausur"))
            type = 2; //Klausur
        else {
            switch (in[6]) {
                case "2":
                    type = 5; //Information
                    break;
                case "1":
                    if(lehrer.equals(urspr_lehrer))
                        type = 3; //Raumwechsel
                    else
                        type = 1;
                    break;
                case "0":
                    type = 4; //Entfall
                    break;
            }
        }
    }
}
