/*
 * SpotFood - 2016
 *
 * Authors:
 *          -> Daniel Moreira   nº21240321
 *          -> Hugo Santos      nº21220702
 *          -> Tiago Santos     nº21230530
 *          -> Carlos Zambrano  nº 21260582
 *          -> Selman Ay        nº21260599
 */

package spotfood.spotfood;

/** This class is used to store the hours that a restaurant open/close in a specific day.
 *  It was decided to use integers instead of date to make it easier to check if a
 *  restaurant is open at certain hours/minutes */
public class Hour {
    private int openHour;
    private int openMinutes;
    private int closeHour;
    private int closeMinutes;

    public Hour() {

    }

    public Hour(int openH, int openM, int closeH, int closeM) {
        this.setOpenHour(openH);
        this.setOpenMinutes(openM);
        this.setCloseHour(closeH);
        this.setCloseMinutes(closeM);
    }

    public int getOpenHour() {
        return openHour;
    }

    public void setOpenHour(int openHour) {
        if(openHour < 0 || openHour > 24){
            this.openHour = 0;
        }
        else {
            this.openHour = openHour;
        }
    }

    public int getOpenMinutes() {
        return openMinutes;
    }

    public void setOpenMinutes(int openMinutes) {
        if(openMinutes < 0 || openMinutes > 59){
            this.openMinutes = 0;
        }
        else{
            this.openMinutes = openMinutes;
        }

    }

    public int getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(int closeHour) {
        if(closeHour < 0 || closeHour > 24){
            this.closeHour = 0;
        }
        else {
            this.closeHour = closeHour;
        }
    }

    public int getCloseMinutes() {
        return closeMinutes;
    }

    public void setCloseMinutes(int closeMinutes) {
        if(closeMinutes < 0 || closeMinutes > 59){
            this.closeMinutes = 0;
        }
        else {
            this.closeMinutes = closeMinutes;
        }
    }
}
