package ch.heigvd.res.labio.impl.filters;

import ch.heigvd.res.labio.impl.Utils;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 * <p>
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti, Robin Cuenoud
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger
          .getLogger(FileNumberingFilterWriter.class.getName());

  private int counter = 1;
  boolean lastR;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override public void write(String str, int off, int len) throws IOException {
   for(int i = off;i < off + len; ++i) {
     //call char by char method
     this.write(str.charAt(i));
   }
  }


  @Override public void write(char[] cbuf, int off, int len)
          throws IOException {
    //call char by char method
    this.write(new String(cbuf),off,len);
  }


  @Override public void write(int c) throws IOException {
    //first line
    if(counter == 1) {
      this.addLineNumber();
    }

    if(c == '\n') {
      super.write(c);
      this.addLineNumber();
    } else {
      if (lastR) {
        this.addLineNumber();
      }
      super.write(c);
    }
    lastR = (c == '\r');

  }

  void addLineNumber() throws IOException {
    String toSend = counter + "\t";
    super.write(toSend,0,toSend.length());
    ++counter;

  }
}

