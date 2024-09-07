package constantpropagation;

public class Branching {

    /**
     * Same value in different branches used in the end
     * @param p unknown
     */
    void entryPoint(boolean p) {
        int a = 0;
        int b = 1;
        if (p) {
            a = 10;
        } else {
            a = 10;
        }
        int c = a + 4;
    }

}
