package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Olivier Liechti, Robin Demarta
 */
public class UpperCaseFilterWriter extends FilterWriter {
  
  public UpperCaseFilterWriter(Writer wrappedWriter) {
    super(wrappedWriter);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    out.write(str.substring(off, off + len).toUpperCase());
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    String result = "";

    for(int i = off; i < off + len; ++i)
      result += Character.toUpperCase(cbuf[i]);

    out.write(result);
  }

  @Override
  public void write(int c) throws IOException {
    out.write(Character.toUpperCase(c));
  }

}
