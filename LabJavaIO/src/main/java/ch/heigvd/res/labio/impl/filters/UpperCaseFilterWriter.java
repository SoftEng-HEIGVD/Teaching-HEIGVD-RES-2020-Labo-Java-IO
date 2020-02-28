package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Olivier Liechti
 */
public class UpperCaseFilterWriter extends FilterWriter {
  
  public UpperCaseFilterWriter(Writer wrappedWriter) {
    super(wrappedWriter);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    String strUpper = str.substring(off, off+len).toUpperCase();
    super.write(strUpper, 0, strUpper.length());
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    String strUpper = new String(cbuf, off, len).toUpperCase();
    super.write(strUpper, 0, strUpper.length());
  }

  @Override
  public void write(int c) throws IOException {
    String str = new String();
    str += (char) c;
    super.write(str.toUpperCase(), 0, 1);
  }

}
