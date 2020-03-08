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
 * Hello\n\World -> 1\tHello\n2\tWorld
 *
 * @author Olivier Liechti
 *
 * \n -> \n[number]\t
 * \r -> \r[number]\t
 * \r\n -> \r\n[number]\t
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int linesCounter = 1;
  private boolean newLine = true;
  private int charBefore = 0;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    for(int i = off; i < len + off; i++){
      write(str.charAt(i));
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for(int i = off; i < len + off; i++){
      write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    if(newLine){ //used for first line only
      writeLinePrefix();
      newLine = false;
    }
    if(c != '\n' && charBefore == '\r'){
      writeLinePrefix();
    }
    super.write(c);
    
    if(c == '\n'){
      writeLinePrefix();
    }
    charBefore = c;
  }
  public void writeLinePrefix() throws IOException {
    super.write(linesCounter + "\t", 0, (int) (Math.log10(linesCounter) + 2));
    linesCounter++;
  }

}
