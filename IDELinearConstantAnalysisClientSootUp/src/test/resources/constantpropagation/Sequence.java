package constantpropagation;

public class Sequence {

    public  class CallSequence {


        public  void foo() {
            // no-op
        }

        public  void bar() {
            // no-op
        }

        public  int sth() {
            return 2;
            // no-op
        }
        public  void abc() {
            // no-op
        }

    }
    public static int doSomething(){
        return 1;
    }

    public void entryPoint() {
        CallSequence callSequence = new CallSequence();
        int a = 1;
        int b=2;
        int c =a+b;
        callSequence.foo();
        callSequence.bar();
        int x = doSomething();
        int some = callSequence.sth();
        callSequence.abc();
    }

}
