package constantpropagation;

public class Loop3 {

    public void entryPoint() {
        int sum = 1;
        while (true) {
            sum = sum + 1;
        }
    }

}
