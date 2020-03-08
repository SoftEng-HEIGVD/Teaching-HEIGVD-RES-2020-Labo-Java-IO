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
  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int lineCount = 0;
  private int prevChar = -1;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    for(int i = off; i < off + len; ++i){
      this.write(str.charAt(i));
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for(int i = off; i < off + len; ++i){
      this.write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    if(lineCount == 0 || (prevChar == '\r' && c != '\n')){  //début du doc ou après séparation Mac
      out.write(Integer.toString(++lineCount) + '\t');
    }

    out.write(c);

    this.prevChar = c;
    if(prevChar == '\n') { //après séparation Windows ou Unix
      out.write(Integer.toString(++lineCount) + '\t');
    }
  }

}
