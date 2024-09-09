package upb.thesis;

import com.google.common.base.Stopwatch;
import heros.solver.Pair;
import soot.*;
import soot.jimple.DefinitionStmt;
import soot.jimple.IntConstant;
import soot.jimple.toolkits.base.Aggregator;
import soot.jimple.toolkits.ide.icfg.JimpleBasedInterproceduralCFG;
import soot.jimple.toolkits.scalar.*;
import soot.jimple.toolkits.typing.TypeAssigner;
import soot.options.Options;
import soot.toolkits.exceptions.DuplicateCatchAllTrapRemover;
import soot.toolkits.exceptions.TrapTightener;
import soot.toolkits.scalar.LocalPacker;
import soot.toolkits.scalar.LocalSplitter;
import soot.toolkits.scalar.UnusedLocalEliminator;
import upb.thesis.config.CallGraphAlgorithm;
import upb.thesis.config.CallGraphApplication;
import upb.thesis.config.CallGraphConfig;
import upb.thesis.config.CallGraphMetricsWrapper;
import upb.thesis.constantpropagation.IDELinearConstantAnalysisProblem;
import upb.thesis.constantpropagation.data.DFF;
import upb.thesis.solver.JimpleIDESolver;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class SetUp {

    private static JimpleIDESolver<?, ?, ?> solver;

    private static List<SootMethod> entryMethods;

    public long defaultPropCount = 0;
    public long sparsePropCount = 0;

    protected void executeStaticAnalysis(String jarPath) {
        setupSoot(jarPath);
        registerSootTransformers();
        executeSootTransformers();
    }

    private void executeSootTransformers() {
        try {
            PackManager.v().getPack("cg").apply();
            Scene.v().releaseCallGraph();
            Stopwatch var1 = Stopwatch.createStarted();
            CallGraphMetricsWrapper var2 = CallGraphApplication.generateCallGraph(Scene.v(), this.constructCallGraphConfig());
            EvalHelper.setCg_construction_duration(var1.elapsed(TimeUnit.MILLISECONDS));
            Scene.v().setCallGraph(var2.getCallGraph());
            EvalHelper.setNumber_of_cg_Edges(Scene.v().getCallGraph().size());
            EvalHelper.setNumber_of_reachable_methods(Scene.v().getReachableMethods().size());
            System.out.println("Number of CallGraph edges: " + Scene.v().getCallGraph().size());
            PackManager.v().getPack("wjtp").apply();
        } catch (Exception var3) {
            var3.printStackTrace();
            System.exit(1);
        }
    }

    private CallGraphConfig constructCallGraphConfig() {
        CallGraphConfig var1 = CallGraphConfig.getInstance();
        var1.setAppPath(EvalHelper.getJarPath());
        CallGraphAlgorithm var2 = this.configureCallgraph(EvalHelper.getCallgraphAlgorithm());
        var1.setCallGraphAlgorithm(var2);
        var1.setIsSootSceneProvided(true);

        return var1;
    }

    protected CallGraphAlgorithm configureCallgraph(Main.CallgraphAlgorithm var1) {
        CallGraphAlgorithm var2;
        switch (var1) {
            case CHA:
                var2 = CallGraphAlgorithm.CHA;
                break;
            case RTA:
                var2 = CallGraphAlgorithm.RTA;
                break;
            default:
                throw new RuntimeException("Invalid callgraph algorithm");
        }

        return var2;
    }

    protected BodyTransformer configureBodyTransformers(Main.BodyTransformer var1) {
        BodyTransformer var2 = null;
        switch (var1) {
            case JB_LS:
                //Local Splitter
                var2 = LocalSplitter.v();
                break;
            case JB_ESE:
                //Empty Switch Eliminator
                var2 = EmptySwitchEliminator.v();
                break;
            case JB_LP:
                //Local Packer
                var2 = LocalPacker.v();
                break;
            case JB_NE:
                //Nop Eliminator
                var2 = NopEliminator.v();
                break;
            case JB_DAE:
                //Dead Assignment Eliminator
                var2 = DeadAssignmentEliminator.v();
                break;
            case JB_ULE:
                //Unused Local Eliminator
                var2 = UnusedLocalEliminator.v();
                break;
            case JB_CP:
                //Copy Propagator
                var2 = CopyPropagator.v();
                break;
            case JB_UCE:
                //Unreachable Code Eliminator
                var2 = UnreachableCodeEliminator.v();
                break;
            case JB_TR:
                //Type Assigner
                var2 = TypeAssigner.v();
                break;
            case JB_TT:
                //Trap Tightener
                var2 = TrapTightener.v();
                break;
            case JB_LNS:
                //LocalName Standardizer
                var2 = LocalNameStandardizer.v();
                break;
            case JB_CBF:
                //Conditional Branch Folder
                var2 = ConditionalBranchFolder.v();
                break;
            case JB_DTR:
                //Duplicate CatchAll Trap Remover
                var2 = DuplicateCatchAllTrapRemover.v();
                break;
            case JB_SILS:
                //Shared Initialization Local Splitter
                //var2 = SharedInitializationLocalSplitter.v();
                break;
            case JB_A:
                //Jimple Local Aggregator
                var2 = Aggregator.v();
                break;
            case JB_ULP:
                //Unsplit Originals Local Packer
                // SAME ????
                var2 = LocalPacker.v();
                break;
            case JB_CP_ULE:
                //Post-copy propagation Unused Local Eliminator
                // SAME ????
                var2 = UnusedLocalEliminator.v();
                break;
            default:
                throw new RuntimeException("Invalid Body Transformer");
        }
        return var2;
    }

    private void registerSootTransformers() {
        Transform transform = new Transform("wjtp.ifds", createAnalysisTransformer());
        PackManager.v().getPack("wjtp").add(transform);
        List<Main.BodyTransformer> bodyTransformers = EvalHelper.getBodyTransformers();
        if (bodyTransformers!=null){
            for (Main.BodyTransformer x : bodyTransformers) {
                Transform transform1 = new Transform("jb." + x.name(), createAnalysisTransformer1(x));
                PackManager.v().getPack("jb").add(transform1);
            }
        }
    }

    private Transformer createAnalysisTransformer1(Main.BodyTransformer bodyTransformer) {
        BodyTransformer configuredBodyTransformer = configureBodyTransformers(bodyTransformer);
        return configuredBodyTransformer;
    }

    protected Transformer createAnalysisTransformer() {
        return new SceneTransformer() {
            @Override
            protected void internalTransform(String phaseName, Map<String, String> options) {
                JimpleBasedInterproceduralCFG icfg = new JimpleBasedInterproceduralCFG(false);
                for (SootMethod method : entryMethods) {
                    // System.out.println("started solving from: " + method.getSignature());
                    IDELinearConstantAnalysisProblem problem = new IDELinearConstantAnalysisProblem(icfg, method, EvalHelper.getThreadCount());
                    @SuppressWarnings({"rawtypes", "unchecked"})
                    JimpleIDESolver<?, ?, ?> mSolver = new JimpleIDESolver<>(problem);
                    mSolver.solve();
                    solver = mSolver;
                    mSolver.addFinalResults(method.getSignature());
                    getResult(mSolver, method);
                }
                if (solver != null) {
                    solver.dumpResults(EvalHelper.getTargetName());
                }

            }
        };
    }

    /*
     * This method provides the options to soot to analyse the respecive
     * classes.
     */
    private void setupSoot(String jarPath) {
        G.reset();
        String userdir = System.getProperty("user.dir");
        String sootCp = jarPath + File.pathSeparator + "lib" + File.separator + "rt.jar";
        Options.v().set_soot_classpath(sootCp);
        Options.v().set_process_dir(Collections.singletonList(jarPath));

        // We want to perform a whole program, i.e. an interprocedural analysis.
        // We construct a basic CHA call graph for the program
        Options.v().set_whole_program(true);
        Options.v().setPhaseOption("cg.spark", "on");
        Options.v().setPhaseOption("cg", "all-reachable:true");

        Options.v().set_no_bodies_for_excluded(true);
        Options.v().set_allow_phantom_refs(true);
        Options.v().setPhaseOption("jb", "use-original-names:true");
        Options.v().set_prepend_classpath(false);

        Scene.v().addBasicClass("java.lang.StringBuilder");
//        SootClass c = Scene.v().forceResolve("com.google.common.io.MultiReader", SootClass.BODIES);
//        if (c != null) {
//            c.setApplicationClass();
//        }
        Scene.v().loadNecessaryClasses();
//        for (SootClass sc : Scene.v().getApplicationClasses()) {
//            Scene.v().forceResolve(sc.getName(), SootClass.BODIES);
//        }
//        Scene.v().loadNecessaryClasses();
        entryMethods = getEntryPointMethods();
    }


    private boolean isPublicAPI(SootMethod method) {
        return !method.isStatic() && method.isPublic() && !method.isAbstract() && !method.isConstructor() && !method.isNative() && !method.isStaticInitializer();
    }

    protected List<SootMethod> getEntryPointMethods() {
        List<SootMethod> methods = new ArrayList<>();
        Set<SootClass> classes = new HashSet<>();
        for (SootClass c : Scene.v().getApplicationClasses()) {
            classes.add(c);
        }
        l1:
        for (SootClass c : classes) {
            for (SootMethod m : c.getMethods()) {
                MethodSource source = m.getSource();
                if (source != null) {
                    if (isPublicAPI(m)) {
                        m.retrieveActiveBody();
                        if (m.hasActiveBody()) {
                            //if(m.getSignature().contains("normalizeDocument")){
                            //if(m.getReturnType() instanceof IntegerType && m.getParameterTypes().stream().anyMatch(t->t instanceof IntegerType && !t.equals(BooleanType.v()))){
                            UnitPatchingChain units = m.getActiveBody().getUnits();
                            for (Unit unit : units) {
                                if (unit instanceof DefinitionStmt) {
                                    DefinitionStmt assign = (DefinitionStmt) unit;
                                    Value rhs = assign.getRightOp();
                                    if (rhs instanceof IntConstant) {
                                        methods.add(m);
                                        if (methods.size() == EvalHelper.getMaxMethod()) {
                                            break l1;
                                        }
                                    }
                                }
                            }
                            //}
                        }
                    }
                }
            }
        }
        System.out.println(methods);
        if (!methods.isEmpty()) {
            System.out.println(methods.size() + " methods will be used as entry points");
            EvalHelper.setActualMethodCount(methods.size());
            return methods;
        }
        System.out.println("no entry methods found to start");
        return Collections.EMPTY_LIST;
    }

    public Set<Pair<String, Integer>> getResult(Object analysis, SootMethod method) {
        //SootMethod m = getEntryPointMethod();
        Map<DFF, Integer> res = null;
        Set<Pair<String, Integer>> result = new HashSet<>();
        if (analysis instanceof JimpleIDESolver) {
            JimpleIDESolver solver = (JimpleIDESolver) analysis;
            res = (Map<DFF, Integer>) solver.resultsAt(method.getActiveBody().getUnits().getLast());
            defaultPropCount += solver.propagationCount;
        }
        for (Map.Entry<DFF, Integer> e : res.entrySet()) {
            Pair<String, Integer> pair = new Pair<>(e.getKey().toString(), e.getValue());
            result.add(pair);
        }
        return result;
    }

}

