package upb.thesis.config;

import soot.Scene;
import soot.jimple.toolkits.callgraph.CallGraph;

public class SceneWrapper {
    private static volatile SceneWrapper instance = null;
    private final Scene sootScene;

    public static SceneWrapper v() {
        if (instance != null) {
            return instance;
        } else {
            throw new RuntimeException("Please Initialize SceneWrapper with a Soot Scene");
        }
    }

    public static SceneWrapper v(Scene sootScene) {
        if (instance == null) {
            Class var1 = SceneWrapper.class;
            synchronized(SceneWrapper.class) {
                if (instance == null) {
                    instance = new SceneWrapper(sootScene);
                }
            }
        }

        return instance;
    }

    private SceneWrapper(Scene scene) {
        this.sootScene = scene;
    }

    public CallGraph getCallGraph() {
        return this.sootScene.getCallGraph();
    }

    public Scene getScene() {
        return this.sootScene;
    }
}
