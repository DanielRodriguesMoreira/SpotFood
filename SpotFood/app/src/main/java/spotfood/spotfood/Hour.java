/*
 * SpotFood - 2016
 *
 * Authors:
 *          -> Daniel Moreira nº21240321
 *          -> Hugo Santos nº21220702
 *          -> Tiago Santos nº21230530
 *          -> Carlos Zambrano nº 21260582
 *          -> Selman Ay nº21260599
 */

package spotfood.spotfood;

import java.util.Date;


public class Hour {
    private int openHour;
    private int openMinutes;
    private int closeHour;
    private int closeMinutes;

    public Hour() {

    }

    public Hour(int openH, int openM, int closeH, int closeM) {
        this.openHour = openH;
        this.openMinutes = openM;
        this.closeHour = closeH;
        this.closeMinutes = closeM;
    }

    public int getOpenHour() {
        return openHour;
    }

    public void setOpenHour(int openHour) {
        this.openHour = openHour;
    }

    public int getOpenMinutes() {
        return openMinutes;
    }

    public void setOpenMinutes(int openMinutes) {
        this.openMinutes = openMinutes;
    }

    public int getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(int closeHour) {
        this.closeHour = closeHour;
    }

    public int getCloseMinutes() {
        return closeMinutes;
    }

    public void setCloseMinutes(int closeMinutes) {
        this.closeMinutes = closeMinutes;
    }
}
