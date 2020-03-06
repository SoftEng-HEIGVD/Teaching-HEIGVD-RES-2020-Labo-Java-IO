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
    String input = str.toUpperCase();
    out.write(input,off,len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    this.write(String.copyValueOf(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {
    out.write(String.format("%c",(char) c).toUpperCase());
  }
}
