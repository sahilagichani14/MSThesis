package sootUp.RQ1.jb_cbf;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class JB_CBF {
    // Function that calls each test
    public static void main(String[] args) throws Exception {
        JB_CBF tester = new JB_CBF();

        System.out.println(tester.tc1(true));
        System.out.println(tester.tc1_1(false));
        System.out.println(tester.tc1_2());
        System.out.println(tester.tc2());
        System.out.println(tester.tc2_1());
        System.out.println(tester.tc3());
        System.out.println(tester.tc3_1());
        System.out.println(tester.tc3_2(10));
        System.out.println(tester.tc3_3());
        System.out.println(tester.tc4());
        System.out.println(tester.tc4_1(Integer.valueOf(7)));
        System.out.println(tester.tc5(false));
        System.out.println(tester.tc6());
        System.out.println(tester.tc7());
        System.out.println(tester.tc7_1());
        System.out.println(tester.tc8());
        System.out.println(tester.tc8_1());
        System.out.println(tester.tc9());
        System.out.println(tester.tc10());
        System.out.println(tester.test0(new File("test.txt")));
        System.out.println(tester.tc11());
        System.out.println(tester.tc12());
        System.out.println(tester.tc13());
        System.out.println(tester.tc14());
        System.out.println(tester.tc15());
        System.out.println(tester.tc16());
        System.out.println(tester.tc16_1());
        System.out.println(tester.tc17());
        System.out.println(tester.tc18());
        System.out.println(tester.tc19());
        System.out.println(tester.tc20());
        System.out.println(tester.tc21());
        System.out.println(tester.tc21_1());
        System.out.println(tester.tc22());
        System.out.println(tester.tc23());
        System.out.println(tester.tc24());
        System.out.println(tester.tc25());
        System.out.println(tester.tc26());
        System.out.println(tester.tc27());
    }

    boolean tc1(boolean bool) {
        bool = true;
        if (bool) {
            System.out.println("True");
            return bool;
        }
        return !bool;
    }

    boolean tc1_1(boolean bool) {
        bool = 10 + 15 > 5;
        if (bool) {
            System.out.println("True");
        }
        return bool;
    }

    boolean tc1_2() {
        int x = 10;
        boolean condition = 10 > 5;
        if (condition) {
            System.out.println("True");
        }
        return condition;
    }

    boolean tc2() {
        boolean bool = false;
        if (bool) {
            System.out.println("False");
        }
        return bool;
    }

    boolean tc2_1() {
        Boolean bool = 10 < 5 + 1;
        if (bool) {
            System.out.println("False");
        }
        return false;
    }

    boolean tc3() {
        boolean bool = true || false;
        if (!bool) {
            if (bool) {
                System.out.println("True");
                return bool;
            }
        }
        return !bool;
    }

    boolean tc3_1() {
        boolean bool = true || false;
        if (bool) {
            if (!bool) {
                System.out.println("True");
            }
        }
        return bool;
    }

    boolean tc3_2(Integer x) {
        boolean bool = true;
        if (bool) {
            if (x instanceof Integer) {
                System.out.println("True");
            }
        }
        return bool;
    }

    boolean tc3_3() {
        boolean bool = true || false;
        if (!bool) {
            if (bool) {
                System.out.println("True");
            }
        }
        return bool;
    }

    boolean tc4() {
        boolean bool = false && true;
        if (bool) {
            if (bool) {
                System.out.println("False");
            }
        }
        return bool;
    }

    boolean tc4_1(Object x) {
        boolean bool = false;
        if (x instanceof Integer) {
            if (bool) {
                System.out.println("False");
            }
        }
        return x instanceof Integer;
    }

    boolean tc5(boolean bool) {
        bool = true;
        if (bool) {
            System.out.println("True");
            return bool;
        } else {
            System.out.println("False");
            return bool;
        }
    }

    boolean tc6() {
        boolean bool = false;
        if (bool) {
            System.out.println("True");
        } else {
            System.out.println("False");
        }
        return bool;
    }

    boolean tc7() {
        boolean bool = true;
        for (int i = 0; i < 10; i++) {
            if (bool) {
                System.out.println("True");
            }
        }
        return bool;
    }

    boolean tc7_1() {
        boolean bool = true;
        for (int i = 0; bool; i++) {
            if (bool && i > 0) {
                System.out.println("True");
            }
        }
        return bool;
    }

    boolean tc8() {
        boolean bool = false;
        for (int i = 0; i < 10; i++) {
            if (bool) {
                System.out.println("False");
            }
        }
        return bool;
    }

    boolean tc8_1() {
        boolean bool = false;
        for (int i = 0; i < 10; i++) {
            if (bool || i > 1000) {
                System.out.println("False");
            }
        }
        return bool;
    }

    boolean tc9() {
        boolean bool = true;
        if (bool) {
            System.out.println("True 1");
        }
        if (bool) {
            System.out.println("True 2");
        }
        return bool;
    }

    boolean tc10() {
        boolean bool = false;
        if (bool) {
            System.out.println("False 1");
        }
        if (bool) {
            System.out.println("False 2");
        }
        return bool;
    }

    private String test0(File storedResults) throws Exception {
        try {
            FileInputStream file = new FileInputStream(storedResults);
            try {
                ObjectInputStream stream = new ObjectInputStream(file);
                try {
                    return (String) stream.readObject();
                } finally {
                    stream.close();
                }
            } finally {
                file.close();
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    boolean tc11() {
        boolean bool = true;
        if (bool) {
            System.out.println("True");
        }
        bool = false;
        if (bool) {
            System.out.println("False");
        }
        return bool;
    }

    boolean tc12() {
        boolean bool = true;
        if (bool) {
            int x = 10;
            System.out.println("True: " + x);
        }
        return bool;
    }

    boolean tc13() {
        boolean bool = false;
        if (bool) {
            int x = 10;
            System.out.println("False: " + x);
        }
        return bool;
    }

    boolean tc14() {
        boolean bool = true;
        if (bool) {
            someMethod();
        }
        return bool;
    }
    private void someMethod() {
        System.out.println("Method called");
    }

    boolean tc15() {
        boolean bool = false;
        if (bool) {
            someMethod();
        }
        return bool;
    }

    boolean tc16() {
        boolean bool = true;
        try {
            if (bool) {
                throw new Exception("True");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bool;
    }

    boolean tc16_1() {
        boolean bool = true;
        try {
            if (bool) {
                throw new Exception("True");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(bool == true && 10 >= 5){
                System.out.println("finally");
            }
        }
        return bool;
    }

    boolean tc17() {
        boolean bool = false;
        try {
            if (bool) {
                throw new Exception("False");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bool;
    }

    boolean tc18() {
        boolean bool = true;
        if (bool) {
            someMethod1(bool);
        }
        return bool;
    }
    private void someMethod1(boolean flag) {
        if (flag) {
            System.out.println("Flag is true");
        }
    }

    boolean tc19() {
        boolean bool = false;
        if (bool) {
            someMethod1(bool);
        }
        return bool;
    }

    boolean tc20() {
        boolean bool = true;
        if (bool) {
            boolean bool1 = false;
            if (bool1) {
                System.out.println("False");
            } else {
                System.out.println("True");
            }
        }
        return bool;
    }

    boolean tc21(){
        while (true){
            int x = 10;
            if (true){
                x = 20;
            } else {
                System.out.println(x);
                break;
            }
        }
        return false;
    }

    boolean tc21_1(){
        boolean bool = true;
        while (bool){
            int x = 10;
            if (bool){
                x = 20;
            } else {
                System.out.println(x);
                break;
            }
        }
        return bool;
    }

    boolean tc22(){
        while (true){
            boolean condition = true;
            if (condition){
                condition &= condition;
            } else {
                break;
            }
        }
        return false;
    }

    boolean tc23(){
        boolean bool = false;
        do {
            if (bool || 10 > 100){
                break;
            }
        } while (!bool);
        System.out.println("outer condition");
        return bool;
    }

    boolean tc24(){
        boolean bool = true;
        do {
            if (!bool || 10 > 100){
                if (10 > 5){
                    System.out.println("Inner");
                }
                break;
            }
        } while (bool);
        System.out.println("Outer");
        return bool;
    }

    boolean tc25(){
        boolean bool = false;
        if (!bool) {
            System.out.println("True");
        } else if (bool){
            System.out.println("False");
        } else {
            System.out.println("last");
        }
        return bool;
    }

    boolean tc26(){
        boolean bool = false;
        if (bool) {
            System.out.println("True");
        } else if (bool || !bool){
            System.out.println("False");
        } else {
            System.out.println("last");
        }
        return !bool;
    }

    boolean tc27() {
        int x = 6;
        if (x == 6){
            System.out.println("test int");
        }

        String s = "Str";
        if (s == "Str"){
            System.out.println("str true");
        } else {
            System.out.println("str false");
        }
        return false;
    }

}
