package upb.thesis.config;

import sootup.callgraph.CallGraph;
import sootup.callgraph.RapidTypeAnalysisAlgorithm;
import sootup.core.model.SootMethod;
import sootup.core.signatures.MethodSignature;
import sootup.core.views.View;
import upb.thesis.SetUp;

import java.util.List;

public class RTACallGraph extends AbstractCallGraphConstructor {
    public RTACallGraph() {
    }

    CallGraph generateCallGraph(View view) {
        RapidTypeAnalysisAlgorithm rtaAlgorithm = new RapidTypeAnalysisAlgorithm(view);
        List<MethodSignature> entryPoints = SetUp.cgEntryMethods.stream().map(sootMethod -> sootMethod.getSignature()).toList();
        CallGraph rtaCG = rtaAlgorithm.initialize(entryPoints); // or entryPoints
        //CallGraph rtaCG = rtaAlgorithm.initialize();
        return rtaCG;
    }
}