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
 * @author Olivier Liechti, Robin Demarta
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  private int lineCounter = 0;
  private int lastChar = -1;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    for(char c : str.substring(off, off+len).toCharArray())
      write(c);

    /*
    // Version using getNextLine()
    if(lineCounter == 0)
      out.write((++lineCounter) + "\t");
    String[] lines = Utils.getNextLine(str.substring(off, off+len));

    if(!lines[0].equals("")) {
      out.write(lines[0]);
      writeLineCounter();
      if(!lines[1].equals(""))
        write(lines[1]);
    } else if(!lines[1].equals(""))
      out.write(lines[1]);
    */
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    write(new String(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {
    if(lastChar == -1) {
      // First line
      writeLineCounter();
    } else if(lastChar == '\r' && c != '\n') {
      // Line ending with \r
      writeLineCounter();
    }

    out.write(c);

    if(c == '\n')
      // Line ending with \n or \r\n
      writeLineCounter();

    lastChar = c;
  }

  public void writeLineCounter() throws IOException {
    out.write((++lineCounter) + "\t");
  }

}