package upb.thesis.constantpropagation.flowfunctions;

import heros.flowfunc.Gen;
import sootup.core.jimple.basic.Value;

import java.util.Set;

/**
 * Flow function for handling assignment statements in constant propagation analysis. For example,
 * in the statement "int a = 5;", this class will manage the propagation of the value '5' to the
 * variable 'a'.
 */
public class AssignmentFF extends Gen<Value> {
  /**
   * Constructs a new flow function for an assignment statement.
   *
   * @param genValue The value being assigned in the statement (e.g., the constant or expression
   *     being assigned).
   * @param zeroValue The default or zero value in the analysis, representing uninitialized or
   *     unknown values.
   */
  public AssignmentFF(Value genValue, Value zeroValue) {
    super(genValue, zeroValue);
  }

  @Override
  public Set<Value> computeTargets(Value source) {
    Set<Value> res = super.computeTargets(source);
    return res;
  }
}
