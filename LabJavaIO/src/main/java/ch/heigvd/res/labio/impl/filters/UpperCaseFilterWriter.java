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
    String strToWrite = str.substring(off, len+off);
    for(int i=0; i<strToWrite.length(); ++i)
    {
      this.write(strToWrite.charAt(i));
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for(int i=off; i<len+off && i<cbuf.length+off; ++i)
    {
      this.write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    if(c>='a' && c<='z')
    {
      super.write(c-'a'+'A');
    }
    else
    {
      super.write(c);
    }
  }

}
