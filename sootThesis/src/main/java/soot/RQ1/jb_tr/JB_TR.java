package soot.RQ1.jb_tr;

import java.util.ArrayList;
import java.util.List;

class MyClass {
    int field = 10;
    static int field1 = 10;
}

public class JB_TR {

    void tc1() {
        int a = 10;
    }

    void tc2() {
        String s = "Hello, World!";
    }

    void tc3() {
        float b = 10.5f;
    }

    void tc4() {
        int[] arr = new int[10];
    }

    void tc5() {
        boolean flag = true;
    }

    void tc6() {
        Object obj = new Object();
    }

    void tc7() {
        String s = null;
    }

    void tc8() {
        for (int i = 0; i < 10; i++) {
            int a = i;
        }
    }

    void tc9() {
        int a;
        if (true) {
            a = 10;
        } else {
            a = 20;
        }
    }

    void tc10() {
        int a = 1;
        switch (a) {
            case 1:
                int b = 10;
                break;
            case 2:
                int c = 20;
                break;
        }
    }

    void tc11() {
        int[] arr = new int[10];
        int a = arr[0];
    }

    void tc12() {
        MyClass obj = new MyClass();
        int a = obj.field;
    }

    void tc13() {
        int a = MyClass.field1;
    }

    void tc14() {
        Object obj = "Hello";
        String str = (String) obj;
    }

    void tc15() {
        List<String> list = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
    }

    void tc16() {
        int a = 10, b = 20;
    }

    void tc17() {
        boolean a = true && false;
    }

    void tc18() {
        char c = 'a';
    }

    void tc19() {
        byte b = 10;
    }

    void tc20() {
        short s = 100;
    }

    void tc21() {
        long l = 1000L;
    }

    void tc22() {
        double d = 100.5;
    }
}
