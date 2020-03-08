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

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  /* Méthodes non fonctionelles */
  @Override
  public void write(String str, int off, int len) throws IOException {
    String str_f = str.substring(off, off+len);
    String[] next;
    String result = "1\t";
    next = Utils.getNextLine(str_f);
    result += next[0];
    int i =2;
    while (next[0].indexOf("\r") != -1 || next[0].indexOf("\n") != -1) {
      next = Utils.getNextLine(next[1]);
      result += (i+"\t");
      result += next[0];
    }
    super.out.write(result);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    String str_f = cbuf.toString().substring(off, off+len);
    String[] next;
    String result = "1\t";
    next = Utils.getNextLine(str_f);
    result += next[0];
    int i =2;
    while (next[0].indexOf("\r") != -1 || next[0].indexOf("\n") != -1) {
      next = Utils.getNextLine(next[1]);
      result += (i+"\t");
      result += next[0];
    }
    super.out.write(result);
  }

  @Override
  public void write(int c) throws IOException {
    super.out.write(c);
  }

}
