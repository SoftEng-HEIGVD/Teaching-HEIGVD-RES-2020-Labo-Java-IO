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
 * Hello\nWorld -> 1\tHello\n2\tWorld
 *
 * @author Olivier Liechti
 * @author Ludovic Bonzon
 */
public class FileNumberingFilterWriter extends FilterWriter {

    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
    private int lineNb = 1;                 // Counter for the line number
    private int lastCharWritten = -1;       // Memorises the last char written in the file

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        // Uses the write method for chars
        for(char c : str.substring(off, off + len).toCharArray()) {
            this.write(c);
        }

        /* Version with Utils.getNextLine()
        //Abandonned because it got issues with double return chars

        if(lineNb == 1) {
            writeLineNb();
        }
        String[] lines = Utils.getNextLine(str);

        // 1 or more lines
        if(lines[0].length() != 0) {
            out.write(lines[0]);
            writeLineNb();
            if(lines[1].length() != 0) {
                write(lines[1]);
            }
        }
        // Only 1 line
        else if(lines[1].length() != 0) {
            out.write(lines[1], off, len);
        }*/
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        this.write(new String(cbuf), off, len);
    }

    @Override
    public void write(int c) throws IOException {
        if(lastCharWritten == -1) { // If it's the first line
            writeLineNb();
        }

        if(c == '\n') { // '\r\n' and '\n'
            super.out.write(c);
            writeLineNb();
        } else if(lastCharWritten == '\r') { // '\r'
            writeLineNb();
            super.out.write(c);
        } else { // Any other char
            super.out.write(c);
        }
        lastCharWritten = c;
    }

    private void writeLineNb() throws IOException {
        out.write(lineNb++ + "\t");
    }

}