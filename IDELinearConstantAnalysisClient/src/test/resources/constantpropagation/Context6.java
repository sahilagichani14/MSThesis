package constantpropagation;


public class Context6 {

    private static int a;

    void assign(){
        a = 100;
    }

    /**
     * assign static in call
     */
    public void entryPoint() {
        assign();
        int x = a;
    }

}
