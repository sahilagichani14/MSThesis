package upb.thesis.config;

public class CallGraphConstructionMetrics {
    private final String callGraphAlgorithm;
    private final float constructionTime;
    private final int cg_Edges;

    public CallGraphConstructionMetrics(String callGraphAlgorithm, float constructionTime, int cg_Edges) {
        this.callGraphAlgorithm = callGraphAlgorithm;
        this.constructionTime = constructionTime;
        this.cg_Edges = cg_Edges;
    }

    public String getCallGraphAlgorithm() {
        return this.callGraphAlgorithm;
    }

    public float getConstructionTime() {
        return this.constructionTime;
    }

    public int getCg_Edges() {
        return this.cg_Edges;
    }
}
