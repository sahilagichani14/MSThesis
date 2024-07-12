package sootUp.RQ1.jb_tr;

import sootUp.RQ1.jb_ls.JB_LS;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("SootUp JB_TR Type Assigner Body Interceptor");
        new BaseSetup().setUp(JB_TR.class.getName());
    }
}
