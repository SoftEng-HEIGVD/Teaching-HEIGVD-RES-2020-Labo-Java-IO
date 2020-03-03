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
    private int lineNb = 0;
    private boolean start = true;

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }

    private void writeLineNb() throws IOException {
        out.write((++lineNb) + "\t");
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        String[] lines = Utils.getNextLine(str.substring(off, off+len));

        // First time only display the line nb
        if(lineNb == 0){
            writeLineNb();
        }

        // There is some line return
        if(!lines[0].equals("")) {
            // Write the line and the line nb
            out.write(lines[0]);
            writeLineNb();
            // Write next line
            if(!lines[1].equals("")){
                write(lines[1]);
            }
        }
        // No return line so only one simple line remaining
        else if(!lines[1].equals("")){
            out.write(lines[1]);
        }


    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        write(String.valueOf(cbuf), off, len);
    }

    @Override
    public void write(int c) throws IOException {
       // TODO
    }

}
