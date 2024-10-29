import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class EvaluationRunnerParallel {
    // private static final String INPUT_DIR = Paths.get("IDELinearConstantAnalysisClientSoot/src/test/resources/latest").toString();
    // private static final String EXECUTABLE = Paths.get("eval_results/IDELinearConstantAnalysisClientSoot-1.0-SNAPSHOT-jar-with-dependencies.jar").toString();
    private static final String INPUT_DIR = Paths.get("IDELinearConstantAnalysisClientSootUp/src/test/resources/latest").toString();
    private static final String EXECUTABLE = Paths.get("eval_results/IDELinearConstantAnalysisClientSootUp-1.0-SNAPSHOT-jar-with-dependencies.jar").toString();
    private static final String MAX_METHOD = "50";
    private static final List<String> THREADS = List.of("1");
    private static final List<String> SOLVERS = List.of("default");
    private static final List<String> MANDATORY_BODY_INTERCEPTORS = List.of("jb.ls", "jb.tr");
    private static final List<String> ALLOWED_CONFIGURATIONS = List.of("CHA", "RTA");
    private static List<String> bodyInterceptors = new ArrayList<>();
    private static List<String> specificCGAlgo = null;
    private static int numberOfIterations;
    public static File outputFile = new File("console_output.txt");

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Execute all files in the directory? (y/n): ");
        boolean executeAll = scanner.nextLine().equalsIgnoreCase("y");

        List<String> selectedFiles = executeAll ? setup() : selectFiles();

        System.out.println("Enter the number of iterations of the experiment: ");
        numberOfIterations = Integer.parseInt(scanner.nextLine());

        System.out.println("Do you need to use any specific CG Algorithm? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            System.out.println("Do you need to use all the allowed configurations? (y/n): ");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                specificCGAlgo = new ArrayList<>(ALLOWED_CONFIGURATIONS);
            } else {
                System.out.println("Enter the specific CG algorithms you want to use (comma-separated): ");
                specificCGAlgo = Arrays.stream(scanner.nextLine().split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());
            }
        } else {
            specificCGAlgo = new ArrayList<>(ALLOWED_CONFIGURATIONS);
        }

        bodyInterceptors = generatePermutations();
        bodyInterceptors = List.of(bodyInterceptors.get(0));
        System.out.println("Total permutations: " + bodyInterceptors.size());

        // Parallelize with a larger pool based on system cores
        ExecutorService executor = Executors.newFixedThreadPool(Math.max(2, Runtime.getRuntime().availableProcessors()));

        List<Future<?>> futures = new ArrayList<>();
        for (String file : selectedFiles) {
            for (String t : THREADS) {
                for (String s : SOLVERS) {
                    for (int i = 0; i < numberOfIterations; i++) {
                        for (String cgAlgo : specificCGAlgo) {
                            for (String appliedBodyInterceptor : bodyInterceptors) {
                                String[] command = constructCommand(file, s, cgAlgo, t, appliedBodyInterceptor);
                                futures.add(executor.submit(() -> runCommand(command, outputFile)));
                            }
                        }
                    }
                }
            }
        }

        // Await task completion with a timeout for each
        for (Future<?> future : futures) {
            try {
                future.get(20, TimeUnit.MINUTES);
            } catch (TimeoutException e) {
                System.out.println("Task exceeded 20 minutes and was skipped.");
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.HOURS)) {
            System.out.println("Some tasks did not finish within the timeout period.");
            executor.shutdownNow();
        }
    }

    private static void runCommand(String[] command, File outputFile) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(outputFile));
            processBuilder.redirectError(ProcessBuilder.Redirect.appendTo(outputFile));
            Process process = processBuilder.start();

            if (!process.waitFor(20, TimeUnit.MINUTES)) {
                System.out.println("Execution timed out for command: " + String.join(" ", command));
                process.destroyForcibly();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static List<String> setup() {
        File inputDir = new File(INPUT_DIR);
        return Arrays.stream(inputDir.listFiles())
                .filter(file -> file.getName().endsWith(".jar") && file.isFile())
                .map(File::getName)
                .sorted()
                .collect(Collectors.toList());
    }

    private static List<String> selectFiles() {
        Scanner scanner = new Scanner(System.in);
        File inputDir = new File(INPUT_DIR);
        List<File> files = Arrays.asList(Objects.requireNonNull(inputDir.listFiles()));
        for (int i = 0; i < files.size(); i++) {
            System.out.println((i + 1) + ". " + files.get(i).getName());
        }
        System.out.println("Enter the numbers of the files to execute (comma-separated): ");
        return Arrays.stream(scanner.nextLine().split(","))
                .map(num -> files.get(Integer.parseInt(num.trim()) - 1).getName())
                .collect(Collectors.toList());
    }

    private static List<String> generatePermutations() {
        List<String> fixedItems = new ArrayList<>(List.of("jb.ls,jb.tr", "jb.lp,jb.ule", "jb.cp", "jb.dae", "jb.ese", "jb.a", "jb.cbf"));
        List<String> permutations = new ArrayList<>();
        permute(fixedItems, 0, permutations);
        return permutations;
    }

    private static void permute(List<String> arr, int index, List<String> results) {
        if (index >= arr.size() - 1) {
            results.add(String.join(",", arr));
            return;
        }
        for (int i = index; i < arr.size(); i++) {
            Collections.swap(arr, index, i);
            permute(arr, index + 1, results);
            Collections.swap(arr, index, i);
        }
    }

    private static String[] constructCommand(String jar, String solver, String cgAlgo, String thread, String bodyInterceptors) {
        return new String[]{
                "java", "-XX:+UseG1GC", "-XX:+UseAdaptiveSizePolicy", "-Xmx1024m", "-Xss1024m", "-jar",
                EXECUTABLE, jar, solver, MAX_METHOD, cgAlgo, thread, bodyInterceptors
        };
    }
}