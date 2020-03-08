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

    String linesSplitted[] = {};
    String result[] = {"",""};

    if (lines.contains("\r\n")) {                                // Windows
      linesSplitted = lines.split("(?<=\r\n)");

    } else if (lines.contains("\n")) {   // Unix
      linesSplitted = lines.split("(?<=\n)");

    } else if (lines.contains("\r")) {   // MacOS
      linesSplitted = lines.split("(?<=\r)");

    } else {                                                     // No separator
      result[1] = lines;
      return result;
    }

    result[0] = linesSplitted[0]; // add the first splitted line into thr array result

    if(linesSplitted.length > 1) { // add the other lines if they exist
      for (int i = 1; i < linesSplitted.length; i++) {
        result[1] += linesSplitted[i];
      }
    }

    return result;
  }
}
