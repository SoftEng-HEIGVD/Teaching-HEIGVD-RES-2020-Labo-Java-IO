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
     out.write(str.toUpperCase(), off, len);

     /* // ALTERNATIVE
     for(int i = off; i < len + off; ++i) {
          this.write(str.charAt(i));
     }*/
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for(int i = off; i < len + off; ++i) {
        this.write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    out.write(Character.toUpperCase(c));
  }

}
