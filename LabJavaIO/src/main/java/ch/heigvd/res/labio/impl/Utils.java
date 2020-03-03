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

    int idxWindows = lines.indexOf("\r\n");
    int idxUnix = lines.indexOf('\n');
    int idxMacOS = lines.indexOf('\r');

    // case Windows
    if(idxWindows != -1 && idxWindows < idxUnix && idxWindows <= idxMacOS)
      return lines.split("(?<=(\r\n))", 2);
    // case Unix or MacOS
    if(idxUnix != -1 || idxMacOS != -1)
      return lines.split("(?<=[\n\r])", 2);
    // default case
    return new String[] { "", lines };
  }

}
