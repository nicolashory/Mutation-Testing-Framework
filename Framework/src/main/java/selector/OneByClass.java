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
    private Class<? extends CtElement> type;

    private boolean wasValidated;
    private CtClass<?> latestClass;

    /**
     * Constructor that gives a sample of the element to select
     * @param element The prototype with the right type
     */
    public OneByClass(Class<? extends CtElement> element)
    {
        type = element;
        wasValidated = false;
    }

    @Override
    public boolean isToBeProcessed(CtElement candidate) {
        if(!latestClass.equals(candidate.getParent(CtClass.class)))
        {
            if (candidate.getParent(CtClass.class) != null) {
                latestClass = candidate.getParent(CtClass.class);
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
        if (candidate.getClass() != null)
            return candidate.getClass().equals(type.getClass());

        else
            return false;
    }
}
