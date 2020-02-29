package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 * @author Olivier Liechti
 * @author Alexandre Piveteau
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  // Note : we use lookahead / lookbehind patterns to not consume the
  // return characters.
  //
  // Here's how this pattern is built :
  //
  // - (1) I try to match \r\n.
  // - (2) I try to match \n.
  // - (3) I try to match \r IFF it is not succeeded by \n.
  //
  // The additional condition on (3) is required to not have the (3)
  // condition match when it should actually be the (1) that will eventually
  // match later.
  private static final String REGEX = "(?<=(\r\n))|(?<=\n)|((?<=\r)(?!\n))";

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
    final String[] split = lines.split(REGEX, 2);
    if (split.length == 2) {
      return split;
    } else {
      // There's only one line, so we must conform to the spec.
      return new String[]{"", split[0]};
    }
  }

}
