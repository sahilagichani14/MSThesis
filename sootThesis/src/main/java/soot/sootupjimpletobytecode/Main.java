package soot.sootupjimpletobytecode;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Convert SootUp produced jimple to bytecode using Soot");
        BaseSetup.setupSoot();
    }
}
