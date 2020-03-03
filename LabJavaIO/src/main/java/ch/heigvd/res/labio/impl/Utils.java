package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments. 
   * 
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines) {
    final char CR = '\r';
    final char LF = '\n';
    final int size = lines.length();
    int off = 1;

    for (int i = 0; i < size; i++) {
      char c = lines.charAt(i);

      if (c == CR) {
        // Linux Unix or Windows case
        if (i + 1 < size && lines.charAt(i + 1) == LF) {
          // Windows case
          off = 2;
        }
        // Linux Unix case
        return new String[]{lines.substring(0, i + off), lines.substring(i + off)};
      } else if (c == LF) {
        // Mac OS case
        return new String[]{lines.substring(0, i + off), lines.substring(i + off)};
      }
    }

    // No line separator found
    return new String[]{"", lines};
  }
}
