package ch.heigvd.res.labio.impl;

import java.util.Arrays;
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

    // get the system-dependent line separator
    // https://www.geeksforgeeks.org/system-lineseparator-method-in-java-with-examples/
    int indexOfLineSep = lines.indexOf(System.lineSeparator());

    // we check if there is/isn't a line separator
    if (indexOfLineSep == -1)
      indexOfLineSep = 0;
    else
      indexOfLineSep += System.lineSeparator().length(); //to include the line separator in the substring

    return new String[] {
      lines.substring(0, indexOfLineSep),
      lines.substring(indexOfLineSep)
    };
  }

}
