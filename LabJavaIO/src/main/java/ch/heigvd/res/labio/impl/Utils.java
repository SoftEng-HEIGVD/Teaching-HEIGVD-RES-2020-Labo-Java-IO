
package ch.heigvd.res.labio.impl;

import sun.nio.cs.ext.MacThai;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 * @author Quentin Saucy
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
    String[] arrayLines = new String[2];
    int nbrToRemove =1;
    int pos;
    int rEndLinePos = lines.indexOf('\r');
    int nEndlinePos = lines.indexOf('\n');
    pos =Math.max(rEndLinePos,nEndlinePos);
    arrayLines[0] = lines.substring(0,pos+1);
    arrayLines[1] = lines.substring(pos+1);
    return arrayLines;

  }

}
