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

  private int lignCounter = 1;
  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    for(int i = off; i < len; i++){
      if(str.charAt(i) == '\n'){
        write('\n'+ lignCounter + '\t');
        lignCounter++;
      }else{
        write(str.charAt(i));
      }

    }
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for(int i = off; i < len; i++){
      if(cbuf[i] == '\n'){
        write('\n'+ lignCounter + '\t');
        lignCounter++;
      }
      write(cbuf[i]);
    }
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

  @Override
  public void write(int c) throws IOException {
    if((char)c == '\n'){
      lignCounter++;
      write((char)c);
    }
    write((char)c);
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

}
