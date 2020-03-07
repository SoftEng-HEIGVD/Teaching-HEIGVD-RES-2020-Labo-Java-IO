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

    int splitPos = -1;

    if (posWindowsSeparator != -1) {

      splitPos = posWindowsSeparator + 2;
    }
    else if (posMacSeparator != -1) {

      splitPos = posMacSeparator + 1;
    }
    else if (posLinuxSeparator != -1) {

      splitPos = posLinuxSeparator + 1;
    }

    result[0] = splitPos != -1 ? lines.substring(0, splitPos) : "";
    result[1] = splitPos != -1 ? lines.substring(splitPos) : lines;

    return result;
  }

}