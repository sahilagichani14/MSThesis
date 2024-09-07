package upb.thesis;

import heros.InterproceduralCFG;
import heros.solver.Pair;
import sootup.analysis.interprocedural.icfg.JimpleBasedInterproceduralCFG;
import sootup.analysis.interprocedural.ide.JimpleIDESolver;
import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.core.jimple.basic.Value;
import sootup.core.jimple.common.constant.IntConstant;
import sootup.core.jimple.common.stmt.AbstractDefinitionStmt;
import sootup.core.jimple.common.stmt.JAssignStmt;
import sootup.core.jimple.common.stmt.Stmt;
import sootup.core.model.Body;
import sootup.core.model.SootClass;
import sootup.core.model.SootMethod;
import sootup.core.model.SourceType;
import sootup.core.signatures.MethodSignature;
import sootup.core.types.ClassType;
import sootup.core.views.View;
import sootup.java.bytecode.inputlocation.JavaClassPathAnalysisInputLocation;
import sootup.java.core.types.JavaClassType;
import sootup.java.core.views.JavaView;
import upb.thesis.constantpropagation.ConstantValue;
import upb.thesis.constantpropagation.IDEConstantPropagationProblem;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static int maxMethodSize = 10;

    public static void main(String[] args) {
        Path jarPath = Paths.get(System.getProperty("user.dir") + "/IDELinearConstantAnalysisClientSootUp/src/test/resources/latest/jcl-over-slf4j-2.0.7.jar");
        //Path workingDirectory = Paths.get(System.getProperty("user.dir") + "/IDELinearConstantAnalysisClient/src/test/resources/");
        //String packageName = "constantpropagation";
        //String targetClassName = "Assignment";
        //String classPath = System.getProperty("user.dir") + File.separator + "IDELinearConstantAnalysisClient" + File.separator + "target" + File.separator + "test-classes";
        AnalysisInputLocation inputLocation = new JavaClassPathAnalysisInputLocation(jarPath.toString(), SourceType.Application, Collections.emptyList());
        View view = new JavaView(List.of(inputLocation));
        List<SootMethod> entryPointMethods = getEntryPointMethods(view);
        System.out.println(entryPointMethods);
        view.getClasses().forEach(clazz -> {
            clazz.getMethods().forEach(entryMethod -> {
                if (entryMethod.isConcrete()) {
                    JimpleBasedInterproceduralCFG icfg =
                            new JimpleBasedInterproceduralCFG(view, entryMethod.getSignature(), false, false);
                    IDEConstantPropagationProblem problem1 = new IDEConstantPropagationProblem(icfg, entryMethod);
                    JimpleIDESolver<?, InterproceduralCFG<Stmt, SootMethod>, ?> solver1 = new JimpleIDESolver(problem1);
                    solver1.solve();
                    Set<Pair<String, String>> pairs = getResult(solver1, entryMethod);
                    pairs.forEach(System.out::println);
                }
            });
        });
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
}