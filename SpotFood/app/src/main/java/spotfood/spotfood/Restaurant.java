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

import java.util.List;

/** This class is used to "store" all the information about a restaurant */
public class Restaurant {
    private String idRestaurant;
    private String idUser;
    private String name;
    private Hour mondayHour;
    private Hour tuesdayHour;
    private Hour wednesdayHour;
    private Hour thursdayHour;
    private Hour fridayHour;
    private Hour saturdayHour;
    private Hour sundayHour;
    private String contacts;
    private String location;
    private List<String> typeOfFood;
    private String menu;
    private String offers;

    public Restaurant() {

    }

    public Restaurant(String idRestaurant, String idUser, String name, Hour mondayHour, Hour tuesdayHour,
                      Hour wednesdayHour, Hour thursdayHour, Hour fridayHour, Hour saturdayHour,
                      Hour sundayHour, String contacts, String location, List<String> typeOfFood,
                      String menu, String offers) {
        this.idRestaurant = idRestaurant;
        this.idUser = idUser;
        this.name = name;
        this.mondayHour = mondayHour;
        this.tuesdayHour = tuesdayHour;
        this.wednesdayHour = wednesdayHour;
        this.thursdayHour = thursdayHour;
        this.fridayHour = fridayHour;
        this.saturdayHour = saturdayHour;
        this.sundayHour = sundayHour;
        this.contacts = contacts;
        this.location = location;
        this.typeOfFood = typeOfFood;
        this.menu = menu;
        this.offers = offers;
    }

    public void setName(String name) {
        if(name instanceof String)
            this.name = name;
        else
            this.name = "Default Name";
    }

    public String getLocation() {
        return location;
    }

    public String getIdRestaurant() {
        return idRestaurant;
    }

    public String getIdUser() { return idUser; }

    public String getName() {
        return name;
    }

    public Hour getMondayHour() {
        return mondayHour;
    }

    public Hour getTuesdayHour() {
        return tuesdayHour;
    }

    public Hour getWednesdayHour() {
        return wednesdayHour;
    }

    public Hour getThursdayHour() {
        return thursdayHour;
    }

    public Hour getFridayHour() {
        return fridayHour;
    }

    public Hour getSaturdayHour() {
        return saturdayHour;
    }

    public Hour getSundayHour() {
        return sundayHour;
    }

    public String getContacts() {
        return contacts;
    }

    public List<String> getTypeOfFood() {
        return typeOfFood;
    }

    public Hour getRestaurantHour(int day){
        switch(day) {
            case 1:
                return sundayHour;
            case 2:
                return mondayHour;
            case 3:
                return tuesdayHour;
            case 4:
                return wednesdayHour;
            case 5:
                return thursdayHour;
            case 6:
                return fridayHour;
            case 7:
                return saturdayHour;
            default:
                return null;
        }
    }

    public String getMenu(){ return menu; }

    public String getOffers(){ return this.offers; }
}
