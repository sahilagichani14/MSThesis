package upb.thesis.constantpropagation.flowfunctions.call;

import heros.FlowFunction;
import soot.Local;
import soot.SootMethod;
import soot.Value;
import soot.jimple.IntConstant;
import soot.jimple.StaticFieldRef;
import upb.thesis.constantpropagation.data.DFF;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CallFF implements FlowFunction<DFF> {

    private List<Value> callArgs;
    private SootMethod dest;
    private DFF zeroValue;
    private List<Local> paramLocals;

    public CallFF(List<Value> callArgs, SootMethod dest, DFF zeroValue, List<Local> paramLocals) {
        this.callArgs = callArgs;
        this.dest = dest;
        this.zeroValue = zeroValue;
        this.paramLocals = paramLocals;
    }


    @Override
    public Set<DFF> computeTargets(DFF source) {
        //ignore implicit calls to static initializers
        if (dest.getName().equals(SootMethod.staticInitializerName) && dest.getParameterCount() == 0) {
            return Collections.emptySet();
        }
        Set<DFF> res = new HashSet<>();
        if(source==zeroValue || source.getValue() instanceof StaticFieldRef){
            res.add(source);
        }
        for (int i = 0; i < callArgs.size(); i++) {
            // Special case: check if function is called with integer literals as params
            if (callArgs.get(i) instanceof IntConstant && source == zeroValue) {
                res.add(DFF.asDFF(paramLocals.get(i)));
            }
            // Ordinary case: just perform the mapping
            if (DFF.asDFF(callArgs.get(i)).equals(source)) {
                res.add(DFF.asDFF(paramLocals.get(i)));
            }
        }
        return res;
    }
}
