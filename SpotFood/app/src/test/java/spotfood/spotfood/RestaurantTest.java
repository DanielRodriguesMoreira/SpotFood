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
                saturdayHour, sundayHour, "231527802", "Rua de trás", typeOfFood);
        dayToFind = 3;
    }

    @Test
    public void getRestaurantHour() throws Exception {
        assertNotNull(testingRestaurant);
        assertEquals(tuesdayHour,testingRestaurant.getRestaurantHour(dayToFind));
    }
}