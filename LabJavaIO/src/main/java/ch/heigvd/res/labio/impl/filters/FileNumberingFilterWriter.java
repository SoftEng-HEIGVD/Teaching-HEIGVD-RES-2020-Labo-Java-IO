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
    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        write(str.toCharArray(), off, len);
    beginingFlag=true;
    number=1;
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        for (int i = off; i < off + len; i++) {
            write(cbuf[i]);
        }
    beginingFlag=true;
    number=1;
    }


    @Override
    public void write(int c) throws IOException {
        if (beginingFlag) {
          beginingFlag=false;
            out.write(Character.forDigit(number++,10));

            /*if (c != (char) 92) { //this is a backslash
                out.write((char) 92);
            }*/
          out.write(9); // tab
            out.write(c);
            //  Hello\n\World -> 1\Hello\n2\tWorld
        } else {
            if(c== 92){
              out.write(Character.forDigit(number++,10));
              out.write(9); // tab
            } else {
              out.write(c);
            }
        }
    }

}
