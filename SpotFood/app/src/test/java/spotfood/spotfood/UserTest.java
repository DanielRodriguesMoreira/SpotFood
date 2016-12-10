package spotfood.spotfood;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    User user;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getIdUser() throws Exception {
        String id = "1";

        user = new User(id, "Basilio", "Basilio", "RM");
        assertEquals(id, user.getIdUser());

        id = null;
        user = new User(id, "Basilio", "Basilio", "RM");
        assertNull(id, user.getIdUser());

    }

    @Test
    public void getUsername() throws Exception {
        String username = "Basilio";

        user = new User("1", username, "Basilio", "RM");
        assertEquals(username, user.getUsername());

        username = null;
        user = new User("1", username, "Basilio", "RM");
        assertNull(username, user.getUsername());
    }

    @Test
    public void getPassword() throws Exception {
        String password = "Basilio";

        user = new User("1", "Basilio", password, "RM");
        assertEquals(password, user.getPassword());

        password = null;
        user = new User("1", "Basilio", password, "RM");
        assertNull(password, user.getPassword());
    }

    @Test
    public void getRole() throws Exception {
        String role = "RM";

        user = new User("1", "Basilio", "test", role);
        assertEquals(role, user.getRole());

        role = null;
        user = new User("1", "Basilio", "test", role);
        assertNull(role, user.getRole());
    }

}