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
    // throw new UnsupportedOperationException("The student has not implemented this method yet.");
    // suffit de faire le bon substring et d'uppercase le tout
    out.write(str.substring(off, off + len).toUpperCase());
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    // throw new UnsupportedOperationException("The student has not implemented this method yet.");
    // on parcours le tableau de char de off à off+len et d'uppercase le tout
    for (int i = 0; i < len; ++i) {
      out.write(Character.toUpperCase(cbuf[off + i]));
    }
  }

  @Override
  public void write(int c) throws IOException {
    // throw new UnsupportedOperationException("The student has not implemented this method yet.");
    // suffit d'uppercase le char reçu
    out.write(Character.toUpperCase(c));
  }

}
