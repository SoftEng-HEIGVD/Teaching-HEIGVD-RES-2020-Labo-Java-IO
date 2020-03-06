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

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int lineCounter;
  private boolean possiblyNewLine;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
    lineCounter = 1;
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    write(str.toCharArray(), off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for (int i = off; i < off + len; ++i) {
      write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    if (lineCounter == 1 || (possiblyNewLine && (char) c != '\n')) {
      newLine();
    }

    super.write(c);

    /**
     * A little bug here, "some\r" will become "1\tsome\r" and not "1\tsome\r2\t"
     * Since the "encoding" of newline words with the charset "\n\r" is not
     * prefix-free, it seems theoretically impossible to handle...but I'm not 
     * 100% sure about that (say 90).
     */
    if ((char) c == '\r') {
      possiblyNewLine = true;
    } else if ((char) c == '\n') {
      newLine();
    }
  }

  private void newLine() throws IOException {
    String s = lineCounter++ + "\t";
    super.write(s, 0, s.length());
    possiblyNewLine = false;
  }

}
