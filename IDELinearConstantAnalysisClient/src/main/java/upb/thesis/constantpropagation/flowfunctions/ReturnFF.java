package upb.thesis.constantpropagation.flowfunctions;

import heros.FlowFunction;
import sootup.core.jimple.basic.Local;
import sootup.core.jimple.basic.Value;
import sootup.core.jimple.common.ref.JStaticFieldRef;

import java.util.HashSet;
import java.util.Set;

/**
 * Flow function for handling method return statements where a value is explicitly returned. This
 * class manages the transfer of return values from the callee to the caller.
 */
public class ReturnFF implements FlowFunction<Value> {

  private final Value tgtLocal;
  private final Local retLocal;

  /**
   * Constructs a ReturnFF instance for managing the transfer of return values.
   *
   * @param tgtLocal The target local variable in the caller method that will receive the return
   *     value.
   * @param retLocal The local variable in the callee method that holds the return value.
   */
  public ReturnFF(Value tgtLocal, Local retLocal) {
    this.tgtLocal = tgtLocal;
    this.retLocal = retLocal;
  }

  /**
   * Computes the possible target values that can be reached from the given source value. This
   * method handles both the transfer of the return value and static field references.
   *
   * @param source The source value from which to compute targets.
   * @return A set of target values that can be reached from the source value.
   */
  @Override
  public Set<Value> computeTargets(Value source) {
    Set<Value> res = new HashSet<>();

    if (source.equals(retLocal)) {
      res.add(tgtLocal);
    }

    if (source instanceof JStaticFieldRef) {
      res.add(source);
    }

    return res;
  }
}
