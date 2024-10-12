package upb.thesis.config;

import soot.PackManager;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.options.Options;

public class RTACallGraph extends AbstractCallGraphConstructor {
    public RTACallGraph() {
    }

    CallGraph generateCallGraph() {
        ParametrisedCallGraphConstructor.setSootOptions();
        Options.v().setPhaseOption("cg.spark", "on");
        Options.v().setPhaseOption("cg.spark", "rta:true");
        // Options.v().setPhaseOption("cg.spark", "apponly:true");
        Options.v().setPhaseOption("cg.spark", "on-fly-cg:false");
        PackManager.v().getPack("cg").apply();
        return SceneWrapper.v().getCallGraph();
    }
}