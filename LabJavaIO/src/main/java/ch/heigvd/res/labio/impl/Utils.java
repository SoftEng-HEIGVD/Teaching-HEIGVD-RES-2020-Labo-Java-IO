package ch.heigvd.res.labio.impl;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {
  public static final String UNIX_SEPARATOR = "\n";
  public static final String WIN = "\r\n";
  public static final String MACOS_SEPARATOR = "\r";

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
    String[] nLine = {"", ""};
    //for unix (if the lines dosent have windows break lines so they wont have macos breaklines)
    if (lines.contains(UNIX_SEPARATOR) && !lines.contains(WIN)) {
      nLine[0] = lines.substring(0, lines.indexOf(UNIX_SEPARATOR) + UNIX_SEPARATOR.length());
      nLine[1] = lines.substring(lines.indexOf(UNIX_SEPARATOR) + UNIX_SEPARATOR.length());
      //Case of mac breaklines
    } else if (lines.contains(MACOS_SEPARATOR) && !lines.contains(WIN)) {
      nLine[0] = lines.substring(0, lines.indexOf(MACOS_SEPARATOR) + MACOS_SEPARATOR.length());
      nLine[1] = lines.substring(lines.indexOf(MACOS_SEPARATOR) + MACOS_SEPARATOR.length());
    }
    //Case of windows breaklines
    else if (lines.contains(WIN)) {
      nLine[0] = lines.substring(0, lines.indexOf(WIN) + WIN.length());
      nLine[1] = lines.substring(lines.indexOf(WIN) + WIN.length());
    } else {
      nLine[1] = lines;
    }

    return nLine;
  }

}
