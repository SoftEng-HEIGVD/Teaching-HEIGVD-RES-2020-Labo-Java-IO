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
    String[] res = new String[2];
    
    // The regex is split into three parts
    // (1) `?<=`      : tells `split()` to keep the delimiter
    // (2) `\r\n`     : windows return delimiter. We want \r AND \n
    // (3) `\n`       : unix return delimiter. We only want a single `\n`
    // (4) `\r(?!\n)` : macos9 return delimiter. We want a `\r` AND NO `\n` after it
    res = lines.split("(?<=(\r\n|\n|\r(?!\n)))", 2);

    // if there isn't a delimiter (i.e. the res array has a size of 1),
    //  move the res in the second position of the returned array
    return res.length == 1 ? new String[] {"", res[0]} : res ;
  }

}
