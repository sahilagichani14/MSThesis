package constantpropagation;

public class Array3 {

    /**
     * Load and store between different arrays
     */
    public void entryPoint() {
        int[] A = new int[10];
        int[] B = {100,200,400};
        A[0] = B[0];
        A[1] = B[1];
        A[2] = B[2];
    }

}
