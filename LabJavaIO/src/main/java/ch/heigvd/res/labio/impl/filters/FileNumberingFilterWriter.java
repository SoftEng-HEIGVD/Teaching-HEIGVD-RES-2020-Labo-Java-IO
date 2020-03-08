package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 * @author Tiffany Bonzon
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  private int lineNumber;
  private boolean isFirstChar;
  private char lastChar;

  private void SBMultipleAppend(StringBuilder sb, String... strings) {
    for (String string : strings) {
      sb.append(string);
    }
  }

  public FileNumberingFilterWriter(Writer out) {
    super(out);
    lineNumber = 0;
    isFirstChar = true;
    lastChar = '\0';
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    write(str.toCharArray(), off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for(int i = off; i < off + len; ++i) {
      write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    StringBuilder sb = new StringBuilder();

    if(isFirstChar) {
      SBMultipleAppend(sb, String.valueOf(++lineNumber), "\t");
      isFirstChar = false;
    }

    if((char) c == '\n') {
      SBMultipleAppend(sb, "\n", String.valueOf(++lineNumber), "\t");
    } else {
      if(lastChar == '\r') {
        SBMultipleAppend(sb, String.valueOf(++lineNumber), "\t");
      }

      sb.append((char) c);
    }

    lastChar = (char) c;

    super.write(sb.toString(), 0, sb.length());
  }
}
