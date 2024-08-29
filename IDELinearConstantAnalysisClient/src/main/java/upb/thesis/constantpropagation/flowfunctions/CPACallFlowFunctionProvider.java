package upb.thesis.constantpropagation.flowfunctions;

import heros.FlowFunction;
import sootup.core.jimple.basic.Immediate;
import sootup.core.jimple.basic.Local;
import sootup.core.jimple.basic.Value;
import sootup.core.jimple.common.expr.AbstractInvokeExpr;
import sootup.core.jimple.common.stmt.Stmt;
import sootup.core.model.SootMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Provider for Call Flow Functions. This class encapsulates the logic for generating flow functions
 * that handle the flow of data across method calls during constant propagation analysis.
 */
public class CPACallFlowFunctionProvider implements FlowFunctionProvider<Value> {

  private final FlowFunction<Value> flowFunction;

  /**
   * Constructs a CPACallFlowFunctionProvider for the given statement, zero value, and destination
   * method.
   *
   * @param curr The current statement containing the method invocation.
   * @param zeroValue The zero value used in the analysis to represent uninitialized or default
   *     values.
   * @param dest The destination method to which the call is made.
   */
  public CPACallFlowFunctionProvider(Stmt curr, Value zeroValue, SootMethod dest) {
    AbstractInvokeExpr invokeExpr = curr.asInvokableStmt().getInvokeExpr().get();
    this.flowFunction = createCallFlowFunction(invokeExpr.getArgs(), zeroValue, dest);
  }

  /**
   * Creates a FlowFunction that maps arguments from the caller to the parameters of the callee
   * method.
   *
   * @param callArgs The list of arguments passed to the method call.
   * @param zeroValue The zero value used in the analysis.
   * @param dest The destination method being called.
   * @return A FlowFunction instance for handling the data flow between method calls.
   */
  private FlowFunction<Value> createCallFlowFunction(
      List<Immediate> callArgs, Value zeroValue, SootMethod dest) {
    List<Local> paramLocals = new ArrayList<>();
    for (int i = 0; i < dest.getParameterCount(); i++) {
      paramLocals.add(dest.getBody().getParameterLocal(i));
    }
    return new CallFF(callArgs, dest, zeroValue, paramLocals);
  }

  /**
   * Returns the FlowFunction instance associated with this provider.
   *
   * @return The FlowFunction instance.
   */
  public FlowFunction<Value> getFlowFunction() {
    return flowFunction;
  }
}
