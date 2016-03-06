package mutation;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Mutator that edits the for increment of a program
 * Created by lpotages on 02/03/2016.
 */
public class ForAlteratedUpdate extends AbstractProcessor<CtElement>
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
        List<CtStatement> updates = op.getForUpdate();
        List<CtStatement> newUpdates = new ArrayList<CtStatement>();

        for(CtStatement s : updates)
        {
            CtCodeSnippetStatement newStatement = getFactory().Core().createCodeSnippetStatement();

            String increment = "";

            if (s.toString().contains("+=")) {
                String[] str = s.toString().split("\\+");
                increment = str[0] + "++";
            }
            else if (s.toString().contains("-="))
            {
                String[] str = s.toString().split("-");
                increment = str[0] + "--";
            }

            else if (s.toString().contains("="))
            {
                String[] str = s.toString().split("=");
                if(str[1].contains("+"))
                    increment = str[0] + "++";
                else
                    increment = str[0] + "--";
            }
            else if (s.toString().contains("++"))
            {
                String[] str = s.toString().split("\\+");
                increment = str[0] + "+=2";
            }
            else if (s.toString().contains("--"))
            {
                String[] str = s.toString().split("-");
                increment = str[0] + "-=2";
            }

            newStatement.setValue(increment);
            newUpdates.add(newStatement);
        }

        op.setForUpdate(newUpdates);
    }
}
