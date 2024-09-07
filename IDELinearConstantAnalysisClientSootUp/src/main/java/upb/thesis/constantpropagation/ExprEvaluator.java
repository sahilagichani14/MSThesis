package upb.thesis.constantpropagation;

import sootup.core.jimple.basic.Immediate;
import sootup.core.jimple.basic.Local;
import sootup.core.jimple.common.constant.NumericConstant;
import sootup.core.jimple.common.expr.*;
import sootup.core.jimple.visitor.AbstractExprVisitor;

/** Expression Evaluator for binary expressions */
public class ExprEvaluator extends AbstractExprVisitor {

  NumericConstant value;

  /**
   * @param value: The actual value of one of the variable in the expression
   */
  public void init(NumericConstant value) {
    this.value = value;
  }

  public NumericConstant getValue() {
    return value;
  }

  @Override
  public void caseAddExpr(JAddExpr expr) {

    Immediate op1 = expr.getOp1();
    Immediate op2 = expr.getOp2();
    if ((op1 instanceof Local) && (op2 instanceof NumericConstant)) {
      value = value.add((NumericConstant) op2);
    } else if ((op2 instanceof Local) && (op1 instanceof NumericConstant)) {
      value = (value.add((NumericConstant) op1));
    } else {
      throw new RuntimeException("Invalid Number");
    }
  }

  @Override
  public void caseAndExpr(JAndExpr expr) {}

  @Override
  public void caseCmpExpr(JCmpExpr expr) {}

  @Override
  public void caseCmpgExpr(JCmpgExpr expr) {}

  @Override
  public void caseCmplExpr(JCmplExpr expr) {}

  @Override
  public void caseDivExpr(JDivExpr expr) {
    Immediate op1 = expr.getOp1();
    Immediate op2 = expr.getOp2();
    if ((op1 instanceof Local) && (op2 instanceof NumericConstant)) {
      value = (value.divide((NumericConstant) op2));
    } else if ((op2 instanceof Local) && (op1 instanceof NumericConstant)) {
      value = (((NumericConstant) op1).divide(value));
    } else {
      throw new RuntimeException("Invalid Number");
    }
  }

  @Override
  public void caseEqExpr(JEqExpr expr) {}

  @Override
  public void caseNeExpr(JNeExpr expr) {}

  @Override
  public void caseGeExpr(JGeExpr expr) {}

  @Override
  public void caseGtExpr(JGtExpr expr) {}

  @Override
  public void caseLeExpr(JLeExpr expr) {}

  @Override
  public void caseLtExpr(JLtExpr expr) {}

  @Override
  public void caseMulExpr(JMulExpr expr) {
    Immediate op1 = expr.getOp1();
    Immediate op2 = expr.getOp2();
    if ((op1 instanceof Local) && (op2 instanceof NumericConstant)) {
      value = (value.multiply((NumericConstant) op2));
    } else if ((op2 instanceof Local) && (op1 instanceof NumericConstant)) {
      value = (((NumericConstant) op1).multiply(value));
    } else {
      throw new RuntimeException("Invalid Number");
    }
  }

  @Override
  public void caseOrExpr(JOrExpr expr) {}

  @Override
  public void caseRemExpr(JRemExpr expr) {}

  @Override
  public void caseShlExpr(JShlExpr expr) {}

  @Override
  public void caseShrExpr(JShrExpr expr) {}

  @Override
  public void caseUshrExpr(JUshrExpr expr) {}

  @Override
  public void caseSubExpr(JSubExpr expr) {
    Immediate op1 = expr.getOp1();
    Immediate op2 = expr.getOp2();
    if ((op1 instanceof Local) && (op2 instanceof NumericConstant)) {
      value = (value.subtract((NumericConstant) op2));
    } else if ((op2 instanceof Local) && (op1 instanceof NumericConstant)) {
      value = (((NumericConstant) op1).subtract(value));
    } else {
      throw new RuntimeException("Invalid Number");
    }
  }

  @Override
  public void caseXorExpr(JXorExpr expr) {}

  @Override
  public void caseSpecialInvokeExpr(JSpecialInvokeExpr expr) {}

  @Override
  public void caseVirtualInvokeExpr(JVirtualInvokeExpr expr) {}

  @Override
  public void caseInterfaceInvokeExpr(JInterfaceInvokeExpr expr) {}

  @Override
  public void caseStaticInvokeExpr(JStaticInvokeExpr expr) {}

  @Override
  public void caseDynamicInvokeExpr(JDynamicInvokeExpr expr) {}

  @Override
  public void caseCastExpr(JCastExpr expr) {}

  @Override
  public void caseInstanceOfExpr(JInstanceOfExpr expr) {}

  @Override
  public void caseNewArrayExpr(JNewArrayExpr expr) {}

  @Override
  public void caseNewMultiArrayExpr(JNewMultiArrayExpr expr) {}

  @Override
  public void caseNewExpr(JNewExpr expr) {}

  @Override
  public void caseLengthExpr(JLengthExpr expr) {}

  @Override
  public void caseNegExpr(JNegExpr expr) {}

  @Override
  public void casePhiExpr(JPhiExpr v) {}

  @Override
  public void defaultCaseExpr(Expr expr) {}
}
