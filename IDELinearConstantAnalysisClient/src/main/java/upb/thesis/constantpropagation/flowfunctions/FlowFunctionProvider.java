package upb.thesis.constantpropagation.flowfunctions;

import heros.FlowFunction;

public interface FlowFunctionProvider<D> {
  FlowFunction<D> getFlowFunction();
}
