package sootUp.RQ1.jb_a;

public class JB_A {
    public void tc1(int d) {
        int a = 5;
        int b = a; // b is simply a copy of a
        int c = b; // c is simply a copy of b
        d = c + 10; // d uses c which uses b which uses a
        System.out.println(d); // Prints the result
    }

    public void tc2(int d) {
        int a = 5;
        int b = a; // b is simply a copy of a
        int c = b; // c is simply a copy of b

        if (d > 10) {
            d = c + 10; // d uses c which uses b which uses a
        } else {
            d = c + 5;
        }
        System.out.println(d); // Prints the result
    }
}
