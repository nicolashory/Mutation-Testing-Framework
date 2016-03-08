package selector;

import core.Selector;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;

/**
 * Edits one mutation by method
 * Created by lpotages on 05/03/16.
 */
public class OneByMethod implements Selector {
    private Class<?> type;
    private boolean wasValidated;
    private String latestMethod;

    @Override
    public boolean isToBeProcessed(CtElement candidate) {
        if(!latestMethod.equals(candidate.getParent(CtMethod.class).getSimpleName()))
        {
            if (candidate.getParent(CtMethod.class) != null) {
                latestMethod= candidate.getParent(CtMethod.class).getSimpleName();

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
