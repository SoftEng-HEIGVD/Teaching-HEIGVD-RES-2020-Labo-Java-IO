package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

import static ch.heigvd.res.labio.impl.Utils.getNextLine;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\tHello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private int lineNumber = 0;
  private boolean isNewLine = true;

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  private String numering(String str, int off, int len){
    StringBuilder tmp = new StringBuilder();

    if(isNewLine){
      lineNumber++;
      tmp.append(lineNumber + "\t");
      isNewLine = false;
    }

      for(int i = off; i < off + len; i++){
        tmp.append(str.charAt(i));
        if(tmp.charAt(tmp.length() - 1) == '\n' || tmp.charAt(tmp.length() - 1) == '\r' && str.charAt(i + 1) != '\n'){
          lineNumber++;
          tmp.append(lineNumber + "\t");
        }
      }


    return tmp.toString();
  }


  @Override
  public void write(String str, int off, int len) throws IOException {
    out.write(numering(str, off, len));
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    String str = String.valueOf(cbuf);
    out.write(numering(str, off, len));
  }

  @Override
  public void write(int c) throws IOException {

    StringBuilder tmp = new StringBuilder();

    if (isNewLine){
      isNewLine = false;
      lineNumber++;
      tmp.append(lineNumber);
      tmp.append('\t');
    }

    if ((char) c == '\n'){
      lineNumber++;
      tmp.append((char)c);
      tmp.append(lineNumber);
      tmp.append('\t');

    } else {
      tmp.append((char)c);
    }
    out.write(tmp.toString());



  }


}
