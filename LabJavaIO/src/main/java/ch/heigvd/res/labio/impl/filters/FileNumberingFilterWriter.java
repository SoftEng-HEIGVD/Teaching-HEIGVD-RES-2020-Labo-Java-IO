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
 * @author Olivier Liechti, Diluckshan Ravindranathan
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  private static final char CARRIAGE_RETURN = '\r';
  private static final char LINE_FEED = '\n';
  private static final char TAB = '\t';
  private int previous = 0;
  private int line = 1;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    write(str.toCharArray(), off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for(int i = off; i < off + len; ++i){
      write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    final String LINE_CONVERTED = String.valueOf(line);

    // Mac carriage or first line
    if( (c!= LINE_FEED && previous == CARRIAGE_RETURN) || (line == 1)){
      super.write(LINE_CONVERTED, 0, String.valueOf(line++).length());
      super.write(TAB);
    }
    super.write(c);
    if(c == LINE_FEED){
      super.write(LINE_CONVERTED, 0 , String.valueOf(line++).length());
      super.write(TAB);
    }
    previous = c;
  }

}
