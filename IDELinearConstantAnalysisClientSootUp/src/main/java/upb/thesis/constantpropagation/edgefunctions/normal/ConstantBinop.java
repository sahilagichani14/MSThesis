package upb.thesis.constantpropagation.edgefunctions.normal;

import heros.EdgeFunction;
import heros.edgefunc.EdgeIdentity;
import sootup.core.jimple.basic.Value;
import sootup.core.jimple.common.constant.IntConstant;
import sootup.core.jimple.common.constant.NumericConstant;
import sootup.core.jimple.common.expr.AbstractBinopExpr;
import upb.thesis.constantpropagation.ConstantValue;
import upb.thesis.constantpropagation.ExprEvaluator;
import upb.thesis.constantpropagation.IDEConstantPropagationProblem;
import upb.thesis.constantpropagation.edgefunctions.CPANormalEdgeFunctionProvider;
import upb.thesis.constantpropagation.edgefunctions.ConstantAllBottom;

/** Binary Operation Class */
public class ConstantBinop implements EdgeFunction<ConstantValue> {

  private final Value srcNode;
  private final Value lop;
  private final Value rop;
  private final AbstractBinopExpr binop;

  /**
   * Constructor for ConstantBinop.
   *
   * @param binop The binary operation expression to be evaluated.
   * @param srcNode The source node associated with the binary operation.
   */
  public ConstantBinop(AbstractBinopExpr binop, Value srcNode) {
    this.binop = binop;
    this.srcNode = srcNode;
    lop = binop.getOp1();
    rop = binop.getOp2();
  }

  /**
   * Returns the binary operation expression associated with this edge function.
   *
   * @return The binary operation expression.
   */
  public AbstractBinopExpr getBinop() {
    return binop;
  }

  @Override
  public ConstantValue computeTarget(ConstantValue source) {

    // Check if at least one op is Integer, it takes the other value from source.
    if ((lop == srcNode && rop instanceof IntConstant)
        || (rop == srcNode && lop instanceof IntConstant)) {
      ExprEvaluator exprEvaluator = new ExprEvaluator();
      exprEvaluator.init(source.getConstant());
      binop.accept(exprEvaluator);
      NumericConstant result = exprEvaluator.getValue();
      return new ConstantValue(result, null);
    }
    return IDEConstantPropagationProblem.BOTTOM;
  }

  /**
   * Binary operation doesn't happen when branches meet so we return Identity most of the time.
   *
   * @param otherFunction: Value from other branch
   * @return EdgeFunction
   */
  @Override
  public EdgeFunction<ConstantValue> meetWith(EdgeFunction<ConstantValue> otherFunction) {
    if (otherFunction instanceof EdgeIdentity) {
      return this;
    } else if ((otherFunction instanceof ConstantAssign)
        || (otherFunction instanceof ConstantBinop)) {
      return CPANormalEdgeFunctionProvider.ALL_BOTTOM;
    } else if (otherFunction instanceof ConstantAllBottom) {
      return otherFunction;
    } else if (otherFunction instanceof ConstantTop) {
      return otherFunction;
    }
    throw new RuntimeException("can't meet: " + this + " and " + otherFunction.toString());
  }

  /**
   * Binary operation doesn't happen in multiple lines we return Identity most of the time.
   *
   * @param secondFunction: Value from other branch
   * @return EdgeFunction
   */
  @Override
  public EdgeFunction<ConstantValue> composeWith(EdgeFunction<ConstantValue> secondFunction) {
    if (secondFunction instanceof EdgeIdentity) {
      return this;
    } else if (secondFunction instanceof ConstantAssign) {
      return secondFunction;
    }
    return this;
  }

  @Override
  public boolean equalTo(EdgeFunction other) {
    return this == other;
  }
}
