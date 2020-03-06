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
      out.write(str.substring(off, off + len).toUpperCase());
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    StringBuilder tmp = new StringBuilder();
    for (int i = off; i < off + len; i++) {
      tmp.append(Character.toUpperCase(cbuf[i]));
    }
    out.write(tmp.toString());

    /* Alternatively, we could have written:
    for (int i = off; i < off + len; i++) {
      write(cbuf[i]);
    }
    Which would use our own implementation, but it'd probably be way less efficient...
    We should test that though...
     */
  }

  @Override
  public void write(int c) throws IOException {
      out.write(Character.toUpperCase(c));
  }

}
