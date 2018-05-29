import com.project.client.RandomPasswordGenerator;
import org.junit.Test;

import java.util.regex.Pattern;

import static junit.framework.TestCase.assertTrue;

public class TestClient {
    @Test
    public void GeneratePasswordContainSpecialCharacterTest() {
        String password = new String(RandomPasswordGenerator.generatePassword());
        assertTrue(Pattern.compile("\\W").matcher(password).find());
    }

    @Test
    public void GeneratePasswordContainNumberTest() {
        String password = new String(RandomPasswordGenerator.generatePassword());
        assertTrue(Pattern.compile("[0-9]+").matcher(password).find());
    }

    @Test
    public void GeneratePasswordContainCapitalLetterTest() {
        String password = new String(RandomPasswordGenerator.generatePassword());
        assertTrue(Pattern.compile("[A-Z]+").matcher(password).find());
    }

    @Test
    public void GeneratePasswordCheckLengthTest() {
        String password = new String(RandomPasswordGenerator.generatePassword());
        assertTrue(password.length() >= 8);
    }
}
