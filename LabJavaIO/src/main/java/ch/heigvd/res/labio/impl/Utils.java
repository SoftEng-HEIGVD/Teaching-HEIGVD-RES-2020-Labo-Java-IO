package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti, Florian Mülhauser
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
    int posSeparatorL = lines.indexOf('\n'); // linux
    int posSeparatorM = lines.indexOf('\r'); // linux
    int posSeparatorW = lines.indexOf("\r\n"); // linux

    int len = lines.length();

    String[] ret = new String[2];
    if (posSeparatorL != -1) {
      ret[0] = lines.substring(0, posSeparatorL + 1);

      if (posSeparatorL + 1 < len) {
        ret[1] = lines.substring(posSeparatorL + 1);
      } else {
        ret[1] = "";
      }
    } else if (posSeparatorM != -1) {

      ret[0] = lines.substring(0, posSeparatorM + 1);
      if (posSeparatorM + 1 < len) {
        ret[1] = lines.substring(posSeparatorM + 1);
      } else {
        ret[1] = "";
      }

    } else if (posSeparatorW != -1) {

      ret[0] = lines.substring(0, posSeparatorW + 1);
      if (posSeparatorW + 1 < len) {
        ret[1] = lines.substring(posSeparatorW + 1);
      } else {
        ret[1] = "";
      }

    } else { //default
      ret[0] = "";
      ret[1] = lines;
    }

    return ret;
  }
}
