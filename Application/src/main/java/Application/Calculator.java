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

    public static int max(int a, int b)
    {
        if (a > b)
            return a;
        else
            return b;
    }

    public static int ifStat()
    {
        int j =0;
        for(int i = 0; i < 5; i++)
        {
            if ( i%2 == 0)
                j++;
            else
                j+=2;
        }

        return j;
    }
}