package core;

import spoon.reflect.declaration.CtElement;

/**
 * Basic interface for selectors
 * Created by lpotages on 04/03/16.
 */
public interface Selector
{
    /**
     * Functions that tells if an element has to be processed
     * @param candidate The element to parse
     * @return Is the element to be processed or not
     */
    public boolean isToBeProcessed(CtElement candidate);
}
