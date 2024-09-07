package upb.thesis.constantpropagation.flowfunctions;

import heros.FlowFunction;
import soot.Local;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.jimple.InvokeExpr;
import soot.jimple.Stmt;
import upb.thesis.constantpropagation.data.DFF;
import upb.thesis.constantpropagation.flowfunctions.call.CallFF;

import java.util.ArrayList;
import java.util.List;


public class CPACallFlowFunctionProvider implements FlowFunctionProvider<DFF>{

    private FlowFunction<DFF> flowFunction;

    public CPACallFlowFunctionProvider(Unit callStmt, SootMethod dest, DFF zeroValue){
        // we want to pass only the mapped parameters and effectively kill everything else. So no identity.v()
        Stmt s = (Stmt) callStmt;
        InvokeExpr ie = s.getInvokeExpr();
        final List<Value> callArgs = ie.getArgs();
        final List<Local> paramLocals = new ArrayList<>(callArgs.size());
        for (int i = 0; i < dest.getParameterCount(); i++) {
            paramLocals.add(dest.getActiveBody().getParameterLocal(i));
        }
        flowFunction = new CallFF(callArgs, dest, zeroValue, paramLocals);
    }

    public FlowFunction<DFF> getFlowFunction(){
        return flowFunction;
    }

}
