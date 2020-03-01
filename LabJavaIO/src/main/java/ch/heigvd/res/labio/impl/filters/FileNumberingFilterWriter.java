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
  private int nbLine=1;
  private String pChar="";
  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
      str = str.substring(off,off+len);

    for (char currentChar: str.toCharArray() ) {
      if (pChar.isEmpty()) {
        super.out.write(nbLine + "\t");
        pChar = nbLine + "ŧ";
        nbLine++;
      }
      if((pChar + currentChar).equalsIgnoreCase(Utils.WINDOWS)){
        super.out.write(pChar + currentChar);
        pChar = "";
      } else if(String.valueOf(currentChar).equalsIgnoreCase(Utils.UNXSUBSYS)){
        super.out.write(currentChar);
        pChar = "";
      } else if (String.valueOf(currentChar).equalsIgnoreCase(Utils.MACOS)) {
        pChar = String.valueOf(currentChar);
      } else if (pChar.equalsIgnoreCase(Utils.MACOS)) {
        super.out.write(pChar);

        super.out.write(nbLine + "\t");
        pChar = nbLine + "\t";
        nbLine++;

        super.out.write(currentChar);
        pChar = String.valueOf(currentChar);
      }
      else {
        super.out.write(currentChar);
        pChar = String.valueOf(currentChar);
      }
    }

    if(pChar.isEmpty()){
      super.out.write(nbLine + "\t");
      pChar = nbLine + "\t";
      nbLine++;
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    write(String.copyValueOf(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {
    write(String.valueOf((char) c), 0, 1);
  }

}
