package constantpropagation;

public class Loop {

    public void entryPoint() {
        int sum = 1;
        for (int a = 0; a < 5; ++a) {
            sum = sum + 1;
        }
    }

}
