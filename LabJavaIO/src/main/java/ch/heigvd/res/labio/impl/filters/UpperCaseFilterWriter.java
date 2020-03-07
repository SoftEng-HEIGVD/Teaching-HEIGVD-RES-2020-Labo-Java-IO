package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Olivier Liechti - Modified by Nicolas Müller on 07.03.2020
 */
public class UpperCaseFilterWriter extends FilterWriter {
  
  public UpperCaseFilterWriter(Writer wrappedWriter) {
    super(wrappedWriter);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {

    out.write(str.toUpperCase(), off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {

    for (int i = off; i < len + off; i++) {
      cbuf[i] = Character.toUpperCase(cbuf[i]);
    }

    out.write(cbuf, off, len);
  }

  @Override
  public void write(int c) throws IOException {

  out.write(Character.toUpperCase(c));

  }

}