package upb.thesis.config;

import soot.G;
import soot.Scene;
import soot.jimple.toolkits.callgraph.CallGraph;

import java.io.File;

public class CallGraphApplication {
    public CallGraphApplication() {
    }

    public static void main(String[] args) {
        CallGraphMetricsWrapper callGraphMetricsWrapper = generateCallGraphFromPath(generateSootCallGraphConfig(true));
        System.out.println(callGraphMetricsWrapper.getCallGraph().size());
    }

    public static CallGraphMetricsWrapper generateCallGraphFromPath(CallGraphConfig callGraphConfig) {
        G.reset();
        String userdir = System.getProperty("user.dir");
        String sootCp = userdir + File.separator + "libs" + File.separator + File.pathSeparator + "libs" + File.separator + "rt.jar";
        CallGraphConfig.setCallGraphConfigInstance(callGraphConfig);
        String callGraphAlgorithm;
        callGraphAlgorithm = callGraphConfig.getCallGraphAlgorithm().toString();

        Scene scene;
//        if (callGraphConfig.getAppPath().contains(".apk")) {
//            MainClass mainClass = new MainClass();
//            scene = mainClass.getSceneForAPK(callGraphConfig, "/Users/palaniappanmuthuraman/Library/Android/sdk/platforms");
//        } else {
//            scene = CreateScene.getSootScene(sootCp, callGraphConfig.getMainClass());
//        }

        scene = CreateScene.getSootScene(sootCp, callGraphConfig.getMainClass());

        SceneWrapper.v(scene);
        CallGraph callGraph = ParametrisedCallGraphConstructor.buildCallGraph();
        CallGraphConstructionMetrics callGraphConstructionMetrics = new CallGraphConstructionMetrics(callGraphAlgorithm, 0.0F, callGraph.size());
        return new CallGraphMetricsWrapper(callGraph, callGraphConstructionMetrics);
    }

    public static CallGraphMetricsWrapper generateCallGraph(Scene sootScene, CallGraphConfig callGraphConfig) {
        if (sootScene != null) {
            setUpSoot(sootScene);
        }

        CallGraphConfig.setCallGraphConfigInstance(callGraphConfig);
        StopWatch stopWatch = StopWatch.newAndStart("CG Construction");
        CallGraph callGraph = ParametrisedCallGraphConstructor.buildCallGraph();
        stopWatch.stop();
        String callGraphAlgorithm;
        callGraphAlgorithm = callGraphConfig.getCallGraphAlgorithm().toString();

        CallGraphConstructionMetrics callGraphConstructionMetrics = new CallGraphConstructionMetrics(callGraphAlgorithm, stopWatch.elapsed(), callGraph.size());
        return new CallGraphMetricsWrapper(callGraph, callGraphConstructionMetrics);
    }

    public static void setUpSoot(Scene scene) {
        SceneWrapper.v(scene);
    }

    public static CallGraphConfig generateSootCallGraphConfig(boolean isApk) {
        CallGraphConfig callGraphConfig = CallGraphConfig.getInstance();
        callGraphConfig.setCallGraphAlgorithm(CallGraphAlgorithm.CHA);
        callGraphConfig.setIsSootSceneProvided(true);
        if (isApk) {
            callGraphConfig.setAppPath("example/ActivityCommunication1.apk");
        } else {
            callGraphConfig.setAppPath("example");
            callGraphConfig.setMainClass("CallGraph");
        }

        return callGraphConfig;
    }
}
