package upb.thesis;

import com.google.common.base.Stopwatch;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.jimple.JimpleBody;
import soot.options.Options;
import soot.util.Chain;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        Path pathToJar = Paths.get(System.getProperty("user.dir") + "/IDELinearConstantAnalysisClientSoot/src/test/resources/latest/");
        String jarPath = pathToJar + File.separator + args[0];
        String solver = args[1];
        int maxMethods = Integer.parseInt(args[2]);
        String algorithm = args[3];
        String numberOfThreads = args[4];
        int numThreads = Runtime.getRuntime().availableProcessors();

        // List<String> allBTList = Arrays.asList("jb.ls", "jb.lp", "jb.ese", "jb.ne", "jb.dae", "jb.ule", "jb.cp", "jb.uce", "jb.tr", "jb.tt", "jb.lns", "jb.cbf", "jb.dtr", "jb.sils", "jb.a", "jb.ulp", "jb.cp-ule");
        // List<String> defaultBTList = Arrays.asList("jb.tt", "jb.dtr", "jb.uce", "jb.ls", "jb.sils", "jb.a", "jb.ule", "jb.tr", "jb.lns", "jb.cp", "jb.dae", "jb.cp-ule", "jb.lp", "jb.ne", "jb.uce");
        // actually it is Enable List, if present in list then enabled = true, jb.ls, jb.tr should be enabled always else at soot.jimple.toolkits.callgraph.CallGraphPack.internalApply
        LinkedList<String> manualDisableBTList = new LinkedList<>();
        manualDisableBTList.add("jb.ls");
        manualDisableBTList.add("jb.tr");
        Options.v().setDisableBTList(manualDisableBTList);
        //Options.v().setPhaseOption("jb.tr", "use-older-type-assigner:true");
        /*
        Options.v().setPhaseOption("bb", "enabled:false"); //bafBody bb bydefault calls bb.lp, bb.ule, bb.ne so disabled
        //disable all jb body transformers
        Options.v().setPhaseOption("jb.dtr", "enabled:false"); //Duplicate CatchAll Trap Remover
        Options.v().setPhaseOption("jb.ese", "enabled:false"); //Empty Switch Eliminator
        Options.v().setPhaseOption("jb.ls", "enabled:false");//LocalSplitter
        Options.v().setPhaseOption("jb.sils", "enabled:false"); //Shared Initialization Local Splitter
        Options.v().setPhaseOption("jb.a", "enabled:false"); //Aggregator
        Options.v().setPhaseOption("jb.ule", "enabled:false"); //Unused Local Eliminator
        Options.v().setPhaseOption("jb.tr", "enabled:true"); //Type Assigner
        Options.v().setPhaseOption("jb.ulp", "enabled:false"); //Unsplit-originals Local    Packer
        Options.v().setPhaseOption("jb.lns", "enabled:false"); //Local Name Standardizer
        Options.v().setPhaseOption("jb.cp", "enabled:false"); // CopyPropagator
        Options.v().setPhaseOption("jb.dae", "enabled:false"); // DeadAssignmentEliminator
        Options.v().setPhaseOption("jb.cp-ule", "enabled:false"); // UnusedLocalEliminator
        Options.v().setPhaseOption("jb.lp", "enabled:false"); //Local Packer
        Options.v().setPhaseOption("jb.ne", "enabled:false"); //No operation Eliminator
        Options.v().setPhaseOption("jb.uce", "enabled:false"); // UnreachableCodeEliminator
        Options.v().setPhaseOption("jb.tt", "enabled:false"); //Trap Tightener
        Options.v().setPhaseOption("jb.cbf", "enabled:false"); // ConditionalBranchFolder
         */

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
                List<String> appliedBTList = Arrays.asList(args[5].split(","));
                // to set in Soot
                Options.v().setDisableBTList(appliedBTList);
                String cmdLineBT = String.join(",", appliedBTList);
                List<BodyTransformer> var6 = parseBodyTransformer(cmdLineBT);
                EvalHelper.setBodyTransformers(var6);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        } else {
            List<String> disableBTList1 = Options.v().getDisableBTList();
            if (disableBTList1 != null) {
                String appliedBTList = String.join(",", disableBTList1);
                try {
                    List<BodyTransformer> var6 = parseBodyTransformer(appliedBTList);
                    EvalHelper.setBodyTransformers(var6);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        EvalHelper.setJarPath(jarPath);
        EvalHelper.setTargetName(getJarName(jarPath));
        EvalHelper.setMaxMethod(maxMethods);
        EvalHelper.setThreadCount(numThreads);
        String msg = MessageFormat.format("Running {0} - {1} solver - {2} threads", EvalHelper.getTargetName(), solver, numThreads);
        System.out.println(msg);
        SetUp setUp = new SetUp();
        Stopwatch stopwatch = Stopwatch.createStarted();
        if (solver.equalsIgnoreCase("default")) {
            setUp.executeStaticAnalysis(jarPath);
            EvalHelper.setTotalPropagationCount(setUp.defaultPropCount);
        }

        long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        EvalHelper.setTotalDuration(elapsed);

        // after all the jb packs are applied
        AtomicInteger stmtCountAfterApplyingBI = new AtomicInteger();
        for (SootClass sc : Scene.v().getApplicationClasses()) {
            Scene.v().forceResolve(sc.getName(), SootClass.BODIES);
            for (SootMethod sm: sc.getMethods()){
                if (sm.hasActiveBody()){
                    stmtCountAfterApplyingBI.addAndGet(sm.getActiveBody().getUnits().size());
                }
            }
        }
        EvalHelper.setStmtCountAfterApplyingBI(stmtCountAfterApplyingBI.get());

        (new EvalPrinter(solver)).generate();

        /*
        Chain<SootClass> applicationClasses_afterpacks = Scene.v().getApplicationClasses();
        for (SootClass sootClass : applicationClasses_afterpacks) {
            for (SootMethod sootMethod : sootClass.getMethods()) {
                if (sootMethod.hasActiveBody()) {
                    JimpleBody jimpleBody = (JimpleBody) sootMethod.getActiveBody();
                    //System.out.println(jimpleBody);
                }
                //LocalSplitter.v().transform(jimpleBody);
            }
        }
         */
    }

    private static List<BodyTransformer> parseBodyTransformer(String s) throws Exception {
        List<BodyTransformer> listOfApplyingBodyTransformers = new LinkedList<>();
        String[] bodytransformer = s.split(",");
        for (String x : bodytransformer) {
            //jb.ls,jb.lp,jb.ese,jb.ne,jb.dae,jb.ule,jb.cp,jb.uce,jb.tr,jb.tt,jb.lns,jb.cbf,jb.dtr,jb.sils,jb.a,jb.ulp,jb.cp-ule
            switch (x) {
                case "jb.ls":
                    listOfApplyingBodyTransformers.add(BodyTransformer.JB_LS);
                    break;
                case "jb.lp":
                    listOfApplyingBodyTransformers.add(BodyTransformer.JB_LP);
                    break;
                case "jb.ese":
                    listOfApplyingBodyTransformers.add(BodyTransformer.JB_ESE);
                    break;
                case "jb.ne":
                    listOfApplyingBodyTransformers.add(BodyTransformer.JB_NE);
                    break;
                case "jb.dae":
                    listOfApplyingBodyTransformers.add(BodyTransformer.JB_DAE);
                    break;
                case "jb.ule":
                    listOfApplyingBodyTransformers.add(BodyTransformer.JB_ULE);
                    break;
                case "jb.cp":
                    listOfApplyingBodyTransformers.add(BodyTransformer.JB_CP);
                    break;
                case "jb.uce":
                    listOfApplyingBodyTransformers.add(BodyTransformer.JB_UCE);
                    break;
                case "jb.tr":
                    listOfApplyingBodyTransformers.add(BodyTransformer.JB_TR);
                    break;
                case "jb.tt":
                    listOfApplyingBodyTransformers.add(BodyTransformer.JB_TT);
                    break;
                case "jb.lns":
                    listOfApplyingBodyTransformers.add(BodyTransformer.JB_LNS);
                    break;
                case "jb.cbf":
                    listOfApplyingBodyTransformers.add(BodyTransformer.JB_CBF);
                    break;
                case "jb.dtr":
                    listOfApplyingBodyTransformers.add(BodyTransformer.JB_DTR);
                    break;
                case "jb.sils":
                    listOfApplyingBodyTransformers.add(BodyTransformer.JB_SILS);
                    break;
                case "jb.a":
                    listOfApplyingBodyTransformers.add(BodyTransformer.JB_A);
                    break;
                case "jb.ulp":
                    listOfApplyingBodyTransformers.add(BodyTransformer.JB_ULP);
                    break;
                case "jb.cp-ule":
                    listOfApplyingBodyTransformers.add(BodyTransformer.JB_CP_ULE);
                    break;
                default:
                    System.err.printf("Invalid or No body transformer applied: %s%n", s);
                    throw new Exception();
            }
        }
        return listOfApplyingBodyTransformers;
    }

    private static String getJarName(String fullpath) {
        int start = fullpath.lastIndexOf(File.separator);
        int endDot = fullpath.lastIndexOf(".");
        int endDash = fullpath.lastIndexOf("-");
        int latest = endDot < endDash ? endDash : endDot;
        return fullpath.substring(start + 1, latest);
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

    public static enum CallgraphAlgorithm {
        CHA,
        RTA;
        private CallgraphAlgorithm() {
        }
    }

    public static enum BodyTransformer {
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

}