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

    //utilisé pour le write(int c)
    private int nbrLine;
    private boolean isBackR;

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        write(str.toCharArray(), off, len);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        for (int i = 0; i < len; ++i) {
            write(cbuf[i + off]);
        }
    }

    @Override
    public void write(int c) throws IOException {
        if (nbrLine == 0 || c != '\n' && isBackR) {
            nbrLine++;
            out.write(nbrLine + "\t");
        }

        out.write(c);

        if (c == '\n') {
            out.write(++nbrLine + "\t");
        }

        isBackR = c == '\r';
    }

}

