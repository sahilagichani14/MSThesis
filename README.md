# README for Running RQ1, RQ2, and RQ3 Experiments

- If you want to run without building use jars in eval_results and run by:
  - command = ["java", "-XX:+UseG1GC", "-XX:+UseAdaptiveSizePolicy", "-Xmx1024m", "-Xss1024m", "-jar", executable, "inputjar" , "solver", max_method, "cg_algo", "thread", "bodyinterceptors"]
  - inputjar are present in IDELinearConstantAnalysisClientSoot/src/test/resources/latest

## RQ1: Generating Jimple and Verifying Body Transformers/Interceptors

- Before generating Jimple file, ensure the following body interceptors are turned on: jb,ls,jb.tr,jb.lns (since parser cannot take unknown nodes & doesn't recognise # var created by ls)
- Generate Jimple File: 
  - Uncomment the generateJimpleOutputClass() method in BaseSetUp
  - This will generate the Jimple file located at sootupRes/sootUp.RQ1.jb_cp.JB_CP.jimple.
- Copy Jimple File:
  - Copy the generated Jimple file from sootupRes/sootUp.RQ1.jb_cp.JB_CP.jimple.
  - Paste it into jimplesrc/sootupjimplesrc/sootUp.RQ1.jb_cp.JB_CP.jimple.
- Run the Jimple-to-Bytecode Program:
  - Run the program located at src/main/java/soot/sootupjimpletobytecode
- Check Bytecode Output: The bytecode output will be located in sootOutput/sootUp or with respective package structure
- run by going to sootOutput/sootUp in cmd use "java sootUp.RQ1.jb_cp.JB_CP"

## RQ2 & RQ3: Performance Comparisons for Soot & SootUp

- For both Soot and SootUp, use the following command-line arguments to run the experiments: 
  - javapoet-1.13.0.jar default 50 CHA 1 jb.ls,jb.tr,jb.a