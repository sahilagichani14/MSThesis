package upb.thesis.constantpropagation.edgefunctions;

import com.googlecode.dex2jar.ir.expr.InvokeExpr;
import heros.EdgeFunction;
import heros.edgefunc.AllBottom;
import heros.edgefunc.EdgeIdentity;
import sootup.core.jimple.basic.Local;
import sootup.core.jimple.basic.Value;
import sootup.core.jimple.common.constant.NumericConstant;
import sootup.core.jimple.common.expr.JSpecialInvokeExpr;
import sootup.core.jimple.common.expr.JVirtualInvokeExpr;
import sootup.core.jimple.common.stmt.JAssignStmt;
import sootup.core.jimple.common.stmt.JInvokeStmt;
import sootup.core.jimple.common.stmt.Stmt;
import sootup.core.model.SootMethod;
import upb.thesis.constantpropagation.ConstantValue;
import upb.thesis.constantpropagation.IDEConstantPropagationProblem;
import upb.thesis.constantpropagation.edgefunctions.normal.ConstantAssign;

/**
 * Provides the appropriate edge function for method calls in constant propagation analysis. This
 * class determines how values are passed from one method to another during inter-procedural
 * analysis.
 */
public class CPACallEdgeFunctionProvider {
  /** Call Edge Function */
  private EdgeFunction<ConstantValue> edgeFunction;

  /** Singleton instance representing the "bottom" value in the lattice. */
  public static final EdgeFunction<ConstantValue> ALL_BOTTOM =
      new AllBottom<>(IDEConstantPropagationProblem.BOTTOM);

  /**
   * Constructs a new CPACallEdgeFunctionProvider to determine the appropriate edge function for a
   * given method call. The edge function is determined based on the current statement, the method
   * being called, and the values involved in the call.
   *
   * @param curr The current statement in the program.
   * @param currNode The current node in the control flow graph (CFG).
   * @param destinationMethod The method that is being called.
   * @param succNode The successor node in the CFG.
   * @param zeroValue A special value representing uninitialized or unknown values in the analysis.
   */
  public CPACallEdgeFunctionProvider(
      Stmt curr, Value currNode, SootMethod destinationMethod, Value succNode, Value zeroValue) {
    edgeFunction = EdgeIdentity.v();

    if (currNode == zeroValue && succNode == zeroValue) {
      edgeFunction = ALL_BOTTOM;
    } else if (curr instanceof JAssignStmt) {
      handleAssignStmt((JAssignStmt) curr, destinationMethod, succNode);
    } else if (curr instanceof JInvokeStmt) {
      handleInvokeStmt((JInvokeStmt) curr, destinationMethod, succNode);
    }
  }

  /**
   * Handles the edge function logic for assignment statements.
   *
   * @param assignment The assignment statement being processed.
   * @param destinationMethod The method being called.
   * @param succNode The successor node in the CFG.
   */
  private void handleAssignStmt(
      JAssignStmt assignment, SootMethod destinationMethod, Value succNode) {
    Value rhs = assignment.getRightOp();
    if (rhs instanceof JVirtualInvokeExpr) {
      for (int i = 0; i < destinationMethod.getParameterCount(); i++) {
        processJVirtualInvokeExpr((JVirtualInvokeExpr) rhs, destinationMethod, succNode, i, assignment);
      }
    } else if (rhs instanceof JSpecialInvokeExpr) {
      for (int i = 0; i < destinationMethod.getParameterCount(); i++) {
        processJSpecialInvokeExpr((JSpecialInvokeExpr) rhs, destinationMethod, succNode, i, assignment);
      }
    }
  }

  /**
   * Handles the edge function logic for invocation statements.
   *
   * @param invokeStmt The invocation statement being processed.
   * @param destinationMethod The method being called.
   * @param succNode The successor node in the CFG.
   */
  private void handleInvokeStmt(
      JInvokeStmt invokeStmt, SootMethod destinationMethod, Value succNode) {
    for (int i = 0; i < destinationMethod.getParameterCount(); i++) {
      if (invokeStmt.asInvokableStmt().getInvokeExpr().get() instanceof JVirtualInvokeExpr) {
        JVirtualInvokeExpr invokeExpr = (JVirtualInvokeExpr) invokeStmt.asInvokableStmt().getInvokeExpr().get();
        processJVirtualInvokeExpr(
                invokeExpr,
                destinationMethod,
                succNode,
                i,
                invokeStmt);
      } else if (invokeStmt.asInvokableStmt().getInvokeExpr().get() instanceof JSpecialInvokeExpr) {
        JSpecialInvokeExpr specialInvokeExpr = (JSpecialInvokeExpr) invokeStmt.asInvokableStmt().getInvokeExpr().get();
        processJSpecialInvokeExpr(
                specialInvokeExpr,
                destinationMethod,
                succNode,
                i,
                invokeStmt);
      }
    }
  }

  /**
   * Processes the method invocation expression and assigns the edge function if applicable.
   *
   * @param invokeExpr The method invocation expression being processed.
   * @param destinationMethod The method being called.
   * @param succNode The successor node in the CFG.
   * @param index The index of the method parameter being processed.
   * @param stmt The statement associated with the invocation.
   */
  private void processJVirtualInvokeExpr(
      JVirtualInvokeExpr invokeExpr,
      SootMethod destinationMethod,
      Value succNode,
      int index,
      Stmt stmt) {
    Local paramLocal = destinationMethod.getBody().getParameterLocal(index);
    if (paramLocal.equals(succNode) && invokeExpr.getArg(index) instanceof NumericConstant) {
      ConstantValue constantValue =
          new ConstantValue((NumericConstant) invokeExpr.getArg(index), stmt);
      edgeFunction = new ConstantAssign(constantValue);
    }
  }

  private void processJSpecialInvokeExpr(
          JSpecialInvokeExpr invokeExpr,
      SootMethod destinationMethod,
      Value succNode,
      int index,
      Stmt stmt) {
    Local paramLocal = destinationMethod.getBody().getParameterLocal(index);
    if (paramLocal.equals(succNode) && invokeExpr.getArg(index) instanceof NumericConstant) {
      ConstantValue constantValue =
          new ConstantValue((NumericConstant) invokeExpr.getArg(index), stmt);
      edgeFunction = new ConstantAssign(constantValue);
    }
  }

  /**
   * Retrieves the edge function determined by this provider.
   *
   * @return The edge function to be applied for the method call.
   */
  public EdgeFunction<ConstantValue> getEdgeFunction() {
    return edgeFunction;
  }
}
