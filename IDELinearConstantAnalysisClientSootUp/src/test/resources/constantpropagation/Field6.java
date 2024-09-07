package constantpropagation;

public class Field6 {

    int x;
    int y;

    /**
     * field store with binop via aliasing
     */
    public void entryPoint() {
        Field6 field = new Field6();
        Field6 alias = field;
        int a = 100;
        field.x = a + 1;
    }

}
