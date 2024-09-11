package upb.thesis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class EvalPrinter {
    private static final String OUT_PUT_DIR = "./IDELinearConstantAnalysisClientSootUp/results";
    private static final String FILE = "sootUp_ide_eval.csv";

    private final String targetProgram;
    private final String solver;
    private int threadCount = 0;
    private long totalDuration = 0;
    private long totalPropagationCount = 0;
    private int methodCount = 0;
    private long callGraphConstructionTime = 0;
    private int callGraphReachableNodes = 0;
    private int callGraphEdges = 0;
    private int initialStmtCount = 0;
    private int stmtCountAfterApplyingBI = 0;

    private String callgraphAlgo;

    private List<Main.BodyInterceptor> bodyInterceptors;


    public EvalPrinter(String solver) {
        this.solver = solver;
        this.targetProgram = EvalHelper.getTargetName();
        this.threadCount = EvalHelper.getThreadCount();
        this.totalDuration = EvalHelper.getTotalDuration();
        this.totalPropagationCount = EvalHelper.getTotalPropagationCount();
        this.methodCount = EvalHelper.getActualMethodCount();
        this.callgraphAlgo = EvalHelper.getCallgraphAlgorithm().name();
        this.bodyInterceptors = EvalHelper.getBodyInterceptors();
        this.callGraphConstructionTime = EvalHelper.getCg_construction_duration();
        this.callGraphEdges = EvalHelper.getNumber_of_cg_Edges();
        this.callGraphReachableNodes = EvalHelper.getNumber_of_reachable_methods();
        this.initialStmtCount = EvalHelper.getInitialStmtCount();
        this.stmtCountAfterApplyingBI = EvalHelper.getStmtCountAfterApplyingBI();
    }

    public void generate() {
        File dir = new File(OUT_PUT_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(OUT_PUT_DIR + File.separator + FILE);
        if(!file.exists()){
            try (FileWriter writer = new FileWriter(file)) {
                StringBuilder str = new StringBuilder();
                str.append("jar");
                str.append(",");
                str.append("solver");
                str.append(",");
                str.append("thread");
                str.append(",");
                str.append("totalRuntime");
                str.append(",");
                str.append("cgConstructionTime");
                str.append(",");
                str.append("prop");
                str.append(",");
                str.append("method");
                str.append(",");
                str.append("mem");
                str.append(",");
                str.append("CallGraphAlgo");
                str.append(",");
                str.append("callGraphEdges");
                str.append(",");
                str.append("callGraphReachableNodes");
                str.append(",");
                str.append("initialStmtCount");
                str.append(",");
                str.append("stmtCountAfterApplyingBI");
                str.append(",");
                str.append("BodyInterceptors");
                str.append(System.lineSeparator());
                writer.write(str.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileWriter writer = new FileWriter(file, true)) {
            StringBuilder str = new StringBuilder();
            str.append(targetProgram);
            str.append(",");
            str.append(solver);
            str.append(",");
            str.append(threadCount);
            str.append(",");
            str.append(totalDuration);
            str.append(",");
            str.append(callGraphConstructionTime);
            str.append(",");
            str.append(totalPropagationCount);
            str.append(",");
            str.append(methodCount);
            str.append(",");
            str.append(getMemoryUsage());
            str.append(",");
            str.append(callgraphAlgo);
            str.append(",");
            str.append(callGraphEdges);
            str.append(",");
            str.append(callGraphReachableNodes);
            str.append(",");
            str.append(initialStmtCount);
            str.append(",");
            str.append(stmtCountAfterApplyingBI);
            str.append(",");
            str.append(bodyInterceptors);
            str.append(System.lineSeparator());
            writer.write(str.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * in MB
     * @return
     */
    private static int getMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        return Math.round((runtime.totalMemory() - runtime.freeMemory()) / (1024*1024));
    }


}
