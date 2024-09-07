package constantpropagation;

public class Array5 {

    /**
     * Array large index
     */
    public void entryPoint() {
        int[] A = new int[1000];
        int[] B = new int[1000];
        A[999] = 100;
        B[42] = A[999];
        int a = B[42];
    }

}
