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

    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
    private int counter = 0;
    private boolean start = true;

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        //call of char[] method
        write(str.toCharArray(), off, len);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {

        String res = new String();
        int lenFinal = len;

        if(start){
          res += ++counter + "\t";
          start = false;
        }

        //copies into result all the characters, from off for len
        for (int i = off; i < len + off; i++){

            if(cbuf[i] == '\r' && cbuf[i+1] == '\n') {
                i++; lenFinal--;
            }

            res += cbuf[i];

            //check windows and MacOS
            if (cbuf[i] == '\n' || cbuf[i] == '\r') {
                res += ++counter + "\t";
            }

        }

        out.write(res, 0, lenFinal + counter * 2);
    }

    @Override
    public void write(int c) throws IOException {

      if(start){
        out.write(++counter + "\t");
        start = false;
      }

      out.write(c);

      if((char)c == '\n')
          out.write(++counter + "\t");

    }

}
