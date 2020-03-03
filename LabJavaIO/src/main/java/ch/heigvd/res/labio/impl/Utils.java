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
    //get all seperators indexes
    int unix = lines.indexOf("\n");
    int macOS = lines.indexOf("\r");
    int windows = lines.indexOf("\r\n");

    //get first line
    int index = Math.max(unix,macOS) + 1;

    //check if no found separator
    if(index == 0)
      return new String[] {"",lines};

    //check if windows of maxOS
    index = windows == macOS && unix == -1 ? index + 1 : index;

    return new String[] {lines.substring(0, index),lines.substring(index)};
  }

}
