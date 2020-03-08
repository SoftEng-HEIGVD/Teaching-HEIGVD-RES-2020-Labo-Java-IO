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
      String lineSeparator;
      if(lines.contains("\r\n")){
        lineSeparator = "\r\n";
      }else if(lines.contains("\r")){
        lineSeparator = "\r";
      }else if(lines.contains("\n")){
        lineSeparator = "\n";
      }else{
        // no line separator
        return new String[]{"", lines};
      }

      String[] linesArray = lines.split(lineSeparator);
      String rest ="";
      for (int i = 1; i < linesArray.length; i++) {
        rest += linesArray[i] + lineSeparator;
      }
      return new String[]{linesArray[0] + lineSeparator, rest};
  }

}
