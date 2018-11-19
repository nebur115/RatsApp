package app.stundenplan.ms.rats.ratsapp;

public class kalender_event {
    String date;
    String Type;
    String Fach;
    String Description;

    public kalender_event(String pType, String pFach, String pDescription, String pDate){
        date=pDate;
        Type=pType;
        Fach=pFach;
        Description = pDescription;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setFach(String fach) {
        Fach = fach;
    }

    public void setDescription(String note) {
        Description = note;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return Type;
    }

    public String getFach() {
        return Fach;
    }

    public String getDescription() {
        return Description;
    }
}
