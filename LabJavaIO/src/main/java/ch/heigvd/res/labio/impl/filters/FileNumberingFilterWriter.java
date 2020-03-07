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
 * @author Olivier Liechti, Florian Mülhauser
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private static boolean isFirst = true;
  private static int lCounter = 0;
  private static int cCounter = 0;


  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {

    char[] cbuf = str.toCharArray();
    this.write(cbuf,off,len); //we will transfor to use the other write, to implements once for all
  }


  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    cCounter = 0;

    if(isFirst){
      isFirst = false;
      lCounter++;
      out.write(lCounter +'\t');
    }
    for (int i = 0; i <len ; i++) {
      cCounter++;
      if((cbuf[off + i] == '\r') && (cbuf[off + i + 1] == '\n') && (i <= len-2) ){
        lCounter++;

        out.write(cbuf, (off + i - cCounter + 1), cCounter + 1);
        out.write(lCounter + '\t');

        cCounter = 0;
        i++;
      } else if(cbuf[i + off] == '\r' || cbuf[i + off] == '\n'){
        out.write(cbuf, (off + i - cCounter + 1), cCounter);
        out.write(lCounter + '\t');

        cCounter = 0;
      } else if(i - 1 == len) {
        out.write(cbuf, (i + off - cCounter + 1), cCounter);
      }

    }


  }

  public void firstLine(){
    isFirst = false;
    lCounter++;
    out.write(lCounter +'\t');
  }

  @Override
  public void write(int c) throws IOException {

    if (isFirst){
      isFirst = false;
      lCounter++;
      out.write(lCounter +'\t');
    }

    if ((char) c == '\n'){
      lCounter++;
      out.write(c);
      out.write(lCounter + '\t');
    } else {out.write(c);}
  }

}
