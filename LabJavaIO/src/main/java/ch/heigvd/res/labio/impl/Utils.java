package ch.heigvd.res.labio.impl;

import java.lang.reflect.WildcardType;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  private static final String UNIX_SEPARATOR = "\n";
  private static final String MAC_OS9_SEPARATOR = "\r";
  private static final String WINDOWS_SEPARATOR = "\r\n";

  private static final Pattern UNIX_PATTERN = Pattern.compile(UNIX_SEPARATOR);
  private static final Pattern MAC_OS9_PATTERN = Pattern.compile(MAC_OS9_SEPARATOR);
  private static final Pattern WINDOWS_PATTERN = Pattern.compile(WINDOWS_SEPARATOR);


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
    String separator;
    // Find which seprator is used
    if(WINDOWS_PATTERN.matcher(lines).find()){
      separator = WINDOWS_SEPARATOR;
    } else if (UNIX_PATTERN.matcher(lines).find()){
      separator = UNIX_SEPARATOR;
    } else if(MAC_OS9_PATTERN.matcher(lines).find()){
      separator = MAC_OS9_SEPARATOR;
    } else { // No separator found
      return new String[]{"", lines};
    }

    String[] splitString = lines.split(separator, 2);
    // split method consume the separator, we need to add one manually
    splitString[0] += separator;
    return splitString;
  }

}
