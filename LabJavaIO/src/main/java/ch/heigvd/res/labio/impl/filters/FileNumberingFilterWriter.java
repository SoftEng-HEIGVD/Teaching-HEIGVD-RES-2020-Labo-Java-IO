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
  boolean iAmTheFirstChar = true;
  boolean theLastCharWasABackSlashR = false;
  int numberOfActualLine = 1;
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
    for(int i=0; i < cbuf.length; i++ ) {
      int number = cbuf[i];
      write(number);
    }
  }

  @Override
  public void write(int c) throws IOException {
    char chara = (char) c;
    if (iAmTheFirstChar) {
      String firstTab = numberOfActualLine++ + "\t";
      out.write(firstTab + chara);
      iAmTheFirstChar = false;
    } else {
      if (chara == '\n') {
        if (theLastCharWasABackSlashR) {
          String aTab = numberOfActualLine++ + "\t";
          out.write(chara + aTab);
          theLastCharWasABackSlashR = false;
        } else {
          String aTab = numberOfActualLine++ + "\t";
          out.write(chara + aTab);
        }
      } else if (chara == '\r') {
        theLastCharWasABackSlashR = true;
        out.write(chara);
      } else {
        if (theLastCharWasABackSlashR) {
          String aTab = numberOfActualLine++ + "\t";
          out.write(aTab + chara);
          theLastCharWasABackSlashR = false;
        } else {
          out.write(chara);
        }
      }
    }
  }
}
