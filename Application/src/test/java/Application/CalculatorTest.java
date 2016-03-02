package Application;

import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest
{
    @Test
    public void testAddition()
    {
        assertEquals(Calculator.addition(1,2),3);
    }

    @Test
    public void multiplication()
    {
        assertEquals(Calculator.multiplication(2,2),4);
    }

    @Test
    public void testSoustraction()
    {
        assertEquals(Calculator.soustraction(2,1),1);
    }

    @Test(timeout=1000)
    public void testFor10()
    {
        assertEquals(Calculator.for10(), 10);
    }
}