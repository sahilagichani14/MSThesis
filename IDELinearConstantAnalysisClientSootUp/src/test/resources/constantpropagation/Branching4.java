package constantpropagation;

public class Branching4 {

    /**
     * different values in different branches not used in the end
     * @param p unknown
     */
    void entryPoint(boolean p) {
        int a = 0;
        int b = 10;
        if (p) {
            a = 23;
        } else {
            a = 42;
        }
        int c = b;
    }

}
