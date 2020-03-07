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

  private int i = 1;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    if(i == 1){
      out.write(i + "\t");
      i++;
    }
    String[] lines = Utils.getNextLine(str.substring(off, off + len));
    while ("".compareTo(lines[0]) != 0){
      out.write(lines[0]);
      int n = lines[0].length();
      if(returnChar(n, 0, lines)){
        out.write(i + "\t");
        i++;
      }
      lines = Utils.getNextLine(lines[1]);
    }

    out.write(lines[1]);
    int n = lines[1].length();
    if(returnChar(n, 1, lines)){
      out.write(i + "\t");
      i++;
    }

  }

  /**
   * @param n string length
   * @param i which string of the array to parse
   * @param lines the array of string
   * @return true if the last character is '\r' or '\n'
   */
  private boolean returnChar(int n, int i, String[] lines) {
    return n == 0 ? false : lines[i].charAt(n - 1) == '\r' | lines[i].charAt(n - 1) == '\n';
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {

  }

  @Override
  public void write(int c) throws IOException {

  }

}
