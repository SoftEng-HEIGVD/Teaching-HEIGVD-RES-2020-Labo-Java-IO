package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti, modified by Rui Filipe Lopes Gouveia
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
    String[] nextLine = new String[2];

    for (int i = 0; i < lines.length(); ++i) {
      if ((i + 1) < lines.length() && lines.charAt(i) == '\r' && lines.charAt(i + 1) == '\n') {
        nextLine[0] = lines.substring(0, i + 2);
        nextLine[1] = lines.substring(i + 2);
      }
      else if (lines.charAt(i) == '\r' || lines.charAt(i) == '\n') {
        nextLine[0] = lines.substring(0, i + 1);
        nextLine[1] = lines.substring(i + 1);
        return nextLine;
      }
      else{
        nextLine[0] = "";
        nextLine[1] = lines;
      }
    }
    return nextLine;
  }

}
