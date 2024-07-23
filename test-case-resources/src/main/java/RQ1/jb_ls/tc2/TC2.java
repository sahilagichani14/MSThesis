package RQ1.jb_ls.tc2;

public class TC2 {
    public int tc2_1(int a) {
        if (a > 20) {
            a = 10;
        } else {
            a = 20;
        }
        int b = a + 5;
        return b + 2 * a;
    }
}
