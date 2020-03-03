package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\nWorld -> 1\tHello\n2\tWorld
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
    int counter = 1;
    String strNumbered = "";
    for (String line : str.split("\\r?\\n")) {
        strNumbered = ++counter + "\t" + line;
    }

    super.write(strNumbered, off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    super.write(cbuf, off, len);
  }

  @Override
  public void write(int c) throws IOException {
    /*if(counter == 0) {
        counter++;
        System.out.println("WRITING : " + counter + '\t' + (char) c);
        super.write(counter + '\t' + (char) c);
    } else if(c == '\n') {
        super.write((char) c + ++counter + '\t');
    } else {
        super.write(c);
    }*/

    super.write(c);
  }

}
