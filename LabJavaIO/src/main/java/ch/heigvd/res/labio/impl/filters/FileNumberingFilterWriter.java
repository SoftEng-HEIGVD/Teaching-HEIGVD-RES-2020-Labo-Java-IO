package ch.heigvd.res.labio.impl.filters;
import ch.heigvd.res.labio.impl.Utils;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
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
  private int nbLines = 0;
  private boolean lastLine = false;
  private char lastChar = ' ';

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
      str = str.substring(off, off + len);
      String[] elements = Utils.getNextLine(str);
      while(!elements[0].equals("")) {
        //Première ligne
        if(!lastLine) {
          out.write(Integer.toString(++nbLines) + '\t' + elements[0]);
        }
        else {
          out.write(elements[0]);
          lastLine = false;
        }
        elements = Utils.getNextLine(elements[1]);
      }
      if(lastLine){
        out.write(elements[1]);
        lastLine = false;
      }
      else {
        out.write(Integer.toString(++nbLines) + '\t' + elements[1]);
        lastLine = true;
      }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    out.write(Arrays.toString(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {
    boolean newLine = false;
    if(nbLines == 0){
      out.write(Integer.toString(++nbLines) + '\t');
    }
    if((lastChar == '\n' ||  lastChar == '\r') && c == '\n') {
      newLine = true;
      //Changement du lastChar pour ne pas rester avec à chaque fois une fin de ligne
      lastChar = ' ';
    }
    else if(lastChar == '\n' || lastChar == '\r'){
      out.write(Integer.toString(++nbLines) + '\t');
      lastChar = ' ';
    }
    if(!newLine && (c == '\n' || c == '\r'))
      lastChar = (char)c;
    out.write(c);
    if(newLine){
      out.write(Integer.toString(++nbLines) + '\t');
    }
  }

}
