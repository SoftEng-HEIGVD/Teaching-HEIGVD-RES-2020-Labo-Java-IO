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
  // carriage_return,  line_feed and end_of_line
  private static final String[] SEPARATORS = {"\r", "\n", "\r\n"};
  private static final int ARRAY_SIZE =   2;

  public static String[] getNextLine(String lines) {

    String[] nextline = new String[ARRAY_SIZE];

    // Abscence of separator
    nextline[0] = "";
    nextline[1] = lines;

    // for all the differnt separators
    for (String element : SEPARATORS) {
      if (lines.contains(element)) {
      nextline = lines.split(element, ARRAY_SIZE);
      nextline[0] += element;
      }
    }
    
    return nextline;
  }
}
