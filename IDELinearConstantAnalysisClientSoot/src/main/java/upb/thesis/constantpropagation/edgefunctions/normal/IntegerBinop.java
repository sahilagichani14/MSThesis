package upb.thesis.constantpropagation.edgefunctions.normal;

import heros.EdgeFunction;
import heros.edgefunc.EdgeIdentity;
import soot.Value;
import soot.jimple.BinopExpr;
import soot.jimple.IntConstant;
import upb.thesis.constantpropagation.IDELinearConstantAnalysisProblem;
import upb.thesis.constantpropagation.data.DFF;
import upb.thesis.constantpropagation.edgefunctions.CPANormalEdgeFunctionProvider;
import upb.thesis.constantpropagation.edgefunctions.IntegerAllBottom;

public class IntegerBinop implements EdgeFunction<Integer> {

    private DFF srcNode;
    private Value lop;
    private Value rop;
    private String op;
    private BinopExpr binop;

    public IntegerBinop(BinopExpr binop, DFF srcNode) {
        this.binop = binop;
        this.srcNode = srcNode;
        lop = binop.getOp1();
        rop = binop.getOp2();
        op = binop.getSymbol();
    }

    public BinopExpr getBinop() {
        return binop;
    }

    @Override
    public Integer computeTarget(Integer source) {
        if (lop == srcNode.getValue() && rop instanceof IntConstant) {
            IntConstant ic = (IntConstant) rop;
            return executeBinOperation(op, source, ic.value);
        } else if (rop == srcNode.getValue() && lop instanceof IntConstant) {
            IntConstant ic = (IntConstant) lop;
            return executeBinOperation(op, ic.value, source);
        }
        return IDELinearConstantAnalysisProblem.BOTTOM;
    }

    public static int executeBinOperation(String op, int lhs, int rhs) {
        if(lhs == IDELinearConstantAnalysisProblem.BOTTOM || rhs == IDELinearConstantAnalysisProblem.BOTTOM){
            return IDELinearConstantAnalysisProblem.BOTTOM;
        }
        int res;
        switch (op.trim()) {
            case "+":
                res = lhs + rhs;
                break;
            case "-":
                res = lhs - rhs;
                break;
            case "*":
                res = lhs * rhs;
                break;
            case "/":
                res = lhs / rhs;
                break;
            case "%":
                res = lhs % rhs;
                break;
            case "<<":
                res = lhs << rhs;
                break;
            case ">>":
                res = lhs >> rhs;
                break;
            case "^":
                res = lhs ^ rhs;
                break;
            case "&":
                res = lhs & rhs;
                break;
            case "|":
                res = lhs | rhs;
                break;
            case ">>>":
                res = lhs >>> rhs;
                break;
            default:
                throw new UnsupportedOperationException("Could not execute unknown operation '" + op + "'!");
        }
        return res;
    }

    @Override
    public EdgeFunction<Integer> meetWith(EdgeFunction otherFunction) {
        if (otherFunction instanceof EdgeIdentity) {
            return this;
        } else if (otherFunction instanceof IntegerAssign) {
            return CPANormalEdgeFunctionProvider.ALL_BOTTOM;
        } else if (otherFunction instanceof IntegerBinop) {
            return CPANormalEdgeFunctionProvider.ALL_BOTTOM;
        } else if (otherFunction instanceof IntegerAllBottom) {
            return otherFunction;
        }
        throw new RuntimeException("can't meet: " + this.toString() + " and " + otherFunction.toString());
    }

    @Override
    public EdgeFunction composeWith(EdgeFunction secondFunction) {
        if (secondFunction instanceof EdgeIdentity) {
            return this;
        } else if (secondFunction instanceof IntegerAssign) {
            return secondFunction;
        }
        return this;
    }

    @Override
    public boolean equalTo(EdgeFunction other) {
        return this == other;
    }
}