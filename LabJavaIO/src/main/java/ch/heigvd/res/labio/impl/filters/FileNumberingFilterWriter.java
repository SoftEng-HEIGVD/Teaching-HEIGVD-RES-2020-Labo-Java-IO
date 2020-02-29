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

  private int lineCounter = 0;
  private boolean firstLine = true;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {

    char[] cbuf = str.toCharArray();
    this.write(cbuf, off, len);

  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {

    int charCounter = 0;

    if(firstLine){
      ++lineCounter;
      out.write(lineCounter + "\t");
      firstLine = false;
    }

    for(int i = 0; i < len; ++i){
      ++charCounter;
      if((i < (len-1)) && (cbuf[off+i] == '\r') && cbuf[off+i+1] == '\n'){
        ++lineCounter;
        out.write(cbuf, (off+i-charCounter+1), charCounter+1);
        out.write(lineCounter + "\t");
        charCounter = 0;
        ++i;
      }
      else if((cbuf[off+i] == '\n' || cbuf[off+i] == '\r')){
        ++lineCounter;
        out.write(cbuf, (off+i-charCounter+1), charCounter);
        out.write(lineCounter + "\t");
        charCounter = 0;
      }
      else if(i == (len-1)){
        out.write(cbuf, (off+i-charCounter+1), charCounter);
      }
    }

  }

  @Override
  public void write(int c) throws IOException {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
    if(firstLine){
      ++lineCounter;
      out.write(lineCounter + "\t");
      firstLine = false;
    }
    if((char) c == '\n'){
      ++lineCounter;
      out.write(c);
      out.write(lineCounter + "\t");
    }else{
      out.write(c);
    }
  }

}
