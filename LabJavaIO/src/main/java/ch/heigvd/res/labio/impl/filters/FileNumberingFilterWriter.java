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

  /**
   * An enumeration that represents the different states that the writer
   * can be in regarding the last character that was written.
   */
  private enum State { Empty, Normal, RetN, RetR }

  private int nextLineNumber = 1;
  private State state = State.Empty;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    this.write(str.toCharArray(), off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for (int i = off; i < off + len; i++) {
      write(cbuf[i]);
    }
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
        state = State.RetN;
        break;
      case '\r':
        state = State.RetR;
        break;
      default:
        state = State.Normal;
        break;
    }
  }

  @Override
  public void write(int c) throws IOException {

    if (state == State.Empty) {
      output();
    } else if (state == State.RetR) {
      if (c != '\n') {
        output();
      }
    }

    out.write(c);
    setState(c);

    if (state == State.RetN)
      output();
  }

}
