package upb.thesis.constantpropagation.edgefunctions;

import heros.EdgeFunction;
import heros.edgefunc.AllBottom;

public class IntegerAllBottom extends AllBottom<Integer> {

    public IntegerAllBottom(Integer bottomElement) {
        super(bottomElement);
    }

    @Override
    public EdgeFunction<Integer> meetWith(EdgeFunction<Integer> otherFunction) {
        return this; // this should override everything
    }
}
