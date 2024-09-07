package upb.thesis.constantpropagation.edgefunctions;

import heros.EdgeFunction;
import heros.edgefunc.EdgeIdentity;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.jimple.BinopExpr;
import soot.jimple.DefinitionStmt;
import soot.jimple.IntConstant;
import upb.thesis.constantpropagation.AnalysisLogger;
import upb.thesis.constantpropagation.IDELinearConstantAnalysisProblem;
import upb.thesis.constantpropagation.data.DFF;
import upb.thesis.constantpropagation.edgefunctions.normal.IntegerAssign;
import upb.thesis.constantpropagation.edgefunctions.normal.IntegerBinop;

public class CPANormalEdgeFunctionProvider {

    public final static EdgeFunction<Integer> ALL_BOTTOM = new IntegerAllBottom(IDELinearConstantAnalysisProblem.BOTTOM);

    private EdgeFunction<Integer> edgeFunction;

    public CPANormalEdgeFunctionProvider(SootMethod method, Unit src, DFF srcNode, DFF tgtNode, DFF zeroValue){
        edgeFunction = EdgeIdentity.v();
        if (srcNode == zeroValue && tgtNode == zeroValue) {
            edgeFunction = ALL_BOTTOM;
        } else if (src instanceof DefinitionStmt) {
            DefinitionStmt assignment = (DefinitionStmt) src;
            Value lhs = assignment.getLeftOp();
            Value rhs = assignment.getRightOp();
            if(lhs == tgtNode.getValue()){
                // check if lhs is the tgtNode we are looking at and if rhs is a constant integer
                if (rhs instanceof IntConstant) {
                    IntConstant iconst = (IntConstant) rhs;
                    edgeFunction = new IntegerAssign(iconst.value, new AnalysisLogger(method, src));
                }
                // check if rhs is a binary expression with known values
                else if (rhs instanceof BinopExpr) {
                    BinopExpr binop = (BinopExpr) rhs;
                    if(isLinearBinop(binop)){
                        edgeFunction = new IntegerBinop(binop, srcNode);
                    }
                }
            }
        }
    }

    /**
     * one of the operands must be constant
     */
    public static boolean isLinearBinop(BinopExpr binop){
        Value op1 = binop.getOp1();
        Value op2 = binop.getOp2();
        if(op1 instanceof IntConstant || op2 instanceof IntConstant){
            return true;
        }
        return false;
    }

    public EdgeFunction<Integer> getEdgeFunction(){
        return edgeFunction;
    }
}
