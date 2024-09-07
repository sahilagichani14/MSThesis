package upb.thesis.constantpropagation.edgefunctions.normal;

import heros.EdgeFunction;
import heros.edgefunc.EdgeIdentity;
import org.slf4j.LoggerFactory;
import soot.Value;
import soot.jimple.BinopExpr;
import soot.jimple.IntConstant;
import upb.thesis.constantpropagation.AnalysisLogger;
import upb.thesis.constantpropagation.edgefunctions.CPANormalEdgeFunctionProvider;
import upb.thesis.constantpropagation.edgefunctions.IntegerAllBottom;
import upb.thesis.constantpropagation.edgefunctions.LoggingEdgeFunction;

public class IntegerAssign extends LoggingEdgeFunction<Integer> {

    private Integer value;
    private AnalysisLogger analysisLogger;

    public IntegerAssign(Integer value, AnalysisLogger analysisLogger){
        this.analysisLogger = analysisLogger;
        this.value = value;
        setLogger();
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public void setLogger() {
        log = LoggerFactory.getLogger(IntegerAssign.class);
    }

    @Override
    public Integer computeTarget(Integer integer) {
        super.computeTarget(integer);
        return value;
    }

    /**
     * first apply this then second
     * @param secondFunction
     * @return
     */
    @Override
    public EdgeFunction<Integer> composeWith(EdgeFunction<Integer> secondFunction) {
        super.composeWith(secondFunction);
        if(secondFunction instanceof EdgeIdentity){
            return this;
        }else if(secondFunction instanceof IntegerAssign){
            return secondFunction;
        }else if(secondFunction instanceof IntegerBinop){
            // Heros paper advises inplace composition for fast execution
            BinopExpr binop = ((IntegerBinop) secondFunction).getBinop();
            Value lop = binop.getOp1();
            Value rop = binop.getOp2();
            if(lop instanceof IntConstant){
                int val = ((IntConstant) lop).value;
                String op = binop.getSymbol();
                int res = IntegerBinop.executeBinOperation(op, value, val);
                return new IntegerAssign(res, analysisLogger);
            }else if(rop instanceof IntConstant){
                int val = ((IntConstant) rop).value;
                String op = binop.getSymbol();
                int res = IntegerBinop.executeBinOperation(op, value, val);
                return new IntegerAssign(res, analysisLogger);
            }
            analysisLogger.log();
            throw new RuntimeException("neither lop nor rop is constant: " + this.getValue() + System.lineSeparator() + ((IntegerBinop) secondFunction).getBinop());
        }
        return this;
    }

    @Override
    public EdgeFunction<Integer> meetWith(EdgeFunction<Integer> otherFunction) {
        super.meetWith(otherFunction);
        if(otherFunction instanceof EdgeIdentity){
            return this;
        }else if(otherFunction instanceof IntegerAssign){
            Integer valueFromOtherBranch = ((IntegerAssign) otherFunction).getValue(); // input int isn't used anyway
            Integer valueFromThisBranch = this.getValue();
            if(valueFromOtherBranch == valueFromThisBranch){
                return this;
            }else{
                return CPANormalEdgeFunctionProvider.ALL_BOTTOM;
            }
        }else if(otherFunction instanceof IntegerBinop){
            return CPANormalEdgeFunctionProvider.ALL_BOTTOM;
        }else if(otherFunction instanceof IntegerAllBottom){
            return otherFunction;
        }
        throw new RuntimeException("can't meeet: " + this.toString() + " and " + otherFunction.toString());
    }

    @Override
    public boolean equalTo(EdgeFunction<Integer> other) {
        super.equalTo(other);
        return this == other;
    }
}