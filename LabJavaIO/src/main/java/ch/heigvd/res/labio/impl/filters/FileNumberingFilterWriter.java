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

  private final int EMPTY = -1;
  private final int NORMAL = 0;
  private final int RET_N = 1;
  private final int RET_R = 2;

  private int previousChar = EMPTY;
  private int line = 1;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    for(int i = off; i < off + len; i++){
      write(str.charAt(i));
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for(int i = off; i < off + len; i++){
      write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    if (previousChar == EMPTY) {
      decorate();
    } else if (previousChar == RET_R && c != '\n') {
      decorate();
    }

    out.write(c);
    updatePreviousChar(c);

    if (previousChar == RET_N) {
      decorate();
    }

  }

  private void updatePreviousChar(int c) {
    switch(c) {
      case '\n':
        previousChar = RET_N;
        break;
      case '\r':
        previousChar = RET_R;
        break;
      default:
        previousChar = NORMAL;
        break;
    }
  }

  private void decorate() throws IOException {
    out.write(line++ + "\t");
  }
}
