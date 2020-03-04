package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Olivier Liechti, modified by Chrstoian Zaccaria
 */
public class UpperCaseFilterWriter extends FilterWriter {
  
  public UpperCaseFilterWriter(Writer wrappedWriter) {
    super(wrappedWriter);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    //Process to the other function with a tab --> more simple
    write(str.toCharArray(), off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    //UpperCase every char from cbuf
    for(int i = off; i < (off + len); ++i){
      write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    //Character is Uppercase
    super.write(Character.toUpperCase(c));
  }

}
