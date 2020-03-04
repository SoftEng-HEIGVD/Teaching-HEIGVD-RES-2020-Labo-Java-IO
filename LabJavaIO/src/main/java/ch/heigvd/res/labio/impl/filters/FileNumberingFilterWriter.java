package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
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
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  private int counter = 0;
  private boolean firstChar = true;
  private char lastChar;


  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    for (int i = off; i < off + len; ++i) {
      write(str.charAt(i));
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    String s = new String(cbuf);
    write(s, off, len);
  }

  @Override
  public void write(int c) throws IOException {
    StringBuilder s = new StringBuilder();
    char tmpCharacter = (char) c;
    if (firstChar) {
      s.append(++counter + "\t");
      firstChar = false;
    }

    if (c == '\n') {
      s.append("\n" + ++counter + "\t");
    } else {
      if (lastChar == '\r') {
        s.append(++counter + "\t");
      }
      s.append((char) c);
    }
    lastChar = tmpCharacter;

    super.write(s.toString(), 0, s.length());
  }


}
