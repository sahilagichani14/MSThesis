package soot.RQ1.jb_ls;

import soot.*;
import soot.jimple.toolkits.scalar.ConditionalBranchFolder;
import soot.options.Options;

import java.io.File;
import java.lang.invoke.StringConcatFactory;
import java.util.ArrayList;
import java.util.List;

public class BaseSetup {

    public void executeStaticAnalysis(List<String> targetTestClassName) {
        setupSoot(targetTestClassName);
        registerSootTransformers();
        executeSootTransformers();
    }

    private void executeSootTransformers() {
        // This will run the intra-procedural analysis
        PackManager.v().runPacks();
        //PackManager.v().runBodyPacks();
        //PackManager.v().getPack("jb").remove("jb");

		/*
		Target name of the class file
		Not all the times the queried class exists in Scene. If not, the result of getSootClass can return a phantom class or throws an exception according to the Soot options. A phantom object is created artificially by Soot to continue the analysis even if some information is missing. This option is quite useful when you are only interested only in the application classes (under analysis) and not other libraries.
		*/

        /*
        SootClass sootClass_af = Scene.v().getSootClass("upb.thesis.RQ1.JB_CBF.JB_CBF");
        SootClass sootClassUnsafe_af = Scene.v().getSootClassUnsafe("upb.thesis.RQ1.JB_CBF.JB_CBF", false);
        if (sootClassUnsafe_af == null) {
            System.out.println("No such class found");
        }
        //Chain<SootClass> sootClasses = Scene.v().getClasses();
        //System.out.println(sootClasses);

        List<SootMethod> sootClassMethods_af = sootClass_af.getMethods();
        for (SootMethod sootMethod : sootClassMethods_af) {
            System.out.println(sootMethod.getActiveBody());
        }
         */

    }

    private void registerSootTransformers() {
        // add the analysis to jimple body pack (jb)
        //Transform transform = new Transform("jb.analysis", createAnalysisTransformer());
        //PackManager.v().getPack("jb").add(transform);
    }

    protected Transformer createAnalysisTransformer() {
        return ConditionalBranchFolder.v();
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

        //Options.v().set_time(true);
        List<String> list = new ArrayList<>();
        list.add("jb.ls");
        //list.add("jb.lp");
        //list.add("jb.ne");
        //list.add("jj.ne");
        Options.v().set_dump_body(list);
        //Options.v().set_allow_phantom_refs(true);
        //List<String> excluded = Options.v().exclude();
        //Options.v().set_whole_program(true);
        //Options.v().set_allow_phantom_refs(true);
        //Options.v().set_no_bodies_for_excluded(true);
        //Options.v().set_include_all(true);
        //System.out.println(Options.v().getPhaseList());

        Options.v().setPhaseOption("bb", "enabled:false"); //bafBody bb bydefault calls bb.lp, bb.ule, bb.ne so disabled
        //Disable all packs
        /*
        Options.v().setPhaseOption("jb", "enabled:false");
        Options.v().setPhaseOption("jj", "enabled:false");
        Options.v().setPhaseOption("wjpp", "enabled:false");
        Options.v().setPhaseOption("wspp", "enabled:false");
        Options.v().setPhaseOption("wstp", "enabled:false");
        Options.v().setPhaseOption("wsop", "enabled:false");
        Options.v().setPhaseOption("wjtp", "enabled:false");
        Options.v().setPhaseOption("wjop", "enabled:false");
        Options.v().setPhaseOption("wjap", "enabled:false");
        Options.v().setPhaseOption("stp", "enabled:false");
        Options.v().setPhaseOption("sop", "enabled:false");
        Options.v().setPhaseOption("jtp", "enabled:false");
        Options.v().setPhaseOption("jop", "enabled:false");
        Options.v().setPhaseOption("gb", "enabled:false");
        Options.v().setPhaseOption("gop", "enabled:false");
        Options.v().setPhaseOption("sop", "enabled:false");
        Options.v().setPhaseOption("bb", "enabled:false");
        Options.v().setPhaseOption("bop", "enabled:false");
        Options.v().setPhaseOption("tag", "enabled:false");
        Options.v().setPhaseOption("db", "enabled:false");
         */
        //Options.v().setPhaseOption("jb", "use-original-names:true");
        //Options.v().setPhaseOption("jb.ls", "use-original-names:true");

        //disable all jb body transformers
        Options.v().setPhaseOption("jb.dtr", "enabled:false"); //Duplicate CatchAll Trap Remover
        Options.v().setPhaseOption("jb.ese", "enabled:false"); //Empty Switch Eliminator
        Options.v().setPhaseOption("jb.ls", "enabled:true");//LocalSplitter
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

        /*
        //disable all jj body transformers
        Options.v().setPhaseOption("jj.ls", "enabled:false");
        Options.v().setPhaseOption("jj.sils", "enabled:false");
        Options.v().setPhaseOption("jj.a", "enabled:false");
        Options.v().setPhaseOption("jj.ule", "enabled:false");
        Options.v().setPhaseOption("jj.tr", "enabled:false");
        Options.v().setPhaseOption("jj.ulp", "enabled:false");
        Options.v().setPhaseOption("jj.lns", "enabled:false");
        Options.v().setPhaseOption("jj.cp", "enabled:false");
        Options.v().setPhaseOption("jj.dae", "enabled:false");
        Options.v().setPhaseOption("jj.cp-ule", "enabled:false");
        Options.v().setPhaseOption("jj.lp", "enabled:false");
        Options.v().setPhaseOption("jj.ne", "enabled:false");
        Options.v().setPhaseOption("jj.uce", "enabled:false");
        */

        //Options.v().setPhaseOption("jj", "use-original-names:true");
        for (String targetTestClassName: targetTestClassNames) {
            SootClass c = Scene.v().forceResolve(targetTestClassName, SootClass.BODIES);
            if (c != null)
                c.setApplicationClass();
        }
        Scene.v().loadNecessaryClasses();
    }

}
