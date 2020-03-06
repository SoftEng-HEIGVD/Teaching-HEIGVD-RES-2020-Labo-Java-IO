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
 */
public class FileNumberingFilterWriter extends FilterWriter {

    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

    private int counter;
    private char lastChar;

    public FileNumberingFilterWriter(Writer out) {
        super(out);

        counter = 1;
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        this.write(str.substring(off, off + len).toCharArray());
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        for(int i = 0; i < cbuf.length; ++i)
            this.write(cbuf[i]);
    }

    @Override
    public void write(int c) throws IOException {
        if(counter == 1) {
            out.write(counter++ + "\t" + (char) c);
        } else if (c == '\r') {

        } else if (c == '\n') {
            out.write((lastChar == '\r' ? "\r" : "") + "\n" + counter++ + "\t");
        } else {
            if(lastChar == '\r')
                out.write("\r" + counter++ + "\t");

            out.write(c);
        }

        lastChar = (char) c;
    }
}
