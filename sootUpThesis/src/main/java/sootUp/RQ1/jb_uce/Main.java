package sootUp.RQ1.jb_uce;

import sootUp.RQ1.jb_ls.JB_LS;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("SootUp JB_UCE Unreachable Code Eliminator Body Interceptor");
        new BaseSetup().setUp(JB_UCE.class.getName());
    }
}
