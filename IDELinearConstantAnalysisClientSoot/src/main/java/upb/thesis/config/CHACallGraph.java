package upb.thesis.config;

import soot.PackManager;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.options.Options;

public class CHACallGraph extends AbstractCallGraphConstructor {
    public CHACallGraph() {
    }

    CallGraph generateCallGraph() {
        ParametrisedCallGraphConstructor.setSootOptions();
        Options.v().setPhaseOption("cg.cha", "on");
        // Options.v().setPhaseOption("cg.cha", "apponly:true");
        PackManager.v().getPack("cg").apply();
        return SceneWrapper.v().getCallGraph();
    }
}