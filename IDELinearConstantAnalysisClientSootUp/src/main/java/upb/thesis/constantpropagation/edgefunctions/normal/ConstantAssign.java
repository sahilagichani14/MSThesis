package upb.thesis.constantpropagation.edgefunctions.normal;

import heros.EdgeFunction;
import heros.edgefunc.AllBottom;
import heros.edgefunc.EdgeIdentity;
import sootup.core.jimple.common.constant.NumericConstant;
import sootup.core.jimple.common.expr.AbstractBinopExpr;
import upb.thesis.constantpropagation.ConstantValue;
import upb.thesis.constantpropagation.ExprEvaluator;
import upb.thesis.constantpropagation.edgefunctions.CPANormalEdgeFunctionProvider;
import upb.thesis.constantpropagation.edgefunctions.ConstantAllBottom;

/** Assignment Resolver for Edge function */
public class ConstantAssign implements EdgeFunction<ConstantValue> {

  private final ConstantValue value;

  /**
   * Constructor for ConstantAssign
   *
   * @param value The constant value for a variable.
   */
  public ConstantAssign(ConstantValue value) {
    this.value = value;
  }

  public ConstantValue getValue() {
    return value;
  }

  @Override
  public ConstantValue computeTarget(ConstantValue integer) {
    return value;
  }

  /**
   * Expression Evaluator evaluates the binary operation. This method sends a query to the
   * ExprEvaluator to resolve it.
   *
   * @param secondFunction
   * @return
   */
  @Override
  public EdgeFunction<ConstantValue> composeWith(EdgeFunction<ConstantValue> secondFunction) {
    if (secondFunction instanceof EdgeIdentity) {
      return this;
    } else if (secondFunction instanceof ConstantAssign) {
      return secondFunction;
    } else if (secondFunction instanceof ConstantBinop) {
      // Heros paper advises inplace composition for fast execution
      AbstractBinopExpr binop = ((ConstantBinop) secondFunction).getBinop();
      ExprEvaluator exprEvaluator = new ExprEvaluator();
      exprEvaluator.init(value.getConstant());
      binop.accept(exprEvaluator);
      NumericConstant result = exprEvaluator.getValue();
      return new ConstantAssign(new ConstantValue(result, null));
    }
    return this;
  }

  /**
   * Method to evaluate merging of different branch values.
   *
   * @param otherFunction: Value from other branch
   * @return EdgeFunction
   */
  @Override
  public EdgeFunction<ConstantValue> meetWith(EdgeFunction<ConstantValue> otherFunction) {
    if (otherFunction instanceof EdgeIdentity) {
      return this;
    } else if (otherFunction instanceof ConstantAssign) {
      ConstantValue valueFromOtherBranch = ((ConstantAssign) otherFunction).getValue();
      ConstantValue valueFromThisBranch = this.getValue();
      // If two values from different branch are same, then pass it below otherwise send to TOP.
      if (valueFromOtherBranch.getConstant() == valueFromThisBranch.getConstant()) {
        return this;
      } else {
        return ConstantTop.v();
      }
    } else if ((otherFunction instanceof ConstantBinop) || ((otherFunction instanceof ConstantAllBottom))) {
      return CPANormalEdgeFunctionProvider.ALL_BOTTOM;
    } else if (otherFunction instanceof ConstantTop) {
      return otherFunction;
    }

    throw new RuntimeException("can't meeet: " + this + " and " + otherFunction.toString());
  }

  @Override
  public boolean equalTo(EdgeFunction<ConstantValue> other) {
    return this == other;
  }
}
