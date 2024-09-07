package constantpropagation;

public class Branching6 {

    /**
     * same values in different branches meet with linear op
     * @param p unknown
     */
    void entryPoint(boolean p) {
        int a = 0;
        int b = 10;
        if (p) {
            a = 23;
        } else {
            a = 23;
        }
        int c = a + 3;
    }

}
