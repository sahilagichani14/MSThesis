package soot.RQ1.jb_cp;

public class JB_CP {

    private int field = 5;
    private static int field1 = 5;

    void tc1() {
        int a = 5;
        int b = a;
        System.out.println(b);
    }

    void tc2() {
        int a = 5;
        int b = a;
        int c = b;
        System.out.println(c);
        System.out.println(b);
    }

    void tc3() {
        int a = 5;
        int b = a + 1;
        System.out.println(b);
    }

    void tc4() {
        int a = getValue();
        int b = a;
        System.out.println(b);
    }
    private int getValue() {
        return 5;
    }

    void tc5() {
        int a = 5;
        int b;
        if (a > 3) {
            b = a;
        } else {
            b = 2;
        }
        System.out.println(b);
    }

    void tc5_1(int x) {
        int a = 5;
        if (x > 6){
            a = 10;
        }
        int b;
        if (a > 3) {
            b = a;
        } else {
            b = 2;
        }
        System.out.println(b);
    }

    void tc5_2(int x) {
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
    }

    void tc5_3() {
        int a, b;
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
    }

    void tc6() {
        int a = 5;
        for (int i = 0; i < 10; i++) {
            int b = a;
            System.out.println(b);
        }
    }

    void tc7() {
        int a = 5;
        {
            int b = a;
            System.out.println(b);
        }
    }

    void tc7_1() {
        int a = 5;
        {
            a = 50;
        }
        {
            int b = a;
            int c = b + a;
            System.out.println(b);
            System.out.println(c);
            System.out.println(a+b);
        }
    }

    void tc7_2(int a) {
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
    }

    void tc8() {
        int a = 5;
        double b = (double) a;
        System.out.println(b);
    }

    void tc9() {
        Object a = null;
        Object b = a;
        System.out.println(b);
    }

    void tc10() {
        int a = 5;
        int b = 6;
        int c = (a > b) ? a : b;
        System.out.println(c);
    }

    void tc11() {
        int a = 5;
        int b = a;
        a = 6;
        System.out.println(b);
    }

    void tc12() {
        int a = 5;
        long b = a;
        System.out.println(b);
    }


    void tc13() {
        int a = field;
        int b = a;
        System.out.println(b);
    }

    void tc13_1() {
        int a = field;
        long b = (long) a;
        System.out.println(b);
    }

    void tc14() {
        int[] array = {1, 2, 3};
        int a = array[0];
        int b = a;
        System.out.println(b);
    }

    void tc15() {
        int a = field1;
        int b = a;
        System.out.println(b);
    }

    void tc16() {
        JB_CP obj = new JB_CP();
        int a = obj.field;
        int b = a;
        System.out.println(b);
    }

    void tc17() {
        final int a = 5;
        int b = a;
        System.out.println(b);
    }

    void tc17_1() {
        int a = 5, c = 6;
        int b = 10 + a + c;
        System.out.println(b + c);
    }

    void tc17_2() {
        int a = 5, c = 6;
        int b = 10 + a + c;
        if(a + c == 11){
            System.out.println(b);
        }
        System.out.println(b + c);
    }

    void tc18() {
        int a = 5;
        try {
            int b = a;
            System.out.println(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void tc18_1() {
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
    }

    private int anotherMethod(int val) {
        return val + 1;
    }

    void tc19() {
        int a = getValue();
        int b = a;
        int c = anotherMethod(b);
        System.out.println(c);
    }

    void tc20() {
        int a = 5;
        int b = 10;
        switch (a){
            case 5: b = a;
        }
        System.out.println(b);
    }

    void tc21() {
        int a = 5;
        int b = 10;
        switch (a + b){
            case 5: b = a;
                    System.out.println(b);
        }
        System.out.println(b);
    }

    void tc21_1() {
        int a = 5;
        int b = 10;
        switch (a + b){
            case 4: a = 20;
            case 5: b = a;
                System.out.println(b);
        }
        System.out.println(b);
    }

    void tc22(){
        boolean a = true;
        boolean b = a;
        Boolean c = b && a;
        boolean d = c;
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
    }

    void tc23(){
        boolean a = true;
        boolean b = a;
        if (b == true? true:false){
            Boolean c = b && a;
            boolean d = c;
            System.out.println(b);
            System.out.println(c);
            System.out.println(d);
        }
    }

}
