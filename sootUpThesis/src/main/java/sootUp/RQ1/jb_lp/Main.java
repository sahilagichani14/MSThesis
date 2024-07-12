package sootUp.RQ1.jb_lp;

import sootUp.RQ1.jb_ls.JB_LS;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("SootUp JB_LP Local Packer Body Interceptor");
        new BaseSetup().setUp(JB_LP.class.getName());
    }
}
