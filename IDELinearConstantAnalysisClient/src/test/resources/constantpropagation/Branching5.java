package constantpropagation;

public class Branching5 {

    /**
     * different values in different branches meet with linear op
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
        int c = a + 3;
    }

}
