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
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private Integer lineC           = 0;
  private boolean endWordR        = false;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  private void addLine() throws IOException
  {
    String toSend = String.valueOf(++lineC) + '\t';
    super.write(toSend, 0, toSend.length());
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    for (int pos = off; pos < off + len;pos++) {
      this.write(str.charAt(pos));
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    this.write(new String(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {
    if (lineC == 0 || lineC == null) {
      this.addLine();
    }

    if (c == '\n') {
      super.write(c);
      this.addLine();
    } else {
      if (endWordR)
        this.addLine();
      super.write(c);
    }
    if (c == '\r') {
      endWordR = true;
    }else {
      endWordR = false;
    }
  }

}
