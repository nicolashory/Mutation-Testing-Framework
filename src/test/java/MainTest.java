import static org.junit.Assert.*;
import org.junit.Test;
/**
 * @author Nicolas HORY
 * @version 12/02/16.
 */
public class MainTest {
    @Test
    public void testHelloWorld() {
        Main mainTest = new Main();
        String testString = mainTest.helloWorld();
        assertEquals("Hello World !", testString);
    }

    @Test
    public void testHelloBla() {
        Main main = new Main();
        String test = main.helloWorld();
        assertEquals("Bye", test);
    }
}
