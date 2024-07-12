package sootUp.RQ1.jb_dtr;

import sootUp.RQ1.jb_ls.JB_LS;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        new BaseSetup().setUp(JB_LS.class.getName());
    }
}
