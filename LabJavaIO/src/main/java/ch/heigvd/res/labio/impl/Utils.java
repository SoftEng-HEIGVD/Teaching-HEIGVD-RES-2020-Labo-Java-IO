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
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
    String[] result = new String[2];
    String str = "";

    for (int i = 0; i < lines.length(); i++) {
      str += lines.charAt(i);
      if (lines.charAt(i) == '\r' && ((i + 1) != lines.length()) && lines.charAt(i + 1) == '\n') {
        continue;
      } else if (lines.charAt(i) == '\r' || lines.charAt(i) == '\n') {
        result[0] = str;
        result[1] = lines.substring(i + 1);
        break;
      } else if (i == lines.length() - 1) {
        result[0] = "";
        result[1] = str;
      }
    }
    return result;
  }
}
