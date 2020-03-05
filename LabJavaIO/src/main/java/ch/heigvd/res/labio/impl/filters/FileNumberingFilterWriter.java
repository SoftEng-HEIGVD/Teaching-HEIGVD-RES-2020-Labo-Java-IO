package ch.heigvd.res.labio.impl.filters;

import ch.heigvd.res.labio.impl.Utils;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {

        //int lineCounter = 1;
        String strNumbered = "";

        /*while (!Utils.getNextLine(str.substring(off, off + len))[0].equals("")) {
            strNumbered += lineCounter + "\t" + Utils.getNextLine(str)[0];
            str = Utils.getNextLine(str.substring(off, off + len))[1];
            lineCounter++;
        }*/

        super.write(strNumbered, off, len);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        String str = "";

        for(char c : cbuf)
            str += c;

        super.write(str, off, len);
    }

    @Override
    public void write(int c) throws IOException {
        super.write(c);
    }

}
