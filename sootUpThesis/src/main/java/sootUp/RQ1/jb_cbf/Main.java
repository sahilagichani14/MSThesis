package sootUp.RQ1.jb_cbf;

import sootUp.RQ1.jb_ls.JB_LS;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        new BaseSetup().setUp(JB_CBF.class.getName());
    }
}
