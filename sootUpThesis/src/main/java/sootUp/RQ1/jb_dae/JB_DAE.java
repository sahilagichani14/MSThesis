package sootUp.RQ1.jb_dae;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class JB_DAE {
//    void tc1() {
//        int x = 10;
//        x = 20;
//        int y = x;
//    }
//
//    void tc2() {
//        int x = 10;
//    }
//
//    void tc3() {
//        for (int i = 0; i < 10; i++) {
//            int x = 5;
//            x = 60;
//            x = 10;
//            int y = x;
//            int z = y;
//            System.out.println(x);
//        }
//    }
//
//    void tc4(boolean flag, int y, int z) {
//        if (flag) {
//            int x = 10;
//            x = 20;
//            y = x;
//            z = y;
//            int temp = x + y + z;
//            System.out.println(x);
//        }
//        System.out.println(z);
//    }
//
    void tc5(int y) {
        int x = 10;
        x = 20;
        if (y > 10){
            x = 30;
        }
        System.out.println(x);
    }
//
//    void tc6() {
//        int x = 10;
//        x = someMethod();
//    }
//
//    int tc6_1(int temp) {
//        int x = 10;
//        temp = x;
//        if (temp > 5) {
//            x = someMethod();
//        }
//        x = 20;
//        return temp;
//    }
//
//    public int someMethod() {
//        return 20;
//    }
//
//    void tc7() {
//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 5; j++) {
//                int x = 5;
//                x = 10;
//                int y = someMethod();
//                int z = 10;
//                System.out.println(x);
//            }
//        }
//    }
//
//    void tc8() {
//        try {
//            int x = 10;
//            x = 20;
//            int z = someMethod();
//            System.out.println(x);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    void tc8_1(int temp) {
//        try {
//            int z = someMethod();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            temp = 5;
//            int z = someMethod();
//        }
//    }
//
//    void tc9() {
//        int[] arr = new int[5];
//        arr[0] = 10;
//        arr[0] = 20;
//        System.out.println(10 + arr[1]);
//    }
//
//    private int field;
//
//    void tc10() {
//        this.field = 10;
//        this.field = 20 + 30;
//    }
//
//    void tc11(int x) {
//        switch (x) {
//            case 1:
//                int y = 10;
//                y = 20;
//                break;
//            case 2:
//                System.out.println("Case 2" + x);
//                break;
//            default:
//                System.out.println("Default case");
//                break;
//        }
//    }
//
//    int tc12(boolean flag) {
//        int x = 10;
//        if (flag) {
//            int temp;
//            x = 20;
//            return x;
//        } else {
//            long temp = 0l;
//            flag = false;
//            return x;
//        }
//    }

//    int tc12_1(boolean flag) {
//        int x = 10;
//        int temp = 0;
//        if (flag) {
//            x = 20;
//            return x;
//        } else if(flag) {
//            temp = 10;
//            flag = false;
//            return x;
//        }
//        System.out.println(temp);
//        return x;
//    }

//    void tc13() {
//        if (false) {
//            int x = 10;
//            x = 20;
//            System.out.println(x);
//        }
//    }
//
//    private static int staticField;
//
//    void tc14() {
//        staticField = 10;
//        staticField = 20;
//        System.out.println(staticField);
//    }
//
//    private class MyObject {
//        int field;
//
//        int getValue() {
//            return field;
//        }
//
//        int setValue(int field) {
//            this.field = field;
//            return this.field;
//        }
//    }
//
//    void tc15() {
//        MyObject obj = new MyObject();
//        obj.field = 10;
//        obj.field = 20;
//        System.out.println(obj.field);
//    }
//
//    void tc16(int a) {
//        a = 10;
//        System.out.println(a);
//    }
//
//    void tc17(int y) {
//        int x = 10;
//        x += 5;
//        x = 20;
//        System.out.println(y);
//    }
//
//    void tc18(int y) {
//        int x = 10 + 5;
//        x = 20;
//        System.out.println(x);
//    }
//
//    void tc19() {
//        MyObject obj = new MyObject();
//        int x = obj.getValue();
//        x = 20;
//        int y = x;
//        System.out.println(y);
//    }
//
//    void tc20() {
//        MyObject obj = new MyObject();
//        int x = obj.getValue();
//        obj.setValue(16);
//        int y = obj.getValue();
//        System.out.println(y);
//    }
//
//    void tc21(int i) {
//        while (i < 10) {
//            int temp = i;
//        }
//        i++;
//    }
//
//    void tc22(int i) {
//        do {
//            int temp = i;
//        }
//        while (i < 10);
//        i++;
//    }
//
//    void tc23(int i) {
//        MyObject object = new MyObject();
//        int i1 = object.setValue(i);
//    }
//
//    void tc24(int i, int x, int y) {
//        while (y >= x ? true : false) {
//            int temp = i;
//        }
//        i++;
//    }
//
//    void tc25(int i) {
//        {
//            i = 5;
//        }
//        {
//            i = 9;
//        }
//        System.out.println(i);
//    }
//
//    void tc26() {
//        List l1 = new ArrayList();
//        List l2 = new ArrayList();
//        List l3, l4;
//        l2 = l1;
//        l4 = l1;
//        l3 = l1;
//        System.out.println(l3);
//    }
//
//    void tc27() {
//        int x = 10;
//        x = x;
//        boolean bool;
//        bool = true;
//        bool = bool;
//        System.out.println("");
//    }

    void tc28(){
        try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) {
            String line;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void tc29(){
        int[] numbers = {4, 5};
        for (int number : numbers) {
            System.out.println("");
        }
    }

    void tc30(){
        enum String { SUNDAY }
        String x = String.SUNDAY;
    }

}
