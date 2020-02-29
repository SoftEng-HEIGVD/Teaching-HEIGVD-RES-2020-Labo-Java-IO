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
 * <p>
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti, Robin Cuenoud
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger
          .getLogger(FileNumberingFilterWriter.class.getName());

  private int counter;
  boolean firstLine;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
    this.counter = 1;
    firstLine = true;
  }

  @Override public void write(String str, int off, int len) throws IOException {


    StringBuilder sb = new StringBuilder();

    String[] nextLine = Utils.getNextLine(str.substring(off, off + len));

    if (firstLine) {
      firstLine = false;
      sb.append(counter + "\t");
      ++counter;
    }
    //line alone
    if (nextLine[0].isEmpty()) {
      sb.append(str.substring(off,off+len));
    }
    while (!nextLine[0].isEmpty()) {
      sb.append(nextLine[0]);
      if(nextLine[0].indexOf('\r') != -1 || nextLine[0].indexOf('\n') != -1) {
        sb.append(counter + "\t");
        ++counter;
      }

        nextLine = Utils.getNextLine(nextLine[1]);
        if(nextLine[0].isEmpty()) {
          sb.append(nextLine[1]);
        }
    }

    out.write(sb.toString());
  }

  // TODO not tested method
  @Override public void write(char[] cbuf, int off, int len)
          throws IOException {
    throw new UnsupportedOperationException(
            "The student has not implemented this method yet.");
  }

  // TODO pass test but will fail if tested with OSX
  @Override public void write(int c) throws IOException {
    if(firstLine) {
      firstLine = false;
      addLineNumber();
    }
    out.write(c);

    if(c == '\n') {
      addLineNumber();
    }

  }
  void addLineNumber() throws IOException {
    out.write(counter + "\t");
    ++counter;
  }
}

