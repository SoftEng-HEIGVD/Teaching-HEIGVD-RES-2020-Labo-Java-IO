package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  private boolean firstLine;
  private int nbLines;
  private int previousChar;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
    firstLine=true;
    nbLines=0;
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
      for(int x=off;x<(off+len);++x)
      {
          write(str.charAt(x));
      }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    throw new UnsupportedOperationException("In weird method");
  }

  @Override
  public void write(int c) throws IOException
  {
      if(firstLine)
      {
          out.write(writeLineNum());
          firstLine=false;
      }

      if(previousChar=='\r' && c !='\n')
      {
          out.write(writeLineNum());
      }

      out.write(c);

      if(c=='\n')
      {
          out.write(writeLineNum());
      }

      previousChar=c;
  }

  private String writeLineNum() // throws IOException
  {
      ++nbLines;
      return(String.valueOf(nbLines)+"\t");
  }
}
