package upb.thesis.constantpropagation.edgefunctions.normal;

import heros.EdgeFunction;
import upb.thesis.constantpropagation.IDELinearConstantAnalysisProblem;

/**
 * Think reverse
 */
public class IntegerTop implements EdgeFunction<Integer> {

    private static final IntegerTop instance = new IntegerTop();

    Integer value = IDELinearConstantAnalysisProblem.TOP;

    private IntegerTop(){
    }

    public static IntegerTop v(){
        return instance;
    }

    @Override
    public Integer computeTarget(Integer integer) {
        return value;
    }

    /**
     * first apply this then second
     * @param secondFunction
     * @return
     */
    @Override
    public EdgeFunction<Integer> composeWith(EdgeFunction<Integer> secondFunction) {
        return secondFunction;
    }

    @Override
    public EdgeFunction<Integer> meetWith(EdgeFunction<Integer> otherFunction) {
        return otherFunction;
    }

    @Override
    public boolean equalTo(EdgeFunction<Integer> other) {
        return this==other;
    }
}