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
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  private int counter = 1;
  private boolean firstLine = true;
  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {

      String sub = str.substring(off, off + len);
      String[] nextLines = Utils.getNextLine(sub);

      //Add the first numbering
      if(firstLine){
          nextLines[0] = "1\t" + nextLines[0];
          firstLine = false;
      }
      //Add the number
      char lastChar = nextLines[0].charAt(nextLines[0].length() - 1);
      if( lastChar == '\n' || lastChar == '\r')
          nextLines[0] += (++counter) + "\t";


      super.out.write(nextLines[0]);
      //If there is more than one line
      if(nextLines[1].length() > 1)
          this.write(nextLines[1]);

  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
      this.write(String.copyValueOf(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {

      String writing = "";
      char caract = (char) c;
      if(firstLine) {
          writing += "1\t";
          firstLine = false;
      }

      if(c == '\n') {
          writing +=  "\n" + (++counter) + "\t";
          super.out.write(writing);
          return;
      }

      super.out.write(writing + caract);
  }

}
