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
    this.out.write(str.toUpperCase(),off,len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for(int i = off;i < off+len;++i){
      cbuf[i] = Character.toUpperCase(cbuf[i]);
    }
    this.out.write(cbuf,off,len);
  }

  @Override
  public void write(int c) throws IOException {
    this.out.write(Character.toUpperCase(c));
  }

}
