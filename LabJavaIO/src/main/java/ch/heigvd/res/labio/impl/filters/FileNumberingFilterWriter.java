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
  private int counter = 1;
  private boolean isFirstLine = true;
  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  //Return a string with line number and tab character after new line chars
  private String fileNumbering(String str, int off, int len){
    StringBuilder transformedStr = new StringBuilder();
    if(isFirstLine){
      transformedStr.append(counter + "\t");
      isFirstLine = false;
    }
    if(str.contains("\n")){//Unix or Windows
      for (int i = off; i < off + len; ++i) {
        transformedStr.append(str.charAt(i));
        if (str.charAt(i) == '\n') {
          counter++;
          transformedStr.append(counter + "\t");
        }
      }

    }else{ //IOS9
      for (int i = off; i < off + len; ++i) {
        transformedStr.append(str.charAt(i));
        if(str.charAt(i) == '\r'){
          counter++;
          transformedStr.append(counter + "\t");
        }
      }
    }

    return transformedStr.toString();
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    this.out.write(fileNumbering(str,off,len));
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    String str = String.valueOf(cbuf);
    this.out.write(fileNumbering(str,off,len));
  }

  @Override
  public void write(int c) throws IOException {
    String str = String.valueOf((char)c);
    this.out.write(fileNumbering(str,0,1));
  }

}
