package upb.thesis;

import com.google.common.base.Stopwatch;
import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.core.model.SourceType;
import sootup.core.views.View;
import sootup.java.bytecode.frontend.inputlocation.JavaClassPathAnalysisInputLocation;
import sootup.java.core.views.JavaView;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static int maxMethodSize = -1;

    public static void main(String[] args) {
        Path pathToJar = Paths.get(System.getProperty("user.dir") + "/IDELinearConstantAnalysisClientSootUp/src/test/resources/latest/");
        String jarPath = pathToJar + File.separator + args[0];
        String solver = args[1];
        int maxMethods = Integer.parseInt(args[2]);
        maxMethodSize = maxMethods;
        String algorithm = args[3];
        String numberOfThreads = args[4];
        int numThreads = Runtime.getRuntime().availableProcessors();

        try {
            CallgraphAlgorithm callgraphAlgorithm = parseCallgraphAlgorithm(algorithm);
            EvalHelper.setCallgraphAlgorithm(callgraphAlgorithm);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        if (args.length > 4) {
            numThreads = Integer.parseInt(numberOfThreads);
        }

        if (args.length > 5) {
            try {
                // Default in order: jb.ne, jb.ese, CastAndReturnInliner, jb.ls, jb.a, jb.cp, ConstantPropagatorAndFolder, jb.tr
                // List<sootup.core.transform.BodyInterceptor> defaultBodyInterceptors = BytecodeBodyInterceptors.Default.getBodyInterceptors();
                String[] split = args[5].split(",");
                List<String> appliedBTList = new LinkedList<>();
                for (String bi: split){
                    appliedBTList.add(bi);
                }
                String cmdLineBT = String.join(",", appliedBTList);
                List<BodyInterceptor> var6 = parseBodyInterceptors(cmdLineBT);
                EvalHelper.setBodyInterceptors(var6);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }

        EvalHelper.setJarPath(jarPath);
        EvalHelper.setTargetName(getJarName(jarPath));
        EvalHelper.setMaxMethod(maxMethodSize);
        EvalHelper.setThreadCount(numThreads);
        String msg = MessageFormat.format("Running {0} - {1} solver - {2} threads", EvalHelper.getTargetName(), solver, numThreads);
        System.out.println(msg);

        AnalysisInputLocation inputLocation = new JavaClassPathAnalysisInputLocation(jarPath, SourceType.Application, Collections.emptyList());
        View viewBeforeBI = new JavaView(List.of(inputLocation));
        AtomicInteger initialStmtCount = new AtomicInteger();
        viewBeforeBI.getClasses().forEach(clazz -> {
            clazz.getMethods().forEach(method -> {
                if (method.isConcrete()) {
                    initialStmtCount.addAndGet(method.getBody().getStmts().size());
                }
            });
        });
        EvalHelper.setInitialStmtCount(initialStmtCount.get());

        SetUp setUp = new SetUp();
        Stopwatch stopwatch = Stopwatch.createStarted();
        if (solver.equalsIgnoreCase("default")) {
            setUp.executeStaticAnalysis(jarPath);
            EvalHelper.setTotalPropagationCount(setUp.defaultPropCount);
        }

        long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        EvalHelper.setTotalDuration(elapsed);
        (new EvalPrinter(solver)).generate();
    }

    /*
    public static Set<Pair<String, String>> getResult(Object analysis, SootMethod entryMethod) {
        Map<Value, ConstantValue> res = null;
        Set<Pair<String, String>> result = new HashSet<>();
        if (analysis instanceof JimpleIDESolver) {
            JimpleIDESolver solver = (JimpleIDESolver) analysis;
            List<Stmt> stmts = entryMethod.getBody().getStmts();
            res = (Map<Value, ConstantValue>) solver.resultsAt(stmts.get(stmts.size() - 1));
        }

        if (res == null) {
            return result;
        }
        for (Map.Entry<Value, ConstantValue> e : res.entrySet()) {
            Pair<String, String> pair =
                    new Pair<>(e.getKey().toString(), e.getValue().getConstant().toString());
            result.add(pair);
        }
        return result;
    }
     */

    private static String getJarName(String fullpath) {
        int start = fullpath.lastIndexOf(File.separator);
        int endDot = fullpath.lastIndexOf(".");
        int endDash = fullpath.lastIndexOf("-");
        int latest = endDot < endDash ? endDash : endDot;
        return fullpath.substring(start + 1, latest);
    }

    public static enum CallgraphAlgorithm {
        CHA,
        RTA;
        private CallgraphAlgorithm() {
        }
    }

    public static enum BodyInterceptor {
        //Local Splitter
        JB_LS,

        //Local Packer
        JB_LP,

        //Empty Switch Eliminator
        JB_ESE,

        //Nop Eliminator
        JB_NE,

        //Dead Assignment Eliminator
        JB_DAE,

        //Unused Local Eliminator
        JB_ULE,

        //Copy Propagator
        JB_CP,

        //Unreachable Code Eliminator
        JB_UCE,

        //Type Assigner
        JB_TR,

        //Trap Tightener
        JB_TT,

        //LocalName Standardizer
        JB_LNS,

        //Conditional Branch Folder
        JB_CBF,

        //Duplicate CatchAll Trap Remover
        JB_DTR,

        //Shared Initialization Local Splitter
        JB_SILS,

        //Jimple Local Aggregator
        JB_A,

        //Unsplit Originals Local Packer
        JB_ULP,

        //Post-copy propagation Unused Local Eliminator
        JB_CP_ULE
    }

    public static CallgraphAlgorithm parseCallgraphAlgorithm(String var0) throws Exception {
        if (var0.equalsIgnoreCase("CHA")) {
            return CallgraphAlgorithm.CHA;
        } else if (var0.equalsIgnoreCase("RTA")) {
            return CallgraphAlgorithm.RTA;
        } else {
            System.err.printf("Invalid callgraph algorithm: %s%n", var0);
            throw new Exception();
        }
    }

    private static List<BodyInterceptor> parseBodyInterceptors(String s) throws Exception {
        List<BodyInterceptor> listOfApplyingBodyInterceptors = new LinkedList<>();
        String[] bodyInterceptors = s.split(",");
        for (String x : bodyInterceptors) {
            //jb.ls,jb.lp,jb.ese,jb.ne,jb.dae,jb.ule,jb.cp,jb.uce,jb.tr,jb.tt,jb.lns,jb.cbf,jb.dtr,jb.sils,jb.a,jb.ulp,jb.cp-ule
            switch (x) {
                case "jb.ls":
                    listOfApplyingBodyInterceptors.add(BodyInterceptor.JB_LS);
                    break;
                case "jb.lp":
                    listOfApplyingBodyInterceptors.add(BodyInterceptor.JB_LP);
                    break;
                case "jb.ese":
                    listOfApplyingBodyInterceptors.add(BodyInterceptor.JB_ESE);
                    break;
                case "jb.ne":
                    listOfApplyingBodyInterceptors.add(BodyInterceptor.JB_NE);
                    break;
                case "jb.dae":
                    listOfApplyingBodyInterceptors.add(BodyInterceptor.JB_DAE);
                    break;
                case "jb.ule":
                    listOfApplyingBodyInterceptors.add(BodyInterceptor.JB_ULE);
                    break;
                case "jb.cp":
                    listOfApplyingBodyInterceptors.add(BodyInterceptor.JB_CP);
                    break;
                case "jb.uce":
                    listOfApplyingBodyInterceptors.add(BodyInterceptor.JB_UCE);
                    break;
                case "jb.tr":
                    listOfApplyingBodyInterceptors.add(BodyInterceptor.JB_TR);
                    break;
                case "jb.tt":
                    listOfApplyingBodyInterceptors.add(BodyInterceptor.JB_TT);
                    break;
                case "jb.lns":
                    listOfApplyingBodyInterceptors.add(BodyInterceptor.JB_LNS);
                    break;
                case "jb.cbf":
                    listOfApplyingBodyInterceptors.add(BodyInterceptor.JB_CBF);
                    break;
                case "jb.dtr":
                    listOfApplyingBodyInterceptors.add(BodyInterceptor.JB_DTR);
                    break;
                case "jb.sils":
                    listOfApplyingBodyInterceptors.add(BodyInterceptor.JB_SILS);
                    break;
                case "jb.a":
                    listOfApplyingBodyInterceptors.add(BodyInterceptor.JB_A);
                    break;
                case "jb.ulp":
                    listOfApplyingBodyInterceptors.add(BodyInterceptor.JB_ULP);
                    break;
                case "jb.cp-ule":
                    listOfApplyingBodyInterceptors.add(BodyInterceptor.JB_CP_ULE);
                    break;
                default:
                    System.err.printf("Invalid or No body interceptors applied: %s%n", s);
                    throw new Exception();
            }
        }
        return listOfApplyingBodyInterceptors;
    }
}