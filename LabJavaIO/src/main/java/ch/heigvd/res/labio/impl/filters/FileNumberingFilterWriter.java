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

        String[] lines = new String[] {"",str};

        //init first tab
        if(start)
            out.write(++counter + "\t", 0, 2);

        while(true){
            //get next line
            lines = Utils.getNextLine(lines[1]);

            if(lines[0].length() != 0) {
                out.write(lines[0], start ? off : 0, Math.min(lines[0].length(),len));
                len -= lines[0].length();
                out.write(++counter + "\t",0,String.valueOf(counter).length() + 1);
                start = false;
            } else {
                out.write(lines[1], start ? off : 0, Math.min(lines[1].length(),len));
                start = false;
                break;
            }

        }

    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        //call of string method
        write(new String(cbuf), off, len);
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
