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
  private boolean isFirstLine;
  private int lastChar;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
    this.lineCounter = 1;
    this.isFirstLine = true;
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    str = str.substring(off,off+len);
    this.write(str.toCharArray(),0,str.length());
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for (int i = off;i < off + len; i++) {
      this.write(cbuf[i]);
    }

  }

  @Override
  public void write(int c) throws IOException {

    if (this.isFirstLine) {
      this.isFirstLine = false;
      super.out.write(this.lineCounter++ + "\t");
    }

    if (this.lastChar == '\r' && c != '\n') {
      super.out.write(this.lineCounter++ + "\t");
    }

    super.out.write(c);

    if(c == '\n') {
      super.out.write(this.lineCounter++ + "\t");
    }

    this.lastChar = c;
  }

}
