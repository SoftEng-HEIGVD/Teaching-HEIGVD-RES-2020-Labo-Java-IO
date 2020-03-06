package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 * @author Olivier Liechti
 */
public class Utils
{

	private static final char   CR  = '\r'; // carriage return
	private static final char   LF  = '\n'; // linefeed
	private static final Logger LOG = Logger.getLogger(Utils.class.getName());

	/**
	 * This method looks for the next new line separators (\r, \n, \r\n) to extract
	 * the next line in the string passed in arguments.
	 *
	 * @param lines a string that may contain 0, 1 or more lines
	 *
	 * @return an array with 2 elements; the first element is the next line with
	 * the line separator, the second element is the remaining text. If the argument does
	 * not
	 * contain any line separator, then the first element is an empty string.
	 */
	public static String[] getNextLine(String lines)
	{
		int posCR = lines.indexOf(CR);
		int posLF = lines.indexOf(LF);
		int pos   = posCR + 1;  // we position ourselves after the carriage return

		if (pos == posLF) { // if there's a linefeed after the carriage return
			pos += 1;      // we position ourselves after the linefeed
		} else if (posLF != -1) {
			pos = posLF + 1;
		}

		return new String[]{lines.substring(0, pos), lines.substring(pos)};
	}

}
