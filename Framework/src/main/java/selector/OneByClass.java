package selector;

import core.Selector;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;

/**
 * Mutates one element in each class
 * Created by lpotages on 04/03/16.
 */
public class OneByClass implements Selector
{
    private Class<?> type;

    private boolean wasValidated;
    private String latestClass;

    /**
     * Constructor that gives a sample of the element to select
     * @param element The prototype with the right type
     */
    public OneByClass(Class<?> element)
    {
        type = element;
        wasValidated = false;
    }

    @Override
    public boolean isToBeProcessed(CtElement candidate) {
        if(!latestClass.equals(candidate.getParent(CtClass.class).getSimpleName()))
        {
            if (candidate.getParent(CtClass.class) != null) {
                latestClass = candidate.getParent(CtClass.class).getSimpleName();

                wasValidated = false;
            }
        }
        if( !wasValidated)
        {
            if(testType(candidate))
            {
                wasValidated = true;
                return true;
            }
        }

        return false;
    }

    private boolean testType(CtElement candidate)
    {
            return candidate.getClass() != null && candidate.getClass().equals(type.getClass());
    }
}
