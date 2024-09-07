package upb.thesis.config;

import soot.jimple.toolkits.callgraph.CallGraph;

public class CallGraphMetricsWrapper {
    private CallGraph callGraph;
    private CallGraphConstructionMetrics callGraphConstructionMetrics;

    public CallGraphMetricsWrapper(CallGraph callGraph, CallGraphConstructionMetrics callGraphConstructionMetrics) {
        this.callGraph = callGraph;
        this.callGraphConstructionMetrics = callGraphConstructionMetrics;
    }

    public CallGraph getCallGraph() {
        return this.callGraph;
    }

    public CallGraphConstructionMetrics getCallGraphConstructionMetrics() {
        return this.callGraphConstructionMetrics;
    }
}
