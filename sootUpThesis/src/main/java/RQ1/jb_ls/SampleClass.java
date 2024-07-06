package RQ1.jb_ls;

public class SampleClass {
//    int global = 10;
//
//    void tc1() {
//        int a, b;
//        a = 1;
//        b = a;
//        b = b + 1;
//    }
//
//    void tc1_1() {
//        int a = 0, b, c, d;
//        if (a > 10) {
//            b = 10;
//        } else {
//            b = 5;
//        }
//        if (a + b > 15) {
//            c = a + b;
//        }
//    }
//
//    void tc1_1_1() {
//        int a = 0, b, c, d;
//        if (a > 10) {
//            b = 10;
//        } else {
//            b = 5;
//        }
//        if (b > 15) {
//            c = a + b;
//        }
//        b = b + 5;
//        a = b + 20;
//    }
//
//    void tc1_1_2() {
//        int a = 1, b = 2, c = 3, d;
//        if (a + b > 10){
//            c = a + b;
//        }
//        d = c;
//    }
//
//    void tc1_1_3() {
//        int a = 1, b = 2, c = 3, d = 4;
//        while (a + b > 6){
//            c = d;
//        }
//    }
//
//    void tc1_1_4() {
//        int a = 1, b = 2, c = 3, d = 4;
//        do {
//            c = d;
//        }
//        while (a + b > 6);
//    }
//
//    void tc1_1_5() {
//        int a = 1, b = 2, c = 3, d = 4;
//        switch (a+b){
//            case 3: a = 10;
//            default: a = 20;
//        }
//        c = a + b;
//    }
//
//    void tc1_1_6() {
//        int a = 1, b = 2, c = 3, d = 4;
//        for (int i=0; a+b>3; i++){
//            int x = a + b;
//        }
//    }
//
//    void tc1_1_7() {
//        int a = 1, b = 2, c = 3, d = 4;
//        for (int i=0; a+b>3; i++){
//            c = d;
//        }
//    }
//
//        void tc1_2() {
//        int a = 0, b, c;
//        b = a;
//        c = b;
//        a = c;
//    }

//    void tc1_3() {
//        {
//            int i = 10, a;
//        }
//        for (int i = 5; i < 10; i++) {
//            System.out.println(i);
//        }
//    }

//    void tc1_4() {
//        int i = 5;
//        while (i > 3) {
//            i = i * 2;
//        }
//    }

//    void tc1_5() {
//        int i = 0;
//        if (i > 5) {
//            i = 10;
//        } else {
//            i = 34;
//        }
//        while (i > 3) {
//            i = i * 2;
//        }
//    }

//    void tc1_6() {
//        int i;
//        if (global==5) {
//            i = 10;
//        } else {
//            i = 34;
//        }
//        while (i > 30) {
//            i = i * 2;
//        }
//    }

//    void tc2() {
//        int a, b;
//        a = 1;
//        b = a + 3;
//    }

    void tc3() {
        int a, b;
        a = 1;
        if (a == 2) {
            b = 5;
        } else {
            b = 10;
        }
        int x = b + 1;
    }

//    void tc3_1() {
//        int a = 0;
//        int b;
//        if (a == 20) {
//            b = 1;
//        } else {
//            b = 2;
//        }
//        a = b + 1;
//    }

//    void tc4() {
//        int a = 0;
//        int b;
//        for (int i = 0; i < 3; i++) {
//            b = a + i;
//        }
//    }

//    void tc5() {
//        int a = 3;
//        switch (a) {
//            case 1:
//                a = a + 1;
//                int b = 1;
//                break;
//            case 2:
//                a = a + 1;
//                b = 2;
//                break;
//            case 3:
//                a = a + 1;
//                b = 3;
//                break;
//            default:
//                a = a - 1;
//        }
//    }

//    void tc6() {
//        {
//            int b = 2;
//        }
//        int b = 5;
//    }

//    void tc7() {
//        try {
//            int a = 8 / 0;
//            int b = a + 2;
//        } catch (ArithmeticException ex) {
//
//        }
//    }

//    void tc8() {
//        int a = global + 5;
//    }

//    void tc9(){
//        String x = "abc";
//        String y = "bcd";
//        String z = x + y;
//    }

//    void localSplitterTest() {
//        int x = 0;
//        if (x > 0) {
//            x++;
//        } else if (x + 10 > 21) {
//            x--;
//        } else {
//            System.out.println("Do Nothing");
//        }
//    }
}
