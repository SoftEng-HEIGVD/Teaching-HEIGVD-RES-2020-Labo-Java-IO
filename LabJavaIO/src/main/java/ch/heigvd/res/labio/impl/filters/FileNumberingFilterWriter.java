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

  private int lineNumber = 1;
  private int lastCharacter = '\0';

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
      for (int i = off; i < off + len; i++) {
          write(str.charAt(i));
      }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
      write(String.valueOf(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {
    switch (c){
        case '\n' :
            if(lastCharacter != '\r'){
                out.write("\n");
            }
            else{
                out.write("\r\n");
            }
            out.write(String.valueOf(lineNumber++));
            out.write("\t");
            lastCharacter = '\n';
            break;
        case '\r' :
            lastCharacter = '\r';
            break;
        default :
            if(lastCharacter == '\0'){
                out.write(String.valueOf(lineNumber++) + '\t');
            }
            if(lastCharacter == '\r'){
                out.write('\r');
                out.write(String.valueOf(lineNumber++) + '\t');
            }
            out.write(c);
            lastCharacter = c;
    }
  }

}
