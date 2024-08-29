package constantpropagation;


public class Context5 {


    void assign(int a, Field f){
        f.x = a;
        f.y = 200;
    }

    /**
     * assign field in call
     */
    public void entryPoint() {
        int a = 100;
        Field field = new Field();
        assign(a, field);
        int b = field.x;
        int c = field.y;
    }

}
