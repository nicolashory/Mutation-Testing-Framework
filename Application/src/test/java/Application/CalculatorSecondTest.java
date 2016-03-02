package Application;

import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorSecondTest
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
}