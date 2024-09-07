package constantpropagation;

public class Field {

    int x;
    int y;

    /**
     * Field loaded with constant
     */
    public void entryPoint() {
        Field field = new Field();
        field.x = 100;
        field.y = 200;
    }

}
