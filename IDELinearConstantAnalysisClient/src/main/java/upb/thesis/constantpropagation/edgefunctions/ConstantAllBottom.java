package upb.thesis.constantpropagation.edgefunctions;

import heros.EdgeFunction;
import heros.edgefunc.AllBottom;
import upb.thesis.constantpropagation.ConstantValue;

/**
 * Represents the "AllBottom" edge function in the constant propagation framework. The "AllBottom"
 * function is a special case where the result of any operation is always the bottom element in the
 * lattice, indicating an unreachable or invalid state.
 */
public class ConstantAllBottom extends AllBottom<ConstantValue> {

  /**
   * Constructs a ConstantAllBottom edge function with the specified bottom element.
   *
   * @param bottomElement The bottom element in the constant propagation lattice.
   */
  public ConstantAllBottom(ConstantValue bottomElement) {
    super(bottomElement);
  }

  /**
   * Computes the meet (least upper bound) with another edge function. Since this function
   * represents "AllBottom", it will override any other function, meaning the result is always this
   * function.
   *
   * @param otherFunction The other edge function to meet with.
   * @return This edge function, as it represents the bottom element.
   */
  @Override
  public EdgeFunction<ConstantValue> meetWith(EdgeFunction<ConstantValue> otherFunction) {
    return this; // this should override everything
  }
}
