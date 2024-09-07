package upb.thesis.config;

import soot.jimple.toolkits.callgraph.CallGraph;

public class ParametrisedCallGraphConstructor {
    public ParametrisedCallGraphConstructor() {
    }

    public static CallGraph buildCallGraph() {
        CallGraphConfig callGraphConfig = CallGraphConfig.getInstance();
        Object abstractCallGraphConstructor;
        switch (callGraphConfig.getCallGraphAlgorithm()) {
            case CHA:
                abstractCallGraphConstructor = new CHACallGraph();
                break;
            case RTA:
                abstractCallGraphConstructor = new RTACallGraph();
                break;
            default:
                throw new RuntimeException();
        }

        return ((AbstractCallGraphConstructor)abstractCallGraphConstructor).generateCallGraph();
    }

    public static void setSootOptions() {
    }
}
