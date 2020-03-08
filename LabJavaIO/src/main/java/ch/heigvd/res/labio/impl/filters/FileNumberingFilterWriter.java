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
  private int lineNumber;
  private boolean lastCharWasAReturn;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
    this.lineNumber = 1;
    this.lastCharWasAReturn = false;
  }

  /*
  indicates whether the character and the next one form a line ender.
  @returns 1 if the first char only is a line ender,
          2 if the first and the second chars form a windows line ender ( \r\n )
          0 if the first char cannot be a line ender.
   */
  private int isSeparator(char c, char c2){
    if(c == '\r'){
      if(c2 == '\n')
        return 2;
      else {
        return 1;
      }
    }
    if(c == '\n') return 1;
    return 0;
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    StringBuilder newStr = new StringBuilder();
    if(lineNumber == 1) newStr.append(lineNumber++ + "\t");
    boolean EOF = false;
    for(int i = off; i < len + off; ++i){
      if(i+1 >= len + off) EOF = true;
      char c = str.charAt(i);
      newStr.append(c);
        int isSeparator = (isSeparator(c, EOF? 0 : str.charAt(i+1)));
        if(isSeparator != 0){
          if(isSeparator == 2){
            ++i;
            newStr.append(str.charAt(i));
          }
          newStr.append(lineNumber++);
          newStr.append("\t");
        }
    }
    super.write(newStr.toString(), 0, newStr.length());
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    String s = new String(cbuf);
    this.write(s, off, len);
  }

  @Override
  public void write(int c) throws IOException {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
    String s;
    int len = 1;
    if(c == '\r'){
      lastCharWasAReturn = true;
    }else{
      if(lastCharWasAReturn){
        s = "\r" + Character.toString((char)c);
        len = 2;
      }else{
        s = Character.toString((char)c);
      }
      this.write(s, 0, len);
      lastCharWasAReturn = false;
    }

    }

}
