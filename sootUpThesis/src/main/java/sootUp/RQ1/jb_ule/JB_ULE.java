package sootUp.RQ1.jb_ule;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JB_ULE {
    private int field;
    private static int staticField;

    void tc1() {
        int x = 10;
    }

    void tc1_1(){
        int x, y;
        String s;
        boolean bool;
        Object object;
        List<String> list;
        int[] arr = new int[5];
        String[] strings;
        Integer count;
        List<String> names;
    }

    void tc1_2(int x) {
        x++;
    }

    void tc2() {
        int a = 10, b, c;
        int d = 20;
    }

    void tc3(int y, int z) {
        int x = 10;
        System.out.println(y);
    }

    void tc4() {
        for (int i = 0; i < 10; i++) {
            int temp;
            int x = 5;
            char grade;
            float height;
        }
    }

    void tc5(boolean flag) {
        if (flag) {
            int temp;
            int x = 10;
            int[] arr = new int[5];
        }
    }

    void tc6() {
        try {
            int x = 10;
            int temp;
        } catch (Exception e) {
            int temp;
            char grade;
            e.printStackTrace();
        } finally {
            char grade;
            float height;
        }
    }

    public int someMethod() {
        int temp;
        return 20;
    }

    void tc7() {
        int temp;
        int x = someMethod();
        int temp1;
    }

    private class MyObject {
        int getValue() {
            return 10;
        }
    }

    void tc8() {
        MyObject obj = new MyObject();
        int x = obj.getValue();
        MyObject obj1;
    }

    void tc9() {
        int x = this.field;
    }

    void tc10() {
        for (int i = 0; i < 10; i++) {
            int temp;
            for (int j = 0; j < 5; j++) {
                j = 10;
                int x = j * 1000;
                int temp1;
            }
        }
    }

    void tc11() {
        int[] arr = new int[5];
        int x = arr[0];
    }

    void tc12() {
        int x = staticField;
    }

    void tc13(int a) {
        int x = a;
    }

    void tc14() {
        int x = 10 + 5;
    }

    void tc15() {
        String str = new String("Hello");
    }

    int tc16(boolean flag) {
        int x = 10;
        if (flag) {
            int temp;
            return 20;
        } else {
            int temp;
            return 30;
        }
    }

    int tc16_1(boolean flag) {
        int x = 10;
        if (true) {
            return 20;
        } else {
            int temp;
            boolean xyz;
            String s;
            return 30;
        }
    }

    void tc17(int x, int y) {
        System.out.println(x);
    }

    void tc18() {
        final int x = 10;
    }

    private volatile int x;
    void tc19() {
        int y = x;
    }

    void tc20(int a) {
        switch (a){
            case 1: int x = 0; int temp;
            case 2: int y = 0; int temp1;
            default: int z = 0;int temp2;
        }
    }

    void tc21(int x, int y, Object obj, String s){}

    void tc22(){
        {
            int x;
        }
        {
            String s;
        }
    }

    void tc23(){
        enum String { SUNDAY }
        String x = String.SUNDAY;
    }

    void tc24(){
        List<Integer> list = new ArrayList<>(List.of(1, 3));
        for (int number : list) {
            System.out.println("");
        }
    }

    void tc25(){
        try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) {
            String line;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void anotherMethod1(){
//        int x = 10;
//        for (;;) {
//            // Some code
//            if (x > 5) {
//                break;
//            }
//        }
//    }
//
//    public void anotherMethod2(){
//        int x = 10;
//        do {
//            // Some code
//            if (x > 5) {
//                break;
//            }
//        } while (true);
//
//    }

}
