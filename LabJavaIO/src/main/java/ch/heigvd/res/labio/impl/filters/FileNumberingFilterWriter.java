package ch.heigvd.res.labio.impl.filters;

import ch.heigvd.res.labio.impl.Utils;

import java.awt.print.PrinterGraphics;
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
  private int lineCounter;
  private int previousChar;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
    this.lineCounter = 0;
  }

  public String insertAfterSeperator(String originString, String stringToAdd, String separator){ ;
    if(originString.equals(separator)){
      return separator + stringToAdd;
    } else {
      return separator + stringToAdd + originString.substring(separator.length());
    }
  }

  public String numberAndTab(){
    return ++lineCounter + "\t";
  }

  @Override
  public void write(String str, int off, int len) throws IOException {

    String newStr = "";
    String subStringToPrint = str.substring(off, off + len);

    // first number and tab
    if(lineCounter == 0){
      newStr = numberAndTab();
    }

    // while there is a line to get
    while(!Utils.getNextLine(str)[0].equals("")){
      newStr = newStr.concat(Utils.getNextLine(str)[0]);
      newStr = newStr.concat(numberAndTab());
      str = Utils.getNextLine(str)[1];
    }

    // in case of partial string
    if(Utils.getNextLine(str)[1].contains(subStringToPrint)){
      newStr = newStr.concat(subStringToPrint);
    } else {
      newStr = newStr.concat(str);
    }

    this.out.write(newStr);

  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
      super.write(cbuf, off, len);
  }

  @Override
  public void write(int c) throws IOException {

    if(lineCounter == 0){
      super.write(numberAndTab());
    }

    super.write(c);

    if(c == '\r'){
      previousChar = '\r';
      return;
    } else if (previousChar == '\r') {
      if (c != '\n') {             // macOS
        super.write('\r');
      }
      super.write(numberAndTab()); // windows
    } else if (c == '\n'){
      super.write(numberAndTab()); // unix
    }

    previousChar = c;
  }

}
