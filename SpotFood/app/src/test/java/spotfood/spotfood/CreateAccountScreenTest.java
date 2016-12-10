package spotfood.spotfood;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CreateAccountScreenTest {

    CreateAccountScreen createAccountScreen;

    @Before
    public void setUp() throws Exception {
        createAccountScreen = new CreateAccountScreen();
        createAccountScreen.mUsernamesList = new ArrayList<>();
    }

    @Test
    public void isLengthCorrect() throws Exception {
        String input;
        // <editor-fold defaultstate="collapsed" desc="False if its null or empty">
        input = null;
        assertFalse(createAccountScreen.isLengthCorrect(input));

        input = "";
        assertFalse(createAccountScreen.isLengthCorrect(input));
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="False if its shorter or equal to 5">
        input = "1234";
        assertFalse(createAccountScreen.isLengthCorrect(input));

        input = "12345";
        assertFalse(createAccountScreen.isLengthCorrect(input));
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="True if its longer then 5">
        input = "123456";
        assertTrue(createAccountScreen.isLengthCorrect(input));

        input = "123455555555";
        assertTrue(createAccountScreen.isLengthCorrect(input));
        // </editor-fold>
    }

    @Test
    public void haveInvalidCharacters() throws Exception {
        String input;

        input = "&";
        assertTrue(createAccountScreen.haveInvalidCharacters(input));

        input = "%";
        assertTrue(createAccountScreen.haveInvalidCharacters(input));

        input = "/";
        assertTrue(createAccountScreen.haveInvalidCharacters(input));

        input = "Teste%";
        assertTrue(createAccountScreen.haveInvalidCharacters(input));

        input = "%Teste";
        assertTrue(createAccountScreen.haveInvalidCharacters(input));

        input = "Tes%te";
        assertTrue(createAccountScreen.haveInvalidCharacters(input));

        input = "Teste&";
        assertTrue(createAccountScreen.haveInvalidCharacters(input));

        input = "&Teste";
        assertTrue(createAccountScreen.haveInvalidCharacters(input));

        input = "Tes&te";
        assertTrue(createAccountScreen.haveInvalidCharacters(input));

        input = "Teste/";
        assertTrue(createAccountScreen.haveInvalidCharacters(input));

        input = "/Teste";
        assertTrue(createAccountScreen.haveInvalidCharacters(input));

        input = "Tes/te";
        assertTrue(createAccountScreen.haveInvalidCharacters(input));
    }

    @Test
    public void checkIfUsernameAlreadyExists() throws Exception {
        String daniel = "Daniel";
        assertFalse(createAccountScreen.checkIfUsernameAlreadyExists(daniel));

        createAccountScreen.mUsernamesList.add(daniel);
        assertTrue(createAccountScreen.checkIfUsernameAlreadyExists(daniel));
    }

}