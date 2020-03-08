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
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int lastChar;
  private int nbrOfLine = 1;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    write(str.toCharArray(),off,len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for(int i = off; i < off + len; ++i) {
      write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    if(nbrOfLine == 1){
      newLineWriter();
    }
    if(c != '\n' && lastChar == '\r'){
      newLineWriter();
    }
    super.write(c);
    if(c == '\n'){
      newLineWriter();
    }
    lastChar = c;
  }

  /***
   * Write a new line with its number and a blank as defined.
   * Increment the line number.
   * @throws IOException when it can't write.
   */
  private void newLineWriter() throws IOException{
    // Manage the case when the line number contains more than 1 digits.
    super.write(String.valueOf(nbrOfLine), 0, String.valueOf(nbrOfLine).length());
    super.write('\t');
    ++nbrOfLine;
  }

}
