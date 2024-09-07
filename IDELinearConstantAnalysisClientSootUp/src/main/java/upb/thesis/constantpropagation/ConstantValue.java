package upb.thesis.constantpropagation;

import sootup.core.jimple.common.constant.NumericConstant;
import sootup.core.jimple.common.stmt.Stmt;

/** Unit Value that gets passed throughout the propagation */
public class ConstantValue {
  private NumericConstant numConstant;
  private final Stmt stmt;

  /**
   * Unit Value that gets passed throughout the propagation
   *
   * @param numConstant: Mostly integer
   * @param stmt: Current Statement
   */
  public ConstantValue(NumericConstant numConstant, Stmt stmt) {
    this.numConstant = numConstant;
    this.stmt = stmt;
  }

  public NumericConstant getConstant() {
    return numConstant;
  }

  public Stmt getStatement() {
    return stmt;
  }

  public void setNumConstant(NumericConstant newValue) {
    this.numConstant = newValue;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" : ");
    if (!numConstant.equals(0)) {
      sb.append(numConstant);
    }
    sb.append(" . ");
    if (stmt != null) {
      sb.append(stmt);
    }
    sb.append(" . ");

    sb.append(" . ");

    return sb.toString();
  }
}
