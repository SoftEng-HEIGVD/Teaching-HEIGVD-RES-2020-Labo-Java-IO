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
 * Modified by Julien Béguin on 08.03.2020
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int index = 0;
  private char lastChar = '\0';

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  private String custom(int c) {
    StringBuilder sb = new StringBuilder();

    if (index == 0 || (lastChar == '\r' && c != '\n')) {
      sb.append(++index).append('\t');
    }

    sb.append((char) c);
    lastChar = (char) c;

    if (c == '\n') {
      sb.append(++index).append('\t');
    }

    return sb.toString();
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    if (off < 0 || len < 1 || off + len > str.length()) {
      throw new IOException("Invalid arguments. Offset or length not in range.");
    }
    StringBuilder sb = new StringBuilder();
    for (int i = off; i < off + len; i++) {
      sb.append(custom(str.charAt(i)));
    }
    out.write(sb.toString());
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    write(new String(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {
    out.write(custom(c));
  }

}
