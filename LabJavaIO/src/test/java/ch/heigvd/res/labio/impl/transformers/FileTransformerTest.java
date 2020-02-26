package ch.heigvd.res.labio.impl.transformers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author Olivier Liechti
 */
public class FileTransformerTest {
  
  @Test
  public void itShouldDuplicateATextFile() throws IOException {
    FileUtils.deleteDirectory(new File("./target/tmp"));
    new File("./target/tmp").mkdir();
    FileTransformer ft = new NoOpFileTransformer();
    File inputFile = new File("./target/tmp/test.txt");
    File outputFile = new File("./target/tmp/test.txt.out");
    OutputStreamWriter writer = new OutputStreamWriter( new FileOutputStream(inputFile), "UTF-8" );
    writer.write("Les bons élèves sont tous très assidus.\nLes bons maîtres sont appliqués.");
    writer.flush();
    writer.close();
    ft.visit(inputFile);
    assertTrue( FileUtils.contentEquals(inputFile, outputFile) );
    FileUtils.deleteDirectory(new File("./target/tmp"));
  }
  
}
