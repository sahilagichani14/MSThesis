package upb.thesis;

import java.util.List;

public class EvalHelper {
    private static String targetName;
    private static int threadCount = -1;
    private static int maxMethod = -1;
    private static long totalDuration = 0L;
    private static long totalPropagationCount = 0L;
    private static int actualMethodCount = 0;
    private static String jarPath;
    private static Main.CallgraphAlgorithm callgraphAlgorithm;
    private static List<Main.BodyTransformer> bodyTransformer;
    private static long cg_construction_duration = 0L;
    private static int number_of_cg_Edges;
    private static long number_of_methods_propagated = 0L;
    private static int number_of_reachable_methods = 0;
    private static int initialStmtCount = 0;
    private static int stmtCountAfterApplyingBI = 0;

    public EvalHelper() {
    }

    public static int getInitialStmtCount() {
        return initialStmtCount;
    }

    public static void setInitialStmtCount(int initialStmtCount) {
        EvalHelper.initialStmtCount = initialStmtCount;
    }

    public static int getStmtCountAfterApplyingBI() {
        return stmtCountAfterApplyingBI;
    }

    public static void setStmtCountAfterApplyingBI(int stmtCountAfterApplyingBI) {
        EvalHelper.stmtCountAfterApplyingBI = stmtCountAfterApplyingBI;
    }

    public static int getThreadCount() {
        return threadCount;
    }

    public static void setThreadCount(int threadCount) {
        EvalHelper.threadCount = threadCount;
    }

    public static long getTotalDuration() {
        return totalDuration;
    }

    public static void setTotalDuration(long totalDuration) {
        EvalHelper.totalDuration = totalDuration;
    }

    public static long getTotalPropagationCount() {
        return totalPropagationCount;
    }

    public static void setTotalPropagationCount(long totalPropagationCount) {
        EvalHelper.totalPropagationCount = totalPropagationCount;
    }

    public static int getActualMethodCount() {
        return actualMethodCount;
    }

    public static void setActualMethodCount(int actualMethodCount) {
        EvalHelper.actualMethodCount = actualMethodCount;
    }

    public static int getMaxMethod() {
        return maxMethod;
    }

    public static void setMaxMethod(int maxMethod) {
        EvalHelper.maxMethod = maxMethod;
    }

    public static String getTargetName() {
        return targetName;
    }

    public static void setTargetName(String targetName) {
        EvalHelper.targetName = targetName;
    }

    public static String getJarPath() {
        return jarPath;
    }

    public static void setJarPath(String jarPath) {
        EvalHelper.jarPath = jarPath;
    }

    public static Main.CallgraphAlgorithm getCallgraphAlgorithm() {
        return callgraphAlgorithm;
    }

    public static void setCallgraphAlgorithm(Main.CallgraphAlgorithm callgraphAlgorithm) {
        EvalHelper.callgraphAlgorithm = callgraphAlgorithm;
    }

    public static List<Main.BodyTransformer> getBodyTransformers() {
        return bodyTransformer;
    }

    public static void setBodyTransformers(List<Main.BodyTransformer> bodyTransformer) {
        EvalHelper.bodyTransformer = bodyTransformer;
    }

    public static int getNumber_of_cg_Edges() {
        return number_of_cg_Edges;
    }

    public static void setNumber_of_cg_Edges(int number_of_cg_Edges) {
        EvalHelper.number_of_cg_Edges = number_of_cg_Edges;
    }

    public static long getCg_construction_duration() {
        return cg_construction_duration;
    }

    public static void setCg_construction_duration(long cg_construction_duration) {
        EvalHelper.cg_construction_duration += cg_construction_duration;
    }

    public static long getNumber_of_methods_propagated() {
        return number_of_methods_propagated;
    }

    public static void setNumber_of_methods_propagated(long number_of_methods_propagated) {
        EvalHelper.number_of_methods_propagated += number_of_methods_propagated;
    }

    public static int getNumber_of_reachable_methods() {
        return number_of_reachable_methods;
    }

    public static void setNumber_of_reachable_methods(int number_of_reachable_methods) {
        EvalHelper.number_of_reachable_methods = number_of_reachable_methods;
    }
}
