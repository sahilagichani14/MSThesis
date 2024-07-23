package soot.RQ1.jb_ls;

import soot.*;
import soot.baf.BafASMBackend;
import soot.options.Options;

import java.io.*;
import java.lang.invoke.StringConcatFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseSetup {

    public void executeStaticAnalysis(List<String> targetTestClassName) throws IOException {
        setupSoot(targetTestClassName);
        executeSootTransformers(targetTestClassName);
    }

    private void executeSootTransformers(List<String> targetTestClassNames) throws IOException {
        // This will run the intra-procedural analysis
        PackManager.v().runPacks();
        for (SootClass targetSootClass: Scene.v().getApplicationClasses()) {
            jimpleClassOutput(targetSootClass);
            //byteCodeFromJimpleOutput(targetSootClass);
        }
    }

    private void jimpleClassOutput(SootClass targetSootClass) throws IOException {
        String fileName = SourceLocator.v().getFileNameFor(targetSootClass, Options.output_format_jimple);

//        int insertPosition = fileName.lastIndexOf('\\');
//        String newFileName = fileName.substring(0, insertPosition) + "\\" + "RQ1" + "\\" + "jb_ls" + "\\" + packageName + "\\" + fileName.substring(insertPosition + 1);
//        Path directoryPath = Paths.get(directoryName);
//        Files.createDirectories(directoryPath);

        String directoryPath = "sootOutput/sootjimplesrc/";
        // Create the directory if it does not exist
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean dirCreated = directory.mkdirs();
            if (!dirCreated) {
                System.err.println("Failed to create directory: " + directoryPath);
                return;
            }
        }

        // Create and write to the file
        String finalFileName = "RQ1.jb_ls." + targetSootClass.getName() + ".jimple";
        File file = new File(directory, finalFileName);
        try (OutputStream streamOut = new FileOutputStream(file);
             PrintWriter writerOut = new PrintWriter(new OutputStreamWriter(streamOut))) {
            Printer.v().printTo(targetSootClass, writerOut);
            writerOut.flush();
            //System.out.println("File written successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private void byteCodeFromJimpleOutput(SootClass targetSootClass) throws IOException {
        int java_version = Options.v().java_version();
        //SootClass sootClass = Scene.v().getSootClass(targetTestClassName);
        //SootClass sootClassUnsafe = Scene.v().getSootClassUnsafe(targetTestClassName, false);
        String fileName = SourceLocator.v().getFileNameFor(targetSootClass, Options.output_format_class);
        int insertPosition = fileName.indexOf('\\');
        String newFileName = fileName.substring(0, insertPosition) + "\\" + "RQ1" + "\\" + "jb_ls" + "\\" + fileName.substring(insertPosition + 1);
        OutputStream streamOut1 = new FileOutputStream(newFileName);
        BafASMBackend backend = new BafASMBackend(targetSootClass, java_version);
        backend.generateClassFile(streamOut1);
        streamOut1.close();
    }

    /*
     * This method provides the options to soot to analyze the respective classes.
     */
    protected static void setupSoot(List<String> targetTestClassNames) {
        G.reset();
        String userdir = System.getProperty("user.dir");
        //String sootCp = userdir + File.separator + "target" + File.separator + "test-classes"+ File.pathSeparator + "lib" + File.separator + "rt.jar";
        //String sootCp = userdir + File.separator + "sootThesis" + File.separator + "target" + File.separator + "classes" + File.pathSeparator + userdir + File.separator + "sootThesis" + File.separator + "lib" + File.separator + "rt.jar";

        String tcpath = userdir + File.separator + "test-case-resources" + File.separator + "target" + File.separator + "classes" + File.separator + "RQ1" + File.separator + "jb_ls";
        String rtjarpath = userdir + File.separator + "sootThesis" + File.separator + "lib" + File.separator + "rt.jar";
        String sootCp1 = tcpath + File.pathSeparator + rtjarpath;
        Options.v().set_process_dir(Arrays.asList(tcpath));


        Options.v().set_num_threads(1);
        //Options.v().set_soot_classpath(sootCp1);
        Options.v().set_output_format(Options.output_format_jimple);

        // for test case 17 using Strings
        Options.v().set_prepend_classpath(true);
        Options.v().set_allow_phantom_refs(true);
        //Options.v().set_whole_program(true);
        //Scene.v().addBasicClass(StringConcatFactory.class.getName(), SootClass.SIGNATURES);
        Scene.v().loadClassAndSupport(StringConcatFactory.class.getName());

        List<String> list = new ArrayList<>();
        list.add("jb.ls");
        Options.v().set_dump_body(list);

        Options.v().setPhaseOption("bb", "enabled:false"); //bafBody bb bydefault calls bb.lp, bb.ule, bb.ne so disabled

        //Options.v().setPhaseOption("jb", "use-original-names:true");
        //Options.v().setPhaseOption("jb.ls", "use-original-names:true");

        //disable all jb body transformers
        Options.v().setPhaseOption("jb.dtr", "enabled:false"); //Duplicate CatchAll Trap Remover
        Options.v().setPhaseOption("jb.ese", "enabled:false"); //Empty Switch Eliminator
        Options.v().setPhaseOption("jb.ls", "enabled:true");//LocalSplitter
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

//        for (String targetTestClassName: targetTestClassNames) {
//            SootClass c = Scene.v().forceResolve(targetTestClassName, SootClass.BODIES);
//            if (c != null)
//                c.setApplicationClass();
//        }
        Scene.v().loadNecessaryClasses();
    }
}
