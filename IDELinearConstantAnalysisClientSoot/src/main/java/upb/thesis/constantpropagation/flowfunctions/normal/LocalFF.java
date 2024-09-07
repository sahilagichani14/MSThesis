package upb.thesis.constantpropagation.flowfunctions.normal;

import heros.FlowFunction;
import soot.Local;
import soot.Value;
import soot.jimple.FieldRef;
import soot.jimple.internal.JArrayRef;
import upb.thesis.constantpropagation.data.DFF;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Assignment from a single local
 */
public class LocalFF implements FlowFunction<DFF> {

    private Local right;
    private Value lhs;
    private DFF zeroValue;

    public LocalFF(Local right, Value lhs, DFF zeroValue) {
        this.right = right;
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
        if (DFF.asDFF(right).equals(source)) {
            res.add(DFF.asDFF(lhs));
        }
        // for arrays
        if(source.getValue() instanceof JArrayRef){
            JArrayRef arrayRef = (JArrayRef) source.getValue();
            if(arrayRef.getBase().equals(right)){
                if(!(lhs instanceof FieldRef)){
                    JArrayRef newRef = new JArrayRef(lhs, arrayRef.getIndex());
                    res.add(DFF.asDFF(newRef));
                }
            }
        }
        return res;
    }
}
