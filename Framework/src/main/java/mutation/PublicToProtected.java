package mutation;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.ModifierKind;

import java.util.Iterator;
import java.util.Set;

/**
 * Transform all Public methods of a program to Protected
 * Created by Loic on 08/03/2016.
 */
public class PublicToProtected extends AbstractProcessor<CtElement> {
    @Override
    public void process(CtElement ctElement) {
        if (!(ctElement instanceof CtMethod)) {
            return;
        }

        CtMethod element = (CtMethod) ctElement;

        element.removeModifier(ModifierKind.PUBLIC);
        element.addModifier(ModifierKind.PROTECTED);
    }
}
