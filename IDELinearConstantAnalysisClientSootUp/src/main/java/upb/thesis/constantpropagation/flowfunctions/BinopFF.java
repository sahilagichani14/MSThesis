package upb.thesis.constantpropagation.flowfunctions;

import heros.FlowFunction;
import sootup.core.jimple.basic.Value;
import sootup.core.jimple.common.constant.IntConstant;
import sootup.core.jimple.common.expr.AbstractBinopExpr;

import java.util.HashSet;
import java.util.Set;

/** Binary Flow Function */
public class BinopFF implements FlowFunction<Value> {

  private final Value lop;
  private final Value rop;
  private final Value lhs;

  private final Value zeroValue;

  /**
   * Constructor for Binary Flow Function
   *
   * @param lhs Left Variable
   * @param binop Binary Operation
   * @param zeroValue Default Value
   */
  public BinopFF(Value lhs, AbstractBinopExpr binop, Value zeroValue) {
    this.zeroValue = zeroValue;
    this.lhs = lhs;
    this.lop = binop.getOp1();
    this.rop = binop.getOp2();
  }

  @Override
  public Set<Value> computeTargets(Value source) {
    Set<Value> res = new HashSet<>();
    res.add(source);
    if (source == zeroValue) {
      return res;
    }
    if ((lop == source && rop instanceof IntConstant)
        || (rop == source && lop instanceof IntConstant)) {
      res.add(lhs);
    }
    return res;
  }
}
