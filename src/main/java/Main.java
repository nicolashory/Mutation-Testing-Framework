import framework.CodeTester;

/**
 * @author Nicolas HORY
 * @version 12/02/16.
 */
public class Main {
    public String helloWorld() {
        return "Hello World !";
    }

    public static void main (String[] args)
    {
        CodeTester tester = new CodeTester();
        tester.execute();
    }
}
