package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

import ch.heigvd.res.labio.impl.Utils;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\tHello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  @SuppressWarnings("unused")
  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  private int numberOfLines = 0;
  private boolean lastCharIsR = false;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    for (int i = off; i < off + len; i++) {
      write(str.charAt(i));
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    write(String.valueOf(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {
    if (numberOfLines == 0) {
      writeLineNumberAndTab();
    }

    if (c == '\n') {
      super.write(c);
      writeLineNumberAndTab();
    } else {
      if (lastCharIsR) {
        writeLineNumberAndTab();
      }

      super.write(c);
    }

    lastCharIsR = (c == '\r');
  }

  private void writeLineNumberAndTab() throws IOException {
    out.write(++numberOfLines + "\t");
  }

}
