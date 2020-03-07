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
 * <p>
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

    private int number = 1;
    private boolean beginingFlag = true;
    private boolean rnFlag = false;
    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        write(str.toCharArray(), off, len);

    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        for (int i = off; i < off + len; i++) {
            write(cbuf[i]);

        }
      write(0);

    }


    @Override
    public void write(int c) throws IOException {
        if (beginingFlag) {
          beginingFlag=false;
            out.write(number++ + "\t");
            out.write(c);
        } else {
            if(c == 13 || c == 10){
              if(!rnFlag){
                rnFlag=true;
                out.write(c);
                //out.write(number++ + "\t");
              } else {
                out.write(c);
              }


            } else {
              if(rnFlag){
                out.write(number++ + "\t");
              }
              rnFlag=false;
              if(c != 0) { // making c out of Java, all of this is due to \r\n
                out.write(c);
              }
            }
        }
    }

}
