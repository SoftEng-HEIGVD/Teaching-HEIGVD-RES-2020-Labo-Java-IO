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
    this.out.write(str.substring(off, len + off).toUpperCase());
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    char[] buf = new char[len]; // new tab of characters

    for(int i = 0; i < len; ++i){
      buf[i] = Character.toUpperCase(cbuf[i + off]);
    }

    this.out.write(buf);
  }

  @Override
  public void write(int c) throws IOException {
    this.out.write(Character.toUpperCase(c));
  }

}
