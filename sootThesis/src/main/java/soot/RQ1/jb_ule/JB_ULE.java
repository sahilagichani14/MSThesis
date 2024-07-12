package soot.RQ1.jb_ule;

public class JB_ULE {
    private int field;
    private static int staticField;

    void tc1() {
        int x = 10;
    }

    void tc2() {
        int a = 10;
        int b = 20;
        int c = 30;
    }

    void tc3() {
        int x = 10;
        int y = 20;
        System.out.println(y);
    }

    void tc4() {
        for (int i = 0; i < 10; i++) {
            int x = 5;
        }
    }

    void tc5(boolean flag) {
        if (flag) {
            int x = 10;
        }
    }

    void tc6() {
        try {
            int x = 10;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int someMethod() {
        return 20;
    }

    void tc7() {
        int x = someMethod();
    }

    private class MyObject {
        int getValue() {
            return 10;
        }
    }

    void tc8() {
        MyObject obj = new MyObject();
        int x = obj.getValue();
    }

    void tc9() {
        int x = this.field;
    }

    void tc10() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                int x = 5;
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
            return 20;
        } else {
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
            case 1: int x = 0;
            case 2: int y = 0;
            default: int z = 0;
        }
    }

}
