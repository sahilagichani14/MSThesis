package soot.RQ1.jb_uce;

public class JB_UCE {

    void tc1() {
        int a = 10;
        if (a > 5) {
            return;
        }
        int b = 20; // Unreachable code
    }

    void tc2() {
        for (int i = 0; i < 10; i++) {
            if (i == 15) {
                int b = 0;
                break;
            }
        }
    }

    void tc3() {
        try {
            int a = 10 / 0;
        } catch (Exception e) {
            return;
        }
        int b = 20; // Unreachable code
    }

    void tc4(int a) {
        while (a == 5.0f) {
            int b = 10; // Unreachable code
        }
    }

    void tc5() {
        int a = 10;
        switch (a) {
            case 10:
                return;
        }
        int b = 20; // Unreachable code
    }

    void tc6() {
        tc6();
        int a = 10; // Unreachable code
    }

    void tc7() {
        int a = 10;
        if (a > 5) {
            return;
        } else {
            int b = 20; // Unreachable code
        }
    }

    void tc8() {
        outer:
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                break outer;
            }
            int a = 10; // Unreachable code
            return;
        }
    }


    public void anotherMethod() {
        int x = 10;
//        while (true){
//            // Some code
//            if (x > 5){
//                break;
//            }
//        }
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

    void tc9() {
        anotherMethod();
        int a = 10; // Unreachable code
    }

    void tc10() {
        try {
            return;
        } finally {
            int a = 10; // Reachable due to finally
        }
    }

    void tc11() {
        System.exit(0);
        int a = 10; // Unreachable code
    }

    void tc12(String x){
        if (x.length() < -2) {
            int temp = 10; // Unreachable code
        }
        if ((true && true) && false){
            int temp = 10;
        }
    }

    void tc13() {
        try {
            int x = 10;
            int temp;
        } catch (Exception e) {
            int temp = 10;
            e.printStackTrace();
        } finally {
            char grade;
        }
    }

    void tc14(){
        if (true){
            int x = 10;
            return;
        }
        int y = 20;
    }

}
