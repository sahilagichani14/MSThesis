package upb.thesis.config;

import sootup.callgraph.CallGraph;
import sootup.core.views.View;

public class ParametrisedCallGraphConstructor {
    public ParametrisedCallGraphConstructor() {
    }

    public static CallGraph buildCallGraph(View view) {
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

        return ((AbstractCallGraphConstructor)abstractCallGraphConstructor).generateCallGraph(view);
    }

    public static void setSootOptions() {
    }
}
