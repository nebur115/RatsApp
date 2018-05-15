package app.stundenplan.ms.rats.ratsapp;

public class QPhase_selector {
    String Fach;
    String Q11;
    String Q12;
    String Q21;
    String Q22;

    public QPhase_selector(){
    }

    public QPhase_selector(String pFach, String pQ11, String pQ12, String pQ21, String pQ22){
        Fach = pFach;
        Q11 = pQ11;
        Q12 = pQ12;
        Q21 = pQ21;
        Q22 = pQ22;
    }

    public void setFach(String fach) {
        Fach = fach;
    }

    public void setQ11(String q11) {
        Q11 = q11;
    }

    public void setQ12(String q12) {
        Q12 = q12;
    }

    public void setQ21(String q21) {
        Q21 = q21;
    }

    public void setQ22(String q22) {
        Q22 = q22;
    }

    public String getFach() {
        return Fach;
    }

    public String getQ11() {
        return Q11;
    }

    public String getQ12() {
        return Q12;
    }

    public String getQ21() {
        return Q21;
    }

    public String getQ22() {
        return Q22;
    }
}
