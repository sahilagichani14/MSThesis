package soot.RQ1.jb_uce;

import soot.*;
import soot.options.Options;

import java.io.*;
import java.lang.invoke.StringConcatFactory;
import java.util.ArrayList;
import java.util.List;

public class BaseSetup {

    public void executeStaticAnalysis(List<String> targetTestClassName) throws FileNotFoundException {
        setupSoot(targetTestClassName);
        executeSootTransformers(targetTestClassName);
    }

    private void executeSootTransformers(List<String> targetTestClassNames) throws FileNotFoundException {
        // This will run the intra-procedural analysis
        PackManager.v().runPacks();
        for (String targetTestClassName: targetTestClassNames){
            //jimpleClassOutput(targetTestClassName);
        }

    }

    private void jimpleClassOutput(String targetTestClassName) throws FileNotFoundException {
        SootClass sootClass = Scene.v().getSootClass(targetTestClassName);
        SootClass sootClassUnsafe = Scene.v().getSootClassUnsafe(targetTestClassName, false);
        String fileName = SourceLocator.v().getFileNameFor(sootClass, Options.output_format_jimple);
        OutputStream streamOut = new FileOutputStream(fileName);
        PrintWriter writerOut = new PrintWriter(new OutputStreamWriter(streamOut));
        Printer.v().printTo(sootClass, writerOut);
        writerOut.flush();
    }


    /*
     * This method provides the options to soot to analyze the respective classes.
     */
    protected static void setupSoot(List<String> targetTestClassNames) {
        G.reset();
        String userdir = System.getProperty("user.dir");
        //String sootCp = userdir + File.separator + "target" + File.separator + "test-classes"+ File.pathSeparator + "lib" + File.separator + "rt.jar";
        String sootCp = userdir + File.separator + "sootThesis" + File.separator + "target" + File.separator + "classes" + File.pathSeparator + userdir + File.separator + "sootThesis" + File.separator + "lib" + File.separator + "rt.jar";
        Options.v().set_num_threads(1);
        Options.v().set_soot_classpath(sootCp);

        // for test case 17 using Strings
        Options.v().set_prepend_classpath(true);
        Options.v().set_allow_phantom_refs(true);
        //Options.v().set_whole_program(true);
        //Scene.v().addBasicClass(StringConcatFactory.class.getName(), SootClass.SIGNATURES);
        Scene.v().loadClassAndSupport(StringConcatFactory.class.getName());

        List<String> list = new ArrayList<>();
        list.add("jb.uce");
        Options.v().set_dump_body(list);

        Options.v().setPhaseOption("bb", "enabled:false"); //bafBody bb bydefault calls bb.lp, bb.ule, bb.ne so disabled
        
        //Options.v().setPhaseOption("jb", "use-original-names:true");
        //Options.v().setPhaseOption("jb.ls", "use-original-names:true");

        //disable all jb body transformers
        Options.v().setPhaseOption("jb.dtr", "enabled:false"); //Duplicate CatchAll Trap Remover
        Options.v().setPhaseOption("jb.ese", "enabled:false"); //Empty Switch Eliminator
        Options.v().setPhaseOption("jb.ls", "enabled:false");//LocalSplitter
        Options.v().setPhaseOption("jb.sils", "enabled:false"); //Shared Initialization Local Splitter
        Options.v().setPhaseOption("jb.a", "enabled:false"); //Aggregator
        Options.v().setPhaseOption("jb.ule", "enabled:false"); //Unused Local Eliminator
        Options.v().setPhaseOption("jb.tr", "enabled:false"); //Type Assigner
        Options.v().setPhaseOption("jb.ulp", "enabled:false"); //Unsplit-originals Local    Packer
        Options.v().setPhaseOption("jb.lns", "enabled:false"); //Local Name Standardizer
        Options.v().setPhaseOption("jb.cp", "enabled:false"); // CopyPropagator
        Options.v().setPhaseOption("jb.dae", "enabled:false"); // DeadAssignmentEliminator
        Options.v().setPhaseOption("jb.cp-ule", "enabled:false"); // UnusedLocalEliminator
        Options.v().setPhaseOption("jb.lp", "enabled:false"); //Local Packer
        Options.v().setPhaseOption("jb.ne", "enabled:false"); //No operation Eliminator
        Options.v().setPhaseOption("jb.uce", "enabled:true"); // UnreachableCodeEliminator
        Options.v().setPhaseOption("jb.tt", "enabled:false"); //Trap Tightener
        Options.v().setPhaseOption("jb.cbf", "enabled:false"); // ConditionalBranchFolder

        for (String targetTestClassName: targetTestClassNames) {
            SootClass c = Scene.v().forceResolve(targetTestClassName, SootClass.BODIES);
            if (c != null)
                c.setApplicationClass();
        }
        Scene.v().loadNecessaryClasses();
    }
}
