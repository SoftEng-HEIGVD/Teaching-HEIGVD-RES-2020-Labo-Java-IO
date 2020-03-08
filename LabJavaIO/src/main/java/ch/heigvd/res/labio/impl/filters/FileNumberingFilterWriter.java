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
  private boolean newFile;
  private boolean lineEnding;
  private int lineNo;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
    lineNo = 1;
    newFile = true;
  }

  @Override
  public void write(String str, int off, int len) throws IOException {

    String strToWrite = str.substring(off, len+off);
    for(int i=0; i<strToWrite.length(); ++i)
    {
      this.write(strToWrite.charAt(i));
    }
    if(lineEnding)
    {
      this.writeLineNo();
    }

    /*String line = "a";
    String rest = str.substring(off, len);
    int lineNo = 1;
    while(!line.isEmpty() && !rest.isEmpty())
    {
      String[] splitLines = Utils.getNextLine(rest);
      line = splitLines[0];
      rest = splitLines[1];
      super.write(String.valueOf(lineNo) + "\t" + line, 0, line.length()+2);
      lineNo++;
    }
    super.write(String.valueOf(lineNo) + "\t" + rest, 0, rest.length()+2);*/
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    int i;
    for(i=off; i<len+off && i<cbuf.length+off; ++i)
    {
      this.write(cbuf[i]);
    }
    if(cbuf[i] == '\r')
    {
      this.writeLineNo();
    }
  }

  @Override
  public void write(int c) throws IOException {

    if(newFile)
    {
      newFile = false;
      writeLineNo();
    }

    if(c != '\n' && lineEnding)
    {
      lineEnding = false;
      writeLineNo();
    }

    super.write(c);

    if(c == '\n')
    {
      lineEnding = false;
      writeLineNo();
    }

    if(c == '\r')
    {
      lineEnding = true;
    }
  }

  public void writeLineNo() throws IOException
  {
    this.writePositiveInteger(lineNo);
    super.write('\t');
    lineNo++;
  }

  public void writePositiveInteger(int n) throws IOException
  {
    if(n>0)
    {
      writePositiveInteger(n/10);
      super.write(n%10+'0');
    }
  }
}
