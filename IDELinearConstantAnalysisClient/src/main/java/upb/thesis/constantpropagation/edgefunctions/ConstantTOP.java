package upb.thesis.constantpropagation.edgefunctions;

import heros.EdgeFunction;
import upb.thesis.constantpropagation.ConstantValue;
import upb.thesis.constantpropagation.IDEConstantPropagationProblem;

/**
 * Represents the "TOP" edge function in the constant propagation framework. The "TOP" function
 * represents the greatest element in the lattice, indicating the most general state where no
 * specific information is known.
 */
public class ConstantTOP implements EdgeFunction<ConstantValue> {
  /** Singleton instance of the ConstantTOP function. */
  private static final ConstantTOP instance = new ConstantTOP();

  /** The TOP element in the constant propagation lattice. */
  ConstantValue value = IDEConstantPropagationProblem.TOP;

  /** Private constructor to enforce the singleton pattern. */
  private ConstantTOP() {}

  /**
   * Retrieves the singleton instance of the ConstantTOP function.
   *
   * @return The singleton instance of ConstantTOP.
   */
  public static ConstantTOP v() {
    return instance;
  }

  /**
   * Computes the target value for the given input. Since this is the TOP function, it always
   * returns the TOP element.
   *
   * @param constantValue The input value to be processed.
   * @return The TOP element.
   */
  @Override
  public ConstantValue computeTarget(ConstantValue constantValue) {
    return value;
  }

  /**
   * Composes this function with another edge function. The composition first applies this function,
   * then the second function.
   *
   * @param secondFunction The function to apply after this function.
   * @return The result of the second function, as TOP has no effect on it.
   */
  @Override
  public EdgeFunction<ConstantValue> composeWith(EdgeFunction<ConstantValue> secondFunction) {
    return secondFunction;
  }

  /**
   * Computes the meet (least upper bound) with another edge function. Since TOP is the greatest
   * element, the result is the other function.
   *
   * @param otherFunction The other edge function to meet with.
   * @return The other function, as TOP has no effect on the meet operation.
   */
  @Override
  public EdgeFunction<ConstantValue> meetWith(EdgeFunction<ConstantValue> otherFunction) {

    return otherFunction;
  }

  @Override
  public boolean equalTo(EdgeFunction<ConstantValue> other) {
    return this == other;
  }
}
