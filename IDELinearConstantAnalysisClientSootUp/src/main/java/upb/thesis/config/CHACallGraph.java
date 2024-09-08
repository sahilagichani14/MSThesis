package upb.thesis.config;

import sootup.callgraph.CallGraph;
import sootup.callgraph.ClassHierarchyAnalysisAlgorithm;
import sootup.core.model.SootMethod;
import sootup.core.signatures.MethodSignature;
import sootup.core.views.View;
import upb.thesis.EvalHelper;
import upb.thesis.SetUp;

import java.util.List;

public class CHACallGraph extends AbstractCallGraphConstructor {
    public CHACallGraph() {
    }

    CallGraph generateCallGraph(View view) {
        ClassHierarchyAnalysisAlgorithm chaAlgorithm = new ClassHierarchyAnalysisAlgorithm(view);
        List<SootMethod> entryPointMethods = SetUp.getEntryPointMethods(view);
        List<MethodSignature> entryPoints = entryPointMethods.stream().map(sootMethod -> sootMethod.getSignature()).toList();
        CallGraph chaCG = chaAlgorithm.initialize(entryPoints); // or entryPoints
        //CallGraph chaCG = chaAlgorithm.initialize();
        return chaCG;
    }
}