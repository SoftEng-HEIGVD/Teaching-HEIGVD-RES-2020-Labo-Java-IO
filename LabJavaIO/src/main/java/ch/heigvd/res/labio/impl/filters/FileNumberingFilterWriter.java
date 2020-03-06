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
 * @author Gabriel Roch
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  private String buffer = "";
  private int line = 0;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  private void writeLineFromBuffer() throws IOException {
    StringBuilder toSend = new StringBuilder();
    if (line == 0) {
      toSend.append(++line + "\t");
    }

    String[] ret = Utils.getNextLine(buffer);
    while (!ret[0].equals("")) {
      toSend.append(ret[0] + ++line + "\t");
      ret = Utils.getNextLine(ret[1]);
    }
    buffer = "";
    toSend.append(ret[1]);
    super.write(toSend.toString(), 0, toSend.length());
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    buffer += str.substring(off, off+len);
    writeLineFromBuffer();
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    buffer += new String(cbuf, off, len);
    writeLineFromBuffer();
  }

  @Override
  public void write(int c) throws IOException {
    buffer += (char) c;
    if (c != '\n' && c != '\r') {
      writeLineFromBuffer();
    }
  }

}
