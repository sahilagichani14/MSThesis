package constantpropagation;

public class Field7 {

    int x;
    int y;

    /**
     * field store with local via aliasing
     */
    public void entryPoint() {
        Field7 field = new Field7();
        Field7 alias = field;
        int a = 100;
        field.x = a;
    }

}
