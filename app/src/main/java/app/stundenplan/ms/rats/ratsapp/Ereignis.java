package app.stundenplan.ms.rats.ratsapp;

public class Ereignis {

    private String Fach;
    private String Kurs;
    private String Stunde;
    private String Grund;
    private String Lehrer;
    private int Zeichen;



    public Ereignis() {}

    public Ereignis(String pfach, String pkurs, String pstunde, String pgrund, String plehrer, int pzeichen){
        Stunde = pstunde;
        Fach = pfach;
        Kurs = pkurs;
        Grund = pgrund;
        Lehrer = plehrer;
        Zeichen = pzeichen;

    }



    public String getFach() {
        return Fach;
    }

    public void setFach(String fach) {
        this.Fach = fach;
    }



    public String getKurs() {
        return Kurs;
    }

    public void setKurs(String kurs) {
        this.Kurs = kurs;
    }



    public String getStunde() {
        return Stunde;
    }

    public void setStunde(String stunde) {
        this.Stunde = stunde;
    }



    public String getGrund(){
        return Grund;
    }

    public void setGrund(String grund) {
        this.Grund = grund;
    }



    public String getLehrer() {
        return Lehrer;
    }

    public void setLehrer(String lehrer) {
        this.Lehrer = lehrer;
    }

    public int getZeichen() {
        return Zeichen;
    }

    public void setZeichen(int zeichen) {
        this.Zeichen = zeichen;
    }
}
