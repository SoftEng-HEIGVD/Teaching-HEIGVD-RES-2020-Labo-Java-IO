package ch.heigvd.res.labio.impl.filters;

import ch.heigvd.res.labio.impl.Utils;

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

  private static enum State { Empty, RetR, RetN, Normal };
  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  private int nbLine;
  private State state;
  private boolean lastR;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
    nbLine = 1;
    state = State.Empty;
    lastR = false;
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
      write(str.toCharArray(), off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for (int i = off; i < off + len; i++)
      write(cbuf[i]);
  }

  private void printLine() throws IOException {
      out.write(nbLine++ + "\t");
  }

  @Override
  public void write(int c) throws IOException {
    switch (c) {
      case '\r':
        if (state == State.RetR) {
          out.write("\r");
          printLine();
        }

        state = State.RetR;
        break;

      case '\n':
        if (state == State.RetR)
          out.write("\r\n");
        else
          out.write("\n");

        printLine();
        state = State.RetN;
        break;
      default:
        if (state == State.RetR) {
          out.write("\r");
          printLine();
          state = State.Normal;
        }
        break;
    }

    if (state == State.Empty) {
      out.write(nbLine++ + "\t");
      state = State.Normal;
    }

    if (state == State.Normal) {
      out.write(c);
    } else if (state == State.RetN) {
      state = State.Normal;
    }
  }
}
