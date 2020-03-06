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
public class FileNumberingFilterWriter extends FilterWriter
{
    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

    public FileNumberingFilterWriter(Writer out) {
      super(out);
    }

    private int lineCounter = 0;
    private boolean lastWasR = false;

    @Override
    public void write(String str, int off, int len) throws IOException
    {
        // Calls the write(char) method for each character
        for (int i = off; i < off + len; ++i)
        {
            this.write(str.charAt(i));
        }
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException
    {
        // Same concept as the UpperCaseFilter
        this.write(new String(cbuf), off, len);
    }

    @Override
    public void write(int c) throws IOException
    {
        if (lineCounter == 0)
        {
            this.addLineNumberAndTab();
        }

        if (c == '\n')
        {
            super.write(c);
            this.addLineNumberAndTab();
        }
        else
        {
            if (lastWasR)
            {
                this.addLineNumberAndTab();
            }

            super.write(c);
        }

        lastWasR = (c == '\r');
    }

    private void addLineNumberAndTab() throws IOException
    {
        String toSend = String.valueOf(++lineCounter) + '\t';
        super.write(toSend, 0, toSend.length());
    }
}
