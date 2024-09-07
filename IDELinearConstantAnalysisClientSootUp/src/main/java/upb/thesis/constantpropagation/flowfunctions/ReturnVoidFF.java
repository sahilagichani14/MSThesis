package upb.thesis.constantpropagation.flowfunctions;

import heros.FlowFunction;
import heros.solver.Pair;
import sootup.core.jimple.basic.Immediate;
import sootup.core.jimple.basic.Local;
import sootup.core.jimple.basic.Value;
import sootup.core.jimple.common.ref.JInstanceFieldRef;
import sootup.core.jimple.common.ref.JStaticFieldRef;
import sootup.core.jimple.common.stmt.JIdentityStmt;
import sootup.core.jimple.common.stmt.JInvokeStmt;
import sootup.core.jimple.common.stmt.Stmt;
import sootup.core.model.SootMethod;
import sootup.core.types.ReferenceType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Flow function for handling method returns where no explicit value is returned, but the value may
 * be returned via an object passed as a parameter.
 */
public class ReturnVoidFF implements FlowFunction<Value> {

  private final Stmt callsite;
  private final SootMethod method;
  private final Value zeroValue;

  /**
   * Constructs a ReturnVoidFF instance.
   *
   * @param callsite The statement at the call site.
   * @param method The method being analyzed.
   * @param zeroValue The zero value used in the analysis.
   */
  public ReturnVoidFF(Stmt callsite, SootMethod method, Value zeroValue) {
    this.callsite = callsite;
    this.method = method;
    this.zeroValue = zeroValue;
  }

  /**
   * Computes the possible target values that can be reached from the given source value. This
   * method handles the propagation of values passed as parameters and field references.
   *
   * @param source The source value from which to compute targets.
   * @return A set of target values that can be reached from the source value.
   */
  @Override
  public Set<Value> computeTargets(Value source) {
    Set<Value> results = new HashSet<>();

    if (source.equals(zeroValue)) {
      return results;
    }

    if ((source instanceof Local) && (callsite instanceof JInvokeStmt)) {
      processLocalSource(results, source);
    } else if ((source instanceof JInstanceFieldRef) && (callsite instanceof JInvokeStmt)) {
      processInstanceFieldRefSource(results, (JInstanceFieldRef) source);
    } else if (source instanceof JStaticFieldRef) {
      results.add(source);
    }

    return results;
  }

  private void processLocalSource(Set<Value> results, Value source) {
    JInvokeStmt invoke = (JInvokeStmt) callsite;
    List<Immediate> args = invoke.getInvokeExpr().get().getArgs();
    for (int argIndex = 0; argIndex < args.size(); argIndex++) {
      if (source.equals(method.getBody().getParameterLocal(argIndex))) {
        results.add(args.get(argIndex));
      }
    }
  }

  private void processInstanceFieldRefSource(Set<Value> results, JInstanceFieldRef fieldRef) {
    JInvokeStmt invoke = (JInvokeStmt) callsite;
    List<Immediate> args = invoke.getInvokeExpr().get().getArgs();
    Value base = fieldRef.getBase();

    for (int argIndex = 0; argIndex < args.size(); argIndex++) {
      Value arg = args.get(argIndex);
      Pair<Value, Integer> methodArg = new Pair<>(arg, argIndex);

      if (isSameParam(method, methodArg, base)) {
        JInstanceFieldRef mappedRef =
            new JInstanceFieldRef((Local) arg, fieldRef.getFieldSignature());
        results.add(mappedRef);
      }
    }
  }

  /**
   * Checks if the actual parameter passed to a method and the formal parameter in the method's body
   * are the same, considering reference types.
   *
   * @param method The method being analyzed.
   * @param actualParam The actual parameter passed to the method.
   * @param formalParam The formal parameter in the method's body.
   * @return True if the parameters match, otherwise false.
   */
  private boolean isSameParam(
      SootMethod method, Pair<Value, Integer> actualParam, Value formalParam) {
    if (!(actualParam.getO1().getType() instanceof ReferenceType)) {
      return false;
    }

    List<Stmt> statements = method.getBody().getStmts();
    int index = -1; // @this is indexed at -1

    for (Stmt stmt : statements) {
      if (!(stmt instanceof JIdentityStmt)) {
        continue;
      }

      JIdentityStmt identityStmt = (JIdentityStmt) stmt;
      Value rightOp = identityStmt.getRightOp();
      Value leftOp = identityStmt.getLeftOp();

      if (isMatchingParam(actualParam, formalParam, rightOp, leftOp, index)) {
        return true;
      }
      index++;
    }

    return false;
  }

  private boolean isMatchingParam(
      Pair<Value, Integer> actualParam, Value formalParam, Value rightOp, Value leftOp, int index) {
    return rightOp.getType().equals(actualParam.getO1().getType())
        && leftOp.equals(formalParam)
        && actualParam.getO2().equals(index);
  }
}
