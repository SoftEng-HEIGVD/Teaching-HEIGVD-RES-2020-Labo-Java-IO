package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
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
  private int counter;
  boolean backSlashRFlag;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
    counter = 1;
    backSlashRFlag = false;
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    if (off + len > str.length()) {
      throw new IllegalArgumentException("Offset + length > than String length");
    }
    for (int i = off; i < off + len; ++i){
      char c = str.charAt(i);
      write(c);
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    write(new String(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {
    // write the first number at first call of function
    if (counter == 1) {
      out.write(counter++ + "\t");
    }
    // check if a \r was previously used
    if (backSlashRFlag && c != '\n') {
      out.write(counter++ + "\t");
    }
    // write char
    out.write(c);
    // update flag or write the number if the char is a \n
    switch (c) {
      case '\r':
        backSlashRFlag = true;
        break;
      case '\n':
        out.write(counter++ + "\t");
      default:
        backSlashRFlag = false;
        break;
    }
  }
}
