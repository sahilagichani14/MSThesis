package upb.thesis;

import javax.annotation.Nullable;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Utils {

  @Nullable
  public static Path compileJavaOTF(Path wkd, Path sourceFile) {
    // Compile source file.
    if (!Files.exists(sourceFile)) {
      throw new IllegalStateException("Source not found");
    }
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    final boolean b;
    try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null)) {
      Iterable<String> options = List.of("--source-path", wkd.toString());

      Iterable<? extends JavaFileObject> filesToCompile =
          fileManager.getJavaFileObjectsFromFiles(List.of(sourceFile.toFile()));
      JavaCompiler.CompilationTask task =
          compiler.getTask(null, fileManager, null, options, null, filesToCompile);

      b = task.call();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    if (b) {
      final File file = new File(sourceFile.toString().replace(".java", ".class"));
      //                  file.deleteOnExit();
      return file.toPath();
    }
    return null;
  }
}
