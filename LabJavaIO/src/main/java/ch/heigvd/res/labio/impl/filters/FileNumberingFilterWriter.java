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

    private int counter = 1;
    private boolean isFirstWrite = true;

    private boolean needNumber = true;

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String[] tmp = {str.substring(off), ""};
        tmp = Utils.getNextLine(tmp[0]);


        if (isFirstWrite) {
            stringBuilder.append(counter++).append("\t");
            isFirstWrite = false;

        }

        while (!tmp[0].isEmpty()) {
            stringBuilder.append(tmp[0]).append(counter++).append("\t");
            tmp = Utils.getNextLine(tmp[1]);

        }
        stringBuilder.append(tmp[1]);

        super.write(stringBuilder.toString(), 0, len + (stringBuilder.length() - str.length() + off));
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        write(new String(cbuf), off, len);
    }

    @Override
    public void write(int c) throws IOException {
        if (needNumber && !(c == '\n' || c == '\r')) {
            out.write((counter++) + "\t");
            needNumber = false;
        } else {

            if (c == '\n' || c == '\r') {
                needNumber = true;
            }
        }
        super.write(c);
    }

}
