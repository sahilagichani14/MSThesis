package upb.thesis;

import com.google.common.base.Stopwatch;
import heros.InterproceduralCFG;
import heros.solver.Pair;
import sootup.analysis.interprocedural.icfg.JimpleBasedInterproceduralCFG;
import sootup.analysis.interprocedural.ide.JimpleIDESolver;
import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.core.jimple.basic.Value;
import sootup.core.jimple.common.constant.IntConstant;
import sootup.core.jimple.common.stmt.JAssignStmt;
import sootup.core.jimple.common.stmt.Stmt;
import sootup.core.model.Body;
import sootup.core.model.SootClass;
import sootup.core.model.SootMethod;
import sootup.core.model.SourceType;
import sootup.core.transform.BodyInterceptor;
import sootup.core.views.View;
import sootup.java.bytecode.inputlocation.DefaultRTJarAnalysisInputLocation;
import sootup.java.bytecode.inputlocation.JavaClassPathAnalysisInputLocation;
import sootup.java.core.interceptors.*;
import sootup.java.core.views.JavaView;
import upb.thesis.config.CallGraphAlgorithm;
import upb.thesis.config.CallGraphApplication;
import upb.thesis.config.CallGraphConfig;
import upb.thesis.config.CallGraphMetricsWrapper;
import upb.thesis.constantpropagation.ConstantValue;
import upb.thesis.constantpropagation.IDEConstantPropagationProblem;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class SetUp {

    private static upb.thesis.solver.JimpleIDESolver<?, ?, ?> solver;

    private static List<SootMethod> entryMethods;

    public static long defaultPropCount = 0;

    protected void executeStaticAnalysis(String jarPath) {
        setupSoot(jarPath);
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
    private void setupSoot(String jarPath) {
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
        AnalysisInputLocation inputLocation = new JavaClassPathAnalysisInputLocation(jarPath, SourceType.Application, appliedBIList);
        View view = new JavaView(List.of(inputLocation));
        entryMethods = getEntryPointMethods(view);
        System.out.println(entryMethods);

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

        for (SootMethod method : entryMethods) {
            JimpleBasedInterproceduralCFG icfg = new JimpleBasedInterproceduralCFG(view, method.getSignature(), false, false);
            System.out.println("started solving from: " + method.getSignature());
            IDEConstantPropagationProblem problem = new IDEConstantPropagationProblem(icfg, method);
            upb.thesis.solver.JimpleIDESolver<?, ?, ?> mSolver = new upb.thesis.solver.JimpleIDESolver<>(problem);
            mSolver.solve();
            solver = mSolver;
            mSolver.addFinalResults(method.getSignature());
            Set<Pair<String, String>> pairs = getResult(mSolver, method);
            pairs.forEach(System.out::println);
        }
        if (solver != null) {
            solver.dumpResults(EvalHelper.getTargetName());
        }

        try {
            Stopwatch var1 = Stopwatch.createStarted();
            CallGraphMetricsWrapper var2 = CallGraphApplication.generateCallGraph(view, this.constructCallGraphConfig());
            EvalHelper.setCg_construction_duration(var1.elapsed(TimeUnit.MILLISECONDS));
            EvalHelper.setNumber_of_cg_Edges(var2.getCallGraph().callCount());
            EvalHelper.setNumber_of_reachable_methods(var2.getCallGraph().getMethodSignatures().size());
            System.out.println("Number of CallGraph edges: " + var2.getCallGraph().callCount());
        } catch (Exception var3) {
            var3.printStackTrace();
            System.exit(1);
        }

    }


    private boolean isPublicAPI(SootMethod method) {
        return !method.isStatic() && method.isPublic() && !method.isAbstract() && !method.isNative();
    }

    public static List<SootMethod> getEntryPointMethods(View view) {
        List<SootMethod> methods = new ArrayList<>();
        Set<SootClass> classes = new HashSet<>();
        classes.addAll(view.getClasses().toList());
        l1:
        for (SootClass c : classes) {
            for (SootMethod m : c.getMethods()) {
                if (m.isConcrete()){
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
            System.out.println(methods.size() + " methods will be used as entry points");
            Main.maxMethodSize = methods.size();
            return methods;
        }
        System.out.println("no entry methods found to start");
        return Collections.EMPTY_LIST;
    }

    public static Set<Pair<String, String>> getResult(Object analysis, SootMethod entryMethod) {
        Map<Value, ConstantValue> res = null;
        Set<Pair<String, String>> result = new HashSet<>();
        if (analysis instanceof JimpleIDESolver<?,?,?>) {
            JimpleIDESolver solver = (JimpleIDESolver) analysis;
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
