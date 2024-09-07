package upb.thesis.constantpropagation.flowfunctions.normal;

import heros.FlowFunction;
import soot.Value;
import soot.jimple.BinopExpr;
import soot.jimple.IntConstant;
import upb.thesis.constantpropagation.data.DFF;

import java.util.HashSet;
import java.util.Set;

public class BinopFF implements FlowFunction<DFF> {

    private DFF zeroValue;
    private Value lop;
    private Value rop;
    private Value lhs;


    public BinopFF(Value lhs, BinopExpr binop, DFF zeroValue) {
        this.zeroValue = zeroValue;
        this.lhs = lhs;
        this.lop = binop.getOp1();
        this.rop = binop.getOp2();
    }

    @Override
    public Set<DFF> computeTargets(DFF source) {
        Set<DFF> res = new HashSet<>();
        res.add(source);
        if (source == zeroValue) {
            return res;
        }
        if ((lop == source.getValue() && rop instanceof IntConstant) || (rop == source.getValue() && lop instanceof IntConstant)) {
            res.add(DFF.asDFF(lhs));
        }
        return res;
    }
}
