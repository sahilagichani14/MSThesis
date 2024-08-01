package sootUp.RQ1.jb_ese;

public class JB_ESE {

    public void tc1(int x) {
        switch (x) {
            default:
                System.out.println("Default case" + x);
        }
    }

    public void tc1_1(String x) {
        switch (x) {
            default:
                System.out.println("Default case" + x);
        }
    }

    public void tc2(int x) {
        switch (x) {
            case 1:
                System.out.println("Case 1" + x);
                break;
            default:
                System.out.println("Default case");
        }
    }

    public void tc3(int x) {
        switch (x) {
        }
    }

    public void tc4(int x) {
        switch (x) {
            default:
            case 1:
                System.out.println("Case 1 or Default");
        }
    }

    public void tc5(int x) {
        switch (x) {
            case 1:
            case 2:
                System.out.println("Empty Case Blocks");
                break;
            default:
                System.out.println("Default case");
        }
    }

    public void tc5_1(String x) {
        switch (x) {
            case "":
                System.out.println("Empty Case Blocks");
            case "c":
                System.out.println("Empty Case Blocks");
            default:
                System.out.println("Default case");
        }
    }

    public void tc6(int x) {
        switch (x) {
            case 1:
                switch (x) {
                    default:
                        System.out.println("Nested Default");
                }
                break;
            default:
                System.out.println("Outer Default");
        }
    }

    public void tc6_1(int x) {
        switch (x) {
            case 1:
                switch (x) {
                    default:
                        System.out.println("Nested Default");
                }
            default:
                System.out.println("Outer Default");
        }
    }

    public void tc7(int x) {
        switch (x) {
            case 1:
            case 2:
            default:
                System.out.println("Empty Cases and Default");
        }
    }

    public void tc8(int x) {
        switch (x) {
            case 1:
                System.out.println("case 1");
                break;
            case 2:
                System.out.println("case 2");
                break;
        }
    }

    public void tc9(int x) {
        switch (x) {
            default:
                System.out.println("default case");
                break;
        }
    }

    public void tc10() {
        switch (2) {
            default:
                System.out.println("fixed");
        }
    }

    public void tc11(int x) {
        String y = "switch inside switch";
        switch (x) {
            default:
                switch (y) {
                    default:
                        System.out.println("Nested Default");
                }
        }
    }

    public void tc12(int x) {
        String y = "3";
        Integer integer = Integer.valueOf(y);
        switch (integer) {
            case 1:
            default:
                switch (y) {
                    default:
                        System.out.println("Nested Default");
                }
        }
    }

    public void tc13(Integer x) {
        int b = 10;
        Integer valueOf = Integer.valueOf(x);
        switch (x) {
            case 10 + 20:
                switch (x) {
                    default:
                        b = 20;
                }
                break;
            default:
                b = 30;
        }
        int c = b * 4;
    }

    public void tc14(Integer x) {
        int b = 10;
        Integer valueOf = Integer.valueOf(x);
        switch (x + b) {
            case 20 * 5:
            default:
                valueOf = valueOf + 10;
                b = 30;
        }
        int c = b * 4;
        System.out.println(valueOf);
    }

    public void tc15(Integer x) {
        for (int i = 0; i < getValue(); i++) {
            switch (x) {
                default:
                    x = 30;
            }
            break;
        }
    }

    public void tc15_1(Integer x) {
        for (int i = 0; i < getValue(); i++) {
            switch (x) {
                default:
                    x = 30;
            }
            continue;
        }
    }

    private int getValue() {
        return 0;
    }

    public void tc16(Integer x) {
        while (x < getValue()) {
            switch (x) {
                default:
                    x = 30;
                    break;
            }
        }
    }

    public void tc17(Integer x) {
        if (x > 5) {
            switch (x + 10) {
                default:
                    break;
            }
        } else {
            switch (x > 10 ? x + 10 : 5) {
                default:
                    switch (x) {
                        default:
                            break;
                    }
                    break;
            }
        }
    }

    public void tc18(Integer x) {
        switch (x) {
            default:
                tc18(x + 10);
        }
    }

    public void tc18_1(Integer x) {
        switch (x > 10 ? x + 10 : 5) {
            default:
                switch (x) {
                    default:
                        tc18_1(x);
                        break;
                }
                break;
        }
    }

    public void tc19(String x) {
        switch (x + "test") {
            default: ;
        }
    }

    public void tc20(String x) {
        switch (x + "test") {
            case "c" + "d":
            default:;
        }
    }

}
