package constantpropagation;

public class Field2 {

    int x;
    int y;

    /**
     * field stored to local
     */
    public void entryPoint() {
        Field2 field = new Field2();
        field.x = 100;
        int a = field.x;
    }

}
