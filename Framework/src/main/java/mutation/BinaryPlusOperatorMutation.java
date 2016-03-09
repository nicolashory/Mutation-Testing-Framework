package mutation;

import core.Selector;
import selector.OneByClass;
import selector.OneByMethod;
import selector.RandomSelector;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtFor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.support.reflect.code.CtBinaryOperatorImpl;

import java.util.function.BinaryOperator;

/**
 * Mutation that switches all binary operators to +
 * Created by lpotages on 26/02/16.
 */
public class BinaryPlusOperatorMutation extends AbstractProcessor<CtElement>
{
    @Override
    public boolean isToBeProcessed(CtElement candidate) {
        return new OneByMethod().isToBeProcessed(candidate);
    }

    @Override
    public void process(CtElement candidate) {
        Selector selector = new RandomSelector(1000);

        if (!selector.isToBeProcessed(candidate))
            return;

        CtBinaryOperator op = (CtBinaryOperator)candidate;

        op.setKind(BinaryOperatorKind.PLUS);
    }
}
