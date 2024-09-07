package upb.thesis.constantpropagation.edgefunctions;

import heros.EdgeFunction;
import org.slf4j.Logger;

public abstract class LoggingEdgeFunction<D> implements EdgeFunction<D> {

    protected static Logger log;

    public abstract void setLogger();

    @Override
    public D computeTarget(D d) {
        log.debug("computeTarget: " + d);
        return null;
    }

    @Override
    public EdgeFunction<D> composeWith(EdgeFunction<D> edgeFunction) {
        log.debug("composeWith: " + edgeFunction);
        return null;
    }

    @Override
    public EdgeFunction<D> meetWith(EdgeFunction<D> edgeFunction) {
        log.debug("meetWith: " + edgeFunction);
        return null;
    }

    @Override
    public boolean equalTo(EdgeFunction<D> edgeFunction) {
        log.debug("equalTo: " + edgeFunction);
        return false;
    }
}
