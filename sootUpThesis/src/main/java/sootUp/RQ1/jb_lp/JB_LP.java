package sootUp.RQ1.jb_lp;

public class JB_LP {
    public void tc0(int l1, int l2) {
        int l3,l4,l5;
        l3 = 10;
        l4 = 0;
        for (l5 = l4 + 1; l5 > l3; ) {
            // do something
        }
    }

    private int a;

    public void tc1() {
        int a;
        if (10 > 20) {
            int temp = 5;
            a = 10;
        } else {
            a = 20;
            int temp = a;
        }
        int b = a + 5;
        int temp;
    }

    public void tc2(){
        for (int i = 0; i < 10; i++) {}
        for (int j = 0; j < 10; j++) {}
    }
    public void tc3(){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {}
        }
    }
    public void tc4(){
        int a = 1;
        int b = 2;
        a = b + 1;
        int c = 3;
        int d = 4;
        c = d + 1;
    }
    public void tc5(){
        {
            int x = 5;
        }
        {
            int y = 10;
        }
    }
    public void tc6(){
        int[] arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        for (int j = 0; j < arr.length; j++) {
            arr[j] = j * 2;
        }
    }
    public void tc7(){
        boolean flag = false;
        if (flag) {
            int x = 5;
        } else {
            int y = 10;
        }
    }
    public void tc8(){
        int i, j=0;
        for (i = 0; i < 10; i++) {}
        while (j < 10) {
            j++;
        }
    }
    public void tc9(){
        try {
            int a = 5;
        } catch (Exception e) {
            int b = 10;
        }
    }
    public void tc10(){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int temp = i + j;
            }
        }
    }
    public void tc11(){
        int a = 0;
        anotherMethod(a);
        int b = 1;
        anotherMethod(b);
    }
    public void anotherMethod(int param) {}

    public void tc12(int k){
        for (int i = 0; i < k; i++) {
            if (i % 2 == 0) {
                int a = i;
            } else {
                int b = i;
            }
        }
    }
    public void tc13(){
        {
            int a = 20; // This a shadows the outer a
        }
        int a = 10;
    }
    public static void tc14(){
        //static method
        int a = 1;
        int b = 2;
    }
    public void tc15(){
        this.a = 1;
        int a = this.a;
        int b = a;
    }

}

