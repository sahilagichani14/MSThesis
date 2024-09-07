package upb.thesis.constantpropagation.flowfunctions.normal;

import heros.flowfunc.Gen;
import upb.thesis.constantpropagation.data.DFF;

import java.util.Set;

public class ConstantFF extends Gen<DFF> {

    public ConstantFF(DFF genValue, DFF zeroValue) {
        super(genValue, zeroValue);
    }

    @Override
    public Set<DFF> computeTargets(DFF source) {
        Set<DFF> res = super.computeTargets(source);
        return res;
    }
}
