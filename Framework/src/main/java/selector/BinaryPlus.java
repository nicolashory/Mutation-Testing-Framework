package selector;

import core.Mutation;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.declaration.CtElement;

/**
 * Mutates all the Binary Plus element
 * Created by lpotages on 04/03/16.
 */
public class BinaryPlus implements Mutation {
    @Override
    public void process(CtElement candidate) {
        if (!(candidate instanceof CtBinaryOperator)) {
            return;
        }
        CtBinaryOperator op = (CtBinaryOperator)candidate;
        op.setKind(BinaryOperatorKind.PLUS);
    }
}
