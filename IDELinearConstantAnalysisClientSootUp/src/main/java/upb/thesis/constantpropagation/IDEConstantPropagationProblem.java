package upb.thesis.constantpropagation;

import heros.*;
import heros.edgefunc.EdgeIdentity;
import heros.flowfunc.Identity;
import sootup.analysis.interprocedural.ide.DefaultJimpleIDETabulationProblem;
import sootup.core.jimple.basic.Local;
import sootup.core.jimple.basic.Value;
import sootup.core.jimple.common.constant.IntConstant;
import sootup.core.jimple.common.stmt.Stmt;
import sootup.core.model.SootMethod;
import sootup.core.types.NullType;
import upb.thesis.constantpropagation.edgefunctions.CPACallEdgeFunctionProvider;
import upb.thesis.constantpropagation.edgefunctions.CPANormalEdgeFunctionProvider;
import upb.thesis.constantpropagation.edgefunctions.CPAReturnEdgeFunctionProvider;
import upb.thesis.constantpropagation.edgefunctions.ConstantTOP;
import upb.thesis.constantpropagation.edgefunctions.normal.ConstantTop;
import upb.thesis.constantpropagation.flowfunctions.CPACallFlowFunctionProvider;
import upb.thesis.constantpropagation.flowfunctions.CPANormalFlowFunctionProvider;
import upb.thesis.constantpropagation.flowfunctions.CPAReturnFlowFunctionProvider;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of a Constant Propagation Problem for interprocedural data flow analysis using
 * IDE framework. This class defines the flow and edge functions for propagating constant values
 * across method calls.
 */
public class IDEConstantPropagationProblem
    extends DefaultJimpleIDETabulationProblem<
        Value, ConstantValue, InterproceduralCFG<Stmt, SootMethod>> {

  protected InterproceduralCFG<Stmt, SootMethod> icfg;
  private final SootMethod entryMethod;

  public static final ConstantValue TOP =
      new ConstantValue(IntConstant.getInstance(Integer.MIN_VALUE), null); // Unknown

  public static final ConstantValue BOTTOM =
      new ConstantValue(IntConstant.getInstance(Integer.MAX_VALUE), null); // Unknown

  /**
   * Constructs an IDEConstantPropagationProblem with the provided interprocedural control flow
   * graph (ICFG) and entry method.
   *
   * @param icfg The interprocedural control flow graph.
   * @param entryMethod The method where the analysis starts.
   */
  public IDEConstantPropagationProblem(
      InterproceduralCFG<Stmt, SootMethod> icfg, SootMethod entryMethod) {
    super(icfg);
    this.icfg = icfg;
    this.entryMethod = entryMethod;
  }

  @Override
  protected FlowFunctions<Stmt, Value, SootMethod> createFlowFunctionsFactory() {
    return new FlowFunctions<Stmt, Value, SootMethod>() {
      /**
       * Creates the normal flow function for propagating values between statements within a method.
       */
      @Override
      public FlowFunction<Value> getNormalFlowFunction(Stmt curr, Stmt succ) {

        CPANormalFlowFunctionProvider normalFlowFunctionProvider =
            new CPANormalFlowFunctionProvider(curr, zeroValue());
        return normalFlowFunctionProvider.getFlowFunction();
      }

      /**
       * Creates the call flow function for propagating values from a call site to the callee
       * method.
       */
      @Override
      public FlowFunction<Value> getCallFlowFunction(Stmt callStmt, SootMethod dest) {

        CPACallFlowFunctionProvider tsaReturnFlowFunctionProvider =
            new CPACallFlowFunctionProvider(callStmt, zeroValue(), dest);
        return tsaReturnFlowFunctionProvider.getFlowFunction();
      }

      /**
       * Creates the return flow function for propagating values from the callee method back to the
       * caller.
       */
      @Override
      public FlowFunction<Value> getReturnFlowFunction(
          Stmt callSite, SootMethod calleeMethod, Stmt exitStmt, Stmt returnSite) {

        CPAReturnFlowFunctionProvider tsaReturnFlowFunctionProvider =
            new CPAReturnFlowFunctionProvider(callSite, exitStmt, calleeMethod, zeroValue());
        return tsaReturnFlowFunctionProvider.getFlowFunction();
      }

      /**
       * Creates a call-to-return flow function for propagating values directly from the call site
       * to the return site, skipping the callee.
       */
      @Override
      public FlowFunction<Value> getCallToReturnFlowFunction(Stmt callSite, Stmt returnSite) {
        return Identity.v();
      }
    };
  }

  /**
   * Creates the zero value, which is a special value representing the initial state in the
   * analysis.
   *
   * @return The zero value for this analysis.
   */
  @Override
  protected Local createZeroValue() {
    return new Local("Zero", NullType.getInstance());
  }

  /**
   * Creates the top edge function, representing the top element in the lattice for edge functions.
   *
   * @return The top edge function.
   */
  @Override
  protected EdgeFunction<ConstantValue> createAllTopFunction() {
    return ConstantTOP.v();
  }

  /**
   * Creates the meet lattice, which defines how to combine multiple values during the analysis.
   *
   * @return The meet lattice for this analysis.
   */
  @Override
  protected MeetLattice<ConstantValue> createMeetLattice() {
    return new MeetLattice<ConstantValue>() {
      @Override
      public ConstantValue topElement() {
        return TOP;
      }

      @Override
      public ConstantValue bottomElement() {
        return BOTTOM;
      }

      /**
       * Defines the meet operation for combining two constant values.
       *
       * @param left The left operand.
       * @param right The right operand.
       * @return The result of the meet operation.
       */
      @Override
      public ConstantValue meet(ConstantValue left, ConstantValue right) {

        if (left == TOP) {
          return right;
        }
        if (right == TOP) {
          return left;
        }
        if (left == BOTTOM) {
          return left;
        }
        if (right == BOTTOM) {
          return right;
        }
        if (left == right) {
          return left;
        } else {
          return BOTTOM;
        }
      }
    };
  }

  @Override
  protected EdgeFunctions<Stmt, Value, SootMethod, ConstantValue> createEdgeFunctionsFactory() {
    return new EdgeFunctions<Stmt, Value, SootMethod, ConstantValue>() {
      /**
       * Creates the normal edge function for transferring values between nodes in the control flow
       * graph.
       */
      @Override
      public EdgeFunction<ConstantValue> getNormalEdgeFunction(
          Stmt curr, Value currNode, Stmt succ, Value succNode) {
        CPANormalEdgeFunctionProvider tsaNormalEdgeFunctionProvider =
            new CPANormalEdgeFunctionProvider(curr, currNode, succ, succNode, zeroValue());

        return tsaNormalEdgeFunctionProvider.getEdgeFunction();
      }

      /**
       * Creates the call edge function for transferring values from the call site to the entry of
       * the callee method.
       */
      @Override
      public EdgeFunction<ConstantValue> getCallEdgeFunction(
          Stmt callStmt, Value srcNode, SootMethod destinationMethod, Value destNode) {

        CPACallEdgeFunctionProvider cpaCallEdgeFunctionProvider1 =
            new CPACallEdgeFunctionProvider(
                callStmt, srcNode, destinationMethod, destNode, zeroValue());
        return cpaCallEdgeFunctionProvider1.getEdgeFunction();
      }

      /**
       * Creates the return edge function for transferring values from the exit of the callee method
       * back to the caller.
       */
      @Override
      public EdgeFunction<ConstantValue> getReturnEdgeFunction(
          Stmt callSite,
          SootMethod calleeMethod,
          Stmt exitStmt,
          Value exitNode,
          Stmt returnSite,
          Value retNode) {
        CPAReturnEdgeFunctionProvider cpaReturnEdgeFunctionProvider =
            new CPAReturnEdgeFunctionProvider(
                callSite, calleeMethod, exitStmt, exitNode, returnSite, retNode, zeroValue());
        return cpaReturnEdgeFunctionProvider.getEdgeFunction();
      }

      /**
       * Creates the call-to-return edge function for transferring values directly from the call
       * site to the return site, skipping the callee method.
       */
      @Override
      public EdgeFunction<ConstantValue> getCallToReturnEdgeFunction(
          Stmt callSite, Value callNode, Stmt returnSite, Value returnSideNode) {
        return EdgeIdentity.v();
      }
    };
  }

  /**
   * Specifies the initial seeds for the analysis, typically the entry points of the program.
   *
   * @return A map from initial statements to the values associated with them.
   */
  @Override
  public Map<Stmt, Set<Value>> initialSeeds() {

    return DefaultSeeds.make(
        Collections.singleton(entryMethod.getBody().getStmtGraph().getStartingStmt()), zeroValue());
  }
}
