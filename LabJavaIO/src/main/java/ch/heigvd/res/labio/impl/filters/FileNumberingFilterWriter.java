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
 * Hello\n\World -> 1\tHello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
    private int counter = 0;
    private boolean isFirst = true;

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        if (off < 0 || len < 1 || off > str.length() || len > str.length() || off + len > str.length()) {
            throw new IOException("Offset or length invalid");
        } else {
            StringBuilder buffer = new StringBuilder();

            if (isFirst) {
                buffer.append(++counter);
                buffer.append('\t');
                isFirst = false;
            }

            for (int i = off; i < off + len; i++) {
                buffer.append(str.charAt(i));
                if (str.charAt(i) == '\n' || (str.charAt(i) == '\r' && str.charAt(i + 1) != '\n')) {
                    buffer.append(++counter);
                    buffer.append('\t');
                }
            }
            out.write(buffer.toString());
        }
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        if (off < 0 || len < 1 || off > cbuf.length || len > cbuf.length || off + len > cbuf.length) {
            throw new IOException("Offset or length invalid");
        } else {
            StringBuilder buffer = new StringBuilder();

            if (isFirst) {
                buffer.append(++counter);
                buffer.append('\t');
                isFirst = false;
            }

            for (int i = off; i < off + len; i++) {
                buffer.append(cbuf[i]);
                if (cbuf[i] == '\n' || (cbuf[i] == '\r' && cbuf[i + 1] != '\n')) {
                    buffer.append(++counter);
                    buffer.append('\t');
                }
            }
            out.write(buffer.toString());
        }
    }

    @Override
    public void write(int c) throws IOException {
        if ((char) c == '\n') {
            out.write('\n' + Integer.toString(++counter) + '\t');
        } else if (isFirst) {
            out.write(Integer.toString(++counter) + '\t' + (char) c);
            isFirst = false;
        } else {
            out.write((char) c);
        }
    }

}
