package mutation;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Mutator that edits the for increment of a program
 * Created by lpotages on 02/03/2016.
 */
public class ForWithoutUpdate extends AbstractProcessor<CtElement>
{
    @Override
    public boolean isToBeProcessed(CtElement candidate) {
        return candidate instanceof CtFor;
    }

    @Override
    public void process(CtElement candidate) {
        if (!(candidate instanceof CtFor)) {
            return;
        }
        CtFor op = (CtFor)candidate;

        // Removes all the initialisations of a for statement
        op.setForUpdate(new ArrayList<CtStatement>());
    }
}
