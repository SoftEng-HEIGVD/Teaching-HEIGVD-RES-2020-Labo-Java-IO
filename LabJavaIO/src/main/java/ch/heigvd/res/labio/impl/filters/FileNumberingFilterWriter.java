package ch.heigvd.res.labio.impl.filters;

import ch.heigvd.res.labio.impl.Utils;

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

  private int lineNumber = 0;
  private boolean lineBreak = true;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    write(str.toCharArray(), off, len);
  }
  
  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for (int it = 0; it < len; ++it)
      write(cbuf[it + off]);
  }

  @Override
  public void write(int c) throws IOException {
    if (lineNumber == 0 || c !=Utils.UNIX_SEP  && lineBreak) {
      lineNumber++;
      out.write(lineNumber + String.valueOf(Utils.TAB));
    }
    out.write(c);
    if (c ==Utils.UNIX_SEP)
      out.write(++lineNumber + String.valueOf(Utils.TAB));
    lineBreak =(c == Utils.MAC_SEP)?true:false;
  }
  }



