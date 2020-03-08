package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 * @author Tiffany Bonzon
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
    String[] ret = new String[2];
    String windowsSeparator = "\r\n";
    String oldMacSeparator = "\r";
    String unixSeparator = "\n";
    int separatorIndex;

    if(lines.contains(windowsSeparator)) {
      separatorIndex = lines.indexOf(windowsSeparator) + windowsSeparator.length();
    } else if(lines.contains(oldMacSeparator)) {
      separatorIndex = lines.indexOf(oldMacSeparator) + oldMacSeparator.length();
    } else if(lines.contains(unixSeparator)) {
      separatorIndex = lines.indexOf(unixSeparator) + unixSeparator.length();
    } else { //No separator
      separatorIndex = 0;
    }

    ret[0] = lines.substring(0, separatorIndex);
    ret[1] = lines.substring(separatorIndex);

    return ret;
  }

}
