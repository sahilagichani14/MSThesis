package sootUp.RQ1.jb_ls;

public class JB_LS {
    int global = 10;

    public void tc1(int a) {
        if (a > 20) {
            a = 10;
        } else {
            a = 20;
        }
        int b = a + 5;
    }

    private void tc2() {
        int a;
        if (10 > 0) {
            if (20 < 30) {
                a = 10;
            } else {
                a = 20;
            }
        } else {
            a = 30;
        }
        int b = a + 5;
    }

    private void tc2_1(int a) {
        if (a > 0) {
            if (a < 30) {
                a = 10;
            } else {
                a = 20;
            }
            a = 6;
        } else {
            a = 30;
        }
        int b = a + 5;
    }

    private void tc2_2(int a) {
        if (a > 0) {
            if (a < 30) {
                a = 10;
            } else {
                a = 20;
            }
        } else {
            a = 30;
        }
        int b = a + 5;
    }

    protected void tc3() {
        int a = 0;
        for (int i = 0; i < 10; i++) {
            a = i * 2;
        }
        int b = a + 1;
    }

    protected void tc3_1(int a) {
        for (int i = 0; i < 10; i++) {
            a = i * 2;
        }
        int b = a + 1;
    }

    protected void tc3_2(int a) {
        for (int i = 0; i < 10; i++) {
            a = i * 2;
        }
        int b = a + 1;
        b++;
        int c = b;
    }

    static void tc4() {
        int a = 0;
        if (10 > 20) {
            a = 10;
        }
        if (20 > 5) {
            a = 20;
        }
        int b = a + 5;
    }

    static void tc4_1(int a) {
        if (a > 20) {
            a = 10;
        }
        if (a > 5) {
            a = 20;
        }
        int b = a + 5;
    }

    int tc5() {
        int a;
        if (10 > 20) {
            a = 1;
        } else {
            a = 2;
        }
        int b = a + 1;
        a = a + 5;
        if (5 == 6) {
            a = 3;
        }
        int c = a + 2;
        return c;
    }

    boolean tc6() {
        int a = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                a = i + j;
            }
        }
        int b = a + 1;
        return false;
    }

    String tc7() {
        int a = 0;
        for (int i = 0; i < 10; i++) {
            if (a > i) {
                a = i * 2;
            } else if (i < 8) {
                a = i * 3;
            } else {
                a = i * 4;
            }
        }
        int b = a + 1;
        return "Complex";
    }

    void tc8() {
        int a = 4;
        switch (a) {
            case 1:
                a = 10;
                break;
            case 2:
                a = 20;
                break;
            default:
                a = 30;
                break;
        }
        int b = a + 5;
    }

    void tc8_1(int a) {
        switch (a) {
            case 1:
                a = 10;
                break;
            case 2:
                a = 20;
                break;
            default:
                a = 30;
                break;
        }
        int b = a + 5;
    }

    void tc9() {
        int a, b, c;
        if (10 > 20) {
            //a = 10;
        } else {
            a = 5;
            b = 20;
        }
        c = a + b;
    }

    void tc9_1(int a, int b, int c) {
        if (a + b > 20) {
            a = 10;
            c = 5;
        } else {
            a = 5;
            b = 20;
        }
        c = a + b;
    }

    void tc10() {
        int a = 0;
        if (a > 5) {
            a = getValue1();
        } else {
            a = getValue2();
        }
        int b = a + 1;
    }

    void tc10_1(int a) {
        if (a > 5) {
            a = getValue1();
        } else {
            a = getValue2();
        }
        int b = a + 1;
    }

    private int getValue1() {
        return 10;
    }

    private int getValue2() {
        return 20;
    }

    void tc11(int a) {
        int b, c, d;
        if (a > 10) {
            b = 10;
        } else {
            b = 5;
        }
        if (b > 15) {
            c = a + b;
        }
        b = b + 5;
        a = b + 20;
    }

    void tc12(int b) {
        int a = 1, c = 3, d = 4;
        while (a + b > 6) {
            c = d;
        }
    }

    void tc13(int a) {
        {
            int i = 10;
            a = 6;
        }
        for (int i = 5; i < 10; i++) {
            System.out.println(i);
        }
        a = 10;
    }

    void tc14() {
        int i;
        if (global == 5) {
            i = 10;
        } else {
            i = 34;
        }
        while (i > 30) {
            i = i * 2;
        }
    }

    void tc14_1(int i) {
        if (global == 5) {
            i = 10;
            global = i + 1;
        } else {
            i = 34;
        }
        while (global > 30) {
            i = i * 2;
        }
    }

    void tc15() {
        try {
            int a = 8 / 0;
            int b = a + 2;
        } catch (ArithmeticException ex) {

        }
    }

    void tc15_1(int a, int b) {
        try {
            a = 8 / 0;
            b = a + 2;
        } catch (ArithmeticException ex) {
            b = b + 1;
            a = a + 5;
        }
        a = b;
    }

    void tc16() {
        int a = global;
        int b, c;
        b = a;
        c = a + b;
    }

    void tc16_1(int c) {
        int a = global;
        int b;
        b = a;
        a = 5;
        global = b + a;
        b = a + b;
        c = b + 1;
        global += 2;
        b = c + b;
    }

    void tc17() {
        String x = "abc";
        String y = "bcd";
        String z = x + y;
    }

    void tc17_1() {
        String x = "abc";
        String y = "bcd";
        x = x + y;
        y = "abcd";
    }

    void tc18() {
        int x = 0;
        if (x > 0) {
            x++;
        } else if (x + 10 > 21) {
            x--;
        } else {
            System.out.println("Do Nothing");
        }
    }

    void tc18_1(int x) {
        if (x > 0) {
            x++;
        } else if (x + 10 > 21) {
            x--;
        } else {
            System.out.println("Do Nothing");
        }
    }

    void tc19(int a) {
        int b;
        if (a == 20 ? true : false) {
            b = 1;
        } else {
            b = 2;
        }
        a = a + b + a;
    }

    void tc19_1() {
        int a = 0 ,b;
        if (a == 20 ? true : false) {
            b = 1;
        } else {
            b = 2;
        }
        a = a + b + a;
    }

    void tc20() {
        ;
        if (false){
            //Nothing
        }
        if (true)
            return;
        return;
    }

    void tc21(int a){
        int b = a + 1;
        a = a + 1;
        b = b + a;
        int c = b;
        c++;
    }

    void tc22() {
        int a = 5;
        int b = 6;
        int c = (a > b) ? a : b;
        System.out.println(c);
    }

    void tc22_1(int x){
        String s = "a" + (x > 10 ? "ok":"not okay") + "d";
        System.out.println(s);
    }

}
