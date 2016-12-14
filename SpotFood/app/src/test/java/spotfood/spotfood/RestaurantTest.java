package spotfood.spotfood;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class RestaurantTest
{
    Restaurant testingRestaurant;
    int dayToFind;
    Hour mondayHour, tuesdayHour, wednesdayHour, thrusdayHour, fridayHour, saturdayHour, sundayHour;

    @Before
    public void setUp() throws Exception
    {
        List<String> typeOfFood = null;

        mondayHour = new Hour(12, 00, 15, 00);
        tuesdayHour = new Hour(12, 00, 15, 00);
        wednesdayHour = new Hour(12, 00, 15, 00);
        thrusdayHour = new Hour(12, 00, 15, 00);
        fridayHour = new Hour(12, 00, 15, 00);
        saturdayHour = new Hour(12, 00, 15, 00);
        sundayHour = new Hour(12, 00, 15, 00);

        testingRestaurant = new Restaurant("1", "1", "RestauranteTeste", mondayHour, tuesdayHour, wednesdayHour, thrusdayHour, fridayHour,
                saturdayHour, sundayHour, "231527802", "Rua de trás", typeOfFood, null, null);
        dayToFind = 3;
    }

    @Test
    public void setRestaurantName() throws Exception {
        String name = null;
        testingRestaurant.setName(name);

        assertEquals("Default Name",testingRestaurant.getName());

        name = "";
        testingRestaurant.setName(name);

        assertEquals("Default Name",testingRestaurant.getName());
    }

    @Test
    public void getRestaurantHour() throws Exception {
        assertNotNull(testingRestaurant);
        assertEquals(tuesdayHour,testingRestaurant.getRestaurantHour(dayToFind));
    }

    @Test
    public void getLocation() throws Exception{
        String location = "Rua de trás";

        testingRestaurant = new Restaurant("1", "1", "RestauranteTeste", mondayHour, tuesdayHour, wednesdayHour, thrusdayHour, fridayHour,
                saturdayHour, sundayHour, "231527802", location, null, null, null);

        assertEquals(location, testingRestaurant.getLocation());

        location = null;
        testingRestaurant = new Restaurant("1", "1", "RestauranteTeste", mondayHour, tuesdayHour, wednesdayHour, thrusdayHour, fridayHour,
                saturdayHour, sundayHour, "231527802", location, null, null, null);

        assertNull(null, testingRestaurant.getLocation());
    }

    @Test
    public void getIdRestaurant() throws Exception{
        String id = "1";

        testingRestaurant = new Restaurant(id, "1", "RestauranteTeste", mondayHour, tuesdayHour, wednesdayHour, thrusdayHour, fridayHour,
                saturdayHour, sundayHour, "231527802", null, null, null, null);

        assertEquals(id, testingRestaurant.getIdRestaurant());

        id = null;
        testingRestaurant = new Restaurant(id, "1", "RestauranteTeste", mondayHour, tuesdayHour, wednesdayHour, thrusdayHour, fridayHour,
                saturdayHour, sundayHour, "231527802", null, null, null, null);

        assertNull(id, testingRestaurant.getIdRestaurant());
    }

    @Test
    public void getIdUser() throws Exception{
        String id = "1";

        testingRestaurant = new Restaurant("1", id, "RestauranteTeste", mondayHour, tuesdayHour, wednesdayHour, thrusdayHour, fridayHour,
                saturdayHour, sundayHour, "231527802", null, null, null, null);

        assertEquals(id, testingRestaurant.getIdUser());

        id = null;
        testingRestaurant = new Restaurant("1", id, "RestauranteTeste", mondayHour, tuesdayHour, wednesdayHour, thrusdayHour, fridayHour,
                saturdayHour, sundayHour, "231527802", null, null, null, null);

        assertNull(id, testingRestaurant.getIdUser());
    }

    @Test
    public void getName() throws Exception{
        String name = "Mc Donalds";

        testingRestaurant = new Restaurant("1", "1", name, mondayHour, tuesdayHour, wednesdayHour, thrusdayHour, fridayHour,
                saturdayHour, sundayHour, "231527802", null, null, null, null);

        assertEquals(name, testingRestaurant.getName());

        name = null;
        testingRestaurant = new Restaurant("1", "1", name, mondayHour, tuesdayHour, wednesdayHour, thrusdayHour, fridayHour,
                saturdayHour, sundayHour, "231527802", null, null, null, null);

        assertNull(name, testingRestaurant.getName());
    }

    @Test
    public void getContacts() throws Exception{
        String contacts = "239020102";

        testingRestaurant = new Restaurant("1", "1", "RestauranteTeste", mondayHour, tuesdayHour, wednesdayHour, thrusdayHour, fridayHour,
                saturdayHour, sundayHour, contacts, null, null, null, null);

        assertEquals(contacts, testingRestaurant.getContacts());

        contacts = null;
        testingRestaurant = new Restaurant("1", "1", "RestauranteTeste", mondayHour, tuesdayHour, wednesdayHour, thrusdayHour, fridayHour,
                saturdayHour, sundayHour, contacts, null, null, null, null);

        assertNull(contacts, testingRestaurant.getContacts());
    }

    @Test
    public void getMenu() throws Exception{
        String menu = "menu";

        testingRestaurant = new Restaurant("1", "1", "RestauranteTeste", mondayHour, tuesdayHour, wednesdayHour, thrusdayHour, fridayHour,
                saturdayHour, sundayHour, "231527802", null, null, menu, null);

        assertEquals(menu, testingRestaurant.getMenu());

        menu = null;
        testingRestaurant = new Restaurant("1", "1", "RestauranteTeste", mondayHour, tuesdayHour, wednesdayHour, thrusdayHour, fridayHour,
                saturdayHour, sundayHour, "231527802", null, null, menu, null);

        assertNull(menu, testingRestaurant.getMenu());
    }

    @Test
    public void getOffers() throws Exception{
        String offers = "offers";

        testingRestaurant = new Restaurant("1", "1", "RestauranteTeste", mondayHour, tuesdayHour, wednesdayHour, thrusdayHour, fridayHour,
                saturdayHour, sundayHour, "231527802", null, null, null, offers);

        assertEquals(offers, testingRestaurant.getOffers());

        offers = null;
        testingRestaurant = new Restaurant("1", "1", "RestauranteTeste", mondayHour, tuesdayHour, wednesdayHour, thrusdayHour, fridayHour,
                saturdayHour, sundayHour, "231527802", null, null, null, offers);

        assertNull(offers, testingRestaurant.getOffers());
    }

}