package upb.thesis.constantpropagation.flowfunctions;

import heros.FlowFunction;
import heros.flowfunc.Identity;
import sootup.core.jimple.basic.Local;
import sootup.core.jimple.basic.Value;
import sootup.core.jimple.common.constant.IntConstant;
import sootup.core.jimple.common.expr.AbstractBinopExpr;
import sootup.core.jimple.common.expr.JNewExpr;
import sootup.core.jimple.common.ref.JArrayRef;
import sootup.core.jimple.common.ref.JInstanceFieldRef;
import sootup.core.jimple.common.ref.JStaticFieldRef;
import sootup.core.jimple.common.stmt.JAssignStmt;
import sootup.core.jimple.common.stmt.Stmt;

/**
 * Provider for Normal Flow Functions. This class determines the appropriate flow function based on
 * the type of assignment in the current statement, facilitating constant propagation analysis.
 */
public class CPANormalFlowFunctionProvider implements FlowFunctionProvider<Value> {

  private FlowFunction<Value> flowFunction;

  /**
   * Constructs a CPANormalFlowFunctionProvider for the given statement and zero value.
   *
   * @param curr The current statement in the analysis.
   * @param zeroValue The zero value used in the analysis to represent uninitialized or default
   *     values.
   */
  public CPANormalFlowFunctionProvider(Stmt curr, Value zeroValue) {
    flowFunction = Identity.v(); // Default to identity function

    if (curr instanceof JAssignStmt) {
      JAssignStmt assignment = (JAssignStmt) curr;
      Value lhs = processValue(assignment.getLeftOp());
      Value rhs = processValue(assignment.getRightOp());

      if (rhs instanceof JNewExpr || rhs instanceof IntConstant) {
        // Handle assignment of a constant integer or new object
        flowFunction = new AssignmentFF(lhs, zeroValue);
      } else if (rhs instanceof AbstractBinopExpr) {
        // Handle assignment involving a binary operation
        flowFunction = new BinopFF(lhs, (AbstractBinopExpr) rhs, zeroValue);
      } else if (rhs instanceof Local) {
        // Handle assignment of a local variable
        flowFunction = new LocalFF((Local) rhs, lhs, zeroValue);
      }
    }
  }

  /**
   * Processes the given value to convert it into a parsable local object if necessary.
   *
   * @param value The raw value to process.
   * @return The processed value, which may be a local object.
   */
  private Value processValue(Value value) {
    if (value instanceof JArrayRef) {
      return new Local(
          ((JArrayRef) value).getBase().getName() + "[" + ((JArrayRef) value).getIndex() + "]",
          value.getType());
    } else if (value instanceof JStaticFieldRef) {
      return new Local(
          ((JStaticFieldRef) value).getFieldSignature().getDeclClassType().getFullyQualifiedName()
              + "."
              + ((JStaticFieldRef) value).getFieldSignature().getName(),
          ((JStaticFieldRef) value).getFieldSignature().getType());
    } else if (value instanceof JInstanceFieldRef) {
      return new Local(
          ((JInstanceFieldRef) value).getBase().getName()
              + "."
              + ((JInstanceFieldRef) value).getFieldSignature().getSubSignature().getName(),
          ((JInstanceFieldRef) value).getFieldSignature().getSubSignature().getType());
    }
    return value;
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
