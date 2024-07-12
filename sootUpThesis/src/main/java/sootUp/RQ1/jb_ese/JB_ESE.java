package sootUp.RQ1.jb_ese;

public class JB_ESE {

    public void tc1(int x){
        switch (x) {
            default:
                System.out.println("Default case");
        }
    }
    public void tc2(int x){
        switch (x) {
            case 1:
                System.out.println("Case 1");
                break;
            default:
                System.out.println("Default case");
        }
    }
    public void tc3(int x){
        switch (x) {
        }
    }
    public void tc4(int x){
        switch (x) {
            default:
            case 1:
                System.out.println("Case 1 or Default");
        }
    }
    public void tc5(int x){
        switch (x) {
            case 1:
            case 2:
                System.out.println("Empty Case Blocks");
                break;
            default:
                System.out.println("Default case");
        }
    }
    public void tc6(int x){
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
    public void tc7(int x){
        switch (x) {
            case 1:
            case 2:
            default:
                System.out.println("Empty Cases and Default");
        }
    }
    public void tc8(int x){
        switch (x) {
            case 1:
                System.out.println("case 1");
                break;
            case 2:
                System.out.println("case 2");
                break;
        }
    }
    public void tc9(int x){
        switch (x) {
            default:
                System.out.println("default case");
                break;
        }
    }
    public void tc10(){
        switch (2) {
            default:
                System.out.println("fixed");
        }
    }

}
