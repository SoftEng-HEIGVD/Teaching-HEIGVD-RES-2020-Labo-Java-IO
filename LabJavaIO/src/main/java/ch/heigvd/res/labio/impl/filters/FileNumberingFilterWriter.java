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
 * <p>
 * Hello\n\tWorld -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti & Moïn DANAI
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private static final char[] NEWLINES = new char[]{'\r', '\n'};

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    write(str.toCharArray(), off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    Integer ctr = 1;
    write(ctr);

    for (int i = off; i < off + len; ++i) {
      if (i != off + len - 1) {
        if (Arrays.binarySearch(NEWLINES, cbuf[i]) < 0 && !(cbuf[i+1] == NEWLINES[0] || cbuf[i+1] == NEWLINES[1])) {
          write(++ctr);
          write('\t');
        }
      }
      write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    this.out.write(c);
  }

}
