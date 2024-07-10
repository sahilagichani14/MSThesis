package soot.RQ1.jb_lp;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Apply JB_LP Local Packer Body Transformer");
        List<String> listOfBodyInterceptors = new ArrayList<>();
        listOfBodyInterceptors.add(JB_LP.class.getName());
        new BaseSetup().executeStaticAnalysis(listOfBodyInterceptors);
    }
}
