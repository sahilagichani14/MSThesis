package upb.thesis.constantpropagation.flowfunctions.call;

import heros.FlowFunction;
import soot.Local;
import soot.jimple.StaticFieldRef;
import upb.thesis.constantpropagation.data.DFF;

import java.util.HashSet;
import java.util.Set;

public class ReturnFF implements FlowFunction<DFF> {

    private Local tgtLocal;
    private Local retLocal;

    public ReturnFF(Local tgtLocal, Local retLocal) {
        this.tgtLocal = tgtLocal;
        this.retLocal = retLocal;
    }


    @Override
    public Set<DFF> computeTargets(DFF source) {
        Set<DFF> res = new HashSet<>();
        if (source.equals(DFF.asDFF(retLocal))) {
            res.add(DFF.asDFF(tgtLocal));
        }
        if(source.getValue() instanceof StaticFieldRef){
            res.add(source);
        }
        return res;
    }
}
