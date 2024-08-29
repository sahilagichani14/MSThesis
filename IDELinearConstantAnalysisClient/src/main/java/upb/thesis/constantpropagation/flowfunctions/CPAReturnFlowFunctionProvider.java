package upb.thesis.constantpropagation.flowfunctions;

import heros.FlowFunction;
import heros.flowfunc.KillAll;
import sootup.core.jimple.basic.Local;
import sootup.core.jimple.basic.Value;
import sootup.core.jimple.common.stmt.AbstractDefinitionStmt;
import sootup.core.jimple.common.stmt.JReturnStmt;
import sootup.core.jimple.common.stmt.JReturnVoidStmt;
import sootup.core.jimple.common.stmt.Stmt;
import sootup.core.model.SootMethod;

/**
 * Provider for Return Flow Functions. This class determines the appropriate flow function when
 * returning from a method, handling both value returns and void returns.
 */
public class CPAReturnFlowFunctionProvider implements FlowFunctionProvider<Value> {

  private FlowFunction<Value> flowFunction;

  /**
   * Constructs a CPAReturnFlowFunctionProvider for the given call site, exit statement, callee
   * method, and zero value.
   *
   * @param callSite The statement where the method was called.
   * @param exitStmt The statement where the method returns.
   * @param callee The callee method from which the return is happening.
   * @param zeroValue The zero value used in the analysis to represent uninitialized or default
   *     values.
   */
  public CPAReturnFlowFunctionProvider(
      Stmt callSite, Stmt exitStmt, SootMethod callee, Value zeroValue) {

    // Default behavior is to kill all values when returning from a nested context
    flowFunction = KillAll.v();

    if (exitStmt instanceof JReturnStmt) {
      // Handle returning a value
      JReturnStmt returnStmt = (JReturnStmt) exitStmt;
      Value op = returnStmt.getOp();

      if (op instanceof Local) {
        // Return the value of a method to the variable in the original method
        if (callSite instanceof AbstractDefinitionStmt) {
          AbstractDefinitionStmt defnStmt = (AbstractDefinitionStmt) callSite;
          Value leftOp = defnStmt.getLeftOp();

          if (leftOp instanceof Local) {
            Local tgtLocal = (Local) leftOp;
            Local retLocal = (Local) op;
            flowFunction = new ReturnFF(tgtLocal, retLocal);
          }
        }
      }
    } else if (exitStmt instanceof JReturnVoidStmt) {
      // Handle void returns
      flowFunction = new ReturnVoidFF(callSite, callee, zeroValue);
    }
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
