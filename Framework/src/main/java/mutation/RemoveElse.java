package mutation;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtElement;

/**
 * Function that removes the else statement of an if condition if there is one
 * Created by Loic on 08/03/2016.
 */
public class RemoveElse extends AbstractProcessor<CtElement>
{
    @Override
    public void process(CtElement ctElement) {
        if(!(ctElement instanceof CtIf))
            return;


        CtIf stat = (CtIf) ctElement;
        if (stat.getElseStatement() != null)
        {
            CtCodeSnippetStatement newStatement = getFactory().Core().createCodeSnippetStatement();
            stat.setElseStatement(newStatement);
        }
    }
}
