package constantpropagation;

public class Loop2 {

    public void entryPoint(int upperBound) {
        int sum = 0;
        for (int a = 1; a < upperBound; ++a) {
            sum = sum + 1;
        }
    }

}
