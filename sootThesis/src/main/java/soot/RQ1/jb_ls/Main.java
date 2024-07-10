package soot.RQ1.jb_ls;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Apply JB_LS LocalSplitter Body Transformer");
        List<String> listOfBodyInterceptors = new ArrayList<>();
        listOfBodyInterceptors.add(JB_LS.class.getName());
        new BaseSetup().executeStaticAnalysis(listOfBodyInterceptors);
    }
}
