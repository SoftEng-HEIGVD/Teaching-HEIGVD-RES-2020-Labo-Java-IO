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
    // using positive look-behind and negative look-ahead regex, explained at https://stackoverflow.com/a/4416576/12199702
    String[] split = lines.split("(?<=\r\n)|(?<=\n)|(?<=\r)(?!\n)", 2); // need negative look-ahead to not resplit on \r\n

    if (split.length != 2) { // no line separator
      return new String[] {"", split[0]};
    }

    return split;
  }

}
