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
    String[] sepStrings = {"\r\n", "\n", "\r"};
    
    int i = -1, j = 0;
    // We suppose the new line word is the same in the whole
    // file (seems quite reasonable)
    while (i == -1 && j < sepStrings.length) {
      i = lines.indexOf(sepStrings[j++]);
    }

    if (i < 0) {
      return new String[] {"", lines};
    }
    
    i += sepStrings[j-1].length();

    return new String[] {lines.substring(0, i), lines.substring(i)};
  }

}
