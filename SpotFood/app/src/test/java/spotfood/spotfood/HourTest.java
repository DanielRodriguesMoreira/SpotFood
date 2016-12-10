package spotfood.spotfood;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HourTest {

    Hour hour;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getOpenHour() throws Exception {
        hour = new Hour(0, 0, 0, 0);
        assertEquals(0, hour.getOpenHour());

        hour = new Hour(-1, 0, 0, 0);
        assertEquals(0, hour.getOpenHour());

        hour = new Hour(44, 0, 0, 0);
        assertEquals(0, hour.getOpenHour());

        hour = new Hour(23, 0, 0, 0);
        assertEquals(23, hour.getOpenHour());
    }

    @Test
    public void getOpenMinutes() throws Exception {
        hour = new Hour(0, 0, 0, 0);
        assertEquals(0, hour.getOpenMinutes());

        hour = new Hour(0, -1, 0, 0);
        assertEquals(0, hour.getOpenMinutes());

        hour = new Hour(0, 67, 0, 0);
        assertEquals(0, hour.getOpenMinutes());

        hour = new Hour(0, 23, 0, 0);
        assertEquals(23, hour.getOpenMinutes());
    }

    @Test
    public void getCloseHour() throws Exception {
        hour = new Hour(0, 0, 0, 0);
        assertEquals(0, hour.getCloseHour());

        hour = new Hour(0, 0, -1, 0);
        assertEquals(0, hour.getCloseHour());

        hour = new Hour(0, 0, 44, 0);
        assertEquals(0, hour.getCloseHour());

        hour = new Hour(0, 0, 23, 0);
        assertEquals(23, hour.getCloseHour());
    }

    @Test
    public void getCloseMinutes() throws Exception {
        hour = new Hour(0, 0, 0, 0);
        assertEquals(0, hour.getOpenMinutes());

        hour = new Hour(0, 0, 0, -1);
        assertEquals(0, hour.getCloseMinutes());

        hour = new Hour(0, 0, 0, 67);
        assertEquals(0, hour.getCloseMinutes());

        hour = new Hour(0, 0, 0, 23);
        assertEquals(23, hour.getCloseMinutes());
    }
}