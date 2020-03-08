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


    String[] ret = new String[2];
    ret[1] = "";

    int posWindowsEndLine = lines.indexOf("\r\n");
    int posLinuxEndLine = lines.indexOf("\n");
    int posMacEndLine = lines.indexOf("\r");


    if(posWindowsEndLine >= 0){

      ret[0] = lines.substring(0, posWindowsEndLine + 2);

      if(lines.length() > posWindowsEndLine + 2) {

        ret[1] = lines.substring(posWindowsEndLine + 2);
      }

    } else if(posLinuxEndLine >= 0) {

      ret[0] = lines.substring(0, posLinuxEndLine + 1);

      if(lines.length() > posLinuxEndLine + 1) {

        ret[1] = lines.substring(posLinuxEndLine + 1);
      }

    } else if(posMacEndLine >= 0) {

      ret[0] = lines.substring(0, posMacEndLine + 1);

      if(lines.length() > posMacEndLine + 1) {

        ret[1] = lines.substring(posMacEndLine + 1);
      }
    } else {

      ret[0] = "";
      ret[1] = lines;
    }

    return ret;
//    throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

}
