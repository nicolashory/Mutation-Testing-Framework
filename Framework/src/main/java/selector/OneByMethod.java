package selector;

import core.Selector;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;

/**
 * Edits one mutation by method
 * Created by lpotages on 05/03/16.
 */
public class OneByMethod implements Selector {
    @Override
    public boolean isToBeProcessed(CtElement candidate) {
        CtMethod parent = candidate.getParent(CtMethod.class);

        if (parent!=null && parent.getSimpleName().equals("soustraction"))
            return true;

        return false;
    }
}
