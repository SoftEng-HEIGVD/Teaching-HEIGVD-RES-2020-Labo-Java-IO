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
  int nbLine = 1;
  boolean isNewLine = true;
  boolean isWindowsNewLine = false;
  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    write(str.toCharArray(),off,len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for(int i = 0; i<len;i++){
      write(cbuf[off + i]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    boolean alreadyWrite = false;
    if(isWindowsNewLine && c != '\n')
      isWindowsNewLine =false;
    if(c == '\n' || c== '\r'){
      alreadyWrite = true;
      super.write(c);
      isNewLine = true;
      if(c == '\r')
        isWindowsNewLine = true;
    }
    if(isNewLine && !isWindowsNewLine){
      for (char charNbLine : String.valueOf(nbLine++).toCharArray())
        super.write(charNbLine);
      super.write('\t');
      isNewLine = false;
    }
  if(!alreadyWrite)
    super.write(c);
  }
}
