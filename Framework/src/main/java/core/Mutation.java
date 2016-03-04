package core;

import spoon.reflect.declaration.CtElement;

/**
 * Interface for one mutation
 * Created by lpotages on 04/03/16.
 */
public interface Mutation
{
    /**
     * Tells how to process the element
     * @param candidate The element to process
     */
    public void process(CtElement candidate);
}
