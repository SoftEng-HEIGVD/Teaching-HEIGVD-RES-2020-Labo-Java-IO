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
  private int numCounter; // counter of lines (starts at 0)
  private int oldC; // old char written

  public FileNumberingFilterWriter(Writer out) {
    super(out);
    this.numCounter = 0;
    this.oldC = 0;
  }

  private void writeNewLine() throws IOException {
    this.out.write(String.format("%d\t", ++numCounter));
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    String strToProcess = str.substring(off, len + off);
    String[] reading = Utils.getNextLine(strToProcess);

    if(numCounter == 0)
      writeNewLine();

    while(!reading[0].isEmpty()){ // While there is a line to read
      this.out.write(reading[0]);
      strToProcess = reading[1];
      reading = Utils.getNextLine(strToProcess);
      writeNewLine();
    }

    this.out.write(reading[1]); // We write the rest of the text because there is any line left
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    this.write(new String(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {
    if(numCounter == 0)
      writeNewLine();

    this.out.write(c);

    // Case Unix
    if(oldC != '\r' && c == '\n')
      writeNewLine();
    // Case Mac OS
    else if(oldC == '\r' && c != '\n') {
      writeNewLine();
      this.out.write('\r');
    }
    else if(c == '\r') {
      oldC = '\r';
      return;
    }
    // Case Windows
    else if(oldC == '\r')
      writeNewLine();

    oldC = c;
  }

}
