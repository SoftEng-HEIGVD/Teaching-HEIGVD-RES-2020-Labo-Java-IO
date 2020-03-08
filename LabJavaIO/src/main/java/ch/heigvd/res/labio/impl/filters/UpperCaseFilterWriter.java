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
    super.out.write(str.substring(off,off+len).toUpperCase());
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {

    write(String.copyValueOf(cbuf, off, len));
  }

  @Override
  public void write(int c) throws IOException {
    write(String.valueOf(new char[]{(char) c},0,1));
  }

}
