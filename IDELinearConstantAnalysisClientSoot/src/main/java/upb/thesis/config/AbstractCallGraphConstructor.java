package upb.thesis.config;

import soot.jimple.toolkits.callgraph.CallGraph;

public abstract class AbstractCallGraphConstructor {
    public CallGraph callGraph = new CallGraph();

    public AbstractCallGraphConstructor() {
    }

    abstract CallGraph generateCallGraph();
}
