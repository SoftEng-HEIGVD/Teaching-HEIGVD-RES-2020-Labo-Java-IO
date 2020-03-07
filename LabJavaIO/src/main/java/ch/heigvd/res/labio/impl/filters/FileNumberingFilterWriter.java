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
  private static boolean isFirstLine ;
  private static int count ;
  private static char previousChar;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
    count = 1;
    isFirstLine = true;
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    for(int i = off; i < off+len; i++){
      write(str.charAt(i));
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for(int i = off; i < off+len; i++){
      write(cbuf[i]);
    }
   }

  @Override
  public void write(int c) throws IOException {
      if(isFirstLine) {
          out.write(count +"\t");
          isFirstLine = false;
          count++;

      }
      if(c == '\n'){
          out.write(c);
          out.write(count + "\t");
          count++;
      }
      else if(c != '\n' && previousChar == '\r'){
          out.write(count + "\t");
          out.write(c);
          count++;
       }

      else{
          out.write(c);
      }
      previousChar = (char)c;


  }

}
