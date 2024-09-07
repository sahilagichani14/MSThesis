package upb.thesis.constantpropagation.flowfunctions.normal;

import heros.FlowFunction;
import soot.Value;
import soot.jimple.internal.JArrayRef;
import upb.thesis.constantpropagation.data.DFF;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ArrayLoadFF implements FlowFunction<DFF> {

    private DFF zeroValue;
    private JArrayRef arrayRef;
    private Value lhs;

    public ArrayLoadFF(JArrayRef arrayRef, Value lhs, DFF zeroValue) {
        this.arrayRef = arrayRef;
        this.lhs = lhs;
        this.zeroValue = zeroValue;
    }


    @Override
    public Set<DFF> computeTargets(DFF source) {
        if(source.equals(zeroValue)){
            return Collections.singleton(source);
        }
        Set<DFF> res = new HashSet<>();
        res.add(source);
        if(DFF.asDFF(arrayRef).equals(source)){
            res.add(DFF.asDFF(lhs));
        }
        return res;
    }
}
