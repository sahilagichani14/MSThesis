package constantpropagation;

public class NonLinear2 {

    /**
     * A typical hashcode calculation case
     */
    public int entryPoint(int n) {
        int hashCode = 1;
        for (int i = 0; i < n; i++) {
            hashCode = 31 * hashCode + hashCode(n);
            hashCode = ~~hashCode;
        }
        return hashCode;
    }

    int hashCode(int i){
        return i;
    }

}
