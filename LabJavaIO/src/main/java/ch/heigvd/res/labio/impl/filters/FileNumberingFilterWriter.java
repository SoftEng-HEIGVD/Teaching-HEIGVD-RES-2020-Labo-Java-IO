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

  @Override
  public void write(String str, int off, int len) throws IOException {
    int i = 1;
    String[] lines = Utils.getNextLine(str);
    while ("".compareTo(lines[0]) != 0){
      out.write(i + "\t" + lines[0]);
      lines = Utils.getNextLine(lines[1]);
      i++;
    }

    out.write(i + "\t" + lines[1]);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {

  }

  @Override
  public void write(int c) throws IOException {

  }

}
