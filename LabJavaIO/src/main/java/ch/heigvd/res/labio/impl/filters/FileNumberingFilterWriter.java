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
 * @author Olivier Liechti, modified by Christian Zaccaria
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private static final char LINE_FEED = '\n';
  private static final char CARRIAGE_RETURN = '\r';
  private static final char TAB = '\t';

  private int line = 1;
  private int previous = 0;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    //Process to the other function with a tab --> more simple
    write(str.toCharArray(), off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    //Pass to the function with 1 character --> more simple
    for(int i = off; i < (off + len); ++i){
      write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    if(line == 1){
      lineWriter();
    }

    //MAC carriage return
    if(c != LINE_FEED && previous == CARRIAGE_RETURN){
      lineWriter();
    }

    super.write(c);

    //Windows or Unix carriage return
    if(c == LINE_FEED){
      lineWriter();
    }

    previous = c;
  }

  /**
   * Write a line with numberline + blank. This function increments the line number
   * @throws IOException Can't write
   */
  private void lineWriter() throws IOException{
    super.write(String.valueOf(line), 0, String.valueOf(line).length());
    super.write(TAB);
    ++line;
  }

}
