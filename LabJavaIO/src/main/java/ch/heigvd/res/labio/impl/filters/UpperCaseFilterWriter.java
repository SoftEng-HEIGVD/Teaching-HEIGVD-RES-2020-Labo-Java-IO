package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Olivier Liechti
 */
public class UpperCaseFilterWriter extends FilterWriter {

  private final int LOWER_MIN =97;
  private final int LOWER_MAX = 122;
  private final int LOW_UP_GAP=32;

  public UpperCaseFilterWriter(Writer wrappedWriter) {
    super(wrappedWriter);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
      for(int x=off;x<(off+len);++x)
      {
          out.write(convertChar(str.charAt(x)));
      }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException
  {
      for(int x=off;x<(off+len);++x)
      {
        out.write(convertChar(cbuf[x]));
      }
  }

  @Override
  public void write(int c) throws IOException {
      out.write(convertChar((char)c));
  }

  private char convertChar(char c)
  {
      if(c>=LOWER_MIN && c<=LOWER_MAX)
      {
          return ((char)(c-=LOW_UP_GAP));
      }
      else
      {
          return c;
      }
  }
}
