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
        Date openDate = new Date(0, 0, 0, 14, 20);
        Date closeDate = new Date(0, 0, 0, 20, 30);
        mondayHour = new Hour(openDate, closeDate);
        tuesdayHour = new Hour(openDate, closeDate);
        wednesdayHour = new Hour(openDate, closeDate);
        thrusdayHour = new Hour(openDate, closeDate);
        fridayHour = new Hour(openDate, closeDate);
        saturdayHour = new Hour(openDate, closeDate);
        sundayHour = new Hour(openDate, closeDate);

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