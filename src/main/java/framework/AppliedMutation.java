package framework;

/**
 * Class that will handle all the informations to add to the report so we can know where the change was made
 * Created by Loic on 24/02/2016.
 */
public class AppliedMutation
{
    private String fileName; // in which file was the edit made
    private int indexOfChange; // Where was the change made
    private String oldValue; // Old value of the section
    private String newValue; // New Value of the edited section
}
