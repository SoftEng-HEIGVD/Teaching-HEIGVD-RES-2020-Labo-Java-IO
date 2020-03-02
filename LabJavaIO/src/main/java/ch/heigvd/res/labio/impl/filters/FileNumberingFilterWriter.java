package ch.heigvd.res.labio.impl.filters;

import ch.heigvd.res.labio.impl.Utils;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
  private int numCounter;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
    this.numCounter = 0;
  }

  private static boolean isABreakLine(char c){
    switch (c){
      case '\n':
      case '\r':
        return true;
      default:
        return false;
    }
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    String strToProcess = str.substring(off, len + off);
    String[] reading = Utils.getNextLine(strToProcess);

    if(numCounter == 0)
      this.out.write(String.format("%d\t", ++numCounter));

    while(!reading[0].isEmpty()){
      this.out.write(reading[0]);
      strToProcess = reading[1];
      reading = Utils.getNextLine(strToProcess);
      this.out.write(String.format("%d\t", ++numCounter));
    }

    this.out.write(reading[1]);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    this.write(new String(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {
    // TODO : je vois pas comment le faire
  }

}
