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
    for(int i = off; i < len; i++){
      if((int)str.charAt(i) >= 97 || (int)str.charAt(i) <= 122)
        write( (char) ((int)str.charAt(i) - 32));
    }
    //throw new UnsupportedOperationException("UpperCaseFilterWriter 1 ");
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for(int i = off; i < len; i++){
      if((int)cbuf[i] >= 97 || (int)cbuf[i] <= 122)
        write ((char)(cbuf[i] - 32));
    }
    //throw new UnsupportedOperationException("UpperCaseFilterWriter 2");
  }

  @Override
  public void write(int c) throws IOException {
    if(c >= 97 || c <= 122){
      c = c -32;
      write((char)c);
    }
    //throw new UnsupportedOperationException("UpperCaseFilterWriter 3");
  }

}
