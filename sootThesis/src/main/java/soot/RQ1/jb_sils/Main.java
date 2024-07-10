package soot.RQ1.jb_sils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Apply JB_SILS Shared Initialization Local Splitter Body Transformer");
        List<String> listOfBodyInterceptors = new ArrayList<>();
        listOfBodyInterceptors.add(JB_SILS.class.getName());
        new BaseSetup().executeStaticAnalysis(listOfBodyInterceptors);
    }
}
