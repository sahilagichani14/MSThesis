package soot.RQ1.jb_cp_ule;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Apply JB_CP_ULE UnusedLocalEliminator Body Transformer");
        List<String> listOfBodyInterceptors = new ArrayList<>();
        listOfBodyInterceptors.add(JB_CP_ULE.class.getName());
        new BaseSetup().executeStaticAnalysis(listOfBodyInterceptors);
    }
}
