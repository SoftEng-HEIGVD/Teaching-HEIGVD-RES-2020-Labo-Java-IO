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
    String[] result = new String[2];
    String temp = "";
    int offset = 0;
    int i;
    for (i = 0; i < lines.length() - 1 && offset == 0; ++i) {
      if (lines.charAt(i) == '\\') {
        if (i + 3 < lines.length()) {
          if (lines.substring(i, i + 4).equals("\\r\\n")) {
            temp = lines.substring(0, i + 4);
            offset = 4;
          }
        } else if (lines.substring(i, i + 2).equals("\\r") || lines.substring(i, i + 2).equals("\\n")) {
          temp = lines.substring(0, i + 2);
          offset = 2;
        }
      }
    }
    result[0] = temp;
    if (offset==0) {
      result[1] = lines;
    }
    else {
      result[1] = lines.substring(i+offset);
    }
    return result;
  }


}
