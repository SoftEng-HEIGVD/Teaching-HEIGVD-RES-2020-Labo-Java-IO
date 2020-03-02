package ch.heigvd.res.labio.impl.filters;

import ch.heigvd.res.labio.impl.Utils;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

import ch.heigvd.res.labio.impl.Utils;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 * <p>
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 * @author Zied Naimi
 */
public class FileNumberingFilterWriter extends FilterWriter {

    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
    private int lineNumber = 0;
    private boolean lineBreak = true;

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        str = str.substring(off, off + len);

        for (char c : str.toCharArray()) {

            if (c == Utils.UNIX_SEPARATOR) { // '/n' and '/r/n' handled
                super.out.write(c);
                super.out.write(++lineNumber + "\t");
                lineBreak = false;
            } else if (lineBreak) { // '/r'
                super.out.write(++lineNumber + "\t");
                super.out.write(c);
                lineBreak = false;
            } else if ((c == Utils.MACOS_SEPARATOR)) {
                super.out.write(c);
                lineBreak = true;
            } else {
                super.out.write(c);
            }
        }
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        write(String.copyValueOf(cbuf), off, len);
    }

    @Override
    public void write(int c) throws IOException {
        write(String.valueOf((char) c), 0, 1);
    }

}
