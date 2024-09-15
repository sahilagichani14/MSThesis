package sootUp.RQ1.jb_uce;

import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.core.model.Body;
import sootup.core.model.SootClass;
import sootup.core.model.SootMethod;
import sootup.core.types.ClassType;
import sootup.core.util.printer.JimplePrinter;
import sootup.interceptors.UnreachableCodeEliminator;
import sootup.java.bytecode.frontend.inputlocation.JavaClassPathAnalysisInputLocation;
import sootup.java.core.JavaSootClass;
import sootup.java.core.views.JavaView;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class BaseSetup {
    public void setUp(String targetClassName) throws FileNotFoundException {

        String classPath = System.getProperty("user.dir") + File.separator + "sootUpThesis" + File.separator + "target" + File.separator + "classes";
        //String pathToBinary = "src/main/java/upb/thesis/latestsootup/example.jar";
//        AnalysisInputLocation inputLocation = new JavaClassPathAnalysisInputLocation(classPath, null,
//                List.of(new ConditionalBranchFolder()));
        // if nothing passed then 8 by default BI are applied which are defined in ByteCodeBodyInterceptors
        AnalysisInputLocation inputLocation = new JavaClassPathAnalysisInputLocation(classPath, null, Collections.emptyList());
        JavaView view = new JavaView(inputLocation);
        Collection<JavaSootClass> viewClasses = view.getClasses().toList();
        System.out.println(viewClasses);
        ClassType classType =
                view.getIdentifierFactory().getClassType("sootUp.RQ1.jb_uce.JB_UCE");
        System.out.println(classType);
        JavaSootClass sootClass = view.getClass(classType).get();
        System.out.println(sootClass);

//        MethodSignature methodSignature = view.getIdentifierFactory()
//                .getMethodSignature("RQ1.jb_ls.SampleClass", "localSplitterTest", "void", Collections.emptyList());
//        System.out.println(methodSignature.getName());

        generateOutputDirs(sootClass, view);
        //generateJimpleOutputClass(sootClass, targetClassName);
    }

    public void generateJimpleOutputClass(SootClass sootClass, String targetClassName) throws FileNotFoundException {
        //JimplePrinter printer = new JimplePrinter(JimplePrinter.Option.LegacyMode);
        //PrintWriter qPrintWriter = new PrintWriter(new StringWriter());
        //printer.printTo(sootClass, qPrintWriter);
        //System.out.println(qPrintWriter);

        String fileName = "sootupRes/" + targetClassName + ".jimple";
        JimplePrinter printer1 = new JimplePrinter(JimplePrinter.Option.LegacyMode);
        OutputStream streamOut = new FileOutputStream(fileName);
        PrintWriter writerOut = new PrintWriter(new OutputStreamWriter(streamOut));
        printer1.printTo(sootClass, writerOut);
        writerOut.flush();
    }

    private static void generateOutputDirs(SootClass sootClass, JavaView view) {
        String userdir = System.getProperty("user.dir");
        String resDir = userdir + File.separator + "sootupRes";
        if (!Files.exists(Paths.get(resDir))){
            File createResDir = new File(resDir);
            createResDir.mkdir();
        }
        String sootClassDir = resDir + File.separator + sootClass.getName();
        if (!Files.exists(Paths.get(sootClassDir))) {
            File createSootClassDir = new File(sootClassDir);
            createSootClassDir.mkdir();
        }

        Set<? extends SootMethod> sootClassMethods = sootClass.getMethods();
        for (SootMethod sootClassMethod: sootClassMethods) {
            String methodName = sootClassMethod.getSignature().getSubSignature().toString();
            if (methodName.contains("init")){
                continue;
            }
            String sootMethodDir = sootClassDir + File.separator + methodName;
            if (!Files.exists(Paths.get(sootMethodDir))){
                File createSootMethodDir = new File(sootMethodDir);
                createSootMethodDir.mkdir();
            }

            String input = sootClassMethod.getBody().toString();
            Body.BodyBuilder builder = Body.builder(sootClassMethod.getBody(), Collections.emptySet());
            new UnreachableCodeEliminator().interceptBody(builder, view);

            String output = builder.getStmtGraph().toString();

            generateOutput(sootMethodDir, input, output);
        }
    }

    private static void generateOutput(String sootMethodDir, String input, String output) {

        // Create a File object for the file
        File file = new File(sootMethodDir + File.separator + "input");
        // Write content to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(input);
            //System.out.println("File created and content written successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the input file.");
            e.printStackTrace();
        }

        File outfile = new File(sootMethodDir + File.separator + "output");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outfile))) {
            writer.write(output);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the output file.");
            e.printStackTrace();
        }
    }

}

