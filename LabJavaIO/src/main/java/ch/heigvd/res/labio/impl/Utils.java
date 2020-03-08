package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Olivier Liechti
 * @author Robin Müller
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  //Didn't find a way to use a positive lookbehind with variable-length \R (\R matches CR, LF and CRLF)
  private static final String LINE_REGEX = "(?<=(\r\n))|(?<=\n)|((?<=\r)(?!\n))";

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
    //Get first line with newline and the rest
    String[] content = lines.split(LINE_REGEX, 2);

    if (content.length == 2)
      return content;
    else
      return new String[]{"", content[0]};
  }

}
