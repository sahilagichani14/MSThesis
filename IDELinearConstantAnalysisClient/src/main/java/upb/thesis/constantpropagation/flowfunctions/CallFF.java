package upb.thesis.constantpropagation.flowfunctions;

import heros.FlowFunction;
import sootup.core.jimple.basic.Immediate;
import sootup.core.jimple.basic.Local;
import sootup.core.jimple.basic.Value;
import sootup.core.jimple.common.constant.IntConstant;
import sootup.core.model.SootMethod;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Flow function for handling method calls in constant propagation analysis. This class maps
 * arguments passed to a method call to the corresponding parameters in the method being called.
 */
public class CallFF implements FlowFunction<Value> {

  private final List<Immediate> callArgs;
  private final SootMethod dest;
  private final Value zeroValue;
  private final List<Local> paramLocals;

  /**
   * Constructs a new flow function for a method call.
   *
   * @param callArgs The list of arguments passed to the method call.
   * @param dest The destination method being called.
   * @param zeroValue The default or zero value in the analysis, representing uninitialized or
   *     unknown values.
   * @param paramLocals The list of parameters in the destination method.
   */
  public CallFF(
      List<Immediate> callArgs, SootMethod dest, Value zeroValue, List<Local> paramLocals) {
    this.callArgs = callArgs;
    this.dest = dest;
    this.zeroValue = zeroValue;
    this.paramLocals = paramLocals;
  }

  /**
   * Computes the set of target values that result from applying the flow function to the source
   * value. This method handles the mapping of call arguments to the parameters in the destination
   * method.
   *
   * @param source The source value being analyzed (typically an argument to the method call).
   * @return A set of values corresponding to the parameters in the destination method.
   */
  @Override
  public Set<Value> computeTargets(Value source) {
    // Ignore implicit calls to static initializers
    if (isStaticInitializer()) {
      return Collections.emptySet();
    }

    Set<Value> res = new HashSet<>();

    for (int i = 0; i < callArgs.size(); i++) {
      // Special case: check if the function is called with integer literals as parameters
      if (isIntegerLiteralArgument(i, source)) {
        res.add(paramLocals.get(i));
      }

      // Ordinary case: map the argument to the corresponding parameter
      if (callArgs.get(i) == source) {
        res.add(paramLocals.get(i));
      }
    }

    return res;
  }

  /**
   * Checks if the destination method is a static initializer.
   *
   * @return true if the destination method is a static initializer, false otherwise.
   */
  private boolean isStaticInitializer() {
    return dest.getName().equals("<clinit>") && dest.getParameterCount() == 0;
  }

  /**
   * Checks if the source value is a zero value and the argument at the specified index is an
   * integer literal.
   *
   * @param index The index of the argument.
   * @param source The source value being analyzed.
   * @return true if the argument is an integer literal and the source is a zero value, false
   *     otherwise.
   */
  private boolean isIntegerLiteralArgument(int index, Value source) {
    return callArgs.get(index) instanceof IntConstant && source == zeroValue;
  }
}
