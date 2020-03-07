package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti - Modified by Nicolas Müller on 07.03.2020
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

    // Get position of first separator occurence in lines. Returns -1 if not found

    int posLinuxSeparator = lines.indexOf("\n");
    int posMacSeparator = lines.indexOf("\r");
    int posWindowsSeparator = lines.indexOf("\r\n");

    // On windows
    if (posWindowsSeparator != -1) {

      result[0] = lines.substring(0, posWindowsSeparator + 2); // +2 because delimiter length is 2

      if (posWindowsSeparator + 3 <= lines.length()) {
        result[1] = lines.substring(posWindowsSeparator + 2);  // Takes the rest of the string
      }
      else {
        result[1] = "";
      }
    }

    // On Linux
    else if (posLinuxSeparator != -1) {

      result[0] = lines.substring(0, posLinuxSeparator + 1);

      if (posLinuxSeparator + 2 <= lines.length()) {
        result[1] = lines.substring(posLinuxSeparator + 1);
      }
      else {
        result[1] = "";
      }
    }

    // On macOS
    else if (posMacSeparator != -1) {

      result[0] = lines.substring(0, posMacSeparator + 1);

      if (posMacSeparator + 2 <= lines.length()) {
        result[1] = lines.substring(posMacSeparator + 1);
      }
      else {
        result[1] = "";
      }
    }

    // Nothing found returns empty string
    else {

      result[0] = "";
      result[1] = lines; // Unchanged string is the remaining part
    }

    return result;
  }

}
