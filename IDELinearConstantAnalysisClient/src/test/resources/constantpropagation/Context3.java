package constantpropagation;

public class Context3 {

    int add(int a, int b) {
        return a + 13;
    }

    public void entryPoint() {
        int a = 100;
        int b = 200;
        int c = add(a, b);
        int d = add(42, 13);
    }

}
