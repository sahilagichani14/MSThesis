package upb.thesis.config;

import sootup.callgraph.CallGraph;
import sootup.core.views.View;

public class CallGraphApplication {

    public CallGraphApplication() {
    }

    public static CallGraphMetricsWrapper generateCallGraph(View view, CallGraphConfig callGraphConfig) {
        CallGraphConfig.setCallGraphConfigInstance(callGraphConfig);
        StopWatch stopWatch = StopWatch.newAndStart("CG Construction");
        CallGraph callGraph = ParametrisedCallGraphConstructor.buildCallGraph(view);
        stopWatch.stop();
        String callGraphAlgorithm;
        callGraphAlgorithm = callGraphConfig.getCallGraphAlgorithm().toString();

        CallGraphConstructionMetrics callGraphConstructionMetrics = new CallGraphConstructionMetrics(callGraphAlgorithm, stopWatch.elapsed(), callGraph.callCount());
        return new CallGraphMetricsWrapper(callGraph, callGraphConstructionMetrics);
    }
}
