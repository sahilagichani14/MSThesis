package soot.RQ1.jb_ls;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Apply Body Transformer");
        List<String> listOfBodyInterceptors = new ArrayList<>();
        //listOfBodyInterceptors.add(JB_CBF.class.getName());
        //listOfBodyInterceptors.add(JB_LS.class.getName());
        listOfBodyInterceptors.add(JB_LS.class.getName());
        new BaseSetup().executeStaticAnalysis(listOfBodyInterceptors);
    }
}
