package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Filter to set text all upper case
 *
 * @author Olivier Liechti & Moïn DANAI
 */
public class UpperCaseFilterWriter extends FilterWriter {

  public UpperCaseFilterWriter(Writer wrappedWriter) {
    super(wrappedWriter);
  }

  /**
   * Write a string in upper case
   *
   * @param str string
   * @param off offset in {@code str}
   * @param len how many characters to write
   */
  @Override
  public void write(String str, int off, int len) throws IOException {
    write(str.toCharArray(), off, len);
  }

  /**
   * Write a char array in upper case
   *
   * @param cbuf char array
   * @param off  offset in {@code cbuf}
   * @param len  how many characters to write
   */
  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for (int i = off; i < len + off; ++i) {
      write(cbuf[i]);
    }
  }

  /**
   * Write a single character in upper case
   *
   * @param c character as int
   */
  @Override
  public void write(int c) throws IOException {
    this.out.write(Character.toUpperCase(c));
  }
}
