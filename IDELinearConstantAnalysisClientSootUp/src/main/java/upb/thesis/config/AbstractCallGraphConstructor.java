package upb.thesis.config;

import sootup.callgraph.CallGraph;
import sootup.core.views.View;

public abstract class AbstractCallGraphConstructor {

    public CallGraph callGraph;

    public AbstractCallGraphConstructor() {
    }

    abstract CallGraph generateCallGraph(View view);
}
