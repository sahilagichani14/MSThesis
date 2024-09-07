package constantpropagation;

public class NonLinear {

    /**
     * We treat as id given such stmts: b = a + x
     */
    public int entryPoint(int x) {
        int a = 1;
        int b = a + x;
        return b;
    }

}
