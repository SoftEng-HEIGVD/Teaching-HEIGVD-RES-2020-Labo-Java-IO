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
  private boolean care = false;
  private boolean tmp = false;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    if(i == 1){
      writeLineNumber();
    }
    String[] lines = Utils.getNextLine(str.substring(off, off + len));
    if(care && lines[0].length() > 0 && lines[0].charAt(0) == '\n'){
      care = false; tmp = false;
      out.write('\n');
      writeLineNumber();
      lines[0] = lines[0].length() == 1 ? "" : lines[0].substring(1, lines.length);
    } else if(care) {
      care = false;
      writeLineNumber();
    }
    while ("".compareTo(lines[0]) != 0){
      out.write(lines[0]);
      int n = lines[0].length();
      if(returnChar(n, 0, lines) && !tmp){
        writeLineNumber();
      }

      lines = Utils.getNextLine(lines[1]);
    }

    out.write(lines[1]);
    int n = lines[1].length();
    if(returnChar(n, 1, lines)  && !tmp){
      writeLineNumber();
    }

  }

  private void writeLineNumber() throws IOException {
    out.write(i + "\t");
    i++;
  }

  /**
   * @param n string length
   * @param i which string of the array to parse
   * @param lines the array of string
   * @return true if the last character is '\r' or '\n'
   */
  private boolean returnChar(int n, int i, String[] lines) {
    if (n == 0){ return false; }
    char c = lines[i].charAt(n - 1);
    care = c == '\r';
    // derniere ligne a examiner
    if(care && "".compareTo(lines[1]) == 0){ tmp = true; }
    return care | c == '\n';
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    write(cbuf.toString(), off, len);
  }

  @Override
  public void write(int c) throws IOException {
    write(Character.toString((char) c), 0, 1);
  }

}
