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
  private boolean isWindowsLignReturn = false;
  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
      for (int i = off; i < len + off ; i++){
        this.write((int)str.charAt(i));
      }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    String tmp = new String(cbuf);
    this.write(tmp,off,len);
  }

  @Override
  public void write(int c) throws IOException {
    if (lignCounter == 1){
      this.out.write(((Integer)lignCounter++).toString());
      this.out.write('\t');
    }
    if(isWindowsLignReturn && (char)c != '\n'){
      this.out.write('\r');
      this.out.write(((Integer)lignCounter++).toString());
      this.out.write('\t');
      isWindowsLignReturn = false;
    }

    if((char)c == '\n'){
      if(isWindowsLignReturn){
        this.out.write('\r');
      }
      this.out.write('\n');
      this.out.write(((Integer)lignCounter++).toString());
      this.out.write('\t');
      isWindowsLignReturn = false;
    }else if((char)c == '\r'){
      isWindowsLignReturn = true;
    }else {
      this.out.write((char)c);
    }
  }
}
