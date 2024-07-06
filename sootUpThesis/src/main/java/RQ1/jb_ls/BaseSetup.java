package RQ1.jb_ls;

import sootup.core.Project;
import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.core.model.Body;
import sootup.core.model.SootClass;
import sootup.core.model.SootMethod;
import sootup.core.signatures.MethodSignature;
import sootup.core.types.ClassType;
import sootup.core.views.View;
import sootup.java.bytecode.inputlocation.JavaClassPathAnalysisInputLocation;
import sootup.java.bytecode.interceptors.LocalSplitter;
import sootup.java.core.JavaProject;
import sootup.java.core.JavaSootClass;
import sootup.java.core.JavaSootClassSource;
import sootup.java.core.JavaSootMethod;
import sootup.java.core.language.JavaLanguage;
import sootup.java.core.views.JavaView;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class BaseSetup {
    public void setUp() {

        String classPath = System.getProperty("user.dir") + File.separator + "target" + File.separator + "classes";
        //String pathToBinary = "src/main/java/upb/thesis/latestsootup/example.jar";
//        AnalysisInputLocation inputLocation = new JavaClassPathAnalysisInputLocation(classPath, null,
//                List.of(new ConditionalBranchFolder()));
        // if nothing passed then 8 by default BI are applied which are defined in ByteCodeBodyInterceptors
        //AnalysisInputLocation inputLocation = new JavaClassPathAnalysisInputLocation(classPath, null, Collections.emptyList());
        //JavaView view = new JavaView(inputLocation);

        AnalysisInputLocation<JavaSootClass> inputLocation =
                new JavaClassPathAnalysisInputLocation(classPath);
        JavaLanguage language = new JavaLanguage(8);
        Project project = JavaProject.builder(language).addInputLocation(inputLocation).build();
        View view = project.createView();

        Collection<JavaSootClass> viewClasses = view.getClasses();
        System.out.println(viewClasses);
        ClassType classType =
                view.getIdentifierFactory().getClassType("upb.thesis.RQ1.jb_ls.SampleClass");
        System.out.println(classType);
        JavaSootClass sootClass = (JavaSootClass) view.getClass(classType).get();
        System.out.println(sootClass);

        MethodSignature methodSignature = view.getIdentifierFactory()
                .getMethodSignature("upb.thesis.RQ1.jb_ls.SampleClass", "localSplitterTest", "void", Collections.emptyList());
        System.out.println(methodSignature.getName());

        generateOutputDirs(sootClass, view);

        /*
        Set<? extends JavaSootMethod> sootMethods =  sootClass.getMethods();
        for (JavaSootMethod jsm: sootMethods){
            boolean condition = jsm.getSignature().getSubSignature().getName().equals(methodSignature.getSubSignature().getName());
            if (condition){
                System.out.println("========Before Jimple Body=========");
                System.out.println(jsm.getBody());
                System.out.println("========Transformed Body=========");
                Body.BodyBuilder builder = Body.builder(jsm.getBody(), Collections.emptySet());
                new LocalSplitter().interceptBody(builder, view);
                //new CopyPropagator().interceptBody(builder, view);
                //new ConditionalBranchFolder().interceptBody(builder, view);
                //System.out.println(builder.getStmtGraph().toString().trim());
                System.out.println(builder.getStmtGraph().toString());
            }
        }
        */
    }

    private static void generateOutputDirs(SootClass sootClass, View view) {
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
            new LocalSplitter().interceptBody(builder, view);

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

