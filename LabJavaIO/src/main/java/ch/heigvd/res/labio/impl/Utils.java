package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 * Text handling utilities
 *
 * @author Olivier Liechti & Moïn DANAI
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments.
   *
   * @author Moïn DANAI
   *
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with the
   *         line separator, the second element is the remaining text. If the
   *         argument does not contain any line separator, then the first element
   *         is an empty string.
   */
  public static String[] getNextLine(String lines) {
    String[] res;

    // defining \r, \n, \r\n
    String separatorMac = "\r", separatorLinux = "\n", separatorWindows = separatorMac + separatorLinux;

    // first test the Windows case, as the separator is the longest
    if (lines.contains(separatorWindows)) {
      res = split(separatorWindows, lines);
    } // then Mac and Linux cases
    else if (lines.contains(separatorMac)) {
      res = split(separatorMac, lines);
    } else if (lines.contains(separatorLinux)) {
      res = split(separatorLinux, lines);
    } else { // no separator detected
      res = new String[] { "", lines };
    }

    return res;
  }

  /**
   * Split the given {@code lines} by the first occurence of {@code sep} and
   * return the produced lines
   * <p>
   * We use such a method because we'd like to keep the {@code sep} at the end of
   * the first line, as per {@see Utils#getNextLine} javadoc.
   *
   * @param sep   string separator
   * @param lines text to split
   */
  public static String[] split(String sep, String lines) {
    String[] res = lines.split(sep, 2);
    res[0] += sep;
    return res;
  }
}
