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
 * @author Olivier Liechti, Florian Mülhauser
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private  boolean isFirst = true;
  private  int lCounter = 0;
  //private static int cCounter = 0;
  private  int lastC = -1;



  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  public void writeLnTab() throws IOException{
    String str = Integer.toString(++lCounter);
    super.write(str, 0 , str.length());
    super.write('\t');

  }
  @Override
  public void write(String str, int off, int len) throws IOException {


    if(isFirst) {
      writeLnTab();
      isFirst = false;
    }

    str = str.substring(off,len + off);
    String[] lines;

    while(!(lines = Utils.getNextLine(str))[0].isEmpty()){
      super.write(lines[0],0,lines[0].length());
      writeLnTab();

      str = lines[1];
    }

    super.write(str, 0 , str.length());

  }


  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {


    String str = new String(cbuf,off,len);
    this.write(str,0,len);

  }

  @Override
  public void write(int c) throws IOException {

    if (isFirst) {
      writeLnTab();
      isFirst = false;
    }

    if (c != '\n' && lastC == '\r') {
      writeLnTab();
    }

    super.write(c);

    if (c == '\n') {
      writeLnTab();
    }

    lastC = c;
  }
}
