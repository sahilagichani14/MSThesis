package sootUp.RQ1.jb_ls;

import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.core.model.Body;
import sootup.core.model.SootClass;
import sootup.core.model.SootMethod;
import sootup.core.model.SourceType;
import sootup.core.util.printer.JimplePrinter;
import sootup.interceptors.LocalSplitter;
import sootup.interceptors.TypeAssigner;
import sootup.java.bytecode.frontend.inputlocation.JavaClassPathAnalysisInputLocation;
import sootup.java.core.JavaSootClass;
import sootup.java.core.views.JavaView;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BaseSetup {
    public void setUp() throws FileNotFoundException {

        //String classPath = System.getProperty("user.dir") + File.separator + "sootUpThesis" + File.separator + "target" + File.separator + "classes";
        //String pathToBinary = "src/main/java/upb/thesis/latestsootup/example.jar";
//        AnalysisInputLocation inputLocation = new JavaClassPathAnalysisInputLocation(classPath, null,
//                List.of(new ConditionalBranchFolder()));
        // if nothing passed then 8 by default BI are applied which are defined in ByteCodeBodyInterceptors

        String userdir = System.getProperty("user.dir");
        String tcpath = userdir + File.separator + "test-case-resources" + File.separator + "target" + File.separator + "classes";

        AnalysisInputLocation inputLocation = new JavaClassPathAnalysisInputLocation(tcpath, SourceType.Application, Collections.singletonList(new TypeAssigner()));
        JavaView view = new JavaView(inputLocation);
        Collection<JavaSootClass> viewClasses = view.getClasses().toList();
        System.out.println(viewClasses);
        List<JavaSootClass> javaSootClassList = viewClasses.stream().filter(viewClass -> viewClass.getName().contains("RQ1.jb_ls")).collect(Collectors.toList());

//        ClassType classType =
//                view.getIdentifierFactory().getClassType("sootUp.RQ1.jb_ls.JB_LS");
//        System.out.println(classType);
//
//        JavaSootClass sootClass = view.getClass(classType).get();
//        System.out.println(sootClass);

//        MethodSignature methodSignature = view.getIdentifierFactory()
//                .getMethodSignature("RQ1.jb_ls.SampleClass", "localSplitterTest", "void", Collections.emptyList());
//        System.out.println(methodSignature.getName());

        javaSootClassList.forEach(sootClass -> {
            try {
                generateOutputDirs(sootClass, view);
                generateJimpleOutputClass(sootClass);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void generateJimpleOutputClass(SootClass sootClass) throws IOException {
        //JimplePrinter printer = new JimplePrinter(JimplePrinter.Option.LegacyMode);
        //PrintWriter qPrintWriter = new PrintWriter(new StringWriter());
        //printer.printTo(sootClass, qPrintWriter);
        //System.out.println(qPrintWriter);

        String fileName = sootClass.getType().getPackageName() + "." + sootClass.getType().getClassName();
        String directoryPath = "sootupRes/sootupjimplesrc/";

        // Create the directory if it does not exist
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean dirCreated = directory.mkdirs();
            if (!dirCreated) {
                System.err.println("Failed to create directory: " + directoryPath);
                return;
            }
        }

        // Create and write to the file
        File file = new File(directory, fileName + ".jimple");
        try {
            JimplePrinter printer1 = new JimplePrinter(JimplePrinter.Option.LegacyMode);
            OutputStream streamOut = new FileOutputStream(file);
            PrintWriter writerOut = new PrintWriter(new OutputStreamWriter(streamOut));
            printer1.printTo(sootClass, writerOut);
            writerOut.flush();
            //System.out.println("File written successfully: " + file.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

