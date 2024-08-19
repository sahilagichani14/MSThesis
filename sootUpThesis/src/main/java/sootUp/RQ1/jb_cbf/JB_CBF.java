package sootUp.RQ1.jb_cbf;

public class JB_CBF {
//    void tc1() {
//        boolean bool = true;
//        if (bool) {
//            System.out.println("True");
//        }
//    }
//
//    void tc1_1() {
//        boolean bool = 10 + 15 > 5;
//        if (bool) {
//            System.out.println("True");
//        }
//    }
//
//    void tc1_2() {
//        int x = 10;
//        boolean condition = 10 > 5;
//        if (condition) {
//            System.out.println("True");
//        }
//    }
//
//    void tc2() {
//        boolean bool = false;
//        if (bool) {
//            System.out.println("False");
//        }
//    }
//
//    void tc2_1() {
//        Boolean bool = 10 < 5 + 1;
//        if (bool) {
//            System.out.println("False");
//        }
//    }
//
//    void tc3() {
//        boolean bool = true || false;
//        if (bool) {
//            if (bool) {
//                System.out.println("True");
//            }
//        }
//    }
//
//    void tc3_1() {
//        boolean bool = true || false;
//        if (bool) {
//            if (!bool) {
//                System.out.println("True");
//            }
//        }
//    }

//    void tc3_2(Integer x) {
//        boolean bool = true;
//        if (bool) {
//            if (x instanceof Integer) {
//                System.out.println("True");
//            }
//        }
//    }

//    void tc3_3() {
//        boolean bool = true || false;
//        if (!bool) {
//            if (bool) {
//                System.out.println("True");
//            }
//        }
//    }
//
//    void tc4() {
//        boolean bool = false && true;
//        if (bool) {
//            if (bool) {
//                System.out.println("False");
//            }
//        }
//    }

//    void tc4_1(Object x) {
//        boolean bool = false;
//        if (x instanceof Integer) {
//            if (bool) {
//                System.out.println("False");
//            }
//        }
//    }
//
//    void tc5() {
//        boolean bool = true;
//        if (bool) {
//            System.out.println("True");
//        } else {
//            System.out.println("False");
//        }
//    }
//
//    void tc6() {
//        boolean bool = false;
//        if (bool) {
//            System.out.println("True");
//        } else {
//            System.out.println("False");
//        }
//    }
//
//    void tc7() {
//        boolean bool = true;
//        for (int i = 0; i < 10; i++) {
//            if (bool) {
//                System.out.println("True");
//            }
//        }
//    }
//
//    void tc7_1() {
//        boolean bool = true;
//        for (int i = 0; bool; i++) {
//            if (bool && i > 0) {
//                System.out.println("True");
//            }
//        }
//    }
//
//    void tc8() {
//        boolean bool = false;
//        for (int i = 0; i < 10; i++) {
//            if (bool) {
//                System.out.println("False");
//            }
//        }
//    }
//
//    void tc8_1() {
//        boolean bool = false;
//        for (int i = 0; i < 10; i++) {
//            if (bool || i > 1000) {
//                System.out.println("False");
//            }
//        }
//    }
//
//    void tc9() {
//        boolean bool = true;
//        if (bool) {
//            System.out.println("True 1");
//        }
//        if (bool) {
//            System.out.println("True 2");
//        }
//    }

    void tc10() {
        boolean bool = true;
        if (!bool) {
            System.out.println("False 1");
        } else if (!bool) {
            if (bool){
                System.out.println("lets see");
            }
            System.out.println("mid");
        }
        if (bool) {
            System.out.println("False 2");
        }
    }

//    void tc11() {
//        boolean bool = true;
//        if (bool) {
//            System.out.println("True");
//        }
//        bool = false;
//        if (bool) {
//            System.out.println("False");
//        }
//    }

//    void tc12() {
//        boolean bool = true;
//        if (bool) {
//            int x = 10;
//            System.out.println("True: " + x);
//        }
//    }
//
//    void tc13() {
//        boolean bool = false;
//        if (bool) {
//            int x = 10;
//            System.out.println("False: " + x);
//        }
//    }
//
//    void tc14() {
//        boolean bool = true;
//        if (bool) {
//            someMethod();
//        }
//    }
//    private void someMethod() {
//        System.out.println("Method called");
//    }
//
//    void tc15() {
//        boolean bool = false;
//        if (bool) {
//            someMethod();
//        }
//    }
//
//    void tc16() {
//        boolean bool = true;
//        try {
//            if (bool) {
//                throw new Exception("True");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    void tc16_1() {
//        boolean bool = true;
//        try {
//            if (bool) {
//                throw new Exception("True");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if(bool == true && 10 >= 5){
//                System.out.println("finally");
//            }
//        }
//    }
//
//    void tc17() {
//        boolean bool = false;
//        try {
//            if (bool) {
//                throw new Exception("False");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    void tc18() {
//        boolean bool = true;
//        if (bool) {
//            someMethod1(bool);
//        }
//    }
//    private void someMethod1(boolean flag) {
//        if (flag) {
//            System.out.println("Flag is true");
//        }
//    }
//
//    void tc19() {
//        boolean bool = false;
//        if (bool) {
//            someMethod1(bool);
//        }
//    }
//
//    void tc20() {
//        boolean bool = true;
//        if (bool) {
//            boolean bool1 = false;
//            if (bool1) {
//                System.out.println("False");
//            } else {
//                System.out.println("True");
//            }
//        }
//    }
//
//    void tc21(){
//        while (true){
//            int x = 10;
//            if (true){
//                x = 20;
//            } else {
//                System.out.println(x);
//                break;
//            }
//        }
//    }
//
//    void tc21_1(){
//        boolean bool = true;
//        while (bool){
//            int x = 10;
//            if (bool){
//                x = 20;
//            } else {
//                System.out.println(x);
//                break;
//            }
//        }
//    }
//
//    void tc22(){
//        while (true){
//            boolean condition = true;
//            if (condition){
//                condition &= condition;
//            } else {
//                break;
//            }
//        }
//    }
//
//    void tc23(){
//        boolean bool = false;
//        do {
//            if (bool || 10 > 100){
//                break;
//            }
//        } while (!bool);
//        System.out.println("outer condition");
//    }
//
//    void tc24(){
//        boolean bool = true;
//        do {
//            if (!bool || 10 > 100){
//                if (10 > 5){
//                    System.out.println("Inner");
//                }
//                break;
//            }
//        } while (bool);
//        System.out.println("Outer");
//    }
//
//    void tc25(){
//        boolean bool = false;
//        if (!bool) {
//            System.out.println("True");
//        } else if (bool){
//            System.out.println("False");
//        } else {
//            System.out.println("last");
//        }
//    }
//
//    void tc26(){
//        boolean bool = false;
//        if (bool) {
//            System.out.println("True");
//        } else if (bool || !bool){
//            System.out.println("False");
//        } else {
//            System.out.println("last");
//        }
//    }
//
//    void tc27() {
//        int x = 6;
//        if (x == 6){
//            System.out.println("test int");
//        }
//
//        String s = "Str";
//        if (s == "Str"){
//            System.out.println("str true");
//        } else {
//            System.out.println("str false");
//        }
//    }

}
