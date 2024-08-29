package upb.thesis.constantpropagation.edgefunctions;

import heros.EdgeFunction;
import heros.edgefunc.EdgeIdentity;
import sootup.core.jimple.basic.Value;
import sootup.core.jimple.common.constant.NumericConstant;
import sootup.core.jimple.common.stmt.JReturnStmt;
import sootup.core.jimple.common.stmt.Stmt;
import sootup.core.model.SootMethod;
import upb.thesis.constantpropagation.ConstantValue;
import upb.thesis.constantpropagation.edgefunctions.normal.ConstantAssign;

/**
 * Provides the appropriate edge function for return statements in constant propagation analysis.
 * This class determines how values are propagated when a method returns a value to its caller.
 */
public class CPAReturnEdgeFunctionProvider {

  /** The edge function that will be applied for the return statement. */
  private EdgeFunction<ConstantValue> edgeFunction;

  /**
   * Constructs a new CPAReturnEdgeFunctionProvider to determine the appropriate edge function for a
   * given return statement. The edge function is determined based on the call site, the callee
   * method, and the return site.
   *
   * @param callSite The statement where the method was called.
   * @param calleeMethod The method being called.
   * @param exitStmt The exit statement of the method.
   * @param exitNode The exit node in the control flow graph (CFG).
   * @param returnSite The statement where the method returns.
   * @param retNode The return node in the CFG.
   * @param zeroValue A special value representing uninitialized or unknown values in the analysis.
   */
  public CPAReturnEdgeFunctionProvider(
      Stmt callSite,
      SootMethod calleeMethod,
      Stmt exitStmt,
      Value exitNode,
      Stmt returnSite,
      Value retNode,
      Value zeroValue) {

    edgeFunction = EdgeIdentity.v();
    assert callSite != null;
    assert exitStmt != null;
    assert calleeMethod != null;

    if (isZeroValueEdge(retNode, exitNode, zeroValue)) {
      edgeFunction = CPANormalEdgeFunctionProvider.ALL_BOTTOM;
    } else if (returnSite instanceof JReturnStmt) {
      handleReturnStmt((JReturnStmt) returnSite, exitStmt);
    }
  }

  /**
   * Checks if both the return node and exit node are zero values, indicating that the "bottom" edge
   * function should be returned.
   *
   * @param retNode The return node in the CFG.
   * @param exitNode The exit node in the CFG.
   * @return true if both nodes are zero values, false otherwise.
   */
  private boolean isZeroValueEdge(Value retNode, Value exitNode, Value zeroValue) {
    return retNode == zeroValue && exitNode == zeroValue;
  }

  /**
   * Processes a return statement to determine the appropriate edge function based on the returned
   * value.
   *
   * @param returnStmt The return statement being processed.
   * @param exitStmt The exit statement of the method.
   */
  private void handleReturnStmt(JReturnStmt returnStmt, Stmt exitStmt) {
    Value rhs = returnStmt.getOp();
    if (rhs instanceof NumericConstant) {
      assignConstantEdgeFunction((NumericConstant) rhs, exitStmt);
    }
  }

  /**
   * Assigns the edge function for a constant value in a return statement.
   *
   * @param constant The constant value being returned.
   * @param exitStmt The exit statement of the method.
   */
  private void assignConstantEdgeFunction(NumericConstant constant, Stmt exitStmt) {
    ConstantValue constantValue = new ConstantValue(constant, exitStmt);
    edgeFunction = new ConstantAssign(constantValue);
  }

  /**
   * Retrieves the edge function determined by this provider.
   *
   * @return The edge function to be applied for the return statement.
   */
  public EdgeFunction<ConstantValue> getEdgeFunction() {
    return edgeFunction;
  }
}
