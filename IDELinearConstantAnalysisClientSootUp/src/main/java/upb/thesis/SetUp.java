package upb.thesis;

import com.google.common.base.Stopwatch;
import heros.solver.Pair;
import sootup.analysis.interprocedural.icfg.JimpleBasedInterproceduralCFG;
import sootup.callgraph.CallGraph;
import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.core.jimple.basic.Value;
import sootup.core.jimple.common.constant.IntConstant;
import sootup.core.jimple.common.stmt.JAssignStmt;
import sootup.core.jimple.common.stmt.Stmt;
import sootup.core.model.Body;
import sootup.core.model.SootClass;
import sootup.core.model.SootMethod;
import sootup.core.model.SourceType;
import sootup.core.signatures.MethodSignature;
import sootup.core.transform.BodyInterceptor;
import sootup.core.transform.BodyInterceptorMetric;
import sootup.core.transform.RunTimeBodyInterceptor;
import sootup.core.views.View;
import sootup.interceptors.*;
import sootup.java.bytecode.frontend.inputlocation.JavaClassPathAnalysisInputLocation;
import sootup.java.core.JavaIdentifierFactory;
import sootup.java.core.views.JavaView;
import upb.thesis.config.*;
import upb.thesis.constantpropagation.ConstantValue;
import upb.thesis.constantpropagation.IDEConstantPropagationProblem;
import upb.thesis.solver.JimpleIDESolver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SetUp {

    private static upb.thesis.solver.JimpleIDESolver<?, ?, ?> solver;

    private static List<SootMethod> ideCPEntryMethods;
    private static List<SootMethod> cgEntryMethods;

    public static long defaultPropCount = 0;

    public CallGraph generatedcallGraph;

    protected void executeStaticAnalysis(String jarPath) {
        setupSootUp(jarPath);
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

    protected BodyInterceptor configureBodyInterceptors(Main.BodyInterceptor var1) {
        BodyInterceptor var2 = null;
        switch (var1) {
            case JB_LS:
                //Local Splitter
                var2 = new LocalSplitter();
                break;
            case JB_ESE:
                //Empty Switch Eliminator
                var2 = new EmptySwitchEliminator();
                break;
            case JB_LP:
                //Local Packer
                var2 = new LocalPacker();
                break;
            case JB_NE:
                //Nop Eliminator
                var2 = new NopEliminator();
                break;
            case JB_DAE:
                //Dead Assignment Eliminator
                var2 = new DeadAssignmentEliminator();
                break;
            case JB_ULE:
                //Unused Local Eliminator
                var2 = new UnusedLocalEliminator();
                break;
            case JB_CP:
                //Copy Propagator
                var2 = new CopyPropagator();
                break;
            case JB_UCE:
                //Unreachable Code Eliminator
                var2 = new UnreachableCodeEliminator();
                break;
            case JB_TR:
                //Type Assigner
                var2 = new TypeAssigner();
                break;
            case JB_TT:
                //Trap Tightener
                var2 = new TrapTightener();
                break;
            case JB_LNS:
                //LocalName Standardizer
                var2 = new LocalNameStandardizer();
                break;
            case JB_CBF:
                //Conditional Branch Folder
                var2 = new ConditionalBranchFolder();
                break;
            case JB_DTR:
                //Duplicate CatchAll Trap Remover
                //var2 = DuplicateCatchAllTrapRemover.v();
                break;
            case JB_SILS:
                //Shared Initialization Local Splitter
                //var2 = SharedInitializationLocalSplitter.v();
                break;
            case JB_A:
                //Jimple Local Aggregator
                var2 = new Aggregator();
                break;
            case JB_ULP:
                //Unsplit Originals Local Packer
                // SAME ????
                //var2 = LocalPacker.v();
                break;
            case JB_CP_ULE:
                //Post-copy propagation Unused Local Eliminator
                // SAME ????
                //var2 = UnusedLocalEliminator.v();
                break;
            default:
                throw new RuntimeException("Invalid Body Interceptor");
        }
        return var2;
    }

    private BodyInterceptor createAnalysisTransformer1(Main.BodyInterceptor bodyInterceptor) {
        BodyInterceptor configuredBodyTransformer = configureBodyInterceptors(bodyInterceptor);
        return configuredBodyTransformer;
    }

    /*
     * This method provides the options to soot to analyse the respecive
     * classes.
     */
    private void setupSootUp(String jarPath) {
        String sootCp = jarPath + File.pathSeparator + "lib" + File.separator + "rt.jar";
        List<Main.BodyInterceptor> bodyInterceptors = EvalHelper.getBodyInterceptors();
        List<BodyInterceptor> appliedBIList = new ArrayList<>();
        if (bodyInterceptors == null || bodyInterceptors.isEmpty()){
            appliedBIList = Collections.emptyList();
        } else {
            for (Main.BodyInterceptor x : bodyInterceptors) {
                BodyInterceptor bodyInterceptor = createAnalysisTransformer1(x);
                appliedBIList.add(bodyInterceptor);
            }
        }
        List<RunTimeBodyInterceptor> runTimeBodyInterceptorsList = new ArrayList<>();
        for (BodyInterceptor bodyInterceptor : appliedBIList) {
            RunTimeBodyInterceptor runTimeBodyInterceptor = new RunTimeBodyInterceptor(bodyInterceptor);
            runTimeBodyInterceptorsList.add(runTimeBodyInterceptor);
        }
        AnalysisInputLocation inputLocation = new JavaClassPathAnalysisInputLocation(jarPath, SourceType.Library, Collections.unmodifiableList(runTimeBodyInterceptorsList));
        View view = new JavaView(List.of(inputLocation));
        AtomicInteger stmtCountAfterApplyingBI = new AtomicInteger();
        view.getClasses().forEach(clazz -> {
            clazz.getMethods().forEach(method -> {
                if (method.isConcrete()) {
                    stmtCountAfterApplyingBI.addAndGet(method.getBody().getStmts().size());
                }
            });
        });

        Map<String, List<Long>> bodyInterceptorMetrics = EvalHelper.getBodyInterceptorMetrics();
        runTimeBodyInterceptorsList.forEach(
                runTimeBodyInterceptor -> {
                    BodyInterceptorMetric biMetric = runTimeBodyInterceptor.getBiMetric();
                    List<Long> runtimeBIList = new LinkedList<>();
                    runtimeBIList.add(0, biMetric.getRuntime());
                    runtimeBIList.add(1, biMetric.getMemoryUsage());
                    bodyInterceptorMetrics.putIfAbsent(runTimeBodyInterceptor.getBodyInterceptor().toString(), runtimeBIList);
                    System.out.println(
                            runTimeBodyInterceptor.getBodyInterceptor()
                                    + " took "
                                    + biMetric.getRuntime()
                                    + " ms.");
                    System.out.println(
                            runTimeBodyInterceptor.getBodyInterceptor()
                                    + " consumed "
                                    + biMetric.getMemoryUsage()
                                    + " MB.");
                });
        EvalHelper.setBodyInterceptorMetrics(bodyInterceptorMetrics);

        EvalHelper.setStmtCountAfterApplyingBI(stmtCountAfterApplyingBI.get());

        ideCPEntryMethods = getIDECPEntryPointMethods(view);
        cgEntryMethods = getCGEntryPointMethods(view);

        /*
        File file = new File("./IDELinearConstantAnalysisClientSootUp/results/test.csv");
        if(!file.exists()){
            try (FileWriter writer = new FileWriter(file, true)) {
                StringBuilder str = new StringBuilder();
                for (SootMethod sm: cgEntryMethods){
                    str.append(sm.getSignature());
                    str.append(System.lineSeparator());
                }
                writer.write(str.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
         */

        //System.out.println(ideCPEntryMethods);

        try {
            Stopwatch var1 = Stopwatch.createStarted();
            CallGraphMetricsWrapper var2 = CallGraphApplication.generateCallGraph(view, this.constructCallGraphConfig());
            EvalHelper.setCg_construction_duration(var1.elapsed(TimeUnit.MILLISECONDS));
            generatedcallGraph = var2.getCallGraph();
            // int noOfReachableNodes = calNumOfReachableNodes(generatedcallGraph);
            int noOfReachableNodes = 0;
            EvalHelper.setNumber_of_cg_Edges(var2.getCallGraph().callCount());
            EvalHelper.setNumber_of_reachable_methods(noOfReachableNodes);
            System.out.println("Number of CallGraph edges: " + var2.getCallGraph().callCount());
        } catch (Exception var3) {
            var3.printStackTrace();
            System.exit(1);
        }

        /*
        view.getClasses().forEach(clazz -> {
            clazz.getMethods().forEach(entryMethod -> {
                if (entryMethod.isConcrete()) {
                    JimpleBasedInterproceduralCFG icfg =
                            new JimpleBasedInterproceduralCFG(view, entryMethod.getSignature(), false, false);
                    IDEConstantPropagationProblem problem1 = new IDEConstantPropagationProblem(icfg, entryMethod);
                    upb.thesis.solver.JimpleIDESolver<?, ?, ?> solver1 = new upb.thesis.solver.JimpleIDESolver<>(problem1);
                    solver1.solve();
                    solver = solver1;
                    solver1.addFinalResults(entryMethod.getSignature().toString());
                    Set<Pair<String, String>> pairs = getResult(solver1, entryMethod);
                    pairs.forEach(System.out::println);
                }
            });
            if (solver!=null) {
                solver.dumpResults(EvalHelper.getTargetName());
            }
        });
         */

        List<MethodSignature> cgEntryPointsMethodSignatures = cgEntryMethods.stream().map(sootMethod -> sootMethod.getSignature()).toList();
        JimpleBasedInterproceduralCFG icfg = new JimpleBasedInterproceduralCFG(generatedcallGraph, view, cgEntryPointsMethodSignatures, false, false);
        for (SootMethod method : ideCPEntryMethods) {
            System.out.println("started solving from: " + method.getSignature());
            IDEConstantPropagationProblem problem = new IDEConstantPropagationProblem(icfg, method);
            upb.thesis.solver.JimpleIDESolver<?, ?, ?> mSolver = new upb.thesis.solver.JimpleIDESolver<>(problem);
            mSolver.solve();
            solver = mSolver;
            mSolver.addFinalResults(method.getSignature());
            Set<Pair<String, String>> pairs = getResult(mSolver, method);
            //pairs.forEach(System.out::println);
        }
        if (solver != null) {
            solver.dumpResults(EvalHelper.getTargetName());
        }

    }

    private int calNumOfReachableNodes(CallGraph generatedcallGraph) {
        Set<MethodSignature> reachableNodes = new HashSet<>();
        List<MethodSignature> entryMethods = generatedcallGraph.getEntryMethods();

        for (MethodSignature startingNode: entryMethods){
            // Stack to implement DFS
            Deque<MethodSignature> stack = new ArrayDeque<>();
            // add all entryMethods as reachableNodes
            stack.push(startingNode);
            // Traverse the call graph using DFS
            while (!stack.isEmpty()) {
                MethodSignature currentMethod = stack.pop();
                // If the method has already been visited, skip it
                if (!reachableNodes.add(currentMethod)) {
                    continue;
                }
                // Get the successors (i.e., called methods) of the current method
                // Set<MethodSignature> successors = generatedcallGraph.callTargetsFrom(currentMethod);
                Set<CallGraph.Call> successors = generatedcallGraph.callsFrom(currentMethod);

                // Push the successors into the stack
                for (CallGraph.Call successor : successors) {
                    if (!reachableNodes.contains(successor.getTargetMethodSignature())) {
                        stack.push(successor.getTargetMethodSignature());
                    }
                }
            }
        }
        return reachableNodes.size();
    }


    private boolean isPublicAPI(SootMethod method) {
        return !method.isStatic() && method.isPublic() && !method.isAbstract() && !method.isNative();
    }

    public static List<SootMethod> getIDECPEntryPointMethods(View view) {
        List<SootMethod> methods = new ArrayList<>();
        Set<SootClass> classes = new HashSet<>();
        classes.addAll(view.getClasses().toList());
        l1:
        for (SootClass c : classes) {
            for (SootMethod m : c.getMethods()) {
                if (m.isConcrete() && m.isPublic() && !m.isNative() && !m.isAbstract()){
                    Body body = m.getBody();
                    List<Stmt> stmts = m.getBody().getStmts();
                    for (Stmt stmt: stmts) {
                        if (stmt instanceof JAssignStmt) {
                            JAssignStmt definitionStmt = (JAssignStmt) stmt;
                            Value rightOp = definitionStmt.getRightOp();
                            if (rightOp instanceof IntConstant) {
                                methods.add(m);
                                if (methods.size() == Main.maxMethodSize) {
                                    break l1;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!methods.isEmpty()) {
            System.out.println(methods.size() + " methods will be used as entry points for IDE LCP");
            Main.maxMethodSize = methods.size();
            return methods;
        }
        System.out.println("no entry methods found to start");
        return Collections.EMPTY_LIST;
    }

    public static List<SootMethod> getCGEntryPointMethods(View view) {
        List<SootMethod> methods = new ArrayList<>();
        Set<SootClass> classes = new HashSet<>();
        classes.addAll(view.getClasses().toList());
        JavaIdentifierFactory identifierFactory = JavaIdentifierFactory.getInstance();
        l1:
        for (SootClass c : classes) {
            for (SootMethod m : c.getMethods()) {
//                if (m.isMain(identifierFactory)){
//                    methods.add(m);
//                }
                if (m.isConcrete()){
                    methods.add(m);
                    if (methods.size() == Main.maxMethodSize) {
                        break l1;
                    }
                }
            }
        }
        if (!methods.isEmpty()) {
            System.out.println(methods.size() + " methods will be used as entry points for cg");
            Main.maxMethodSize = methods.size();
            return methods;
        }
        System.out.println("no entry methods found to start");
        return Collections.EMPTY_LIST;
    }

    public static Set<Pair<String, String>> getResult(Object analysis, SootMethod entryMethod) {
        Map<Value, ConstantValue> res = null;
        Set<Pair<String, String>> result = new HashSet<>();
        if (analysis instanceof upb.thesis.solver.JimpleIDESolver) {
            JimpleIDESolver solver = (upb.thesis.solver.JimpleIDESolver) analysis;
            List<Stmt> stmts = entryMethod.getBody().getStmts();
            res = (Map<Value, ConstantValue>) solver.resultsAt(stmts.get(stmts.size() - 1));
            defaultPropCount += solver.propagationCount;
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

    /*
        ClassType javaClassType = view.getIdentifierFactory().getClassType(targetClassName, packageName);
        String entryMethodName = "entryPoint";
        if (view.getClass(javaClassType).isPresent()) {
            SootClass sc = view.getClass(javaClassType).get();
            SootMethod entryMethod = sc.getMethods().stream()
                    .filter(e -> e.getName().equals(entryMethodName))
                    .findFirst()
                    .get();
            assert entryMethod != null;
            MethodSignature methodSignature = entryMethod.getSignature();
            JimpleBasedInterproceduralCFG icfg =
                    new JimpleBasedInterproceduralCFG(view, methodSignature, false, false);
            IDEConstantPropagationProblem problem1 = new IDEConstantPropagationProblem(icfg, entryMethod);
            JimpleIDESolver<?, InterproceduralCFG<Stmt, SootMethod>, ?> solver1 = new JimpleIDESolver(problem1);
            solver1.solve();
            Set<Pair<String, String>> pairs = getResult(solver1, entryMethod);
            pairs.forEach(System.out::println);
           }
     */

}

