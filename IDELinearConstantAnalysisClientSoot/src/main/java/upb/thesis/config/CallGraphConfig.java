package upb.thesis.config;

public class CallGraphConfig {
    private static CallGraphConfig instance;
    private boolean isSootSceneProvided;
    private String APP_PATH;
    private String MAIN_CLASS;
    private String JAR_PATH;
    private boolean single_entry;
    private CallGraphAlgorithm callGraphAlgorithms;

    private CallGraphConfig() {
    }

    public static CallGraphConfig getInstance() {
        if (instance == null) {
            instance = new CallGraphConfig();
        }

        return instance;
    }

    public static void setCallGraphConfigInstance(CallGraphConfig callGraphConfig) {
        instance = callGraphConfig;
    }

    public boolean isIsSootSceneProvided() {
        return this.isSootSceneProvided;
    }

    public void setIsSootSceneProvided(boolean isSootSceneProvided) {
        this.isSootSceneProvided = isSootSceneProvided;
    }

    public String getAppPath() {
        return this.APP_PATH;
    }

    public void setAppPath(String appPath) {
        this.APP_PATH = appPath;
    }

    public String getMainClass() {
        return this.MAIN_CLASS;
    }

    public void setMainClass(String mainClass) {
        this.MAIN_CLASS = mainClass;
    }

    public CallGraphAlgorithm getCallGraphAlgorithm() {
        return this.callGraphAlgorithms;
    }

    public void setCallGraphAlgorithm(CallGraphAlgorithm callGraphAlgorithms) {
        this.callGraphAlgorithms = callGraphAlgorithms;
    }

    public String getJAR_PATH() {
        return this.JAR_PATH;
    }

    public void setJAR_PATH(String JAR_PATH) {
        this.JAR_PATH = JAR_PATH;
    }

    public boolean isSingle_entry() {
        return this.single_entry;
    }

    public void setSingle_entry(boolean single_entry) {
        this.single_entry = single_entry;
    }
}
