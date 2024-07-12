package soot.RQ1.jb_dae;

public class JB_DAE {
    void tc1() {
        int x = 10;
        x = 20;
        int y = x;
    }

    void tc2() {
        int x = 10;
    }

    void tc3() {
        for (int i = 0; i < 10; i++) {
            int x = 5;
            x = 10;
            System.out.println(x);
        }
    }

    void tc4(boolean flag) {
        if (flag) {
            int x = 10;
            x = 20;
            System.out.println(x);
        }
    }

    void tc5() {
        int x = 10;
        x = 20;
        x = 30;
        System.out.println(x);
    }

    void tc6() {
        int x = 10;
        x = someMethod();
        System.out.println(x);
    }

    public int someMethod() {
        return 20;
    }

    void tc7() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                int x = 5;
                x = 10;
                System.out.println(x);
            }
        }
    }

    void tc8() {
        try {
            int x = 10;
            x = 20;
            System.out.println(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void tc9() {
        int[] arr = new int[5];
        arr[0] = 10;
        arr[0] = 20;
        System.out.println(arr[0]);
    }

    private int field;

    void tc10() {
        this.field = 10;
        this.field = 20;
        System.out.println(this.field);
    }

    void tc11(int x) {
        switch (x) {
            case 1:
                int y = 10;
                y = 20;
                System.out.println(y);
                break;
            case 2:
                System.out.println("Case 2");
                break;
            default:
                System.out.println("Default case");
                break;
        }
    }

    int tc12(boolean flag) {
        int x = 10;
        if (flag) {
            x = 20;
            return x;
        } else {
            x = 30;
            return x;
        }
    }

    void tc13() {
        if (false) {
            int x = 10;
            x = 20;
            System.out.println(x);
        }
    }

    private static int staticField;

    void tc14() {
        staticField = 10;
        staticField = 20;
        System.out.println(staticField);
    }

    private class MyObject {
        int field;
        int getValue() {
            return 10;
        }
    }

    void tc15() {
        MyObject obj = new MyObject();
        obj.field = 10;
        obj.field = 20;
        System.out.println(obj.field);
    }

    void tc16(int a) {
        a = 10;
        System.out.println(a);
    }

    void tc17() {
        int x = 10;
        x += 5;
        x = 20;
        System.out.println(x);
    }

    void tc18() {
        int x = 10 + 5;
        x = 20;
        System.out.println(x);
    }

    void tc19() {
        MyObject obj = new MyObject();
        int x = obj.getValue();
        x = 20;
        System.out.println(x);
    }

    void tc20() {
        int x = 10;
        x = 20;
    }

}
