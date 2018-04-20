package app.stundenplan.ms.rats.ratsapp;

/**
 * Created by User on 11.02.2018.
 */

public class Datum {

    private String Date;

    public Datum(){

    }

    public Datum(String pDate) {
        Date = pDate;
    }



    public String getDate() {
        return Date;
    }

    public void setDate(String pDate){
        this.Date= pDate;
    }

}
