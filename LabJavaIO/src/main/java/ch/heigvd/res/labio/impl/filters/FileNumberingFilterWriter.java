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
  private int line = 0;
  private int last = -1;
  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    for (int i = off; i < off + len; i++)
      this.write(str.charAt(i));
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for (int i = off; i < off + len; i++)
      this.write(cbuf[i]);
  }

  @Override
  public void write(int c) throws IOException {
    String output = "";

    // if it's the first line
    // add the numbering & tab
    if (line == 0)
      output += ++this.line + "\t";

    // if the current char is a `\n`,
    // add a `\n` and the numbering
    if (c == '\n') {
      output += "\n" + ++this.line + "\t";
    } else {
      // if the last char was a `\r` (i.e. macos9 new line)
      //  add line number and tab
      if (last == '\r')
        output += ++this.line + "\t";

      output += (char) c;
    }

    this.last = c;
    out.write(output);
  }

}
