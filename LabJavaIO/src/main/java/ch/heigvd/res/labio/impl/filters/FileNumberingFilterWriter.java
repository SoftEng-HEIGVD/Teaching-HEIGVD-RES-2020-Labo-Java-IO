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
 * <p>
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  private static final int STATE_EMPTY = -1;
  private static final int STATE_LAST_CHAR_NORMAL = 0;
  private static final int STATE_LAST_CHAR_RET_N = 1;
  private static final int STATE_LAST_CHAR_RET_R = 2;

  private int nextLineNumber = 1;
  private int state = STATE_EMPTY;

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
    this.write(new String(cbuf), off, len);
  }

  /**
   * Outputs the current line number and increments it for the next
   * iteration. The number will be written at the current position in
   * the writer.
   *
   * @throws IOException If there is an error write writing the number.
   */
  private void output() throws IOException {
    out.write(nextLineNumber++ + "\t");
  }

  /**
   * Sets the state of the {@link FileNumberingFilterWriter}, to make the
   * state machine in the write method work properly.
   *
   * @param c The last written character.
   */
  private void setState(int c) {
    switch (c) {
      case '\n':
        state = STATE_LAST_CHAR_RET_N;
        break;
      case '\r':
        state = STATE_LAST_CHAR_RET_R;
        break;
      default:
        state = STATE_LAST_CHAR_NORMAL;
        break;
    }
  }

  private void outputIfNeeded() throws IOException {
    if (state == STATE_LAST_CHAR_RET_N)
      output();
  }

  @Override
  public void write(int c) throws IOException {

    if (state == STATE_EMPTY) {
      output();
    } else if (state == STATE_LAST_CHAR_RET_R) {
      if (c != '\n') {
        output();
      }
    }

    out.write(c);
    setState(c);
    outputIfNeeded();
  }

}
