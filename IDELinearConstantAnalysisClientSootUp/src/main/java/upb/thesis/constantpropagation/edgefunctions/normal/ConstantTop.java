package upb.thesis.constantpropagation.edgefunctions.normal;

import heros.EdgeFunction;
import upb.thesis.constantpropagation.ConstantValue;
import upb.thesis.constantpropagation.IDEConstantPropagationProblem;

/**
 * Singleton class representing the TOP value in the constant propagation lattice. The TOP value
 * typically represents an unknown or uninitialized state.
 */
public class ConstantTop implements EdgeFunction<ConstantValue> {
  // Singleton instance of ConstantTop
  private static final ConstantTop instance = new ConstantTop();

  // The constant value representing TOP
  ConstantValue value = IDEConstantPropagationProblem.TOP;

  // Private constructor to enforce the singleton pattern
  private ConstantTop() {}

  /**
   * Returns the singleton instance of ConstantTop.
   *
   * @return The singleton instance of ConstantTop.
   */
  public static ConstantTop v() {
    return instance;
  }

  /**
   * Computes the target value, which is always the TOP value in this case.
   *
   * @param integer The input constant value (ignored).
   * @return The TOP constant value.
   */
  @Override
  public ConstantValue computeTarget(ConstantValue integer) {
    return value;
  }

  /**
   * Composes this edge function with another, effectively ignoring the second function since
   * applying TOP followed by any other function still results in TOP.
   *
   * @param secondFunction The second edge function to compose with.
   * @return This edge function, as the result of composing with TOP is always TOP.
   */
  @Override
  public EdgeFunction<ConstantValue> composeWith(EdgeFunction<ConstantValue> secondFunction) {
    return secondFunction;
  }

  /**
   * Computes the meet (least upper bound) with another edge function. Since TOP represents the
   * least upper bound, the result is always TOP.
   *
   * @param otherFunction The other edge function to meet with.
   * @return This edge function, as the meet with TOP is always TOP.
   */
  @Override
  public EdgeFunction<ConstantValue> meetWith(EdgeFunction<ConstantValue> otherFunction) {
    if (otherFunction instanceof ConstantAssign || otherFunction instanceof ConstantBinop) {
      return this;
    }
    return otherFunction;
  }

  /**
   * Checks if this edge function is equal to another edge function. The TOP edge function is only
   * equal to itself.
   *
   * @param other The other edge function to compare with.
   * @return true if the other edge function is the same instance as this, false otherwise.
   */
  @Override
  public boolean equalTo(EdgeFunction<ConstantValue> other) {
    return this == other;
  }
}
