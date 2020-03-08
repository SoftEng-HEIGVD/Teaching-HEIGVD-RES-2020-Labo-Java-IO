package ch.heigvd.res.labio.impl;

import java.util.concurrent.RunnableScheduledFuture;
import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */



public class Utils {
  public static final char  TAB='\t';
  public static final char UNIX_SEP = '\n';/*unix seperator*/
  public static final char MAC_SEP = '\r';/*mac os seperator*/
  public static final String WIN_SEP = "\r\n";/*windows seperator*/


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
    String[] str = {"", ""};

    int l=WIN_SEP.length();
    /* case Windows*/
    if (lines.contains(WIN_SEP)) {

      str[0] = lines.substring(0, lines.indexOf(WIN_SEP) + l);
      str[1] = lines.substring(lines.indexOf(WIN_SEP) + l);
    }
    /* case MacOs*/
    else if (lines.contains(String.valueOf(MAC_SEP))) {

      str[0] = lines.substring(0, lines.indexOf(MAC_SEP) + 1);
      str[1] = lines.substring(lines.indexOf(MAC_SEP) + 1);

    }
    /* case Unix*/
    else if (lines.contains(String.valueOf(UNIX_SEP)) ) {

        str[0] = lines.substring(0, lines.indexOf(UNIX_SEP) + 1);
        str[1] = lines.substring(lines.indexOf(UNIX_SEP) + 1);
    }
    else {

      str[1] = lines;
    }

    return str;
  }

}
