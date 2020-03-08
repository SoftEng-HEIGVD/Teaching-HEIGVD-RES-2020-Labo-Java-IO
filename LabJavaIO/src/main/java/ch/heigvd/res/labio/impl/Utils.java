package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
      String[] array = new String[2];
      Pattern p = Pattern.compile("\r\n|[\n|\r]");
      Matcher m = p.matcher(lines);
      if(m.find()) {
          array[0] = lines.substring(0, m.end());
          array[1] = lines.substring(m.end(), lines.length());
      }
      else{
          array[0] = "";
          array[1] = lines;
      }
      return array;
  }

}
