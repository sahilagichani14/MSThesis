package upb.thesis.constantpropagation.flowfunctions;

import heros.FlowFunction;
import heros.flowfunc.KillAll;
import soot.Local;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.jimple.DefinitionStmt;
import soot.jimple.ReturnStmt;
import soot.jimple.internal.JReturnVoidStmt;
import upb.thesis.constantpropagation.data.DFF;
import upb.thesis.constantpropagation.flowfunctions.call.ReturnFF;
import upb.thesis.constantpropagation.flowfunctions.call.ReturnVoidFF;


public class CPAReturnFlowFunctionProvider implements FlowFunctionProvider<DFF> {

    private FlowFunction<DFF> flowFunction;

    public CPAReturnFlowFunctionProvider(Unit callSite, Unit exitStmt, SootMethod caller, SootMethod callee){
        flowFunction = KillAll.v(); // we want to kill everything else when returning from a nested context
        if (exitStmt instanceof ReturnStmt) {
            ReturnStmt returnStmt = (ReturnStmt) exitStmt;
            Value op = returnStmt.getOp();
            if (op instanceof Local) {
                if (callSite instanceof DefinitionStmt) {
                    DefinitionStmt defnStmt = (DefinitionStmt) callSite;
                    Value leftOp = defnStmt.getLeftOp();
                    if (leftOp instanceof Local) {
                        final Local tgtLocal = (Local) leftOp;
                        final Local retLocal = (Local) op;
                        flowFunction = new ReturnFF(tgtLocal, retLocal);
                    }
                }
            }
        }else if(exitStmt instanceof JReturnVoidStmt){
            flowFunction = new ReturnVoidFF(callSite, callee);
        }
    }

    public FlowFunction<DFF> getFlowFunction(){
        return flowFunction;
    }

}
