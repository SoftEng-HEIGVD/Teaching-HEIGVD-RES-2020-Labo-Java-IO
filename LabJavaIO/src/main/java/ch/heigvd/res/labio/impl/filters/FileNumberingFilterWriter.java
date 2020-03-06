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

  private int counter = 1;
  private boolean firstLine = true;
  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {

      //If empty string
      if(len < 1) return;

      String sub = str.substring(off, off + len );

      /*
         nextLines provide a two strings tab :
         [0] : first line found with a delimiter (\n,\r, \r\n)
         [1] : the rest of the input string
         If the input string has no delimiter (one line), [0] will be empty and [1] will contains the line
      */
      String[] nextLines = Utils.getNextLine(sub);

      //Add the first numbering
      if(firstLine){

          if(nextLines[0] == "" && nextLines[1] != ""){
              nextLines[1] = "1\t" + nextLines[1];
          }else{
              nextLines[0] = "1\t" + nextLines[0];
          }
          firstLine = false;
      }

      //Case of only one line
      if(nextLines[0] == "" && nextLines[1] != ""){
          super.out.write(nextLines[1]);
          return;
      }

      //Add the number
      if(nextLines[0] != "") {
          char lastChar = nextLines[0].charAt(nextLines[0].length() - 1);
          if (lastChar == '\n' || lastChar == '\r')
              nextLines[0] += (++counter) + "\t";
      }

      //Write the line
      super.out.write(nextLines[0]);
      //If there is more than one line
      if(nextLines[1].length() > 1)
          this.write(nextLines[1]);

  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
      this.write(String.copyValueOf(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {

      /*
        I could do it with this only instruction , but it won't passe the test itShouldHandleWriteWithAnInt()
        Because this test assume that if you have \r then \n you have only one line numbering !
       */
      // this.write(Character.toString((char) c),0, 1);

      String writing = "";
      char caract = (char) c;
      if(firstLine) {
          writing += "1\t";
          firstLine = false;
      }

      if(c == '\n') {
          writing +=  "\n" + (++counter) + "\t";
          super.out.write(writing);
          return;
      }

      super.out.write(writing + caract);
  }

}
