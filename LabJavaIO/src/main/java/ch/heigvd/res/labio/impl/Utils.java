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
    char c;
    int offset = 1;
    // check chars of lines from left to right to find the first \r or \n char
    for (int i = 0; i < lines.length(); ++i) {
      c = lines.charAt(i);
      if (c == '\r' || c == '\n') {
        // adapt the offset if the next char is a \n
        if (i+1 < lines.length() && lines.charAt(i+1) == '\n') {
          ++offset;
        }
        return new String[] {lines.substring(0, i+offset), lines.substring(i+offset)};
      }
    }
    // return this string array if no \r or \n were found
    return new String[] {"", lines};
  }

}
