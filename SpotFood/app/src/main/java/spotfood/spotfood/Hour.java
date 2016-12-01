package spotfood.spotfood;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Hour {
    private Date open;
    private Date close;

    public Hour() {

    }

/*    public Hour(String op, String clo) {
            String [] strList = op.split(":");
            this.open = Integer.parseInt(strList[0])*100 + Integer.parseInt(strList[1]);
            strList = clo.split(":");
            this.close = Integer.parseInt(strList[0])*100 + Integer.parseInt(strList[1]);
    }*/

    public Hour(Date open, Date close) {
        this.open = open;
        this.close = close;
    }

    public int getClose() {
        return close.getHours() * 100 + close.getMinutes();
    }

    public void setClose(Date close) {
        this.close = close;
    }

    public int getOpen() {
        return open.getHours() * 100 + open.getMinutes();
    }

    public void setOpen(Date open) {
        this.open = open;
    }

}
