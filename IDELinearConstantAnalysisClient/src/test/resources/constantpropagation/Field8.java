package constantpropagation;

public class Field8 {

    int x;
    int y;

    /**
     * field store with local via aliasing
     */
    public void entryPoint() {
        Field8 field = new Field8();
        Field8 alias = field;
        int a = 100;
        field.x = a;
        field.y = 200;
        int b = alias.y;
        int c = alias.x;
    }

}
