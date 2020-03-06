package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;
import ch.heigvd.res.labio.impl.Utils;


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
  private int counter = 0;
  private boolean isFirstLine = true;
  private char lastChar;
  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  //Return a string with line number and tab character after new line chars
  private String fileNumbering(String str, int off, int len){
    StringBuilder transformedStr = new StringBuilder();

    String toExecute = str.substring(off,off+len);



    if(isFirstLine){
      transformedStr.append(++counter + "\t");
      isFirstLine = false;
    }

    if(toExecute.length() == 1){
      if(toExecute.charAt(0) == '\r'){
        lastChar = toExecute.charAt(0);
        return transformedStr.toString();
      }else if(toExecute.charAt(0) == '\n' && lastChar =='\r') {
        lastChar = toExecute.charAt(0);
        return transformedStr.toString() + "\r\n" + ++counter + "\t";
      }else if(toExecute.charAt(0) != '\n' && lastChar == '\r'){
        lastChar = toExecute.charAt(0);
        return transformedStr.toString() + "\r" + ++counter + "\t" + toExecute.charAt(0);
      }
      lastChar = toExecute.charAt(0);
    }

    String[] lines = Utils.getNextLine(toExecute);
    while(lines[0] != ""){
      transformedStr.append(lines[0]);
      transformedStr.append(++counter + "\t");
      lines = Utils.getNextLine(lines[1]);
    }
    if(lines[1] != ""){
      transformedStr.append(lines[1]);
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
    String str = Character.toString((char)c);
    str = fileNumbering(str,0,1);
    this.out.write(str);
  }

}