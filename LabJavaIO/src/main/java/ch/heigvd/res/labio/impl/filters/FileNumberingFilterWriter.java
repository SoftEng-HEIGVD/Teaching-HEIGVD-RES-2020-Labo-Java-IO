package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;
import java.util.regex.Pattern;

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

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    StringBuilder sb = new StringBuilder(str);
    int count = 1;
    for (int i = 0; i < str.length() ; i++) {
      if(str.charAt(i) == '\n')
        sb.insert(i,count++);
    }

    super.out.write(sb.toString());

  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    StringBuilder sb = new StringBuilder();
    int count = 1;
    for (char c : cbuf) {
      if (c == '\n')
        sb.append(count++);
      sb.append(c);
    }

    super.out.write(sb.toString().toCharArray(),off,len);
  }

  @Override
  public void write(int c) throws IOException {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
    super.out.write(c);
  }

}
