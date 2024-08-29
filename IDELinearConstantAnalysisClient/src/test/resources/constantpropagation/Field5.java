package constantpropagation;

public class Field5 {

    int x;
    int y;

    /**
     * field stored with field load
     */
    public void entryPoint() {
        Field5 field = new Field5();
        field.x = 100;
        field.y = field.x;
    }

}
