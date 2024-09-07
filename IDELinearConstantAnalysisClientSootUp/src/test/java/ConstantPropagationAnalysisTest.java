import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import heros.InterproceduralCFG;
import heros.solver.Pair;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import sootup.analysis.interprocedural.icfg.JimpleBasedInterproceduralCFG;
import sootup.analysis.interprocedural.ide.JimpleIDESolver;
import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.core.jimple.basic.Value;
import sootup.core.jimple.common.stmt.Stmt;
import sootup.core.model.SootClass;
import sootup.core.model.SootMethod;
import sootup.core.model.SourceType;
import sootup.core.signatures.MethodSignature;
import sootup.java.bytecode.inputlocation.JavaClassPathAnalysisInputLocation;
import sootup.java.core.types.JavaClassType;
import sootup.java.core.views.JavaView;
import upb.thesis.Utils;
import upb.thesis.constantpropagation.ConstantValue;
import upb.thesis.constantpropagation.IDEConstantPropagationProblem;

/** Constant propagation analysis using the Heros and SootUp frameworks. */
public class ConstantPropagationAnalysisTest {

  protected JavaView view;
  protected SootMethod entryMethod;
  private static JimpleIDESolver<?, InterproceduralCFG<Stmt, SootMethod>, ?> solved1 = null;

  /**
   * Executes static analysis on the given Java file.
   *
   * @param workingDirectory the directory containing the Java files
   * @param javaFilePath the path to the Java file
   * @param targetTestClassName the name of the class to analyze
   * @param packageName the package name of the class
   * @param entryMethodName the name of the entry method
   * @return a {@link ReturnWrapper} containing the analysis results
   */
  protected ReturnWrapper executeStaticAnalysis(
      String workingDirectory,
      Path javaFilePath,
      String targetTestClassName,
      String packageName,
      String entryMethodName) {
    setupSootUp(workingDirectory, javaFilePath);
    SootMethod method = extracted(targetTestClassName, packageName, entryMethodName);
    assert method != null;
    runAnalysis(method);
    if (solved1 == null) {
      throw new NullPointerException("Something went wrong solving the IDE problem!");
    }

    return new ReturnWrapper(solved1);
  }

  /** Wrapper class for the results of the static analysis. */
  class ReturnWrapper {
    JimpleIDESolver<?, InterproceduralCFG<Stmt, SootMethod>, ?> solved1;

    /**
     * Constructs a new {@link ReturnWrapper}.
     *
     * @param solved1 the solver instance
     */
    public ReturnWrapper(
        JimpleIDESolver<?, InterproceduralCFG<Stmt, SootMethod>, ?> solved1) {
      this.solved1 = solved1;
    }
  }

  /**
   * Runs the constant propagation analysis using the given method.
   *
   * @param method the method to analyze
   */
  private void runAnalysis(SootMethod method) {
    MethodSignature methodSignature = method.getSignature();
    JimpleBasedInterproceduralCFG icfg =
        new JimpleBasedInterproceduralCFG(view, methodSignature, false, false);
    IDEConstantPropagationProblem problem1 = new IDEConstantPropagationProblem(icfg, method);
    JimpleIDESolver<?, InterproceduralCFG<Stmt, SootMethod>, ?> solver1 =
        new JimpleIDESolver(problem1);

    solver1.solve();
    solved1 = solver1;
  }

  /**
   * Sets up the SootUp framework for analysis.
   *
   * @param workingDirectory the working directory for analysis
   * @param javaFilePath the path to the Java file
   */
  private void setupSootUp(String workingDirectory, Path javaFilePath) {
    Utils.compileJavaOTF(Path.of(workingDirectory), javaFilePath);

    AnalysisInputLocation inputLocation =
        new JavaClassPathAnalysisInputLocation(workingDirectory, SourceType.Application);
    view = new JavaView(List.of(inputLocation));
  }

  /**
   * Finds the entry method in the target class.
   *
   * @param targetTestClassName the name of the target class
   * @param packageName the package name of the class
   * @param entryMethodName the name of the entry method
   * @return the {@link SootMethod} object for the entry method, or null if not found
   */
  private SootMethod extracted(
      String targetTestClassName, String packageName, String entryMethodName) {
    JavaClassType mainClassSignature =
        view.getIdentifierFactory().getClassType(targetTestClassName, packageName);
    if (view.getClass(mainClassSignature).isPresent()) {
      SootClass sc = view.getClass(mainClassSignature).get();

      entryMethod =
          sc.getMethods().stream()
              .filter(e -> e.getName().equals(entryMethodName))
              .findFirst()
              .get();

      assertNotNull(entryMethod);

      return entryMethod;
    }
    return null;
  }

  /**
   * Extracts and processes the results of the analysis.
   *
   * @param analysis the analysis object
   * @return a set of pairs representing the analysis results
   */
  private Set<Pair<String, String>> getResult(Object analysis) {
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

  /**
   * Sets up the test environment for a specific target class.
   *
   * @param targetClassName the name of the target class
   * @return a {@link ReturnWrapper} containing the analysis results
   */
  public ReturnWrapper setupTest(String targetClassName) {
    String workingDirectory = "src/test/resources";
    String packageName = "constantpropagation";
    Path javaFilePath =
        Paths.get(workingDirectory + "/" + packageName + "/" + targetClassName + ".java");
    return executeStaticAnalysis(
        workingDirectory, javaFilePath, targetClassName, packageName, "entryPoint");
  }

  /**
   * Performs the test by comparing the actual results against the expected results.
   *
   * @param targetClassName the name of the target class
   * @param expected the expected results
   */
  private void performTest(String targetClassName, Set<Pair<String, String>> expected) {
    ReturnWrapper result = setupTest(targetClassName);
    Set<Pair<String, String>> defaultIDEResult = getResult(result.solved1);
    checkResults(defaultIDEResult, expected);
  }

  @Test
  public void testAssignment() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l1", "100"));
    expected.add(new Pair<>("l2", "200"));
    expected.add(new Pair<>("l3", "400"));
    expected.add(new Pair<>("l4", "121"));
    performTest("Assignment", expected);
  }

  @Test
  public void testAssignment2() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l1", "100"));
    expected.add(new Pair<>("l2", "200"));
    expected.add(new Pair<>("l3", "40"));
    performTest("Assignment2", expected);
  }

  @Test
  public void testAssignment3() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l2", "200"));
    expected.add(new Pair<>("l3", "400"));
    performTest("Assignment3", expected);
  }

  @Test
  public void testAssignment4() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l2", "200"));
    expected.add(new Pair<>("l3", "413"));
    expected.add(new Pair<>("$stack4", "400"));
    performTest("Assignment4", expected);
  }

  @Test
  public void testAssignment5() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l1#0", "100"));
    expected.add(new Pair<>("l1#1", "13"));
    expected.add(new Pair<>("l2", "200"));
    performTest("Assignment5", expected);
  }

  @Test
  public void testAssignment6() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l1#1", "101"));
    expected.add(new Pair<>("l2#1", "201"));
    performTest("Assignment6", expected);
  }

  @Test
  public void testAssignment7() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l2", "110"));
    expected.add(new Pair<>("l3", "105"));
    expected.add(new Pair<>("l4", "210"));
    performTest("Assignment7", expected);
  }

  @Test
  public void testAssignment8() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l2", "100"));
    expected.add(new Pair<>("l3", "100"));
    performTest("Assignment8", expected);
  }

  @Test
  public void testAssignment9() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("constantpropagation.Assignment9.a", "100"));
    expected.add(new Pair<>("l1", "100"));
    performTest("Assignment9", expected);
  }

  @Test
  public void testLoop() {
    Set<Pair<String, String>> expected = new HashSet<>();
    performTest("Loop", expected);
  }

  @Test
  public void testLoop2() {
    Set<Pair<String, String>> expected = new HashSet<>();
    performTest("Loop2", expected);
  }

  @Test
  public void testLoop3() {
    Set<Pair<String, String>> expected = new HashSet<>();
    performTest("Loop3", expected);
  }

  @Test
  public void testLoop4() {
    Set<Pair<String, String>> expected = new HashSet<>();
    performTest("Loop4", expected);
  }

  @Test
  public void testLoop5() {
    Set<Pair<String, String>> expected = new HashSet<>();
    performTest("Loop5", expected);
  }

  @Test
  public void testField() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("$stack2.y", "200"));
    expected.add(new Pair<>("$stack2.x", "100"));
    performTest("Field", expected);
  }

  @Test
  public void testField2() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l2", "100"));
    expected.add(new Pair<>("$stack3.x", "100"));
    performTest("Field2", expected);
  }

  @Test
  public void testField3() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l3", "100"));
    expected.add(new Pair<>("$stack4.x", "100"));
    performTest("Field3", expected);
  }

  @Test
  public void testField4() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("$stack3.x", "101"));
    performTest("Field4", expected);
  }

  @Test
  public void testField5() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("$stack2.y", "100"));
    expected.add(new Pair<>("$stack2.x", "100"));
    performTest("Field5", expected);
  }

  @Test
  public void testField6() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("$stack4.x", "101"));
    performTest("Field6", expected);
  }

  @Test
  public void testField7() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l3", "100"));
    expected.add(new Pair<>("$stack4.x", "100"));
    performTest("Field7", expected);
  }

  @Test
  public void testField8() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l3", "100"));
    expected.add(new Pair<>("l4", "200"));
    expected.add(new Pair<>("l5", "100"));
    expected.add(new Pair<>("$stack6.x", "100"));
    expected.add(new Pair<>("$stack6.y", "200"));
    performTest("Field8", expected);
  }

  @Test
  public void testBranching() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l3", "1"));
    expected.add(new Pair<>("l4", "14"));
    expected.add(new Pair<>("l2#0", "0"));
    performTest("Branching", expected);
  }

  @Test
  public void testBranching2() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l3#2", "42"));
    expected.add(new Pair<>("l3#1", "42"));
    expected.add(new Pair<>("l3#0", "0"));
    expected.add(new Pair<>("l2", "0"));
    expected.add(new Pair<>("l4", "13"));
    performTest("Branching2", expected);
  }

  @Test
  public void testBranching3() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l2#0", "0"));
    performTest("Branching3", expected);
  }

  @Test
  public void testBranching4() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l2#0", "0"));
    expected.add(new Pair<>("l2#2", "42"));
    expected.add(new Pair<>("l2#1", "23"));
    expected.add(new Pair<>("l3", "10"));
    expected.add(new Pair<>("l4", "10"));
    performTest("Branching4", expected);
  }

  @Test
  public void testBranching5() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l2#0", "0"));
    expected.add(new Pair<>("l3", "10"));
    performTest("Branching5", expected);
  }

  @Test
  public void testBranching6() {
    String targetClassName = "Branching6";
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l2#0", "0"));
    expected.add(new Pair<>("l3", "10"));
    expected.add(new Pair<>("l4", "26"));
    performTest(targetClassName, expected);
  }

  @Test
  public void testArray() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l1[0]", "100"));
    performTest("Array", expected);
  }

  @Test
  public void testArray2() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l1[0]", "100"));
    expected.add(new Pair<>("l2", "100"));
    performTest("Array2", expected);
  }

  @Test
  public void testArray3() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l1[0]", "100"));
    expected.add(new Pair<>("l1[1]", "200"));
    expected.add(new Pair<>("l1[2]", "400"));
    performTest("Array3", expected);
  }

  @Test
  public void testArray4() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l1[0]", "100"));
    expected.add(new Pair<>("l1[1]", "200"));
    expected.add(new Pair<>("l1[2]", "400"));
    performTest("Array4", expected);
  }

  @Test
  public void testArray5() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l1[999]", "100"));
    expected.add(new Pair<>("l2[42]", "100"));
    expected.add(new Pair<>("l3", "100"));
    performTest("Array5", expected);
  }

  @Test
  public void testContext() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l1", "100"));
    expected.add(new Pair<>("l2", "200"));
    expected.add(new Pair<>("l3", "300"));
    expected.add(new Pair<>("l4", "400"));
    performTest("Context1", expected);
  }

  @Test
  public void testContext2() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l1", "100"));
    expected.add(new Pair<>("l2", "200"));
    expected.add(new Pair<>("l3", "101"));
    expected.add(new Pair<>("l4", "201"));
    performTest("Context2", expected);
  }

  @Test
  public void testContext3() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l1", "100"));
    expected.add(new Pair<>("l2", "200"));
    expected.add(new Pair<>("l3", "113"));
    expected.add(new Pair<>("l4", "55"));
    performTest("Context3", expected);
  }

  @Test
  public void testContext4() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l1", "100"));
    expected.add(new Pair<>("l2", "200"));
    expected.add(new Pair<>("l3", "101"));
    expected.add(new Pair<>("l4", "201"));
    performTest("Context4", expected);
  }

  @Test
  @Disabled("Needs pointer information")
  public void testContext5() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l1", "100"));
    expected.add(new Pair<>("l3", "100"));
    expected.add(new Pair<>("l4", "200"));
    performTest("Context5", expected);
  }

  @Test
  @Disabled("Needs pointer information")
  public void testContext6() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l1", "100"));
    performTest("Context6", expected);
  }

  @Test
  public void testNonLinear() {
    Set<Pair<String, String>> expected = new HashSet<>();
    performTest("NonLinear", expected);
  }

  @Test
  public void testNonLinear2() {
    Set<Pair<String, String>> expected = new HashSet<>();
    performTest("NonLinear2", expected);
  }

  @Test
  public void testSequence() {
    Set<Pair<String, String>> expected = new HashSet<>();
    expected.add(new Pair<>("l2", "1"));
    expected.add(new Pair<>("l4", "3"));
    performTest("Sequence", expected);
  }

  /**
   * Checks if the actual results match the expected results.
   *
   * @param defaultIDEResult the actual results
   * @param expected the expected results
   */
  private void checkResults(
      Set<Pair<String, String>> defaultIDEResult, Set<Pair<String, String>> expected) {
    defaultIDEResult.forEach(System.out::println);
    assertTrue(defaultIDEResult.containsAll(expected));
  }
}
