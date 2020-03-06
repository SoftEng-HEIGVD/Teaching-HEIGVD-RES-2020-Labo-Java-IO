package ch.heigvd.res.labio.impl.filters;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriterTest {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriterTest.class.getName());

  @Test
  public void itShouldPrintANumberForFileWithOneLine() throws IOException {
    String line = "this is a line\n";
    StringWriter stringWriter = new StringWriter();
    FileNumberingFilterWriter writer = new FileNumberingFilterWriter(stringWriter);
    writer.write(line);
    assertEquals("1\t" + line + "2\t", stringWriter.toString());
  }

  @Test
  public void itShouldPrintANumberForFileWithTwoLines() throws IOException {
    String line1 = "this is a line\n";
    String line2 = "this is a second line\n";
    StringWriter stringWriter = new StringWriter();
    FileNumberingFilterWriter writer = new FileNumberingFilterWriter(stringWriter);
    writer.write(line1);
    writer.write(line2);
    assertEquals("1\t" + line1 + "2\t" + line2 + "3\t", stringWriter.toString());
  }
  
  @Test
  public void itShouldWorkIfThereIsNoNewLineAtTheEnd() throws IOException {
    String line1 = "this is a line\n";
    String line2 = "this is a second line";
    StringWriter stringWriter = new StringWriter();
    FileNumberingFilterWriter writer = new FileNumberingFilterWriter(stringWriter);
    writer.write(line1);
    writer.write(line2);  
    assertEquals("1\t" + line1 + "2\t" + line2, stringWriter.toString());
  }

  @Test
  public void itShouldWorkWithPartialLines() throws IOException {
    String line1_1 = "this is";
    String line1_2 = "a first line\n";
    String line2 = "this is a second line";
    StringWriter stringWriter = new StringWriter();
    FileNumberingFilterWriter writer = new FileNumberingFilterWriter(stringWriter);
    writer.write(line1_1);
    writer.write(line1_2);
    writer.write(line2);  
    assertEquals("1\t" + line1_1 + line1_2 + "2\t" + line2, stringWriter.toString());
  }

  @Test
  public void itShouldHandleWriteWithPartOfAString() throws IOException {
    String line = "I only want part of this line";
    StringWriter stringWriter = new StringWriter();
    FileNumberingFilterWriter writer = new FileNumberingFilterWriter(stringWriter);
    int startIndex = 2;
    int len = 4;
    writer.write(line, startIndex, len);
    assertEquals("1\t" + "only", stringWriter.toString());
  }

  @Test
  public void itShouldHandleWriteWithAnInt() throws IOException {
    String line = "This is line 1\r\nThis is line 2\nThis is line 3";
    StringWriter stringWriter = new StringWriter();
    FileNumberingFilterWriter writer = new FileNumberingFilterWriter(stringWriter);
    for (int i=0; i<line.length(); i++) {
      int c = line.charAt(i);
      writer.write(c);
    }
    assertEquals("1\tThis is line 1\r\n2\tThis is line 2\n3\tThis is line 3", stringWriter.toString());
  }

  @Test
  public void itShouldWorkOnUnix() throws IOException {
    String line = "This is line 1\nThis is line 2\nThis is line 3";
    String expected = "1\tThis is line 1\n2\tThis is line 2\n3\tThis is line 3";
    StringWriter stringWriter = new StringWriter();
    FileNumberingFilterWriter writer = new FileNumberingFilterWriter(stringWriter);
    writer.write(line);
    assertEquals(expected, stringWriter.toString());
  }

  @Test
  public void itShouldWorkOnMacOS9() throws IOException {
    String line = "This is line 1\rThis is line 2\rThis is line 3";
    String expected = "1\tThis is line 1\r2\tThis is line 2\r3\tThis is line 3";
    StringWriter stringWriter = new StringWriter();
    FileNumberingFilterWriter writer = new FileNumberingFilterWriter(stringWriter);
    writer.write(line);
    assertEquals(expected, stringWriter.toString());
  }

  @Test
  public void itShouldWorkOnWindows() throws IOException {
    String line = "This is line 1\r\nThis is line 2\r\nThis is line 3";
    String expected = "1\tThis is line 1\r\n2\tThis is line 2\r\n3\tThis is line 3";
    StringWriter stringWriter = new StringWriter();
    FileNumberingFilterWriter writer = new FileNumberingFilterWriter(stringWriter);
    writer.write(line);
    assertEquals(expected, stringWriter.toString());
  }

  @Test
  public void itShouldWorkWhenThereAreOneTwoOrThreeDigitsInTheLineNumber() throws IOException {
    StringWriter referenceWriter = new StringWriter();
    StringWriter stringWriter = new StringWriter();
    FileNumberingFilterWriter writer = new FileNumberingFilterWriter(stringWriter);
    for (int i=1; i< 120; i++) {
      writer.write("content\n");
      referenceWriter.write(i + "\tcontent\n");
    }
    writer.write("last");
    referenceWriter.write(120 + "\tlast");
    
    stringWriter.close();
    String computedValue = stringWriter.toString();
    String referenceValue = referenceWriter.toString();
    assertEquals(referenceValue, computedValue);
  }

}
