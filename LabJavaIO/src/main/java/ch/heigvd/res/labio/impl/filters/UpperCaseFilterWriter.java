package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Olivier Liechti, Robin Cuenoud
 */
public class UpperCaseFilterWriter extends FilterWriter {
  
  public UpperCaseFilterWriter(Writer wrappedWriter) {
    super(wrappedWriter);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    out.write(str.toUpperCase(),off,len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    // char[] tempBuf = new char[len]; TODO determine if better to use a tempBuffer check exception (off + len > size)
    for(int i = off; i < len + off; ++i) {
      cbuf[i] = Character.toUpperCase(cbuf[i]);
    }
    out.write(cbuf,off,len);
  }

  @Override
  public void write(int c) throws IOException {
    out.write(Character.toUpperCase(c));
  }

}
