package constantpropagation;

public class Loop4 {

    public void entryPoint(boolean p) {
        int sum = 1;
        while (p) {
            sum = sum + 1;
        }
    }

}
