package spotfood.spotfood;

import java.util.Date;


public class Hour {
    private Date open;
    private Date close;

    public Hour() {

    }

    public Hour(Date open, Date close) {
        this.open = open;
        this.close = close;
    }

    public Date getClose() {
        return close;
    }

    public void setClose(Date close) {
        this.close = close;
    }

    public Date getOpen() {
        return open;
    }

    public void setOpen(Date open) {
        this.open = open;
    }
}
