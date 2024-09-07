package constantpropagation;

public class Array4 {

    /**
     * Aliased Arrays
     */
    public void entryPoint() {
        int[] A = new int[10];
        int[] B = A;
        A[0] = 100;
        A[1] = 200;
        A[2] = 400;
    }

}
