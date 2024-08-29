package upb.thesis.constantpropagation.edgefunctions;

import heros.EdgeFunction;
import heros.edgefunc.AllBottom;
import heros.edgefunc.EdgeIdentity;
import sootup.core.jimple.basic.Local;
import sootup.core.jimple.basic.Value;
import sootup.core.jimple.common.constant.IntConstant;
import sootup.core.jimple.common.expr.AbstractBinopExpr;
import sootup.core.jimple.common.ref.JArrayRef;
import sootup.core.jimple.common.ref.JInstanceFieldRef;
import sootup.core.jimple.common.ref.JStaticFieldRef;
import sootup.core.jimple.common.stmt.JAssignStmt;
import sootup.core.jimple.common.stmt.Stmt;
import upb.thesis.constantpropagation.ConstantValue;
import upb.thesis.constantpropagation.IDEConstantPropagationProblem;
import upb.thesis.constantpropagation.edgefunctions.normal.ConstantAssign;
import upb.thesis.constantpropagation.edgefunctions.normal.ConstantBinop;

/**
 * Provides the appropriate edge function for normal assignments in constant propagation analysis.
 * This class determines how values are propagated across normal control flow edges during the
 * analysis.
 */
public class CPANormalEdgeFunctionProvider {

  /** The edge function that will be applied for the normal assignment. */
  private EdgeFunction<ConstantValue> edgeFunction;

  /** Singleton instance representing the "bottom" value in the lattice. */
  public static final EdgeFunction<ConstantValue> ALL_BOTTOM =
      new AllBottom<>(IDEConstantPropagationProblem.BOTTOM);

  /**
   * Constructs a new CPANormalEdgeFunctionProvider to determine the appropriate edge function for a
   * given assignment. The edge function is determined based on the current statement, the target
   * node, and the successor node in the control flow graph (CFG).
   *
   * @param curr The current statement in the program.
   * @param currNode The current node in the CFG.
   * @param succ The successor statement in the program.
   * @param succNode The successor node in the CFG.
   * @param zeroValue A special value representing uninitialized or unknown values in the analysis.
   */
  public CPANormalEdgeFunctionProvider(
      Stmt curr, Value currNode, Stmt succ, Value succNode, Value zeroValue) {
    edgeFunction = EdgeIdentity.v();

    if (shouldReturnAllBottom(currNode, succNode, zeroValue)) {
      edgeFunction = ALL_BOTTOM;
    } else if (curr instanceof JAssignStmt) {
      handleAssignStmt((JAssignStmt) curr, succNode);
    }
  }

  /**
   * Checks if both the current node and successor node are zero values, indicating that the
   * "bottom" edge function should be returned.
   *
   * @param currNode The current node in the CFG.
   * @param succNode The successor node in the CFG.
   * @return true if both nodes are zero values, false otherwise.
   */
  private boolean shouldReturnAllBottom(Value currNode, Value succNode, Value zeroValue) {
    return currNode == zeroValue && succNode == zeroValue;
  }

  /**
   * Processes an assignment statement to determine the appropriate edge function.
   *
   * @param assignment The assignment statement being processed.
   * @param succNode The successor node in the CFG.
   */
  private void handleAssignStmt(JAssignStmt assignment, Value succNode) {
    Value lhs = processValue(assignment.getLeftOp());
    Value rhs = processValue(assignment.getRightOp());

    if (lhs.equals(succNode)) {
      if (rhs instanceof IntConstant) {
        assignConstantEdgeFunction((IntConstant) rhs, assignment);
      } else if (rhs instanceof AbstractBinopExpr) {
        assignBinopEdgeFunction((AbstractBinopExpr) rhs);
      }
    }
  }

  /**
   * Processes the given value to handle specific cases like arrays and fields, and returns a
   * corresponding local variable representation.
   *
   * @param value The value to process.
   * @return The processed value.
   */
  private Value processValue(Value value) {
    if (value instanceof JArrayRef) {
      return processArrayRef((JArrayRef) value);
    } else if (value instanceof JStaticFieldRef) {
      return processStaticFieldRef((JStaticFieldRef) value);
    } else if (value instanceof JInstanceFieldRef) {
      return processInstanceFieldRef((JInstanceFieldRef) value);
    }
    return value;
  }

  /**
   * Processes an array reference to create a corresponding local variable.
   *
   * @param arrayRef The array reference to process.
   * @return The local variable representation of the array reference.
   */
  private Local processArrayRef(JArrayRef arrayRef) {
    return new Local(
        arrayRef.getBase().getName() + "[" + arrayRef.getIndex() + "]", arrayRef.getType());
  }

  /**
   * Processes a static field reference to create a corresponding local variable.
   *
   * @param staticFieldRef The static field reference to process.
   * @return The local variable representation of the static field reference.
   */
  private Local processStaticFieldRef(JStaticFieldRef staticFieldRef) {
    return new Local(
        staticFieldRef.getFieldSignature().getDeclClassType().getFullyQualifiedName()
            + "."
            + staticFieldRef.getFieldSignature().getName(),
        staticFieldRef.getFieldSignature().getType());
  }

  /**
   * Processes an instance field reference to create a corresponding local variable.
   *
   * @param instanceFieldRef The instance field reference to process.
   * @return The local variable representation of the instance field reference.
   */
  private Local processInstanceFieldRef(JInstanceFieldRef instanceFieldRef) {
    return new Local(
        instanceFieldRef.getBase().getName()
            + "."
            + instanceFieldRef.getFieldSignature().getSubSignature().getName(),
        instanceFieldRef.getFieldSignature().getSubSignature().getType());
  }

  /**
   * Assigns the edge function for a constant value in an assignment.
   *
   * @param constant The constant value being assigned.
   * @param assignment The assignment statement.
   */
  private void assignConstantEdgeFunction(IntConstant constant, JAssignStmt assignment) {
    ConstantValue constantValue = new ConstantValue(constant, assignment);
    edgeFunction = new ConstantAssign(constantValue);
  }

  /**
   * Assigns the edge function for a binary operation in an assignment.
   *
   * @param binop The binary operation expression.
   */
  private void assignBinopEdgeFunction(AbstractBinopExpr binop) {
    if (isLinearBinop(binop)) {
      edgeFunction = new ConstantBinop(binop, binop.getOp1());
    }
  }

  /**
   * Checks if the binary operation is linear, i.e., if one of the operands is a constant.
   *
   * @param binop The binary operation expression to check.
   * @return true if the operation is linear, false otherwise.
   */
  public static boolean isLinearBinop(AbstractBinopExpr binop) {
    Value op1 = binop.getOp1();
    Value op2 = binop.getOp2();
    return op1 instanceof IntConstant || op2 instanceof IntConstant;
  }

  /**
   * Retrieves the edge function determined by this provider.
   *
   * @return The edge function to be applied for the normal assignment.
   */
  public EdgeFunction<ConstantValue> getEdgeFunction() {
    return edgeFunction;
  }
}
