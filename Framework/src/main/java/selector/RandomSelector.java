package selector;

import core.Selector;
import spoon.reflect.declaration.CtElement;

/**
 * Selector that selects randomly 1 out of X element
 * Created by Loic on 08/03/2016.
 */
public class RandomSelector implements Selector {
    private int rate; // Specifies the rate chance to apply a mutation, basically it will mutate 1 element out of 5

    public RandomSelector()
    {
        rate = 5;
    }
    public RandomSelector(int n)
    {
        if(n >= 2)
            rate=n;
        else
            rate = 5;
    }

    @Override
    public boolean isToBeProcessed(CtElement candidate) {
        int rand = (int)(Math.random() * ((rate + 1)));
        return rand == 1;
    }
}
