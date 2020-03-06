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
public class FileNumberingFilterWriter extends FilterWriter
{

	private static final Logger LOG      = Logger.getLogger(
		FileNumberingFilterWriter.class.getName());
	private              int    line     = 1;
	private              int    lastChar = -1;

	// the following are used as String and not char so that we can print without having
	// to cast our integers in write(int c)
	private static final String CR  = "\r"; // carriage return
	private static final String LF  = "\n"; // linefeed
	private static final String TAB = "\t"; // tabulation

	public FileNumberingFilterWriter(Writer out)
	{
		super(out);
	}

	@Override
	public void write(String str, int off, int len) throws IOException
	{
		for (int i = off; i < off + len; i++) {
			write(str.charAt(i));
		}
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException
	{
		for (int i = off; i < off + len; i++) {
			write(cbuf[i]);
		}
	}

	@Override
	public void write(int c) throws IOException
	{
		String str = "";
		if (line == 1) {
			str += line + TAB;
			line += 1;
		}

		if (LF.charAt(0) == c) {
			str += LF + line + TAB;
			line += 1;
		} else {
			if (CR.charAt(0) == lastChar) {
				str += line + TAB;
				line += 1;
			}
			str += (char) c;
		}
		lastChar = c;
		super.write(str, 0, str.length());
	}

}
