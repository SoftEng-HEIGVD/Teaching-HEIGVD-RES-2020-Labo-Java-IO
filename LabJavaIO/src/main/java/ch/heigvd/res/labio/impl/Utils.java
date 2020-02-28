package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

import static java.lang.Integer.min;

/**
 *
 * @author Olivier Liechti
 * @author Gabriel Roch
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
    int posR = lines.indexOf("\r");
    int posN = lines.indexOf("\n");
    int pos;

    if (posN == -1)
      pos = posR + 1;
    else if (posR == -1 || posR + 1 == posN)
      pos = posN + 1;
    else
      pos = min(posN, posR) + 1;

    return new String[] {
            lines.substring(0, pos),
            lines.substring(pos)
    };
  }

}
