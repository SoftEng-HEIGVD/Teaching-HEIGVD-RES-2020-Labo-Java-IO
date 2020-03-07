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
 * @author Olivier Liechti, Vitor Vaz Afonso
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int lineNumber = 1;
  private int previousC = -1; // character undefined

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {

    str = str.substring(off,off+len);

    for (int i = 0; i < str.length(); i++){
      char c = str.charAt(i);
      this.write(c);
    }

  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    this.write(new String(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {

    // write the first number line in the beginning
    if(lineNumber == 1){
      writeNumberLine();
    }

    // if the previous character is defined and equals '\r'
    if (previousC !=-1 && previousC == '\r'){
          out.write('\r');
          if (c!='\n') {
            writeNumberLine();
          }
    }

    // write the character if it's not '\r'
    if (c != '\r') {
      out.write(c);
    }

    // if the character is '\n' do a break line
    if (c == '\n'){
      writeNumberLine();
    }

    previousC = c;
  }

  public void writeNumberLine() throws IOException {
    out.write(lineNumber++ + "\t");
  }

}
