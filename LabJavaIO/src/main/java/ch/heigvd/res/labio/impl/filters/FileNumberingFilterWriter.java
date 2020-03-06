package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;
import ch.heigvd.res.labio.impl.Utils;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 * @author Robin Müller
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int line = 0;
  private int previous;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  /**
   * Write the line number and a tab
   * @throws IOException
   */
  private void writeLineNumber() throws IOException {
    String strLine = Integer.toString(++line);
    super.write(strLine, 0, strLine.length());
    super.write('\t');
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    if (line == 0)
      writeLineNumber();

    str = str.substring(off, off + len);
    String[] lines;
    while (!(lines = Utils.getNextLine(str))[0].isEmpty()) {
      super.write(lines[0], 0, lines[0].length());
      writeLineNumber();

      str = lines[1];
    }

    super.write(str, 0, str.length());
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    this.write(new String(cbuf, off, len), 0, len);
  }

  @Override
  public void write(int c) throws IOException {
    //Write line number if this is the first line or if we have a single \r newline (and not \r\n)
    if (line == 0 || (previous == '\r' && c != '\n'))
      writeLineNumber();

    super.write(c);

    //Write line number if we have a \n
    if (c == '\n')
      writeLineNumber();

    previous = c;
  }

}
