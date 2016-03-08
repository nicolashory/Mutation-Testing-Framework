package mutation;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtElement;

/**
 * Processor that edits an if condition to set it always true
 * Created by Loic on 08/03/2016.
 */
public class IfTrue  extends AbstractProcessor<CtElement> {
    @Override
    public void process(CtElement ctElement) {
        if(!(ctElement instanceof CtIf))
            return;

        CtIf stat = (CtIf) ctElement;
        if (stat.getCondition() != null)
        {
            CtCodeSnippetExpression newCond = getFactory().Core().createCodeSnippetExpression();
            newCond.setValue("true");

           stat.setCondition(newCond);
        }
    }
}
