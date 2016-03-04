package mutation;

import core.Mutation;
import core.Selector;
import selector.BinaryPlus;
import selector.OneByClass;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.declaration.CtElement;

/**
 * Crash test processor
 * Created by lpotages on 04/03/16.
 */
public class CrashProcessor extends AbstractProcessor<CtElement>
{
    private Selector selector;
    private Mutation mutation;

    public CrashProcessor()
    {
        selector = new OneByClass(CtBinaryOperator.class);
        mutation = new BinaryPlus();
    }

    @Override
    public boolean isToBeProcessed(CtElement candidate)
    {
        //return selector.isToBeProcessed(candidate);
        return true;
    }

    @Override
    public void process(CtElement candidate) {
        //mutation.process(candidate);
    }
}
