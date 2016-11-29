package spotfood.spotfood;

import java.util.Date;

/**
 * Created by hugosantos on 29/11/16.
 */

public class Hour {
    private Date open;
    private Date close;

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
