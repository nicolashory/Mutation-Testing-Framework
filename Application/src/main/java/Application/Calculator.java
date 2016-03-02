package Application;

public class Calculator
{
    public static int addition(int a,int b)
    {
        return a+b;
    }
    public static int multiplication(int a,int b)
    {
        return a*b;
    }
    public static int soustraction(int a,int b)
    {
        return a-b;
    }
    public static int for10()
    {
        int a = 0;
        for (int i =0; i < 10; i++)
        {
            a++;
        }

        return a;
    }
}