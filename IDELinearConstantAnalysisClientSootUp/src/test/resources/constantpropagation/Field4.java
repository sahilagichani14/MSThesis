package constantpropagation;

public class Field4 {

    int x;
    int y;

    /**
     * field stored with binop
     */
    public void entryPoint() {
        Field4 field = new Field4();
        int a = 100;
        field.x = a + 1;
    }

}
