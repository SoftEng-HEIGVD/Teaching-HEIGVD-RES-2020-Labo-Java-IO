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
  private int linecpt = 0;
  private int lastChar = -1;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {

    for(int i = off; i < len + off; i++) {

      this.write(str.charAt(i));
    }

//    throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {

    for(int i = off; i < len + off; i++) {
      this.write(cbuf[i]);
    }

//    throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

  @Override
  public void write(int c) throws IOException {


    if(linecpt == 0){
      out.write(Integer.toString(++linecpt) + '\t');
    }


    if(lastChar == '\r' && c != '\n') {

      out.write(Integer.toString(++linecpt) + '\t');

    }

    out.write(c);

    if(c == '\n') {

      out.write(Integer.toString(++linecpt) + '\t');
    }

    this.lastChar = c;

//    throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

}
