package sootUp.RQ1.jb_cp;

public class JB_CP {

    private int field = 5;
    private static int field1 = 5;

    public static void main(String[] args) {
        JB_CP obj = new JB_CP();
        System.out.println("tc1: " + obj.tc1());
        System.out.println("tc1_1: " + obj.tc1_1("example"));
        System.out.println("tc2: " + obj.tc2(5));
        System.out.println("tc3: " + obj.tc3(5));
        System.out.println("tc4: " + obj.tc4());
        System.out.println("tc5: " + obj.tc5(5));
        System.out.println("tc5_1: " + obj.tc5_1(7));
        System.out.println("tc5_2: " + obj.tc5_2(7));
        System.out.println("tc5_3: " + obj.tc5_3(6,7));
        System.out.println("tc6: " + obj.tc6(19));
        System.out.println("tc7: " + obj.tc7(12));
        System.out.println("tc7_1: " + obj.tc7_1(22));
        System.out.println("tc7_2: " + obj.tc7_2(10));
        System.out.println("tc8: " + obj.tc8(33));
        System.out.println("tc9: " + obj.tc9());
        System.out.println("tc10: " + obj.tc10());
        System.out.println("tc11: " + obj.tc11(31));
        System.out.println("tc12: " + obj.tc12(1));
        System.out.println("tc13: " + obj.tc13());
        System.out.println("tc13_1: " + obj.tc13_1());
        System.out.println("tc14: " + obj.tc14());
        System.out.println("tc15: " + obj.tc15());
        System.out.println("tc16: " + obj.tc16());
        System.out.println("tc17: " + obj.tc17());
        System.out.println("tc17_1: " + obj.tc17_1());
        System.out.println("tc17_2: " + obj.tc17_2());
        System.out.println("tc18: " + obj.tc18());
        System.out.println("tc18_1: " + obj.tc18_1());
        System.out.println("tc19: " + obj.tc19());
        System.out.println("tc20: " + obj.tc20(4));
        System.out.println("tc20_1: " + obj.tc20_1());
        System.out.println("tc21: " + obj.tc21());
        System.out.println("tc21_1: " + obj.tc21_1());
        System.out.println("tc22: " + obj.tc22(true));
        System.out.println("tc23: " + obj.tc23(false));
        System.out.println("tc24: " + obj.tc24());
        System.out.println("tc25: " + obj.tc25());
        System.out.println("tc26: " + obj.tc26());
        System.out.println("tc27: " + obj.tc27());
        System.out.println("tc28: " + obj.tc28(5));
        System.out.println("tc29: " + obj.tc29());
        System.out.println("tc30: " + obj.tc30(5));
    }

    int tc1() {
        int a = 5;
        int b = a;
        System.out.println(b);
        return b;
    }

    String tc1_1(String x){
        int a = 6, b;
        b = a;
        if (x.length() > 10){
            b = 10;
        }
        a = 10;
        a = b;
        System.out.println(a);
        System.out.println(b);
        return " " + a;
    }

    int tc2(int c) {
        int a = 5;
        int b = a;
        c = c + b;
        System.out.println(c);
        System.out.println(b);
        return c + a;
    }

    int tc3(int b) {
        int a = 5;
        b = a + 1;
        System.out.println(b);
        return b;
    }

    int tc4() {
        int a = getValue();
        int b = a;
        System.out.println(b);
        return b;
    }

    private int getValue() {
        return 5;
    }

    int tc5(int a) {
        a = 5;
        int b;
        if (a > 3) {
            b = a;
        } else {
            b = 2;
        }
        System.out.println(b);
        return b + a;
    }

    int tc5_1(int x) {
        int a = 5;
        if (x > 6) {
            a = 10;
        }
        int b;
        if (a > 3) {
            b = a;
        } else {
            b = 2;
        }
        System.out.println(b);
        return b + x;
    }

    int tc5_2(int x) {
        int a = 5;
        int b = 12;
        if (x > 6){
            a = 10;
            b = 25;
        }
        if (a + b < 3) {
            b = a;
        } else {
            b = 2;
        }
        System.out.println(b);
        return a + b;
    }

    int tc5_3(int a, int b) {
        if (false) {
            a = 10;
            b = 25;
        }
        if (10 > 5){
            a = 5;
            b = 3;
        }
        if (a + b > 30) {
            b = a;
        } else {
            b = 2;
        }
        System.out.println(b);
        return a + b;
    }

    int tc6(int b) {
        int a = 5;
        for (int i = 0; i < 10; i++) {
            b = a;
            System.out.println(b);
        }
        return b;
    }

    int tc7(int b) {
        int a = 5;
        {
            b = a;
            System.out.println(b);
        }
        return a + b;
    }

    int tc7_1(int x) {
        int a = 5;
        {
            a = 50;
        }
        {
            int b = a;
            int c = b + a + x;
            System.out.println(b);
            System.out.println(c);
            System.out.println(a+b);
        }
        return x;
    }

    int tc7_2(int a) {
        {
            {
                int b = a;
                a = 10;
                System.out.println(b);
                System.out.println(a);
            }
            {
                int b = 10;
                System.out.println(b);
            }
            System.out.println(a);
        }
        return a;
    }

    double tc8(double b) {
        int a = 5;
        b = (double) a;
        System.out.println(b);
        return b;
    }

    Object tc9() {
        Object a = null;
        Object b = a;
        System.out.println(b);
        return b;
    }

    int tc10() {
        int a = 5;
        int b = 6;
        int c = (a > b) ? a : b;
        System.out.println(c);
        return c;
    }

    int tc11(int b) {
        int a = 5;
        b = a;
        a = 6;
        System.out.println(b);
        return a + b;
    }

    long tc12(long b) {
        int a = 5;
        b = a;
        System.out.println(b);
        return 5l + b;
    }


    int tc13() {
        int a = field;
        int b = a;
        System.out.println(b);
        return b;
    }

    long tc13_1() {
        int a = field;
        long b = (long) a;
        System.out.println(b);
        return a + b;
    }

    int tc14() {
        int[] array = {1, 2, 3};
        int a = array[0];
        int b = a;
        System.out.println(b);
        return b;
    }

    int tc15() {
        int a = field1;
        int b = a;
        System.out.println(b);
        return b;
    }

    int tc16() {
        JB_CP obj = new JB_CP();
        int a = obj.field;
        int b = a;
        System.out.println(b);
        return b;
    }

    int tc17() {
        final int a = 5;
        int b = a;
        System.out.println(b);
        return b;
    }

    int tc17_1() {
        int a = 5, c = 6;
        int b = 10 + a + c;
        System.out.println(b + c);
        return b + c;
    }

    int tc17_2() {
        int a = 5, c = 6;
        int b = 10 + a + c;
        if(a + c == 11){
            System.out.println(b);
        }
        System.out.println(b + c);
        return b+c;
    }

    int tc18() {
        int a = 5;
        try {
            int b = a;
            System.out.println(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

    int tc18_1() {
        int a = 5;
        int b, c;
        try {
            b = a;
            c = 15;
            System.out.println(b);
        } catch (Exception e) {
            b = 5;
            c = b;
        }
        System.out.println(c);
        return c;
    }

    private int anotherMethod(int val) {
        return val + 1;
    }

    int tc19() {
        int a = getValue();
        int b = a;
        int c = anotherMethod(b);
        System.out.println(c);
        return c;
    }

    int tc20(int b) {
        int a = 5;
        b = 10;
        switch (a){
            case 5: b = a;
        }
        System.out.println(b);
        return b;
    }

    int tc20_1() {
        int a = 5;
        int b = 10;
        switch (a){
            case 5: b = b;
        }
        System.out.println(b);
        return a + b;
    }

    int tc21() {
        int a = 5;
        int b = 10;
        switch (a + b){
            case 5: b = a;
                a = 15;
                System.out.println(b);
        }
        System.out.println(b);
        return a + b;
    }

    int tc21_1() {
        int a = 5;
        int b = 10;
        switch (a + b){
            case 4: a = 20;
            case 5: b = a;
                System.out.println(b);
        }
        System.out.println(b);
        return b;
    }

    Boolean tc22(boolean b){
        boolean a = true;
        b = a;
        Boolean c = b && a;
        boolean d = c;
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        return b;
    }

    boolean tc23(boolean b){
        boolean a = true;
        b = a;
        if (b == true? true:false){
            Boolean c = b && a;
            boolean d = c;
            c = false;
            System.out.println(b);
            System.out.println(c);
            System.out.println(d);
        }
        return b;
    }

    int tc24(){
        int l1 = 5;
        int l2 = l1;
        l2 = methodWith4Parameters(4, l2, l2, l2);
        return l2;
    }

    int methodWith4Parameters(int a, int b, int c, int d){
        System.out.println(a + " " + b + " " + c + " " + d + " ");
        return a + b + c + d;
    }

    int tc25(){
        int l1 = 1;
        int l2 = 3;
        int[] array = {9, 8, 7, 25};
        array [0] = l1;
        l1 = l2;
        array[l1] = l2;
        return array[l2];
    }

    int tc26(){
        Integer inte = Integer.valueOf(6);
        int l1 = inte;
        int l2 = 10;
        l2 = Integer.valueOf(l2);
        l1 = l1;
        System.out.println(l2);
        return l1;
    }

    int tc27(){
        int l0, l1, l2, l3, l4;
        l0 = Integer.valueOf(10);
        l3 = Integer.valueOf(20);

        l1 = l0;
        l2 = Integer.valueOf(l1);
        l1 = l3;
        l3 = 10;
        l4 = Integer.valueOf(l1);
        return l4;
    }

    Integer tc28(int l4){
        int l0, l1, l2, l3;
        l0 = Integer.valueOf(10);
        l3 = Integer.valueOf(20);

        l1 = l0;
        l2 = Integer.valueOf(l1);
        l1 = l3;
        if (l0 >= l4) {
            l3 = 10;
        }
        l4 = Integer.valueOf(l1);
        return l4;
    }

    Integer tc29(){
        int l0, l1, l2, l3, l4;
        l1 = 1;
        l2 = Integer.valueOf(l1);
        l1 = 2;
        l1 = l2;
        l4 = Integer.valueOf(l1);
        return l4;
    }

    int tc30(int w) {
        int y, z = 0;
        y = w;
        w = 10;
        z = 20;
        int x = y + z;
        System.out.println("if y changed to w at last step then value of x is diff");
        return x;
    }

    class MyObj{
        MyObj getCheck(MyObj obj){
            obj = new MyObj();
            obj.getCheck(obj);
            return obj;
        }
    }

}
