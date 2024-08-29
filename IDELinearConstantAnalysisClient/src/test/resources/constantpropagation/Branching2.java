package constantpropagation;

public class Branching2 {

    /**
     * same value in different branches not used in the end
     * @param p unknown
     */
    void entryPoint(boolean p) {
        int a = 0;
        int b = 0;
        if (p) {
            b = 42;
        } else {
            b = 42;
        }
        int c = a + 13;
    }

}
