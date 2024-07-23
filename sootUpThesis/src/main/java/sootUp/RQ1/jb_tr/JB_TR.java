package sootUp.RQ1.jb_tr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;

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
        String lowerCase = s.toLowerCase();
        String str = lowerCase.concat(s);
    }

    void tc3() {
        float b = 10.5f;
        Float f = 444.66666666666666666666666666666666666666f;
        int compareTo = f.compareTo(b);
    }

    void tc4() {
        int[] arr = new int[10];
    }

    void tc5() {
        boolean flag = true;
    }

    void tc6() {
        Object obj = new Object();
        String objString = obj.toString();
    }

    void tc7() {
        String s = null;
    }

    void tc8() {
        for (int i = 0; i < 10; i++) {
            int a = i;
            boolean b = a >= i;
        }
    }

    void tc9() {
        int a;
        if (true) {
            a = 10;
            boolean b = true;
        } else {
            a = 20;
        }
    }

    void tc9_1(int a, String str) {
        if (a > 56) {
            a = 10;
            boolean b = true;
        } else {
            String x = "cha";
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

    void tc10_1(int a) {
        switch (a) {
            case 1:
                int b = 10;
                break;
            case 2:
                boolean c = a > 6;
                break;
        }
    }

    void tc11() {
        int[] arr = new int[10];
        int a = arr[0];
        OptionalInt max = Arrays.stream(arr).max();
    }

    void tc12() {
        MyClass obj = new MyClass();
        int a = obj.field;
        obj.field = a + obj.field;
    }

    void tc13() {
        int a = MyClass.field1;
        a = a + MyClass.field1;
    }

    void tc14() {
        Object obj = "Hello";
        String str = (String) obj;
        byte[] strBytes = str.getBytes();
        strBytes.toString();
    }

    void tc14_1() {
        Object obj = "2";
        String str = (String) obj;
        Integer integer = (Integer) obj;
        byte[] strBytes = str.getBytes();
        strBytes.toString();
    }

    void tc15() {
        List<String> list = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        list.add("str");
        //double brackets
        list1.add((0));
        Integer i = list1.get(0);
    }

    void tc15_1() {
        List<String> list = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        list.add(("str"));
        //double brackets
        list1.add((0));
        Integer i = list1.get(0);
    }

    void tc16() {
        int a = 10, b = 20;
        Integer c = a;
        Integer d = c + a;
    }

    void tc17() {
        boolean a = true && false;
        Boolean b = true;
        int compareTo = b.compareTo(true);
    }

    void tc18() {
        char c = 'a';
        Character d = 'c';
        String str = d.toString();
        int i = c + d;
    }

    void tc19() {
        byte b = 10;
        Byte c = 20;
        byte byteValue = c.byteValue();
    }

    void tc20() {
        short s = 100;
        Short x = 20;
        int intValue = x.intValue();
    }

    void tc21() {
        long l = 1000L;
        Long p = 300L;
        int intValue = p.intValue();
    }

    void tc22() {
        double d = 100.5;
        Double s = 2.3333;
        int intValue = s.intValue();
    }

//    void tc23() {
//        String someString = "Some String abc";
//        boolean a = someString.contains("abc");
//    }
}
