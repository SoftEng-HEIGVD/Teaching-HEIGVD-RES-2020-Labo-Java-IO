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
  private static final char TAB   = '\t';
  private static final char CR    = '\r';
  private static final char LF    = '\n';
  private int lineCounter;
  private boolean lineSeparatorUsedJustBefore;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
    lineCounter = 1;
    lineSeparatorUsedJustBefore = false;
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    // Using the write(int c) method for each character in str
    for (int i = off; i < off + len; i++) {
      write(str.charAt(i));
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    // Using write(String str, int off, int len) methods
    write(new String(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {
    // At the beginning of a line, print the line number and a '\t'
    // The first time the function is called and ctr == 1, it is a line start.
    if(lineCounter == 1 || (lineSeparatorUsedJustBefore && c != LF)){
      out.write(Integer.toString(lineCounter++) + Character.toString(TAB));
    }

    out.write(c);

    switch(c){
      case CR:
        lineSeparatorUsedJustBefore = true;
        break;
      case LF:
        out.write(Integer.toString(lineCounter++) + Character.toString(TAB));
      default:
        lineSeparatorUsedJustBefore = false;
        break;
    }
  }

}
