package RQ1.jb_ls.tc1;

public class TC1 {
    public int tc1_1(int a) {
        if (a > 20) {
            a = 10;
        } else {
            a = 20;
        }
        int b = a + 5;
        return b + 2 * a;
    }
}
