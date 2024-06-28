# SootUp
#### Issues:
1. No Tutorials for beginners
2. cannot find below dependency

    <dependency>
      <groupId>com.github.soot-oss.SootUp</groupId>
      <artifactId>sootup</artifactId>
      <version>develop-SNAPSHOT</version>
    </dependency>

3. Warning:(60, 5)  Provides transitive vulnerable dependency maven:org.eclipse.platform:org.eclipse.core.resources:3.14.0 CVE-2023-4218 5.0 Improper Restriction of XML External Entity Reference vulnerability with Medium severity found  Results powered by Checkmarx(c) 

4. To get latest SootUp jar & add to project:
   git clone https://github.com/secure-software-engineering/SootUp.git
   cd SootUp (git bash or cmd)
   mvn install  -Dmaven.test.skip.exec -Dmaven.test.skip=true
   OR
   mvn clean install -DskipTests
   

4. To get latest SootUp jar & add to project:
   git clone https://github.com/secure-software-engineering/SootUp.git
   cd SootUp (git bash or cmd)
   mvn install  -Dmaven.test.skip.exec -Dmaven.test.skip=true
   OR
   mvn clean install -DskipTests
   
1_1_5, 1_5, 1_6 3, 3_1