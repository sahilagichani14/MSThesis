package upb.thesis.constantpropagation.flowfunctions;

import heros.FlowFunction;
import sootup.core.jimple.basic.Local;
import sootup.core.jimple.basic.Value;
import sootup.core.jimple.common.ref.JArrayRef;
import sootup.core.jimple.common.ref.JFieldRef;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Flow function for handling assignments from a single local variable. This class handles the
 * transfer of values in cases where the right-hand side of an assignment is a local variable or an
 * array reference.
 */
public class LocalFF implements FlowFunction<Value> {

  private final Local right;
  private final Value lhs;
  private final Value zeroValue;

  /**
   * Constructs a LocalFF instance for handling assignments from a single local variable.
   *
   * @param right The local variable on the right-hand side of the assignment.
   * @param lhs The left-hand side value to which the right-hand side is assigned.
   * @param zeroValue The zero value used in the analysis to represent uninitialized or default
   *     values.
   */
  public LocalFF(Local right, Value lhs, Value zeroValue) {
    this.right = right;
    this.lhs = lhs;
    this.zeroValue = zeroValue;
  }

  /**
   * Computes the possible target values that can be reached from the given source value. This
   * method handles both regular local variable assignments and array references.
   *
   * @param source The source value from which to compute targets.
   * @return A set of target values that can be reached from the source value.
   */
  @Override
  public Set<Value> computeTargets(Value source) {
    if (source.equals(zeroValue)) {
      return Collections.singleton(source);
    }

    Set<Value> res = new HashSet<>();
    res.add(source);

    if (right.equals(source)) {
      res.add(lhs);
    }

    // Handle array references
    if (source instanceof JArrayRef) {
      JArrayRef arrayRef = (JArrayRef) source;
      if (arrayRef.getBase().equals(right) && !(lhs instanceof JFieldRef)) {
        JArrayRef newRef = new JArrayRef((Local) lhs, arrayRef.getIndex());
        res.add(newRef);
      }
    }

    return res;
  }
}
