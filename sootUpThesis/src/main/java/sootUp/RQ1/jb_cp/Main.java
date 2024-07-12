package sootUp.RQ1.jb_cp;

import sootUp.RQ1.jb_ls.JB_LS;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("SootUp JB_CP Copy Propagator Body Interceptor");
        new BaseSetup().setUp(JB_CP.class.getName());
    }
}
