package soot.RQ1.jb_cbf;

public class JB_CBF {
    void tc1() {
        if (true) {
            System.out.println("True");
        }
    }

    void tc2() {
        if (false) {
            System.out.println("False");
        }
    }

    void tc3() {
        if (true) {
            if (true) {
                System.out.println("True");
            }
        }
    }

    void tc4() {
        if (false) {
            if (false) {
                System.out.println("False");
            }
        }
    }

    void tc5() {
        if (true) {
            System.out.println("True");
        } else {
            System.out.println("False");
        }
    }

    void tc6() {
        if (false) {
            System.out.println("True");
        } else {
            System.out.println("False");
        }
    }

    void tc7() {
        for (int i = 0; i < 10; i++) {
            if (true) {
                System.out.println("True");
            }
        }
    }

    void tc8() {
        for (int i = 0; i < 10; i++) {
            if (false) {
                System.out.println("False");
            }
        }
    }

    void tc9() {
        if (true) {
            System.out.println("True 1");
        }
        if (true) {
            System.out.println("True 2");
        }
    }

    void tc10() {
        if (false) {
            System.out.println("False 1");
        }
        if (false) {
            System.out.println("False 2");
        }
    }

    void tc11() {
        if (true) {
            System.out.println("True");
        }
        if (false) {
            System.out.println("False");
        }

    }

    void tc12() {
        if (true) {
            int x = 10;
            System.out.println("True: " + x);
        }
    }

    void tc13() {
        if (false) {
            int x = 10;
            System.out.println("False: " + x);
        }
    }

    void tc14() {
        if (true) {
            someMethod();
        }
    }
    private void someMethod() {
        System.out.println("Method called");
    }

    void tc15() {
        if (false) {
            someMethod();
        }
    }

    void tc16() {
        try {
            if (true) {
                throw new Exception("True");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void tc17() {
        try {
            if (false) {
                throw new Exception("False");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void tc18() {
        if (true) {
            someMethod1(true);
        }
    }
    private void someMethod1(boolean flag) {
        if (flag) {
            System.out.println("Flag is true");
        }
    }

    void tc19() {
        if (false) {
            someMethod1(false);
        }
    }

    void tc20() {
        if (true) {
            if (false) {
                System.out.println("False");
            } else {
                System.out.println("True");
            }
        }
    }

}
