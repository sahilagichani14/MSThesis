package constantpropagation;

public class Field3 {

    int x;
    int y;

    /**
     * field store with constant via aliasing
     */
    public void entryPoint() {
        Field3 field = new Field3();
        Field3 alias = field;
        field.x = 100;
        int a = alias.x;
    }

}
