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
 * @author Olivier Liechti - Modified by Nicolas Müller on 07.03.2020
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private boolean isFirstChar = true;
  private int lineCounter = 0;
  private char lastChar = '\0';

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

    for (int i = off; i < off + len; i++) {
      write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {

    // Adds tab and numbers if it's the first char
    if (isFirstChar) {

      out.write(Integer.toString(++lineCounter) + '\t');
      isFirstChar = false;
    }

    // Windows has \r\n, Linux \n, Mac \r
    // Function adds newline only with \n. Make sure to add a new line if \r appears without the \n.
    if (lastChar == '\r' && c != '\n') {

      out.write(Integer.toString(++lineCounter) + '\t');
    }

    // Writes current char
    out.write((char)c);

    // Creates new line if \n found
    if (c == '\n') {

      out.write(Integer.toString(++lineCounter) + '\t');
    }

    // Used to make it work on windows, mac and linux
    lastChar = (char) c;
  }
}
