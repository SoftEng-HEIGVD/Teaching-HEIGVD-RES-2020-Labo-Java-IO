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
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
    private int lineNb = 1;
    private boolean firstLine = true;

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        if(firstLine){
            str = lineNb++ + "\t" + str;
            firstLine = false;
        }

        // todo: compter combien y a de \r et replace autant de fois .. comme ca si y a 2 \r dans 1 ligne ba on fait 2 fois le lineNb++ etc...

        if(str.contains("\n")){
            str = str.replaceAll("\n", "\n" + lineNb++ + "\t");
        }else if(str.contains("\r")){
            str = str.replaceAll("\r", "\r" + lineNb++ + "\t");
        }else if(str.contains("\r\n")){
            str = str.replaceAll("\r\n", "\r\n" + lineNb++ + "\t");
        }

        super.write(str, off, str.length());
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        throw new UnsupportedOperationException("The student has not implemented this method yet.");
    }

    @Override
    public void write(int c) throws IOException {
        throw new UnsupportedOperationException("The student has not implemented this method yet.");
    }

}
