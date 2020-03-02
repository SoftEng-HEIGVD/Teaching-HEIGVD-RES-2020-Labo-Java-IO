package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti, Robin Demarta
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
    // ?<= means that the separator (aka \n, \r or \r\n) will be kept in output
    // \r(?!\n) means that we don't want the \r to be followed by \n (otherwise the (\r\n) would never be considered)
    String[] result = lines.split("(?<=((\r\n)|\n|(\r(?!\n))))", 2);

    return result.length == 1 ? new String[]{"", result[0]} : result; // Complete array if there's no line return
  }
}
