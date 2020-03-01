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
  private static int nbLines = 0;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
      String[] elements = Utils.getNextLine(str);
      while(!elements[0].equals("")) {
        //Première ligne
        if(nbLines == 0) {
          out.write(Integer.toString(++nbLines) + '\t' + elements[0]);
        }
        else {
          out.write(elements[0]);
        }
        elements = Utils.getNextLine(elements[1]);
      }
    out.write(Integer.toString(++nbLines) + '\t' + elements[0]);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

  @Override
  public void write(int c) throws IOException {
    throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

}
