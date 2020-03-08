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
    super.out.write(str.toUpperCase(), off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    char[] bufUpper = new char[cbuf.length];
    for (int i=0; i<cbuf.length; ++i) {
      bufUpper[i] = Character.toUpperCase(cbuf[i]);
    }
    super.out.write(bufUpper, off, len);
  }

  @Override
  public void write(int c) throws IOException {
    super.out.write((int)Character.toUpperCase((char)c));
  }

}
