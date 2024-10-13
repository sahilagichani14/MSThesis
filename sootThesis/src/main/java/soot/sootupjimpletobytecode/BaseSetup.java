package soot.sootupjimpletobytecode;

import soot.*;
import soot.baf.BafASMBackend;
import soot.options.Options;
import soot.util.Chain;

import java.io.*;
import java.lang.invoke.StringConcatFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BaseSetup {

    protected static void setupSoot() throws IOException {

        String userdir = System.getProperty("user.dir");
        String jimpleFilesSrcPath = userdir + File.separator + "jimplesrc" + File.separator + "sootupjimplesrc";
        String rtjarpath = userdir + File.separator + "sootThesis" + File.separator + "lib" + File.separator + "rt.jar";

        /*
        // configure Soot
        G.reset();
        soot.options.Options.v().set_num_threads(1);
        soot.options.Options.v().set_debug(false);
        soot.options.Options.v().set_debug_resolver(true);
        soot.options.Options.v().set_prepend_classpath(true);
        soot.options.Options.v().set_keep_line_number(true);
        soot.options.Options.v().set_output_format(soot.options.Options.output_format_none);
        soot.options.Options.v().set_allow_phantom_refs(true);
        soot.options.Options.v().set_omit_excepting_unit_edges(true);
        Options.v().set_app(true);
        Options.v().set_src_prec(Options.src_prec_jimple);// Set the source precedence to Jimple or 3
        Options.v().set_output_format(Options.output_format_class); // Set output format to bytecode
        soot.options.Options.v().set_process_dir(Collections.singletonList(jimpleFilesSrcPath));
        Scene.v().loadNecessaryClasses();
        Chain<SootClass> classes = Scene.v().getApplicationClasses();
         */

        G.reset();
        Options.v().set_num_threads(1);

        Options.v().set_prepend_classpath(true);
        Options.v().set_allow_phantom_refs(true);
        Options.v().set_process_dir(Arrays.asList(jimpleFilesSrcPath));
        Options.v().set_src_prec(Options.src_prec_jimple);// Set the source precedence to Jimple or 3
        Options.v().set_output_format(Options.output_format_class); // Set output format to bytecode


        // for test case which use Strings
        Scene.v().loadClassAndSupport(StringConcatFactory.class.getName());
        Scene.v().loadClassAndSupport("java.lang.Object");
        Scene.v().loadClassAndSupport("java.lang.System");

        //Options.v().set_time(true);
        List<String> list = new ArrayList<>();
        //list.add("jb.ls");
        Options.v().set_dump_body(list);
        //Options.v().set_allow_phantom_refs(true);
        //List<String> excluded = Options.v().exclude();
        //Options.v().set_whole_program(true);
        //Scene.v().addBasicClass(java.lang.ThreadGroup.class.getName() ,SootClass.BODIES);
        //Options.v().set_allow_phantom_refs(true);
        //Options.v().set_no_bodies_for_excluded(true);
        //Options.v().set_include_all(true);
        //System.out.println(Options.v().getPhaseList());

        //List<String> classesUnder = SourceLocator.v().getClassesUnder(jimpleFilesSrcPath);
        //Options.v().set_oaat(true);
        //Options.v().set_full_resolver(true);
        //Options.v().set_app(true); //Run in application mode

        Options.v().setPhaseOption("bb", "enabled:false"); //bafBody bb bydefault calls bb.lp, bb.ule, bb.ne so disabled

        //Options.v().setPhaseOption("jb.ls", "use-original-names:true");
        //disable all jb body transformers
        {
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
            Options.v().setPhaseOption("jb.uce", "enabled:false"); // UnreachableCodeEliminator
            Options.v().setPhaseOption("jb.tt", "enabled:false"); //Trap Tightener
            Options.v().setPhaseOption("jb.cbf", "enabled:false"); // ConditionalBranchFolder
        }

        Scene.v().loadNecessaryClasses();
        PackManager.v().runPacks();
        List<SootClass> sootClassList = Scene.v().getClasses().stream().filter(sootClass -> sootClass.getName().contains("RQ1")).collect(Collectors.toList());

//        Chain<SootClass> applicationClasses = Scene.v().getApplicationClasses();
//        Chain<SootClass> phantomClasses = Scene.v().getPhantomClasses();
//        List<SootClass> sootClassesBodies = Scene.v().getClasses(SootClass.BODIES);

        for (SootClass sootClass : sootClassList) {
            //sootClass.setSuperclass(Scene.v().getSootClass("java.lang.Object"));
            //Scene.v().addClass(sootClass);
            //Scene.v().loadClass(sootClass.getName(), SootClass.HIERARCHY);
            //Scene.v().tryLoadClass(sootClass.getName(), SootClass.BODIES);
            //Scene.v().loadClassAndSupport(sootClass.getName());
            //SootClass forceResolvedSootClass = Scene.v().forceResolve(sootClass.getName(), SootClass.BODIES);
            jimpletobytecode(sootClass);
        }
    }

    protected static void jimpletobytecode(SootClass sootClass) throws IOException {
        //Scene.v().loadClassAndSupport("java.lang.Object");
        int java_version = Options.v().java_version();
        String fileName = SourceLocator.v().getFileNameFor(sootClass, Options.output_format_class);

        // Create the directory if it does not exist
        File dir = new File(fileName);
        String directoryPath = dir.getParent();
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean dirCreated = directory.mkdirs();
            if (!dirCreated) {
                System.err.println("Failed to create directory: " + directoryPath);
                return;
            }
        }
        // Create and write to the file
        //File file = new File(directory, fileName);
        try {
            OutputStream streamOut = new FileOutputStream(fileName);
            BafASMBackend backend = new BafASMBackend(sootClass, java_version);
            backend.generateClassFile(streamOut);
            streamOut.close();
            //System.out.println("File written successfully: " + file.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
