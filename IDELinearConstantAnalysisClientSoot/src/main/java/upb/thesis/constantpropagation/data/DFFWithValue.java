package upb.thesis.constantpropagation.data;

import soot.SootField;
import soot.Unit;
import soot.Value;

import java.util.List;
import java.util.logging.Logger;

/**
 * Represents a Data Flow Fact (an access path) with value
 */
public class DFFWithValue<T> extends DFF {

    private final static Logger LOGGER = Logger.getLogger(DFFWithValue.class.getName());

    private T payload;

    public DFFWithValue(Value value, Unit generatedAt, T payload) {
        super(value, generatedAt);
        this.payload = payload;
    }

    public DFFWithValue(Value base, Unit generatedAt, List<SootField> fields, T payload) {
        super(base, generatedAt, fields);
        this.payload = payload;
    }

    public boolean equalsWithValue(Object obj) {
        boolean eq = super.equals(obj);
        DFFWithValue dff = (DFFWithValue) obj;
        return eq && this.payload.equals(dff.payload);
    }
}
