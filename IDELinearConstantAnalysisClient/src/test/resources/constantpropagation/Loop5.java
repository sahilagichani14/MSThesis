package constantpropagation;

public class Loop5 {

    public void entryPoint(boolean p, int upperBound) {
        int sum = 1;
        while (p) {
            for (int a = 1; a < upperBound; ++a) {
                sum = sum + 1;
            }
        }
    }

}
