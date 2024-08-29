package constantpropagation;

public class Branching3 {

    /**
     * different values in different branches used in the end
     * @param p unknown
     */
    void entryPoint(boolean p) {
        int a = 0;
        if (p) {
            a = 23;
        } else {
            a = 42;
        }
        int c = a;
    }

}
