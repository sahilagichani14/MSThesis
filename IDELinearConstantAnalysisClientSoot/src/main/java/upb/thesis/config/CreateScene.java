package upb.thesis.config;

import soot.G;
import soot.Scene;
import soot.SootClass;
import soot.options.Options;

public class CreateScene {
    private static Scene scene;

    public CreateScene() {
    }

    public static Scene getSootScene(String classPath, String testClassName) {
        return setupSoot(testClassName, classPath);
    }

    private static Scene setupSoot(String targetTestClassName, String classPath) {
        G.reset();
        Options.v().set_soot_classpath(classPath);
        Options.v().set_whole_program(true);
        Options.v().setPhaseOption("cg.cha", "on");
        Options.v().setPhaseOption("cg", "all-reachable:true");
        Options.v().set_no_bodies_for_excluded(true);
        Options.v().set_allow_phantom_refs(true);
        Options.v().setPhaseOption("jb", "use-original-names:true");
        Options.v().setPhaseOption("jb.ls", "enabled:false");
        Options.v().set_prepend_classpath(false);
        Scene.v().addBasicClass("java.lang.StringBuilder");
        SootClass c = Scene.v().forceResolve(targetTestClassName, 3);
        if (c != null) {
            c.setApplicationClass();
        }

        Scene.v().loadNecessaryClasses();
        return Scene.v();
    }
}
