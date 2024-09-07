package constantpropagation;


public class Context4 {

    int increment(int a) {
        return a + 1;
    }

    int nestedCaller(int a){
        return nestedCaller1(a);
    }

    int nestedCaller1(int a){
        return increment(a);
    }

    /**
     * nested calls
     */
    public void entryPoint() {
        int a = 100;
        int b = 200;
        int c = increment(a);
        int d = nestedCaller(b);
    }

}
