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
 * Hello\n\tWorld -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti & Moïn DANAI
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private static final char[] NEWLINES = new char[] { '\r', '\n' };

  private int ctr = 1;
  private boolean newlineR = false;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  /**
   * Writes the given string from {@code off} to {@code off+len}, with line number at
   * the beginning of each line.
   *
   * @param cbuf string to write
   * @param off  index offset
   * @param len  how many characters to write
   */
  @Override
  public void write(String str, int off, int len) throws IOException {
    for (int i = off; i < off + len; ++i) {
      this.write(str.charAt(i));
    }
  }

  /**
   * Writes the given char array from {@code off} to {@code off+len}, with line number
   * at the beginning of each line.
   *
   * @param cbuf char array to write
   * @param off  index offset
   * @param len  how many characters to write
   */
  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for (int i = off; i < off + len; ++i) {
      this.write(cbuf[i]);
    }
  }

  /**
   * Writes the given character (as {@code int}) with line number
   * at the beginning of each line.
   * <p>
   * This method actually contains the line numbering logic.
   *
   * @param c character to write
   */
  @Override
  public void write(int c) throws IOException {
    if (ctr == 1) { // first line
      this.out.write(String.valueOf(ctr++));
      this.out.write('\t');
      this.out.write(c);
    } else if (c == NEWLINES[1]) { // \n detected
      newlineR = false; // reset \r flag
      this.out.write(c);
      this.out.write(String.valueOf(ctr++));
      this.out.write('\t');
    } else if (c == NEWLINES[0]) { // \r detected
      newlineR = true; // set flag for \n
      this.out.write(c);
    } else if (newlineR) { // text after \r
      newlineR = false;
      this.out.write(String.valueOf(ctr++));
      this.out.write('\t');
      this.out.write(c);
    } else { // normal text
      this.out.write(c);
    }
  }
}
