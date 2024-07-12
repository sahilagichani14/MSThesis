package sootUp.RQ1.jb_uce;

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
        }
        int a = 10; // Unreachable code
    }


    public void anotherMethod() {
        while (true){
            // Some code
        }
    }

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

}
