package RQ1.jb_ls.tc2;

public class Main {
    public static void main(String[] args) {
        TC2 tc1 = new TC2();
        int i = tc1.tc2_1(Integer.min(4, 76));
        int i1 = tc1.tc2_1(5);
        int i2 = tc1.tc2_1(0);
        System.out.println(i);
        System.out.println(i1);
        System.out.println(i2);
    }
}