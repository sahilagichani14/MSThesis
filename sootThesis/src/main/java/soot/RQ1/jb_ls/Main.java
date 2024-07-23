package soot.RQ1.jb_ls;

import RQ1.jb_ls.tc1.TC1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Apply JB_LS LocalSplitter Body Transformer");
        List<String> listOfBodyInterceptors = new ArrayList<>();
        listOfBodyInterceptors.add(RQ1.jb_ls.tc1.Main.class.getName());
        new BaseSetup().executeStaticAnalysis(listOfBodyInterceptors);
    }
}
